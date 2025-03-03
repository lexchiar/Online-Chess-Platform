package com.tco.gameplaying;

import com.tco.usermanagement.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class MatchStartTest {
    private Match testMatch;
    private User player1;
    private User player2;

    @BeforeEach
    public void initEach() {
        this.player1 = new User(1, "chesslover1", "chesslover1@outlook.com", "password");
        this.player2 = new User(2, "chesslover2", "chesslover2@outlook.com", "password");

        testMatch = new Match(1, 2);
    }

    @Test
    @DisplayName("mholter")
    public void testWhiteSideNotNull() {
        Piece[][] board = testMatch.getBoard();
        
        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 2; ++j) {
                assertNotNull(board[i][j]);
            }
        }
    }

    @Test
    @DisplayName("mholter")
    public void testBlackSideNotNull() {
        Piece[][] board = testMatch.getBoard();
        
        for(int i = 0; i < 8; ++i) {
            for (int j = 6; j < 8; ++j) {
                assertNotNull(board[i][j]);
            }
        }
    }

    @Test
    @DisplayName("mholter")
    public void testMiddleOfBoardNull() {
        Piece[][] board = testMatch.getBoard();
        
        for(int i = 0; i < 8; ++i) {
            for (int j = 2; j < 6; ++j) {
                assertNull(board[i][j]);
            }
        }
    }
    
    @Test
    @DisplayName("mholter")
    public void testWhiteIsWhite() {
        Piece[][] board = testMatch.getBoard();
        
        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 2; ++j) {
                Piece whitePiece = board[i][j];
                assertTrue(whitePiece.getColor() == PieceColor.WHITE, "Piece should be white");
            }
        }
    }

    @Test
    @DisplayName("mholter")
    public void testBlackIsBlack() {
        Piece[][] board = testMatch.getBoard();
        
        for(int i = 0; i < 8; ++i) {
            for (int j = 6; j < 8; ++j) {
                Piece blackPiece = board[i][j];
                assertTrue(blackPiece.getColor() == PieceColor.BLACK, "Piece should be black");
            }
        }
    }
    
    @Test
    @DisplayName("mholter")
    public void testRooks() {
        Piece[][] board = testMatch.getBoard();

        assertTrue(board[0][0] instanceof Rook, "Should be of type Rook");
        assertTrue(board[7][0] instanceof Rook, "Should be of type Rook");
        assertTrue(board[0][7] instanceof Rook, "Should be of type Rook");
        assertTrue(board[7][7] instanceof Rook, "Should be of type Rook");
    }

    @Test
    @DisplayName("mholter")
    public void testKnights() {
        Piece[][] board = testMatch.getBoard();

        assertTrue(board[1][0] instanceof Knight, "Should be of type Knight");
        assertTrue(board[6][0] instanceof Knight, "Should be of type Knight");
        assertTrue(board[1][7] instanceof Knight, "Should be of type Knight");
        assertTrue(board[6][7] instanceof Knight, "Should be of type Knight");
    }

    @Test
    @DisplayName("mholter")
    public void testBishops() {
        Piece[][] board = testMatch.getBoard();
        
        assertTrue(board[2][0] instanceof Bishop, "Should be of type Bishop");
        assertTrue(board[5][0] instanceof Bishop, "Should be of type Bishop");
        assertTrue(board[2][7] instanceof Bishop, "Should be of type Bishop");
        assertTrue(board[5][7] instanceof Bishop, "Should be of type Bishop");
    }

    @Test
    @DisplayName("mholter")
    public void testQueens() {
        Piece[][] board = testMatch.getBoard();
        
        assertTrue(board[3][0] instanceof Queen, "Should be of type Queen");
        assertTrue(board[3][7] instanceof Queen, "Should be of type Queen");
    }

    @Test
    @DisplayName("mholter")
    public void testKings() {
        Piece[][] board = testMatch.getBoard();
        
        assertTrue(board[4][0] instanceof King, "Should be of type King");
        assertTrue(board[4][7] instanceof King, "Should be of type King");
    }

    @Test
    @DisplayName("mholter")
    public void testPawns() {
        Piece[][] board = testMatch.getBoard();

        for(int i = 0; i < 8; ++i) {
            assertTrue(board[i][1] instanceof Pawn, "Should be of type Pawn");
            
            assertTrue(board[i][6] instanceof Pawn, "Should be of type Pawn");
        }
    }
}
