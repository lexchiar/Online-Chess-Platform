package com.tco.gameplaying;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceFactoryTest {
    @Test
    @DisplayName("mholter")
    public void testMakeBishop() {
        ChessCoordinate bishopLocation = new ChessCoordinate(2, 7);
        Piece bishop = PieceFactory.getPiece(PieceType.BISHOP, PieceColor.BLACK, bishopLocation);

        assertTrue(bishop.getType() == PieceType.BISHOP);
        assertTrue(bishop.getColor() == PieceColor.BLACK);
        assertTrue(bishop.getLocation() == bishopLocation);
    }

    @Test
    @DisplayName("mholter")
    public void testMakeKing() {
        ChessCoordinate kingLocation = new ChessCoordinate(4, 7);
        Piece king = PieceFactory.getPiece(PieceType.KING, PieceColor.BLACK, kingLocation);
        
        assertTrue(king.getType() == PieceType.KING);
        assertTrue(king.getColor() == PieceColor.BLACK);
        assertTrue(king.getLocation() == kingLocation);
    }

    @Test
    @DisplayName("mholter")
    public void testMakeKnight() {
        ChessCoordinate knightLocation = new ChessCoordinate(1, 7);
        Piece knight = PieceFactory.getPiece(PieceType.KNIGHT, PieceColor.BLACK, knightLocation);
        
        assertTrue(knight.getType() == PieceType.KNIGHT);
        assertTrue(knight.getColor() == PieceColor.BLACK);
        assertTrue(knight.getLocation() == knightLocation);
    }

    @Test
    @DisplayName("mholter")
    public void testMakePawn() {
        ChessCoordinate pawnLocation = new ChessCoordinate(0, 6);
        Piece pawn = PieceFactory.getPiece(PieceType.PAWN, PieceColor.BLACK, pawnLocation);
        
        assertTrue(pawn.getType() == PieceType.PAWN);
        assertTrue(pawn.getColor() == PieceColor.BLACK);
        assertTrue(pawn.getLocation() == pawnLocation);
    }

    @Test
    @DisplayName("mholter")
    public void testMakeQueen() {
        ChessCoordinate queenLocation = new ChessCoordinate(3, 7);
        Piece queen = PieceFactory.getPiece(PieceType.QUEEN, PieceColor.BLACK, queenLocation);
        
        assertTrue(queen.getType() == PieceType.QUEEN);
        assertTrue(queen.getColor() == PieceColor.BLACK);
        assertTrue(queen.getLocation() == queenLocation);
    }

    @Test
    @DisplayName("mholter")
    public void testMakeRook() {
        ChessCoordinate rookLocation = new ChessCoordinate(0, 7);
        Piece rook = PieceFactory.getPiece(PieceType.ROOK, PieceColor.BLACK, rookLocation);
        
        assertTrue(rook.getType() == PieceType.ROOK);
        assertTrue(rook.getColor() == PieceColor.BLACK);
        assertTrue(rook.getLocation() == rookLocation);
    }
}
