package com.tco.usermanagement;

import java.util.*;
import com.tco.gamemanagement.MatchStatistics;

public class UserStatistics {

    private int numberOfGamePlayed, numberOfLost, numberOfWin;
    private int numberOfGameAbandoned, numberOfTie;
    
    public UserStatistics()
    {
        numberOfGamePlayed = 0;
        numberOfLost = 0;
        numberOfWin = 0;
        numberOfGameAbandoned = 0;
        numberOfTie = 0;
    }


    public void update(ArrayList<MatchStatistics> MatchStatistics)
    {
        return;
    }

    public int getNumberOfGamePlayed()
    {
        return numberOfGamePlayed;
    }
    public int getNumberOfGameLost()
    {
        return numberOfLost;
    }
    public int getNumberOfGameWon()
    {
        return numberOfWin;
    }
    public int getNumberOfGameTie()
    {
        return numberOfTie;
    }
    public int getNumberOfGameAbandonned()
    {
        return numberOfGameAbandoned;
    }
    public float getWinLostRatio()
    {
        return numberOfWin / numberOfLost;
    }


}
