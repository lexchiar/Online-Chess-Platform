package com.tco.gameplaying;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;


class PawnTest {

    private Piece[][] board;
    private Pawn whitePawn;
    private Pawn blackPawn;
    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;

    @BeforeEach
    void setUp() {
        board = new Piece[8][8];

        //create a white pawn at (6, 3)
        whitePawn = new Pawn(white);
        board[6][3] = whitePawn; 

        //create a black pawn at (1, 4)
        blackPawn = new Pawn(black);
        board[1][4] = blackPawn;
    }

    @Test
    @DisplayName("lexchiar")
    void testValidMoves() {
        //move pawn one square forward
        ChessCoordinate startWhite = new ChessCoordinate(1, 1); 
        ChessCoordinate endWhite = new ChessCoordinate(1, 2);  

        whitePawn.location = startWhite;
        assertTrue(whitePawn.isValidMove(endWhite, board),
            "White pawn should be able to move one square forward");

        //move pawn two squares forward from starting position
        ChessCoordinate twoSquareMove = new ChessCoordinate(1, 3); 
        whitePawn.location = startWhite;
        whitePawn.setHasMoved(false);
        assertTrue(whitePawn.isValidMove(twoSquareMove, board),
            "White pawn should be able to move two squares forward on its first move");
    }

    @Test
    @DisplayName("lexchiar")
    void testInvalidMoves() {
        //try to move three squares
        ChessCoordinate invalidMove = new ChessCoordinate(3, 3); 
        ChessCoordinate startWhite = new ChessCoordinate(3, 6);  
        whitePawn.location = startWhite;
        assertFalse(whitePawn.isValidMove(invalidMove, board),
            "White pawn should not be able to move three squares forward");

        //add piece to block move
        board[5][3] = new Pawn();  
        ChessCoordinate blockedMove = new ChessCoordinate(3, 5);
        assertFalse(whitePawn.isValidMove(blockedMove, board),
            "White pawn should not be able to move forward to an occupied square");
    }

    @Test
    @DisplayName("lexchiar")
    void testDiagonalCapture() {
        ChessCoordinate startWhite = new ChessCoordinate(3, 3);
        whitePawn = new Pawn(white, startWhite);
        board[4][3] = whitePawn;  // Note: board coordinates are inverted on y-axis (7-y)

        // Place black pawn at (4,4)
        ChessCoordinate blackPawnPos = new ChessCoordinate(4, 4);
        blackPawn = new Pawn(black, blackPawnPos);
        board[3][4] = blackPawn;  // Note: board coordinates are inverted on y-axis (7-y)

        ChessCoordinate captureMove = new ChessCoordinate(4, 4);
        assertTrue(whitePawn.isValidMove(captureMove, board),
            "White pawn should be able to capture the black pawn diagonally");
    }

    //test for invalid diagonal movement - must capture a piece to move diagonally
    @Test
    @DisplayName("lexchiar")
    void testInvalidDiagonalCapture() {
        ChessCoordinate startWhite = new ChessCoordinate(3, 6);
        ChessCoordinate invalidCapture = new ChessCoordinate(4, 5);
        whitePawn.location = startWhite;

        assertFalse(whitePawn.isValidMove(invalidCapture, board),
            "White pawn should not be able to move diagonally if there is no piece to capture");
    }

    //test if each piece can promote
    @Test
    @DisplayName("lexchiar")
    void testPromotion() {
        ChessCoordinate promotionPos = new ChessCoordinate(3, 0); 
        assertTrue(whitePawn.canPromote(promotionPos),
            "White pawn should be able to promote when it reaches the last rank");

        ChessCoordinate promotionBlackPos = new ChessCoordinate(4, 7); 
        blackPawn.location = new ChessCoordinate(4, 1); 
        assertTrue(blackPawn.canPromote(promotionBlackPos),
            "Black pawn should be able to promote when it reaches the last rank");
    }

    @Test
    @DisplayName("mholter")
    public void testPawnPieceType(){
        Pawn pawn1 = new Pawn();
        Pawn pawn2 = new Pawn(white);
        ChessCoordinate testCoord = new ChessCoordinate(0, 1);
        Pawn pawn3 = new Pawn(white, testCoord);

        Piece pawn4 = new Piece();
        pawn4.setType(PieceType.PAWN);

        assertTrue(pawn1.getType() == PieceType.PAWN);
        assertTrue(pawn2.getType() == PieceType.PAWN);
        assertTrue(pawn3.getType() == PieceType.PAWN);
        assertTrue(pawn4.getType() == PieceType.PAWN);
    }
}
