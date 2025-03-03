package com.tco.gameplaying;

import com.tco.gameplaying.PieceColor;
import com.tco.gameplaying.PieceType;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.Rook;
import com.tco.gameplaying.ChessCoordinate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    @Test
    @DisplayName("ahuss12")
    public void testVerticalMoveUp_NoObstruction() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        board[0][0] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 5);

        // Assert
        assertTrue(rook.isValidMove(target, board),
                "Rook should be able to move vertically upwards with no obstructions.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testVerticalMoveDown_NoObstruction() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 7));
        Piece[][] board = new Piece[8][8];
        board[0][7] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 2);

        // Assert
        assertTrue(rook.isValidMove(target, board),
                "Rook should be able to move vertically downwards with no obstructions.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testHorizontalMoveRight_NoObstruction() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        board[0][0] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(5, 0);

        // Assert
        assertTrue(rook.isValidMove(target, board),
                "Rook should be able to move horizontally right with no obstructions.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testHorizontalMoveLeft_NoObstruction() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(7, 0));
        Piece[][] board = new Piece[8][8];
        board[7][0] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(2, 0);

        // Assert
        assertTrue(rook.isValidMove(target, board),
                "Rook should be able to move horizontally left with no obstructions.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testInvalidDiagonalMove() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        board[0][0] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(5, 5);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to move diagonally.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testInvalidLShapedMove() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];
        board[0][0] = rook;

        // Act
        ChessCoordinate target = new ChessCoordinate(1, 2);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to move in an L-shape.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testPathBlockedByOwnPiece() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece ownPiece = new Pawn(PieceColor.WHITE, new ChessCoordinate(0, 3));
        Piece[][] board = new Piece[8][8];
        // Place pieces with inverted y-coordinates
        board[7 - 0][0] = rook;
        board[7 - 3][0] = ownPiece;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 5);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to move through its own piece.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testPathBlockedByOpponentPiece() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece opponentPiece = new Pawn(PieceColor.BLACK, new ChessCoordinate(0, 3));
        Piece[][] board = new Piece[8][8];
        // Place pieces with inverted y-coordinates
        board[7 - 0][0] = rook;
        board[7 - 3][0] = opponentPiece;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 5);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to move through opponent's piece.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testCaptureOpponentPiece() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece opponentPiece = new Pawn(PieceColor.BLACK, new ChessCoordinate(0, 3));
        Piece[][] board = new Piece[8][8];
        board[0][0] = rook;
        board[0][3] = opponentPiece;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 3);

        // Assert
        assertTrue(rook.isValidMove(target, board),
                "Rook should be able to capture opponent's piece.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testCannotCaptureOwnPiece() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece ownPiece = new Pawn(PieceColor.WHITE, new ChessCoordinate(0, 3));
        Piece[][] board = new Piece[8][8];
        // Place pieces with inverted y-coordinates
        board[7 - 0][0] = rook;
        board[7 - 3][0] = ownPiece;

        // Act
        ChessCoordinate target = new ChessCoordinate(0, 3);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to capture its own piece.");
    }

    @Test
    @DisplayName("ahuss12")
    public void testMoveOffBoard() {
        // Arrange
        Rook rook = new Rook(PieceColor.WHITE, new ChessCoordinate(0, 0));
        Piece[][] board = new Piece[8][8];

        // Act
        ChessCoordinate target = new ChessCoordinate(-1, 0);

        // Assert
        assertFalse(rook.isValidMove(target, board),
                "Rook should not be able to move off the board.");
    }

    @Test
    @DisplayName("mholter")
    public void testRookPieceType(){
        Rook rook1 = new Rook();
        Rook rook2 = new Rook(PieceColor.WHITE);
        ChessCoordinate testCoord = new ChessCoordinate(0, 0);
        Rook rook3 = new Rook(PieceColor.WHITE, testCoord);

        Piece rook4 = new Piece();
        rook4.setType(PieceType.ROOK);

        assertTrue(rook1.getType() == PieceType.ROOK);
        assertTrue(rook2.getType() == PieceType.ROOK);
        assertTrue(rook3.getType() == PieceType.ROOK);
        assertTrue(rook4.getType() == PieceType.ROOK);
    }
}
