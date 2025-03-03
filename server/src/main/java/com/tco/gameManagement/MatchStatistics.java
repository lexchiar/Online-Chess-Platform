package com.tco.gamemanagement;

import com.tco.usermanagement.User;

import java.time.*;

public class MatchStatistics {

    private MatchStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // -1 == not set
    private int looser = -1;
    private int winner = -1;

    
    public MatchStatistics()
    {
        startTime = LocalDateTime.now();
        endTime = null;
        status = MatchStatus.PLAYING;
    }

    public MatchStatistics(LocalDateTime start)
    {
        startTime = start;
        endTime = null;
        status = MatchStatus.PLAYING;
    }

    //Load from database
    public MatchStatistics(LocalDateTime start, LocalDateTime end, MatchStatus status, int winner, int looser)
    {
        this.startTime = start;
        this.endTime = end;
        this.status = status;

        if(this.status == MatchStatus.PLAYING)
        {
            this.winner = -1;
            this.looser = -1;
        }
        else
        {
            this.winner = winner;
            this.looser = looser;
        }

    }


    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setStartTime (LocalDateTime start)
    {
        startTime = start;
    }

    public void setEndTime (LocalDateTime end)
    {
        endTime = end;
    }

     
    public void setStatus(MatchStatus newStatus)
    {
        status = newStatus;
    }
   
    public MatchStatus getStatus()
    {
        return status;
    }
    

    public Duration getTimePlayed()
    {
        Duration duration;

        if(endTime != null)
        {
            duration  = Duration.between(startTime, endTime);
        }
        else
        {
            //Still playing
            duration  = Duration.between(startTime, LocalDateTime.now());
        }

        return duration;
    }

    public int getWinner()
    {
        return winner;
    }

    public int getLooser()
    {
        return looser;
    }

    public void setWinner(int id)
    {
        winner = id;
    }
    public void setLooser(int id)
    {
        looser = id;
    }

    

    @Override
    public String toString(){

        if(endTime == null)
        {
            return "\n Start Time: " + this.startTime.toString() + 
           "\n End Time: " + "NULL" +
            "\n Status: " + this.status  +
            "\n Winner id: " + this.winner +
            "\n Looser id: " + this.looser  + "\n";
        }

        return "Start Time: " + this.startTime.toString() + 
           " End Time: " + this.endTime.toString() +
            " Status: " + this.status  +
            "\n Winner id: " + this.winner +
            "\n Looser id: " + this.looser + "\n";
    }


}
