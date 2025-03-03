package com.tco.database;

import java.sql.Connection;
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

public class CoordinatesDatabase {



    public static ChessCoordinate findCoordinatesWithId(int id) throws Exception {
			
			String sqlQuerry = "SELECT * FROM `coordinates` WHERE id = " + id + ";";
			try {
				// connect to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();

				
				ResultSet results = query.executeQuery(sqlQuerry);

				if (!results.isBeforeFirst() ) 
				{    
					return null;
				} 

				ChessCoordinate coordinates = null; 
			
				while (results.next())
				{
					Integer x = results.getInt(2);
					Integer y = results.getInt(3);	

                    coordinates = new ChessCoordinate(x,y);
				}

				return  coordinates;


			} catch (Exception e) {
				
				throw e;
			}
		}


		public static int findCoordinatesIdWithXandY(int x, int y) throws Exception {
			
			//SELECT id FROM `coordinates` WHERE (x = 2 AND y = 2);
			String sqlQuerry = "SELECT * FROM `coordinates` WHERE (x = " + x + " AND y = " + y + ");";
			try 
			{
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();
				
				ResultSet results = query.executeQuery(sqlQuerry);

				if (!results.isBeforeFirst() ) 
				{    
					return -1;
				} 

				int coordinatesID = -1; 
			
				while (results.next())
				{
					coordinatesID = results.getInt(1);
				}

				return  coordinatesID;


			} 
			catch (Exception e) 
			{
				
				throw e;
			}
		}

	}
