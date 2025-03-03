package com.tco.database;


import com.tco.database.MatchDatabase;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


public class DatabasePieceTest 
{
    @Test
    @DisplayName("vtoffoli")
    public void testCreatePieceWithoutAMtach()
    {      
        try 
        {
            Piece piece = new Queen(PieceColor.WHITE, new ChessCoordinate(0,0));

            PieceDatabase.saveNewPiece(piece, -1);

            assertTrue(false, "Can not create a piece with a invalid match id" );
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindPiecesOfInvalidMatch()
    {      
        try 
        {

            ArrayList<Piece> pieces = PieceDatabase.findPiecesWithMatchId(-1);

            assertTrue(pieces == null);
        }
        catch(Exception e) 
        {
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindPieceWithInvalidPieceID()
    {      
        try 
        {

            Piece piece = PieceDatabase.findPieceWithId(-1);

            assertTrue(piece == null);
        }
        catch(Exception e) 
        {
            assertTrue(false);
        }
    }
}
