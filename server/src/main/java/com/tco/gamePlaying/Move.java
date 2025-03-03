package com.tco.gameplaying;
public class Move {

    private Piece piece;
    private ChessCoordinate startingLocation; 
    private ChessCoordinate endingLocation;

    
    public Move(Piece chesspiece, ChessCoordinate startLocation,  ChessCoordinate endLocation)
    {
        piece = chesspiece;
        startingLocation = startLocation;
        endingLocation = endLocation;
    }
    
    public Piece getPiece()
    {
        return piece;
    }

    public ChessCoordinate getStartLocation()
    {
        return startingLocation;
    }

    public ChessCoordinate getEndLocation()
    {
        return endingLocation;
    }
}
