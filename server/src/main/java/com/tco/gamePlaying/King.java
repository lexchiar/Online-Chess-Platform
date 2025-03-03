package com.tco.gameplaying;

public class King extends Piece {
    public King() {
        this.type = PieceType.KING;
    }

    public King(PieceColor teamColor) {
        super(teamColor);
        this.type = PieceType.KING;
    }

    public King(PieceColor teamColor, ChessCoordinate location) {
        super(teamColor, location);
        this.type = PieceType.KING;
    }

    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        System.out.println("Checking move for King from (" + location.getX() + "," + location.getY() + 
                           ") to (" + coordinates.getX() + "," + coordinates.getY() + ")");

        int startCol = location.getX();
        int startRow = location.getY();
        int endCol = coordinates.getX();
        int endRow = coordinates.getY();

        // Check if the move is within the bounds of the chessboard
        if (!isMoveMeetPrerequisites(coordinates, board)) {
            System.out.println("Move does not meet prerequisites");
            return false;
        }

        // Invert Y coordinate for board access
        int boardEndRow = 7 - endRow;
        Piece destinationPiece = board[boardEndRow][endCol];

        // Check if the move is within one square in any direction
        if (isMovingOneCaseOnly(startCol, startRow, endCol, endRow)) {
            // Check if the destination is either empty or contains an opponent's piece
            if (destinationPiece == null || !destinationPiece.getColor().equals(this.color)) {
                System.out.println("Valid move: Destination is empty or contains opponent's piece");
                return true;
            } else {
                System.out.println("Invalid move: Destination contains friendly piece");
            }
        } else {
            System.out.println("Invalid move: Not moving one case only");
        }

        return false;
    }

    private boolean isMovingOneCaseOnly(int startCol, int startRow, int endCol, int endRow) {
        boolean result = (Math.abs(startCol - endCol) <= 1 && Math.abs(startRow - endRow) <= 1);
        System.out.println("Moving one case only: " + result);
        return result;
    }

    @Override
    protected boolean isOccupiedBySameTeam(ChessCoordinate coordinates, Piece[][] board) {
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
            this.location = coordinates;
            return true;
        }
        return false;
    }
}