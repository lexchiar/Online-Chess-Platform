package com.tco.misc;

import com.tco.gameplaying.ChessCoordinate;
import com.tco.gameplaying.PieceColor;
import com.tco.gameplaying.PieceType;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.Knight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;


public class KnightTest {
    private Piece[][] board;
    private Knight knight;
    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;
    
    @BeforeEach
    public void testSetup() {
        this.board = new Piece[8][8];
        this.knight = new Knight();
        knight.setColor(black);
        ChessCoordinate start = new ChessCoordinate(4, 3);
        //knight.move(start, board, null); // Include the board as the second parameter
        knight.setLocation(start);
        board[3][4] = knight;
    }


    @Test
    @DisplayName("mholter")
    public void validMoveTesting() {
        ChessCoordinate[] validMoves = {
            new ChessCoordinate(2, 4),
            new ChessCoordinate(2, 2),
            new ChessCoordinate(3, 5),
            new ChessCoordinate(3, 1),
            new ChessCoordinate(5, 5),
            new ChessCoordinate(5, 1),
            new ChessCoordinate(6, 4),
            new ChessCoordinate(6, 2)
        };

        for (ChessCoordinate move : validMoves) {
            assertTrue(knight.isValidMove(move, board), 
            "Move to (" + move.getX() + ", " + move.getY() + ") should be valid");
        }
    }

    @Test
    @DisplayName("mholter")
    public void invalidMoveTesting() {
        ChessCoordinate[] invalidMoves = {
            new ChessCoordinate(5, 4),
            new ChessCoordinate(4, 5),
            new ChessCoordinate(3, 2),
            new ChessCoordinate(3, 7),
            new ChessCoordinate(7, 7),
            new ChessCoordinate(5, 2),
            new ChessCoordinate(7, 4),
            new ChessCoordinate(6, 3)
        };

        for (ChessCoordinate move : invalidMoves) {
            assertFalse(knight.isValidMove(move, board), 
            "Move to (" + move.getX() + ", " + move.getY() + ") should be invalid");
        }
    }

    @Test
    @DisplayName("mholter")
    public void opponentOccupiedTest() {
        Piece oppPiece = new Piece(white);
        board[2][4] = oppPiece;
        ChessCoordinate oppSpot = new ChessCoordinate(2, 4);
        assertTrue(knight.isValidMove(oppSpot, board), "Knight should be able to capture piece in valid place");
    }

    @Test
    @DisplayName("mholter")
    public void teamOccupiedTest(){
        Piece teamPiece = new Piece(black);
        board[3][2] = teamPiece;
        ChessCoordinate teamSpot = new ChessCoordinate(2, 4);
        assertFalse(knight.isValidMove(teamSpot, board), "Knight should not be able to move onto spot with team piece");
    }

    @Test
    @DisplayName("mholter")
    public void nonMoveTest() {
        ChessCoordinate sameSpot = new ChessCoordinate(4, 3);
        assertFalse(knight.isValidMove(sameSpot, board), "Move to same spot should be invalid");
    }

    @Test
    @DisplayName("mholter")
    public void testKnightPieceType(){
        Knight knight1 = new Knight();
        Knight knight2 = new Knight(white);
        ChessCoordinate testCoord = new ChessCoordinate(1, 0);
        Knight knight3 = new Knight(white, testCoord);

        Piece knight4 = new Piece();
        knight4.setType(PieceType.KNIGHT);

        assertTrue(knight1.getType() == PieceType.KNIGHT);
        assertTrue(knight2.getType() == PieceType.KNIGHT);
        assertTrue(knight3.getType() == PieceType.KNIGHT);
        assertTrue(knight4.getType() == PieceType.KNIGHT);
    }
}
