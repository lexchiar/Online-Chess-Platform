package com.tco.requests;

import com.tco.gameplaying.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 public class GuestRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(GuestRequest.class);

    public Piece piece;
    public ChessCoordinate destination;
    public Piece[][] board;
    private boolean validity;

    @Override 
    public void buildResponse(){
        switch(piece.getType()){
            case PAWN:
                piece = new Pawn(piece.getColor(), piece.getLocation());
                break;
            case KNIGHT:
                piece = new Knight(piece.getColor(), piece.getLocation());
                break;
            case ROOK:
                piece = new Rook(piece.getColor(), piece.getLocation());
                break;
            case BISHOP:
                piece = new Bishop(piece.getColor(), piece.getLocation());
                break;
            case QUEEN:
                piece = new Queen(piece.getColor(), piece.getLocation());
                break;
            case KING:
                piece = new King(piece.getColor(), piece.getLocation());
                break;
        }
        validity = piece.isValidMove(destination, board);
        log.trace("buildResponse -> {}", this);
    }

    // Test Methods only
    public GuestRequest(Piece piece, ChessCoordinate destination, Piece[][] board){
        super();
        this.requestType = "guestMove";
        this.piece = piece;
        this.destination = destination;
        this.board = board;
    }

    public Piece getPiece() {return this.piece;}
    public Piece[][] getBoard() {return this.board;}
    public ChessCoordinate getDestination() {return this.destination;}
    public boolean getValidity() {return this.validity;}
 }