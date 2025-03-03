package com.tco.database;


import com.tco.database.MatchDatabase;
import com.tco.gameplaying.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


public class DatabaseCoordinatesTest {

   

    @Test
    @DisplayName("vtoffoli")
    public void testFindCoordinateId() 
    {
        CoordinatesDatabase database = new CoordinatesDatabase();
             
        try 
        {
            int id = database.findCoordinatesIdWithXandY(2,3);

            assertTrue(id != -1, "Coordinates Id should not be -1");
            assertTrue(id == 20, "Coordinates Id for (2,3) should be 20");
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindCoordinateIdWithInvalidCoordinates() 
    {
        CoordinatesDatabase database = new CoordinatesDatabase();
             
        try 
        {
            int id = database.findCoordinatesIdWithXandY(-1,-1);

            assertTrue(id == -1);
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindCoordinatesWithId() 
    {
        CoordinatesDatabase database = new CoordinatesDatabase();
             
        try 
        {
            ChessCoordinate coordinates = database.findCoordinatesWithId(20);

            assertTrue(coordinates != null, "Coordinates should not be null");
            assertTrue(coordinates.getX() == 2, "x should be 2");
            assertTrue(coordinates.getY() == 3, "y should be 3");
        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
    }

    @Test
    @DisplayName("vtoffoli")
    public void testFindCoordinatesWithInvalidId() 
    {
        CoordinatesDatabase database = new CoordinatesDatabase();
             
        try 
        {
            ChessCoordinate coordinates = database.findCoordinatesWithId(-1);
            assertTrue(coordinates == null);

        }
        catch(Exception e) 
        {
            assertTrue(false, "Error: " + e.toString());
        }
    }


}