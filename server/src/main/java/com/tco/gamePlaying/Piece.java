package com.tco.gameplaying;

import com.tco.gameplaying.*;

public class Piece {

    //-1 == not set
    public int id = -1;
    public PieceColor color;
    public PieceType type;
    public ChessCoordinate location;
    
    public Piece(){
    }

    public Piece(PieceColor teamColor){
        this.color = teamColor;
    }

    public Piece(PieceColor teamColor, ChessCoordinate location){
        this.color = teamColor;
        this.location = location;
    }

    public Piece(PieceColor teamColor, ChessCoordinate location, PieceType type){
        this.color = teamColor;
        this.location = location;
        this.type = type;
    }

    public ChessCoordinate getLocation() {
        return location;
    }

    public void setLocation(ChessCoordinate location) {
        this.location = location;
    }


    public PieceColor getColor()
    {
        return color;
    }

    public int  getId()
    {
        return id;
    }


    public void setColor(PieceColor color){this.color = color;}

    public PieceType getType(){
        return this.type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public boolean move(ChessCoordinate coordinates, Piece[][] board, Move lastOpponentMove)
    {
        if(isValidMove(coordinates, board))
        {
            this.location = coordinates;
            return true;
        }
       
        return false;
    }

    public  boolean isValidMove(ChessCoordinate coordinates, Piece[][] board){
        return true;
    }

    public void capture(Piece piece){

    }

    protected boolean isMoveMeetPrerequisites(ChessCoordinate coordinates, Piece[][] board)
    {
        return !(!coordinates.isValid() || !isMoving(coordinates) || isOccupiedBySameTeam(coordinates, board ));
    }

    protected boolean isMoving(ChessCoordinate coordinates)
    {
    	return (coordinates.getX()  != location.getX() || coordinates.getY()  != location.getY());
    }
    

    protected boolean isOccupiedBySameTeam(ChessCoordinate coordinates, Piece[][] board )
    {
    	if(board[coordinates.getX()][coordinates.getY()] == null)
        {
            return false;
        }
        	
        else
        {
        	if((board[coordinates.getX()][coordinates.getY()].getColor()).equals(this.color))
            {
            	return true;
            }
        }
        return false;
    }

    public void setId(int pieceId) 
    {
        //Can not change the id once set
        if(id == -1)
        {
            id = pieceId;
        }
       
    }

    
}
