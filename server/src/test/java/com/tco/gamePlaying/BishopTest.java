package com.tco.gameplaying;

import com.tco.gameplaying.PieceColor;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.Bishop;
import com.tco.gameplaying.ChessCoordinate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;



public class BishopTest {
    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;

    @Test
    @DisplayName("ahuss12")
    public void testValidDiagonalMove() {
        Bishop bishop = new Bishop(white);
        bishop.setLocation(new ChessCoordinate(3, 3));
        Piece[][] board = new Piece[8][8];

        ChessCoordinate destination = new ChessCoordinate(5, 5);
        assertTrue(bishop.isValidMove(destination, board));
    }

    @Test
    @DisplayName("ahuss12")
    public void testInvalidNonDiagonalMove() {
        Bishop bishop = new Bishop(white);
        bishop.setLocation(new ChessCoordinate(3, 3));
        Piece[][] board = new Piece[8][8];

        ChessCoordinate destination = new ChessCoordinate(3, 5);
        assertFalse(bishop.isValidMove(destination, board));
    }

    @Test
    @DisplayName("ahuss12")
    public void testPathBlockedByPiece() {
        Bishop bishop = new Bishop(white);
        bishop.setLocation(new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        Piece blockingPiece = new Piece(black);
        blockingPiece.setLocation(new ChessCoordinate(1, 1));
        //Y coordinate inverted
        board[7 - 1][1] = blockingPiece; // Place a piece in the path

        ChessCoordinate destination = new ChessCoordinate(2, 2);
        assertFalse(bishop.isValidMove(destination, board));
    }

    @Test
    @DisplayName("ahuss12")
    public void testCaptureOpponentPiece() {
        Bishop bishop = new Bishop(white);
        bishop.setLocation(new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        Piece opponentPiece = new Piece(black);
        opponentPiece.setLocation(new ChessCoordinate(2, 2));
        board[2][2] = opponentPiece;

        ChessCoordinate destination = new ChessCoordinate(2, 2);
        assertTrue(bishop.isValidMove(destination, board));
    }

    @Test
    @DisplayName("ahuss12")
    public void testBlockedBySameTeamPiece() {
        Bishop bishop = new Bishop(white);
        bishop.setLocation(new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        Piece sameTeamPiece = new Piece(white);
        sameTeamPiece.setLocation(new ChessCoordinate(2, 2));
        //y coordinate inverted
        board[7 - 2][2] = sameTeamPiece;

        ChessCoordinate destination = new ChessCoordinate(2, 2);
        assertFalse(bishop.isValidMove(destination, board));
    }

    @Test
    @DisplayName("mholter")
    public void testBishopPieceType(){
        Bishop bishop1 = new Bishop();
        Bishop bishop2 = new Bishop(white);
        ChessCoordinate testCoord = new ChessCoordinate(0, 2);
        Bishop bishop3 = new Bishop(white, testCoord);

        Piece bishop4 = new Piece();
        bishop4.setType(PieceType.BISHOP);

        assertTrue(bishop1.getType() == PieceType.BISHOP);
        assertTrue(bishop2.getType() == PieceType.BISHOP);
        assertTrue(bishop3.getType() == PieceType.BISHOP);
        assertTrue(bishop4.getType() == PieceType.BISHOP);
    }
}
