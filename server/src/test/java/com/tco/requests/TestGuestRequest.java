package com.tco.requests;

import com.tco.gameplaying.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestGuestRequest {

    private Piece piece;
    private ChessCoordinate destination;
    private Piece[][] board;

    @BeforeEach
    public void setUp() {
        board = new Piece[8][8];

        piece = new Pawn(PieceColor.WHITE, new ChessCoordinate(1, 1));
        board[1][1] = piece;
        
        destination = new ChessCoordinate(1, 2);
    }

    @Test
    @DisplayName("lexchiar")
    public void testBuildResponseValidMove() {
        GuestRequest guestRequest = new GuestRequest(piece, destination, board);

        guestRequest.buildResponse();

        assertTrue(guestRequest.getValidity());
    }

    @Test
    @DisplayName("lexchiar")
    public void testBuildResponseInvalidMove() {
        destination = new ChessCoordinate(3, 3);
        
        GuestRequest guestRequest = new GuestRequest(piece, destination, board);

        guestRequest.buildResponse();

        assertFalse(guestRequest.getValidity());
    }

    @Test
    @DisplayName("lexchiar")
    public void testBuildResponsePieceTypeSwitch() {
        piece = new Pawn(PieceColor.BLACK, new ChessCoordinate(6, 1));
        GuestRequest guestRequest = new GuestRequest(piece, destination, board);

        guestRequest.buildResponse();

        assertTrue(guestRequest.getPiece() instanceof Pawn);
    }
}
