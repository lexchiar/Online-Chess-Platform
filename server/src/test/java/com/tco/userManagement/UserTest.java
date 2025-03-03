package com.tco.gamemanagement;

import java.time.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

import com.tco.gameplaying.Match;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gameplaying.Move;
import com.tco.gameplaying.Piece;
import com.tco.gameplaying.ChessCoordinate;
import com.tco.gamemanagement.Invitation;
import com.tco.usermanagement.User;
import com.tco.usermanagement.UserCredential;


public class UserTest {

    

    @Test
    @DisplayName("vtoffoli")
    public void testVerifyCredentialCorrect() {
       
        User player = new User("Bob", "bob@gmail.com", "secretPassword"); 
        UserCredential credentials = player.getCredentials();
        assertTrue(credentials.verifyCredential("bob@gmail.com", "secretPassword"));
    }
    @Test
    @DisplayName("vtoffoli")
    public void testVerifyCredentialIncorrectEmail() {
       
        User player = new User("Bob", "bob@gmail.com", "secretPassword"); 
        UserCredential credentials = player.getCredentials();
        assertFalse(credentials.verifyCredential("tod@gmail.com", "secretPassword"));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testVerifyCredentialIncorrectPassword() {
       
        User player = new User("Bob", "bob@gmail.com", "secretPassword"); 
        UserCredential credentials = player.getCredentials();
        assertFalse(credentials.verifyCredential("bob@gmail.com", "wrongPassword"));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testCantInviteWithoutID() {
       
        User player = new User("Bob", "bob@gmail.com", "secretPassword"); 
        assertTrue( player.invitePlayer(20) == null);
    }

    @Test
    @DisplayName("vtoffoli")
    public void testUserMakeMove() {
       
        User player1 = new User("Bob", "bob@gmail.com", "secretPassword");
        User player2 = new User("Germaine", "germaine@gmail.com", "qwerty");
        UserCredential credentials1 = player1.getCredentials();
        UserCredential credentials2 = player2.getCredentials();

        credentials1.setId(1);
        credentials2.setId(2);

        Match match = new Match(1, 2);
        Piece[][] board = match.getBoard();
        Piece piece =  board[1][0];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 0);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 2);
        Move move = new Move(piece, startCoordinates, endCoordinates);
        
        assertTrue(1 == credentials1.getId(), "Id is incorect" + credentials1.getId());
        assertTrue(player1.makeMove(match, move));
    }

    @Test
    @DisplayName("vtoffoli")
    public void testUserMakeMoveWhenNotItTurn() {
       
        User player1 = new User("Bob", "bob@gmail.com", "secretPassword");
        User player2 = new User("Germaine", "germaine@gmail.com", "qwerty");
        UserCredential credentials1 = player1.getCredentials();
        UserCredential credentials2 = player2.getCredentials();

        credentials1.setId(1);
        credentials2.setId(2);

        Match match = new Match(1, 2);
        Piece[][] board = match.getBoard();
        Piece piece =  board[1][7];

        ChessCoordinate startCoordinates = new ChessCoordinate(1, 7);
        ChessCoordinate endCoordinates = new ChessCoordinate(2, 5);
        Move move = new Move(piece, startCoordinates, endCoordinates);
        
       assertFalse(player2.makeMove(match, move));
    }


    
}
