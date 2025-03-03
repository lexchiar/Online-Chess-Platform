package com.tco.gameplaying;

public class Rook extends Piece {

    public Rook() {
        this.type = PieceType.ROOK;
    }

    public Rook(PieceColor teamColor) {
        super(teamColor);
        this.type = PieceType.ROOK;
    }

    public Rook(PieceColor teamColor, ChessCoordinate location) {
        super(teamColor, location);
        this.type = PieceType.ROOK;
    }

    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        System.out.println("Checking Rook move from (" + location.getX() + "," + location.getY() + 
                           ") to (" + coordinates.getX() + "," + coordinates.getY() + ")");

        if (!isMoveMeetPrerequisites(coordinates, board)) {
            System.out.println("Move does not meet prerequisites");
            return false;
        }

        int deltaX = coordinates.getX() - location.getX();
        int deltaY = coordinates.getY() - location.getY();

        // Check if the movement is horizontal or vertical
        if (!isHorizontalOrVerticalMove(deltaX, deltaY)) {
            System.out.println("Not a horizontal or vertical move");
            return false;
        }

        // Check if the path is clear
        boolean pathClear = isPathClear(coordinates, deltaX, deltaY, board);
        System.out.println("Is path clear: " + pathClear);
        return pathClear;
    }

    private boolean isHorizontalOrVerticalMove(int deltaX, int deltaY) {
        return (deltaX == 0 && deltaY != 0) || (deltaY == 0 && deltaX != 0);
    }

    private boolean isPathClear(ChessCoordinate coordinates, int deltaX, int deltaY, Piece[][] board) {
        int xDirection = Integer.signum(deltaX);
        int yDirection = Integer.signum(deltaY);

        int x = location.getX() + xDirection;
        int y = location.getY() + yDirection;

        while (x != coordinates.getX() || y != coordinates.getY()) {
            // Invert Y coordinate when accessing the board
            if (board[7 - y][x] != null) {
                System.out.println("Path blocked at (" + x + "," + y + ")");
                return false;
            }

            x = moveForward(x, coordinates.getX(), xDirection);
            y = moveForward(y, coordinates.getY(), yDirection);
        }

        return true;
    }

    private int moveForward(int i, int coordinate, int direction) {
        if (i != coordinate) {
            i += direction;
        }
        return i;
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
            System.out.println("Rook moving to (" + coordinates.getX() + "," + coordinates.getY() + ")");
            this.location = coordinates;
            return true;
        }
        System.out.println("Invalid Rook move");
        return false;
    }
}