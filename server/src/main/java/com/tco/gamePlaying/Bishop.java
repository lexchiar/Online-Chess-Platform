package com.tco.gameplaying;

public class Bishop extends Piece {

    public Bishop() {
        // Default constructor
        this.type = PieceType.BISHOP;
    }

    public Bishop(PieceColor teamColor) {
        super(teamColor);
        this.type = PieceType.BISHOP;
    }

    public Bishop(PieceColor teamColor, ChessCoordinate location) {
        super(teamColor, location);
        this.type = PieceType.BISHOP;
    }

    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        System.out.println("Checking Bishop move from (" + location.getX() + "," + location.getY() + 
                           ") to (" + coordinates.getX() + "," + coordinates.getY() + ")");

        if(!isMoveMeetPrerequisites(coordinates, board)) {
            System.out.println("Move does not meet prerequisites");
            return false;
        }

        int deltaX = coordinates.getX() - location.getX();
        int deltaY = coordinates.getY() - location.getY();

        // Check if the movement is diagonal
        if (!isDiagonalMove(deltaX, deltaY)) {
            System.out.println("Not a diagonal move");
            return false;
        }

        // Check if the path is clear
        boolean pathClear = isPathClear(coordinates, deltaX, deltaY, board);
        System.out.println("Path clear: " + pathClear);
        return pathClear;
    }

    private boolean isDiagonalMove(int deltaX, int deltaY) {
        boolean isDiagonal = Math.abs(deltaX) == Math.abs(deltaY);
        System.out.println("Is diagonal move: " + isDiagonal);
        return isDiagonal;
    }

    private boolean isPathClear(ChessCoordinate coordinates, int deltaX, int deltaY, Piece[][] board) {
        int xDirection = deltaX > 0 ? 1 : -1;
        int yDirection = deltaY > 0 ? 1 : -1;

        int x = location.getX() + xDirection;
        int y = location.getY() + yDirection;

        while (x != coordinates.getX() && y != coordinates.getY()) {
            // Invert Y coordinate when accessing the board
            if (board[7 - y][x] != null) {
                System.out.println("Path blocked at (" + x + "," + y + ")");
                return false;
            }
            x += xDirection;
            y += yDirection;
        }

        return true;
    }

    @Override
    protected boolean isOccupiedBySameTeam(ChessCoordinate coordinates, Piece[][] board) {
        // Ensure we're using the correct indices for the board array
        int x = coordinates.getX();
        int y = 7 - coordinates.getY();  // Invert Y coordinate
        
        if (board[y][x] == null) {
            return false;
        }
        
        return board[y][x].getColor() == this.getColor();
    }

    @Override
    public boolean move(ChessCoordinate coordinates, Piece[][] board, Move lastOpponentMove) {
        if (isValidMove(coordinates, board)) {
            System.out.println("Bishop moving to (" + coordinates.getX() + "," + coordinates.getY() + ")");
            this.location = coordinates;
            return true;
        }
        System.out.println("Invalid Bishop move");
        return false;
    }
}