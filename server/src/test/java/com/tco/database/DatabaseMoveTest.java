package com.tco.database;


import com.tco.database.MatchDatabase;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


public class DatabaseMoveTest 
{
    @Test
    @DisplayName("vtoffoli")
    public void testFindMoveOfInvalidMatch()
    {      
        try 
        {

            ArrayList<Move> moves = MoveDatabase.findMovesWithMatchId(-1);

            assertTrue(moves == null);
        }
        catch(Exception e) 
        {
            assertTrue(false);
        }
    }
    public void testSaveMoveWithInvalidId()
    {      
        try 
        {

            MoveDatabase.saveNewMove(1,  2, -1, -1);

            assertTrue(false);
        }
        catch(Exception e) 
        {
            assertTrue(true);
        }
    }


}
