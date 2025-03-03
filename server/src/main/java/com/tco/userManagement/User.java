package com.tco.usermanagement;

import java.time.*; 
import java.util.*;
import com.tco.gameplaying.Match;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gameplaying.Move;
import com.tco.gamemanagement.Invitation;

public class User {

    private ArrayList<MatchStatistics> gameHistory;
    private UserStatistics statistics;
    private UserCredential credentials;


    //-- SHOULD NOT BE USED, REMOVE WHEN MATCH AND MATCHAPPLYMOVETEST WILL REPLACE IT ---
    public User(int id, String userNickname, String userEmailAddress, String userPassword ) 
    {}

    
    public User(String userNickname, String userEmailAddress, String userPassword ) 
    {
        this.credentials = new UserCredential(userNickname, userEmailAddress, userPassword);
        statistics = new UserStatistics();  
        gameHistory = new ArrayList<MatchStatistics>();
    }

    //Creation from database 
    public User(int userId, String userNickname, String userEmailAddress, String userPassword, LocalDate userAccountCreationDate, ArrayList<MatchStatistics> userGameHistory  ) 
    {
        this.credentials = new UserCredential(userId, userNickname, userEmailAddress, userPassword, userAccountCreationDate);
        gameHistory = userGameHistory;
        statistics = new UserStatistics();  
        updateStatistics();
    }


    public Invitation invitePlayer(int playerID) 
    {
        int id = this.credentials.getId();
        if(id != -1)
        {
            return new Invitation(id, playerID);
        }
        
        return null;
    }

    public void acceptInviation(Invitation invitation)
    {
        invitation.accept();
    }

    public void declineInviation(Invitation invitation)
    {
        invitation.decline();
    }

    public void leaveGame(Match game)
    {
        //Wait until match is implemented
        return;
    }
    

    public boolean makeMove(Match game, Move move) 
    {
        int id = this.credentials.getId();
        return game.applyMove(id, move);
    }

    public void updateStatistics()
    {
        statistics.update(gameHistory);
    }

    
    public ArrayList<MatchStatistics> seeGameHistory()
    {
        return gameHistory;
    }

    public UserStatistics seeStatistics()
    {
        return statistics;
    }

    public UserCredential getCredentials(){
        return this.credentials;
    }

 
    
}