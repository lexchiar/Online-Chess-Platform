package com.tco.database;

import com.tco.database.Credential;
import com.tco.gameplaying.*;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gamemanagement.MatchStatus;

public  class PieceCreator 
{
    public static Piece createPiece(int id, PieceColor color, PieceType type, ChessCoordinate coordinates)
    {   

        Piece piece;

        if(type == PieceType.PAWN)
        {
            piece = new Pawn(color, coordinates);
        }
        else if (type == PieceType.ROOK)
        {
            piece = new Rook(color, coordinates);
        }
        else if (type == PieceType.KNIGHT)
        {
            piece = new Knight(color, coordinates);
        }
        else if (type == PieceType.KING)
        {
            piece = new King(color, coordinates);
        }
        else if (type == PieceType.QUEEN)
        {
            piece = new Queen(color, coordinates);
        }
        else
        {
            piece = new Bishop(color, coordinates);
        }

        piece.setId(id);

        return piece;
    }
}

