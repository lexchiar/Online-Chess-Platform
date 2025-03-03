package com.tco.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

import com.tco.database.Credential;
import com.tco.database.PieceCreator;

import com.tco.gameplaying.*;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gamemanagement.MatchStatus;


import java.time.*;

public class PieceDatabase {



    public static ArrayList<Piece> findPiecesWithMatchId(int matchId) throws Exception {
			
			String sqlQuerry = "SELECT piece.id, piece.type, piece.color, coordinates.x, coordinates.y FROM piece INNER JOIN coordinates ON piece.location = coordinates.id WHERE piece.match_id = " + matchId + ";";
			
            try {

				// Connection to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();

				//Execute Querry and Fetch Data
				ResultSet results = query.executeQuery(sqlQuerry);

				// No Result was found
				if (!results.isBeforeFirst() ) 
				{    
					return null;
				} 

				ArrayList<Piece> pieces = createPiecesFromMatchID(results);
			
				return  pieces;

			} catch (Exception e) 
			{
				throw e;
			}
		}


        public static Piece findPieceWithId(int id) throws Exception {
			
			String sqlQuerry = "SELECT * FROM piece WHERE id = " + id + ";";
			
            try {

				// Connection to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();

				//Execute Querry and Fetch Data
				ResultSet results = query.executeQuery(sqlQuerry);

				// No Result was found
				if (!results.isBeforeFirst() ) 
				{    
					return null;
				} 

				Piece piece = null;


				while (results.next())
				{
					piece = create1PieceFromPieceID(results);
                    
				}

				return  piece;

			} catch (Exception e) {
				
				throw e;
			}
		}

		//Only save new piece 
		public static int saveNewPiece(Piece piece, int matchId) throws Exception {
			
			//Find locationId
			CoordinatesDatabase database = new CoordinatesDatabase();
			int locationId = database.findCoordinatesIdWithXandY(piece.getLocation().getX(),piece.getLocation().getY());

			//Type and color
			String type = "'" + piece.getType().name() + "'";
			String color = "'" + piece.getColor().name() + "'";

			//Create SQL querry
			String sqlQuerry = "INSERT INTO`piece` ( `location`, `type`, `match_id`, `color`) VALUES ( " + locationId + ", " + type + ", " + matchId + ", " + color + ");";
			
			
			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Creating piece failed, no rows affected.");
				}
		
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) 
				{
					if (generatedKeys.next()) 
					{
						//Get the id
						long IDLong  = generatedKeys.getLong(1);
						int pieceID = (int) IDLong;

						//Set ID
						piece.setId(pieceID) ;

						return pieceID;
					}
					else 
					{
						throw new Exception("Creating piece failed, no ID obtained.");
					}
				
				}
			}
			catch (Exception e) {
				
				throw e;
			}
		}


		public static void removingPieceAtLocation(int locationId, int matchID) throws Exception {
			
			//Create SQL querry
			String sqlQuerry = "UPDATE `piece` SET `location`= Null WHERE (`location`= " + locationId +  " AND `match_id` = " + matchID + ");";
			
			

			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
				
			}
		}

		public static void movePieceAtLocation(int locationId, int pieceID) throws Exception {
			
			//Create SQL querry
			String sqlQuerry = "UPDATE `piece` SET `location`= " + locationId + " WHERE id = " + pieceID + ";";
			
			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Moving piece failed, no rows affected.");
				}
		
			}
		}

		private static ArrayList<Piece> createPiecesFromMatchID( ResultSet results) throws Exception 
		{
			ArrayList<Piece> pieces = new ArrayList<>();
			while (results.next())
			{
				Piece piece = create1PieceFromMatchID(results);
				pieces.add(piece);
			}

			return pieces;
		}

		private static Piece create1PieceFromMatchID(ResultSet results) throws Exception 
		{
			
			Integer id = results.getInt(1);
			String type_str = results.getString(2);
			String color_str = results.getString(3);
			Integer x = results.getInt(4);
			Integer y = results.getInt(5);

			PieceColor color = PieceColor.valueOf(color_str);
			PieceType type = PieceType.valueOf(type_str);

			PieceCreator creator = new PieceCreator();

			ChessCoordinate coo = new ChessCoordinate(x, y);

			Piece piece = creator.createPiece(id, color, type, coo);
			
			return piece;
		}

		private static Piece create1PieceFromPieceID(ResultSet results) throws Exception 
		{
			Piece piece = null;

			Integer piece_id = results.getInt(1);
            Integer location = results.getInt(2);
			String type_str = results.getString(3);
            String color_str = results.getString(5);

            PieceColor color = PieceColor.valueOf(color_str);
            PieceType type = PieceType.valueOf(type_str);

            PieceCreator creator = new PieceCreator();

            if(location == 0)
            {
                piece = creator.createPiece(piece_id, color, type, null);
            }
            else
            {
                CoordinatesDatabase coordinatesDatabase = new CoordinatesDatabase();
                ChessCoordinate coordinates = coordinatesDatabase.findCoordinatesWithId(location);

                piece = creator.createPiece(piece_id, color, type, coordinates);
            }
			
			return piece;
		}
	}
