package com.tco.database;


import com.tco.database.MatchDatabase;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


public class DatabaseMatchTest 
{
    private int matchID = -1;


    @Test
    @DisplayName("vtoffoli")
    public void testMatchDatabase() 
    {
        //Create a match
        createMatch();

        //Check if the match is correctly saved and retrieved
        Match match = retrieveMatch();
        verifyThatMatchIsNew(match);

        //Move Piece
        Piece piece = match.getBoard()[0][0];

        Move move = new Move(piece, new ChessCoordinate(0,0), new ChessCoordinate(4,4) );

        saveTurn(1, move);

        //Retreive match
        match = retrieveMatch();
        verifyMatchAfterFirstMove(match);

        //End the game
        endMatch();

        //Retreive match
        match = retrieveMatch();
        verifyMatchAfterEnding(match);

        //Delete from datatbase
        deleteMatch();

       
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindMatchThatDoesNotExist() 
    {
             
        try 
        {
            Match match = MatchDatabase.findMatchWithId(matchID);
            assertTrue(match == null, "Match should be null" );
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testCreateMatchWithInvalidData() 
    {
             
        try 
        {
            Match match = new Match(-1,-1);
            MatchDatabase.saveNewMatch(match);

            assertTrue(false);
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testUpdateMatchTurnCountWithInvalidID() 
    {
             
        try 
        {
            MatchDatabase.updatingTurnCount(-1,  1);

            assertTrue(false);
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testEndMatchWithInvalidID() 
    {
             
        try 
        {
            MatchDatabase.endMatch(-1, -1, -1, MatchStatus.FINISHED);

            assertTrue(false);
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testDeleteWithInvalidID() 
    {
             
        try 
        {
            MatchDatabase.delete(-1);

            assertTrue(false);
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }

    public void createMatch() 
    {
        
        Match match  = new Match(6,6);
             
        try 
        {
            this.matchID = MatchDatabase.saveNewMatch(match);
            
            assertTrue(matchID != -1, "Id should not be -1, it should have it the id of the new match" );
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
       
    }

    public Match retrieveMatch()
    {
    
        try 
        {
            Match match = MatchDatabase.findMatchWithId(this.matchID);
        
            return match;

        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }

        return null;
    }

    public void saveTurn(int turnCount, Move move) 
    {
            
        try 
        {
            SavingTurn.savingTurnInDatabase(this.matchID, turnCount, move);
             
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
       
    }

    public void endMatch() 
    {
            
        try 
        {
            MatchDatabase.endMatch(this.matchID, 6, 6, MatchStatus.FINISHED);
             
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
       
    }

    public void deleteMatch()
    {
        try
        {
            MatchDatabase.delete(this.matchID);
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
        
    }

    public void verifyThatMatchIsNew(Match match) 
    {
        assertTrue(this.matchID != -1, "matchID: " + matchID);
        
        Piece[][] board = match.getBoard();

        assertTrue(match != null, "Match is null" );

        assertTrue(match.getStatistics() != null, "Stats is null" );

        assertTrue(match.getBoard() != null, "Board is null" );
            
        assertTrue(match.getBoard()[0][0] != null, "It should have a piece object" );

        assertTrue(match.getGameHistory() != null, "History is null" );
            
        assertTrue(match.getGameHistory().getNumberOfMoves() == 0, "It should have 0 moves" );

            
    }

    public void verifyMatchAfterFirstMove(Match match) 
    {
        assertTrue(this.matchID != -1, "matchID: " + matchID);
        
        Piece[][] board = match.getBoard();

        assertTrue(match != null, "Match is null");

        assertTrue(match.getStatistics() != null, "Stats is null");

        assertTrue(match.getBoard() != null, "Board is null");
            
        assertTrue(match.getBoard()[0][0] == null, "It should not have a piece object");

        assertTrue(match.getBoard()[4][4] != null, "It should have a piece object");

        assertTrue(match.getGameHistory() != null, "History is null");
            
        assertTrue(match.getGameHistory().getNumberOfMoves() == 1, "It should have 1 moves");        
    }

    public void verifyMatchAfterEnding(Match match) 
    {
        assertTrue(this.matchID != -1, "matchID: " + matchID);
        
        Piece[][] board = match.getBoard();

        assertTrue(match != null, "Match is null");

        assertTrue(match.getStatistics() != null, "Stats is null");

        assertTrue(match.getBoard() != null, "Board is null");
            
        assertTrue(match.getStatistics().getEndTime() != null, "End time is null");

        assertTrue(match.getStatistics().getWinner() != -1, "Winner is not set");

        assertTrue(match.getStatistics().getLooser() != -1, "Looser is not set");

        assertTrue(match.getStatistics().getStatus() == MatchStatus.FINISHED, "Status was not changed" );
    }
}
