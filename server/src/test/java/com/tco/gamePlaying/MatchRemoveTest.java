package com.tco.gameplaying;

import com.tco.usermanagement.User;
import com.tco.gamemanagement.MatchStatus;
import com.tco.gamemanagement.MatchStatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class MatchRemoveTest {
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
    public void testRemovePlayer1() {
        testMatch.removePlayer(1);

        int winningId = testStats.getWinner();
        int losingId = testStats.getLooser();

        assertTrue(winningId == 2, "Winner should be player 2");
        assertTrue(losingId == 1, "Loser should be player 1");
    }
    @Test
    @DisplayName("mholter")
    public void testRemovePlayer2() {
        testMatch.removePlayer(2);

        int winningId = testStats.getWinner();
        int losingId = testStats.getLooser();

        assertTrue(winningId == 1, "Winner should be player 1");
        assertTrue(losingId == 2, "Loser should be player 2");
    }
    @Test
    @DisplayName("mholter")
    public void testMatchStatusIsAbandoned() {
        testMatch.removePlayer(1);
        assertTrue(testStats.getStatus() == MatchStatus.ABANDONED, "Match status should be Abandoned");
    }
    @Test
    @DisplayName("mholter")
    public void testMatchHasEndTimeAfterRemoval() {
        testMatch.removePlayer(1);
        assertNotNull(testStats.getEndTime(), "Match should have set end time");
    }
}
