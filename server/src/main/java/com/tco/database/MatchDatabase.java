package com.tco.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

import com.tco.database.*;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gamemanagement.MatchStatus;

import java.time.*;

public class MatchDatabase {

    public static Match findMatchWithId(int id) throws Exception {
			
			String sqlQuerry = "SELECT * FROM `match` WHERE id = " + id + ";";
			try {
				// connect to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();
				
				ResultSet results = query.executeQuery(sqlQuerry);

				// No match was found
				if (!results.isBeforeFirst() ) 
				{    
					return null;
				} 

				Match match = null; 
			
				while (results.next())
				{
					match = createMatch(results);
				}

				return  match;

			} catch (Exception e) {
				
				throw e;
			}
		}

		//Only save new match 
		public static int saveNewMatch(Match match) throws Exception {
			
			//Convert the date
			DateConverter converter = new DateConverter();
			String date = converter.LocalDateTimeToSQLString(match.getStatistics().getStartTime());
	
			//Create SQL querry
			String sqlQuerry = "INSERT INTO `match` (`player_1`, `player_2`, `start_time`, `status`) VALUES (" + match.getWhitePlayerID() + "," + match.getBlackPlayerID() + "," + date + ", 'PLAYING' );";
			
			PieceDatabase pieceDatabase = new PieceDatabase();
			
			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Creating match failed, no rows affected.");
				}
				
				int matchID = updateMatchIdAndSaveBoard(match, statement);

				return matchID;
				
			}
		}

		public static void updatingTurnCount(int matchID, int turnCount) throws Exception {
			
			//Create SQL querry
			String sqlQuerry = "UPDATE `match` SET `turn_count`= " + turnCount + " WHERE `id`= " + matchID + ";";

			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Updating the match failed, no rows affected.");
				}
		
			}
		}

		public static void endMatch(int matchID, int winnerID, int looserID, MatchStatus status) throws Exception {
			
			String now = DateConverter.LocalDateTimeToSQLString(LocalDateTime.now());
			String statusSTR = "'" + status.name() + "'";

			//Create SQL querry
			String sqlQuerry = "UPDATE `match` SET `end_time`= " + now + ", `status`= " + statusSTR + ", `winner_id`= " + winnerID + ", `looser_id`= " + looserID + " WHERE `id`= " + matchID + ";";

			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Updating the match failed, no rows affected.");
				}
		
			}
		}


		// ONLY USE FOR TESTING PURPOSE. OTHERWISE YOU WILL LOOSE THE USER STATISTICS
		public static void delete(int matchID) throws Exception {
			
			String sqlQuerry = "DELETE FROM  `match` where id=" + matchID + ";";

			try 
			(
				Connection connection =  DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sqlQuerry, Statement.RETURN_GENERATED_KEYS);
			) 
			{
				int affectedRows = statement.executeUpdate();
		
				if (affectedRows == 0) 
				{
					throw new Exception("Deleting the match failed, no rows affected.");
				}
		
			}
		}

		private static Match createMatch(ResultSet results) throws Exception
		{
			Integer match_id = results.getInt(1);
			Integer player_1 = results.getInt(2);
			Integer player_2 = results.getInt(3);
			Integer turn_count = results.getInt(4);
			LocalDateTime start_time = results.getObject(5,  LocalDateTime.class);
			LocalDateTime end_time = results.getObject(6,  LocalDateTime.class);
			String status_str = results.getString(7);

			Integer winner_id = results.getInt(8);
			Integer looser_id = results.getInt(9);

			MatchStatus status = MatchStatus.valueOf(status_str);
			MatchStatistics statistics = new MatchStatistics(start_time, end_time, status, winner_id, looser_id);

			//Retrieve Board
			PieceDatabase pieceDatabase = new PieceDatabase();
			ArrayList<Piece> pieces = pieceDatabase.findPiecesWithMatchId(match_id);

			//Retrieve Move
			MoveDatabase movesDatabase = new MoveDatabase();
			ArrayList<Move> moves = movesDatabase.findMovesWithMatchId(match_id);

			Match match = new Match(match_id, player_1, player_2, turn_count, statistics);

			match.populateBoard(pieces);
			match.populateHistory(moves);

			return match;
		}

		private static int updateMatchIdAndSaveBoard(Match match, PreparedStatement statement) throws Exception
		{
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) 
			{
				if (generatedKeys.next()) 
				{
					//Get the id
					long IDLong  = generatedKeys.getLong(1);
					int matchID = (int) IDLong;

					//Set the id to the match
					match.setId(matchID);

					saveBoard(match);

					return matchID;
				}
				else 
				{
					throw new Exception("Creating match failed, no ID obtained.");
				}
			
			}
		}

		private static void saveBoard(Match match) throws Exception
		{
			for (int i = 0; i < match.getBoard().length; i++)
			{
				for (int j = 0; j < match.getBoard()[i].length; j++)
				{
				   if(match.getBoard()[i][j] != null)
				   {
						//save piece
						PieceDatabase.saveNewPiece(match.getBoard()[i][j], match.getId());
				   }
				}
		   
		   }
		}

	}
