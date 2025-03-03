package com.tco.requests;

import com.tco.gameplaying.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.tco.gameplaying.PieceType;

public class ValidateMoveRequest extends Request {
    private Piece piece;
    private Piece[][] board;
    private List<ChessCoordinate> validMoves;

    @Override
    public void buildResponse() {
        System.out.println("Building response for piece: " + piece.getType() + " at location: " + piece.getLocation().getX() + "," + piece.getLocation().getY());
        validMoves = new ArrayList<>();
        
        Piece specificPiece = createSpecificPiece(piece);
        
        // Calculate valid moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessCoordinate coordinate = new ChessCoordinate(col, row);
                if (specificPiece.isValidMove(coordinate, board)) {
                    validMoves.add(coordinate);
                    System.out.println("Added valid move: " + col + "," + row);
                }
            }
        }
        
        System.out.println("Total valid moves found: " + validMoves.size());
    }

    private Piece createSpecificPiece(Piece genericPiece) {
        PieceType type = genericPiece.getType();
        ChessCoordinate location = genericPiece.getLocation();
        PieceColor color = genericPiece.getColor();
    
        System.out.println("Creating specific piece:");
        System.out.println("Type: " + type);
        System.out.println("Color: " + color);
        System.out.println("Location: (" + location.getX() + "," + location.getY() + ")");
        
        switch(type) {
            case KING:
                return new King(color, location);
            case QUEEN:
                return new Queen(color, location);
            case ROOK:
                return new Rook(color, location);
            case BISHOP:
                return new Bishop(color, location);
            case KNIGHT:
                return new Knight(color, location);
            case PAWN:
                Pawn newPawn = new Pawn(color, location);
                // Check if pawn is not in its starting position
                if (color == PieceColor.WHITE && location.getY() != 1 ||
                    color == PieceColor.BLACK && location.getY() != 6) {
                    newPawn.setHasMoved(true);
                }
                return newPawn;
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }

    public ValidateMoveRequest() {
        this.requestType = "validateMoves";
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public List<ChessCoordinate> getValidMoves() {
        return validMoves;
    }
}