package com.tco.gamemanagement;

import java.time.*;

import com.tco.gamemanagement.MatchStatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


public class MatchStatisticsTest {

    

    @Test
    @DisplayName("vtoffoli")
    public void testGetTimePlayedEnded() {
       
        MatchStatistics stat = new MatchStatistics();
        LocalDateTime now  = LocalDateTime.now();

        stat.setStartTime(now);
        stat.setEndTime(now.plusDays(1));

        Duration timePlayed = stat.getTimePlayed();
        assertTrue( timePlayed.toDays() == 1);
    }

    @Test
    @DisplayName("vtoffoli")
    public void testGetTimePlayedNotEnded() {
       
        MatchStatistics stat = new MatchStatistics();
        LocalDateTime now  = LocalDateTime.now();

        stat.setStartTime(now.minusDays(1));

        Duration timePlayed = stat.getTimePlayed();
        assertTrue( timePlayed.toDays() >= 1 && timePlayed.toDays() <2 );
    }

    @Test
    @DisplayName("vtoffoli")
    public void testGetTimePlayedWithSimpleConstructor() {
       
        MatchStatistics stat = new MatchStatistics();

        LocalDateTime now  = LocalDateTime.now();
        stat.setEndTime(now.plusDays(1));

        Duration timePlayed = stat.getTimePlayed();
        assertTrue(timePlayed.toDays() == 1);
    }

    @Test
    @DisplayName("vtoffoli")
    public void testGetTimePlayedWithAdvancedConstructor() {
       
        LocalDateTime now  = LocalDateTime.now();
        MatchStatistics stat = new MatchStatistics(now.minusDays(1));

        Duration timePlayed = stat.getTimePlayed();
        assertTrue( timePlayed.toDays() >= 1 && timePlayed.toDays() <2 );
    }
}
