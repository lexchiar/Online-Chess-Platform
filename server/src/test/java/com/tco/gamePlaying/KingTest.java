package com.tco.misc;

import com.tco.gameplaying.PieceColor;
import com.tco.gameplaying.PieceType;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.King;
import com.tco.gameplaying.ChessCoordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    private Piece[][] board;
    private King king;
    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;

    @BeforeEach
    public void setUp() {
        board = new Piece[8][8];
        king = new King();
        king.setColor(white);
        //king.move(new ChessCoordinate(4, 4), board, null); // Set initial location of the king
        king.setLocation(new ChessCoordinate(4, 4));
        board[3][4] = king; // Place the king at (4, 4)
    }

    // Test valid moves
    @Test
    @DisplayName("tthymes")
    public void testValidMove() {
        ChessCoordinate[] validMoves = {
            new ChessCoordinate(3, 3),
            new ChessCoordinate(3, 4),
            new ChessCoordinate(3, 5),
            new ChessCoordinate(4, 3),
            new ChessCoordinate(4, 5),
            new ChessCoordinate(5, 3),
            new ChessCoordinate(5, 4),
            new ChessCoordinate(5, 5)
        };

        for (ChessCoordinate move : validMoves) {
            assertTrue(king.isValidMove(move, board), "Move to " + move + " should be valid.");
        }
    }

    // Test invalid moves
    @Test
    @DisplayName("tthymes")
    public void testInvalidMove() {
        ChessCoordinate[] invalidMoves = {
            new ChessCoordinate(6, 6),
            new ChessCoordinate(4, 6),
            new ChessCoordinate(2, 2),
            new ChessCoordinate(4, 7)
        };

        for (ChessCoordinate move : invalidMoves) {
            assertFalse(king.isValidMove(move, board), "Move to " + move + " should be invalid.");
        }
    }

    // Test capturing of opponent piece
    @Test
    @DisplayName("tthymes")
    public void testMoveToOpponentPiece() {
        Piece opponentPiece = new Piece(); // Assuming there's a default Piece class
        opponentPiece.setColor(black);
        board[3][3] = opponentPiece;
        
        assertTrue(king.isValidMove(new ChessCoordinate(3, 3), board), "King should be able to capture opponent's piece");
    }

    // Test to ensure piece isn't moved to same location, a.k.a, turn skip.
    @Test
    @DisplayName("tthymes")
    public void testMoveToSamePosition() {
        ChessCoordinate samePosition = new ChessCoordinate(4, 4);
        assertFalse(king.isValidMove(samePosition, board), "Move to the same position should be invalid.");
    }    

    // Test moving to team occupied coordinate.
    @Disabled
    @Test
    @DisplayName("tthymes")
    public void testMoveToSameColorPiece() {
        Piece samePiece = new Piece();
        samePiece.setColor(white);
        board[4][3] = samePiece;
        
        assertFalse(king.isValidMove(new ChessCoordinate(3, 3), board), "King should not be able to move to square occupied by same color piece");
    }

    // Additional valid movement tests
    @Test
    @DisplayName("tthymes")
    public void testEdgeCases() {
        //king.move(new ChessCoordinate(0, 0), board, null); // Move king to corner
        king.setLocation(new ChessCoordinate(0, 0));
        board[0][0] = king;
        
        assertTrue(king.isValidMove(new ChessCoordinate(1, 1), board), "King should be able to move diagonally from corner");
        assertTrue(king.isValidMove(new ChessCoordinate(0, 1), board), "King should be able to move vertically from corner");
        assertTrue(king.isValidMove(new ChessCoordinate(1, 0), board), "King should be able to move horizontally from corner");
    }

    // Additional invalid movement tests
    @Test
    @DisplayName("tthymes")
    public void testOutOfBounds() {
        
        //king.move(new ChessCoordinate(0, 0), board, null); // Move king to corner
        king.setLocation(new ChessCoordinate(0, 0));
        board[0][0] = king;
        
        assertFalse(king.isValidMove(new ChessCoordinate(-1, 0), board), "King should not be able to move out of bounds");
        assertFalse(king.isValidMove(new ChessCoordinate(0, -1), board), "King should not be able to move out of bounds");
    }

    @Test
    @DisplayName("mholter")
    public void testKingPieceType(){
        King king1 = new King();
        King king2 = new King(white);
        ChessCoordinate testCoord = new ChessCoordinate(4, 0);
        King king3 = new King(white, testCoord);

        Piece king4 = new Piece();
        king4.setType(PieceType.KING);

        assertTrue(king1.getType() == PieceType.KING);
        assertTrue(king2.getType() == PieceType.KING);
        assertTrue(king3.getType() == PieceType.KING);
        assertTrue(king4.getType() == PieceType.KING);
    }
}
