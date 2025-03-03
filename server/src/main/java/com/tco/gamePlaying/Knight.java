package com.tco.gameplaying;

public class Knight extends Piece {

    public Knight() {
        this.type = PieceType.KNIGHT;
    }

    public Knight(PieceColor teamColor) {
        super(teamColor);
        this.type = PieceType.KNIGHT;
    }

    public Knight(PieceColor teamColor, ChessCoordinate location) {
        super(teamColor, location);
        this.type = PieceType.KNIGHT;
    }

    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        int startX = this.location.getX();
        int startY = 7 - this.location.getY();  // Invert Y coordinate
        int endX = coordinates.getX();
        int endY = 7 - coordinates.getY();  // Invert Y coordinate
        int changeX = Math.abs(startX - endX);
        int changeY = Math.abs(startY - endY);
        Piece occupying = board[endY][endX];  // Swap X and Y for board access

        if (outOfBounds(coordinates)) {
            return false;
        }
        if (occupationCheck(occupying)) {
            return false;
        }
        return isPathCorrect(changeX, changeY, 2, 1) || isPathCorrect(changeX, changeY, 1, 2);
    }

    private boolean isPathCorrect(int changeX, int changeY, int wantedChangeX, int wantedChangeY) {
        return (changeX == wantedChangeX) && (changeY == wantedChangeY);
    }

    public boolean outOfBounds(ChessCoordinate endLocation) {
        int endX = endLocation.getX();
        int endY = endLocation.getY();
        return endX < 0 || endX >= 8 || endY < 0 || endY >= 8;
    }

    public boolean occupationCheck(Piece occupyingPiece) {
        if (occupyingPiece == null) {
            return false;
        }
        return occupyingPiece.getColor().equals(this.color);
    }
}