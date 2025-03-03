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


public class SavingTurn 
{
    
    public static void savingTurnInDatabase(int matchID, int turnCount, Move move) throws Exception
    {
        
        //STEP 1: Changing  the turn count
        MatchDatabase.updatingTurnCount(matchID, turnCount);

        //STEP 2: Find locationID of endLocation
        int endLocationID = CoordinatesDatabase.findCoordinatesIdWithXandY(move.getEndLocation().getX(), move.getEndLocation().getY());

        //STEP 3: Find locationID of startLocation
        int startLocationID = CoordinatesDatabase.findCoordinatesIdWithXandY(move.getStartLocation().getX(), move.getStartLocation().getY());

        //STEP 4: Removing pieces int the endLocation
        PieceDatabase.removingPieceAtLocation(endLocationID, matchID);

        //STEP 5: Updating the location of the piece
        PieceDatabase.movePieceAtLocation(endLocationID, move.getPiece().getId());

        //STEP 6: Adding the move is the database
        MoveDatabase.saveNewMove(startLocationID, endLocationID, move.getPiece().getId(), matchID);

    }
}
