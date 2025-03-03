package com.tco.gameplaying;


import com.tco.gameplaying.*;
import com.tco.gamemanagement.GameHistory;
import com.tco.usermanagement.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

public class MatchApplyMoveTest 
{
    private Match  match;

    private User player1;
    private User player2;    

    private int player1_id;
    private int player2_id;    


    @BeforeEach
    public void initEach()
    {
        player1 = new User(1, "Bob", "bob@gmail.com", "secretPassword");
        player2 = new User(2, "Germaine", "germaine@gmail.com", "qwerty");

        player1_id = 1;
        player2_id = 2;

        match = new Match(1, 2);
      
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidFirstMove() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][0];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 0);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);

        Move move = new Move(piece, startCoordinates, endCoordinates);
        
       assertTrue(piece.getColor() == PieceColor.WHITE, "First move should be from a white piece. ");
       assertTrue(match.applyMove(player1_id, move));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testInvalidFirstMove() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][0];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 1);
        ChessCoordinate endCoordinates = new ChessCoordinate(4, 5);

        Move move = new Move(piece, startCoordinates, endCoordinates);

        assertFalse(match.applyMove(player1_id, move));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testInvalidFirstMoveWrongPlayer() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][0];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 1);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);

        Move move = new Move(piece, startCoordinates, endCoordinates);

        assertFalse(match.applyMove(player2_id, move));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testInvalidMoveIncorectPieceColor() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][7];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 7);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 5);

        Move move = new Move(piece, startCoordinates, endCoordinates);

        assertFalse(match.applyMove(player1_id, move));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidMultipleMove() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][0];
        ChessCoordinate startCoordinates = new ChessCoordinate(1, 0);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);
        Move move = new Move(piece, startCoordinates, endCoordinates);

        assertTrue(match.applyMove(player1_id, move));

        piece =  board[1][7];
        startCoordinates = new ChessCoordinate(1, 7);
        endCoordinates = new ChessCoordinate(2, 5);
        move = new Move(piece, startCoordinates, endCoordinates);

        assertTrue(match.applyMove(player2_id, move));

        piece =  board[2][2];
        startCoordinates = new ChessCoordinate(2, 2);
        endCoordinates = new ChessCoordinate(3, 4);
        move = new Move(piece, startCoordinates, endCoordinates);

        assertTrue(match.applyMove(player1_id, move));       
      
    }

    @Test
    @DisplayName("vtoffoli")
    public void testMoveAddedInGameHistory() {
        
        Piece[][] board = match.getBoard();

        Piece piece =  board[1][0];
        ChessCoordinate startCoordinates = new ChessCoordinate(1, 0);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);
        Move move = new Move(piece, startCoordinates, endCoordinates);

        match.applyMove(player1_id, move);

        GameHistory moveHistory =match.getGameHistory();

        assertTrue(moveHistory.getNumberOfMoves() == 1);
        assertTrue(moveHistory.getLastMove() == move);
      
    }

    @Test    
    @DisplayName("tthymes")    
    public void testWhiteStarts() {        
        Piece[][] board = match.getBoard();        
        Piece piece = board[1][0];        
        ChessCoordinate startCoordinates = new ChessCoordinate(1, 0);        
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);        
        Move move = new Move(piece, startCoordinates, endCoordinates);        
        assertTrue(piece.getColor() == PieceColor.WHITE, "First move should be from a white piece.");        
        assertTrue(match.applyMove(player1_id, move));    
    }    

    @Test    
    @DisplayName("tthymes")    
    public void testBlackCannotMoveFirst() {        
        Piece[][] board = match.getBoard();        
        Piece piece = board[6][0];        
        ChessCoordinate startCoordinates = new ChessCoordinate(6, 0);        
        ChessCoordinate endCoordinates = new ChessCoordinate(5, 2);        
        Move move = new Move(piece, startCoordinates, endCoordinates);        
        assertFalse(match.applyMove(player2_id, move), "Black should not be able to move first.");    
    }    

    @Test    
    @DisplayName("tthymes")    
    public void testBlackCannotMoveTwiceConsecutively() {        
        Piece[][] board = match.getBoard();        
        Piece piece1 = board[1][0];        
        ChessCoordinate startCoordinates1 = new ChessCoordinate(1, 0);        
        ChessCoordinate endCoordinates1 = new ChessCoordinate(2, 2);        
        Move move1 = new Move(piece1, startCoordinates1, endCoordinates1);        
        match.applyMove(player1_id, move1);        
        Piece piece2 = board[6][0];        
        ChessCoordinate startCoordinates2 = new ChessCoordinate(6, 0);        
        ChessCoordinate endCoordinates2 = new ChessCoordinate(5, 2);        
        Move move2 = new Move(piece2, startCoordinates2, endCoordinates2);        
        match.applyMove(player2_id, move2);        
        assertFalse(match.applyMove(player2_id, move2), "Black should not be able to move twice consecutively.");    
    }

}
