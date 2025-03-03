package com.tco.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

import com.tco.database.*;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gamemanagement.MatchStatus;

import java.time.*;

public class MoveDatabase {



    public static ArrayList<Move> findMovesWithMatchId(int matchId) throws Exception {
			
			String sqlQuerry = "SELECT co1.x AS 'startX', co1.y AS 'starY', co2.x AS 'endX', co2.y AS 'endY', mov.piece_id AS 'piece_id' FROM move as mov INNER JOIN coordinates AS co1 ON co1.id = mov.start_location  INNER JOIN coordinates AS co2 ON co2.id = mov.end_location  WHERE mov.match_id = " + matchId + " ORDER BY `time` DESC;";
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

				ArrayList<Move> moves = new ArrayList<>();


				while (results.next())
				{

                    ChessCoordinate start_coordinates = new ChessCoordinate(results.getInt(1), results.getInt(2));
                    ChessCoordinate end_coordinates = new ChessCoordinate(results.getInt(3), results.getInt(4));

                    PieceDatabase pieceDatabase = new PieceDatabase();
					Piece piece = pieceDatabase.findPieceWithId(results.getInt(5));

                    Move move = new Move(piece, start_coordinates, end_coordinates);

                    moves.add(move);
				}

			
				return  moves;
			} catch (Exception e) {
				
				throw e;
			}
		}


		public static void saveNewMove(int startLocationID, int endLocationID, int pieceID, int matchId) throws Exception {
			
			String now = DateConverter.LocalDateTimeToSQLString(LocalDateTime.now());

			//Create SQL querry
			String sqlQuerry = "INSERT INTO `move` (`match_id`, `time`, `start_location`, `end_location`, `piece_id`) VALUES (" + matchId +", "+ now + ", "+ startLocationID + ", "+ endLocationID + ", "+ matchId + ");";
			
			
			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Creating move failed, no rows affected.");
				}
		
			}
		}

		


	}
