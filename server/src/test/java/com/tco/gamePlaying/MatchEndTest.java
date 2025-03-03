package com.tco.gameplaying;

import com.tco.usermanagement.User;
import com.tco.gamemanagement.MatchStatus;
import com.tco.gamemanagement.MatchStatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class MatchEndTest {
    private Match testMatch;
    private MatchStatistics testStats;
    private Piece[][] board;
    private User player1;
    private User player2;

    @BeforeEach
    public void setup() {
        this.player1 = new User(1, "chesslover1", "chesslover1@outlook.com", "password");
        this.player2 = new User(2, "chesslover2", "chesslover2@outlook.com", "password");

        this.testMatch = new Match(1, 2);
        this.board = testMatch.getBoard();
        this.testStats = testMatch.getStatistics();
    }

    @Test
    @DisplayName("mholter")
    public void testNoMovesEnd() {
        testMatch.matchEnd();

        int winningId = testStats.getWinner();
        int losingId = testStats.getLooser();

        assertTrue(winningId == 1, "Winner should be player 1");
        assertTrue(losingId == 2, "Loser should be player 2");
    }

    @Test
    @DisplayName("mholter")
    public void testOddMovesEnd() {
        //make one move
        Piece piece = board[1][0];        
        ChessCoordinate start = new ChessCoordinate(0, 1);        
        ChessCoordinate end = new ChessCoordinate(0, 2); 
        Move move = new Move(piece, start, end);

        testMatch.applyMove(1, move);
        testMatch.matchEnd();

        int winningId = testStats.getWinner();
        int losingId = testStats.getLooser();

        assertTrue(winningId == 1, "Winner should be player 1");
        assertTrue(losingId == 2, "Loser should be player 2");
    }
    @Test
    @DisplayName("mholter")
    public void testEvenMovesEnd() {
        Piece piece1 = board[0][1];        
        ChessCoordinate start1 = new ChessCoordinate(0, 1);        
        ChessCoordinate end1 = new ChessCoordinate(0, 2); 
        Move move1 = new Move(piece1, start1, end1);
        
        Piece piece2 = board[0][6];        
        ChessCoordinate start2 = new ChessCoordinate(0, 6);        
        ChessCoordinate end2 = new ChessCoordinate(0, 5);
        Move move2 = new Move(piece2, start2, end2);

        testMatch.applyMove(1, move1);
        testMatch.applyMove(2, move2);
        testMatch.matchEnd();

        int winningId = testStats.getWinner();
        int losingId = testStats.getLooser();

        assertTrue(winningId == 1, "Winner should be player 1");
        assertTrue(losingId == 2, "Loser should be player 2");
    }
    @Test
    @DisplayName("mholter")
    public void testEndStatusUpdate() {
        testMatch.matchEnd();
        assertTrue(testStats.getStatus() == MatchStatus.FINISHED, "Match status should be Finished");
    }
    @Test
    @DisplayName("mholter")
    public void testEndTimeUpdate() {
        testMatch.matchEnd();
        assertNotNull(testStats.getEndTime(), "Match should have a set end time");
    }
}