package com.tco.gameplaying;

import com.tco.gameplaying.PieceColor;
import com.tco.gameplaying.PieceType;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.Queen;
import com.tco.gameplaying.ChessCoordinate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;



public class QueenTest {

    private Queen queen;
    private Piece[][] board;
    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;

    @BeforeEach
    public void initEach()
    {
        queen = new Queen(white);
        queen.setLocation(new ChessCoordinate(4, 4));
        
        board = new Piece[8][8];
        board[3][4] = queen;
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidVerticalMoveUp() {
        
        ChessCoordinate destination = new ChessCoordinate(4, 6);
        assertTrue(queen.isValidMove(destination, board));
        //assertTrue(queen.isValidMove(destination, board, null));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidVerticalMoveDown() {
        
        ChessCoordinate destination = new ChessCoordinate(4, 2);
        assertTrue(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidHorizontalMoveLeft() {
        
        ChessCoordinate destination = new ChessCoordinate(2, 4);
        assertTrue(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidHorizontalMoveRight() {

        ChessCoordinate destination = new ChessCoordinate(6, 4);
        assertTrue(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidDiagonalMoveUpRight() {
        
        ChessCoordinate destination = new ChessCoordinate(6, 6);
        assertTrue(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidDiagonalMoveUpLeft() {
        
        ChessCoordinate destination = new ChessCoordinate(1, 7);
        assertTrue(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidDiagonalMoveDownLeft() {
        
        ChessCoordinate destination = new ChessCoordinate(0, 0);
        assertTrue(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testValidDiagonalMoveDownRight() {
        
        ChessCoordinate destination = new ChessCoordinate(7, 1);
        assertTrue(queen.isValidMove(destination, board));

    }


    @Test
    @DisplayName("vtoffoli")
    public void testInvalidMove() {
        
        ChessCoordinate destination = new ChessCoordinate(3, 6);
        assertFalse(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testOutOfBoundMove() {
        
        ChessCoordinate destination = new ChessCoordinate(8, -1);
        assertFalse(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testNoMove() {
        
        ChessCoordinate destination = new ChessCoordinate(4, 4);
        assertFalse(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testOccupiedBySameTeam() {
        
        Queen friend = new Queen(white);
        friend.setLocation(new ChessCoordinate(5, 5));
        board[2][5] = friend;

        ChessCoordinate destination = new ChessCoordinate(5, 5);
        assertFalse(queen.isValidMove(destination, board));

    }


    @Test
    @DisplayName("vtoffoli")
    public void testHorizontalMoveRightBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(5, 4));
        board[3][5] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(7, 4);
        assertFalse(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testHorizontalMoveLeftBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(2, 4));
        board[3][2] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(1, 4);
        assertFalse(queen.isValidMove(destination, board));

    }

    
    @Test
    @DisplayName("vtoffoli")
    public void testVerticalMoveUpBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(4, 6));
        board[1][4] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(4, 7);
        assertFalse(queen.isValidMove(destination, board));

    }

    @Test
    @DisplayName("vtoffoli")
    public void testVerticalMoveDownBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(4, 3));
        board[4][4] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(4, 1);
        assertFalse(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testDiagonalMoveUpRightBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(5, 5));
        board[2][5] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(7, 7);
        assertFalse(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testDiagonalMoveUpLeftBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(2, 6));
        board[1][2] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(1, 7);
        assertFalse(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testDiagonalMoveDownLeftBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(2, 2));
        board[5][2] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(1, 1);
        assertFalse(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testDiagonalMoveDownRightBlocked() {
        
        Queen secondPiece = new Queen(white);
        secondPiece.setLocation(new ChessCoordinate(6, 2));
        board[5][6] = secondPiece;

        ChessCoordinate destination = new ChessCoordinate(7, 1);
        assertFalse(queen.isValidMove(destination, board));
    }

    @Test
    @DisplayName("mholter")
    public void testQueenPieceType(){
        Queen queen1 = new Queen();
        Queen queen2 = new Queen(white);
        ChessCoordinate testCoord = new ChessCoordinate(3, 0);
        Queen queen3 = new Queen(white, testCoord);

        Piece queen4 = new Piece();
        queen4.setType(PieceType.QUEEN);

        assertTrue(queen1.getType() == PieceType.QUEEN);
        assertTrue(queen2.getType() == PieceType.QUEEN);
        assertTrue(queen3.getType() == PieceType.QUEEN);
        assertTrue(queen4.getType() == PieceType.QUEEN);
    }
}
