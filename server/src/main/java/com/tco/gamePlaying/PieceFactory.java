package com.tco.gameplaying;

public class PieceFactory {

    public static Piece getPiece(PieceType pieceType, PieceColor color, ChessCoordinate coord){
        Piece piece;
        switch(pieceType) {
            case BISHOP:
                piece = new Bishop(color, coord);
                break;
            case KING:
                piece = new King(color, coord);
                break;
            case KNIGHT:
                piece = new Knight(color, coord);
                break;
            case PAWN:
                piece = new Pawn(color, coord);
                break;
            case QUEEN:
                piece = new Queen(color, coord);
                break;
            case ROOK:
                piece = new Rook(color, coord);
                break;
            default:
                piece = null;
                break;
        }
            return piece;
    }
}
