package com.tco.gameplaying;

public class Queen extends Piece {

    public Queen() {
        this.type = PieceType.QUEEN;
    }

    public Queen(PieceColor teamColor) {
        super(teamColor);
        this.type = PieceType.QUEEN;
    }

    public Queen(PieceColor teamColor, ChessCoordinate location) {
        super(teamColor, location);
        this.type = PieceType.QUEEN;
    }

    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        System.out.println("Checking Queen move from (" + location.getX() + "," + location.getY() + 
                           ") to (" + coordinates.getX() + "," + coordinates.getY() + ")");

        if (!isMoveMeetPrerequisites(coordinates, board)) {
            System.out.println("Move does not meet prerequisites");
            return false;
        }

        int deplacementInX = coordinates.getX() - location.getX();
        int deplacementInY = coordinates.getY() - location.getY();
        
        boolean isValid = isPathClear(coordinates, deplacementInX, deplacementInY, board);
        System.out.println("Is path clear: " + isValid);
        return isValid;
    }

    private boolean isDeplacementVertical(int deplacementInX, int deplacementInY) {
        return (deplacementInX == 0);
    }

    private boolean isDeplacementHorizontal(int deplacementInX, int deplacementInY) {
        return (deplacementInY == 0);
    }

    private boolean isDeplacementDiagonal(int deplacementInX, int deplacementInY) {
        return (Math.abs(deplacementInX) == Math.abs(deplacementInY));
    }

    private boolean isDeplacementPositive(int deplacement) {
        return deplacement > 0;
    }

    private boolean isPathClear(ChessCoordinate coordinates, int deplacementInX, int deplacementInY, Piece[][] board) {
        if (isDeplacementVertical(deplacementInX, deplacementInY)) {
            return isPathClearVertical(coordinates, deplacementInX, deplacementInY, board);
        }
        
        if (isDeplacementHorizontal(deplacementInX, deplacementInY)) {
            return isPathClearHorizontal(coordinates, deplacementInX, deplacementInY, board);
        }
        
        if (isDeplacementDiagonal(deplacementInX, deplacementInY)) {
            return isPathClearDiagonal(coordinates, deplacementInX, deplacementInY, board);
        }
        return false;
    }

    private boolean isPathClearVertical(ChessCoordinate coordinates, int deplacementInX, int deplacementInY, Piece[][] board) {
        boolean returnValue = false;

        if (isDeplacementPositive(deplacementInY)) {
            // Go UP
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInY, 0, 1);
        } else {
            // GO DOWN
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInY, 0, -1);
        }
        return returnValue;
    }

    private boolean isPathClearHorizontal(ChessCoordinate coordinates, int deplacementInX, int deplacementInY, Piece[][] board) {
        boolean returnValue = false;

        if (isDeplacementPositive(deplacementInX)) {
            // Go RIGHT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, 1, 0);
        } else {
            // GO LEFT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, -1, 0);
        }
        return returnValue;
    }

    private boolean isPathClearDiagonal(ChessCoordinate coordinates, int deplacementInX, int deplacementInY, Piece[][] board) {
        boolean returnValue = false;

        if (isDeplacementDiagonalUpRight(deplacementInX, deplacementInY)) {
            // Go UP & RIGHT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, 1, 1);   
        } else if (isDeplacementDiagonalUpLeft(deplacementInX, deplacementInY)) {
            // Go UP & LEFT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, -1, 1);
        } else if (isDeplacementDiagonalDownRight(deplacementInX, deplacementInY)) {
            // Go DOWN & RIGHT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, 1, -1);
        } else if (isDeplacementDiagonalDownLeft(deplacementInX, deplacementInY)) {
            // Go DOWN & LEFT
            returnValue = verifyNoPiecesBetweenPath(board, deplacementInX, -1, -1);
        }
             
        return returnValue;
    }

    private boolean isDeplacementDiagonalUpRight(int deplacementInX, int deplacementInY) {
        return isDeplacementPositive(deplacementInX) && isDeplacementPositive(deplacementInY);
    }

    private boolean isDeplacementDiagonalUpLeft(int deplacementInX, int deplacementInY) {
        return !isDeplacementPositive(deplacementInX) && isDeplacementPositive(deplacementInY);
    }

    private boolean isDeplacementDiagonalDownRight(int deplacementInX, int deplacementInY) {
        return isDeplacementPositive(deplacementInX) && !isDeplacementPositive(deplacementInY);
    }

    private boolean isDeplacementDiagonalDownLeft(int deplacementInX, int deplacementInY) {
        return !isDeplacementPositive(deplacementInX) && !isDeplacementPositive(deplacementInY);
    }

    // Multiplier parameters guide:
    // 0 = stay on the same index
    // 1 = increase index
    // -1 = decrease index
    private boolean verifyNoPiecesBetweenPath(Piece[][] board, int deplacement, int xMultiplier, int yMultiplier) {
        for (int i = 1; i < Math.abs(deplacement); i++) {
            int x = location.getX() + (xMultiplier * i);
            int y = 7 - (location.getY() + (yMultiplier * i));  // Invert Y coordinate
            
            System.out.println("Checking square (" + x + "," + y + ")");
            
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                System.out.println("Out of bounds");
                return false;
            }
            
            if (board[y][x] != null) {
                System.out.println("Piece found at (" + x + "," + y + ")");
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isOccupiedBySameTeam(ChessCoordinate coordinates, Piece[][] board) {
        int x = coordinates.getX();
        int y = 7 - coordinates.getY();  // Invert Y coordinate
        
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        }
        
        if (board[y][x] == null) {
            return false;
        }
        
        return board[y][x].getColor() == this.getColor();
    }
}