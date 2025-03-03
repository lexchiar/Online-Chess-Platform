package com.tco.gameplaying;
public class Pawn extends Piece {
    
    private boolean hasMoved;
    private int lastMoveTurn;
    private int direction;

    public Pawn() {
        this.hasMoved = false;
        this.lastMoveTurn = -1;
        this.type = PieceType.PAWN;
    }
    public Pawn(PieceColor teamColor){
        this.hasMoved = false;
        this.lastMoveTurn = -1;
        this.color = teamColor;
        this.type = PieceType.PAWN;
        direction = (color == PieceColor.WHITE) ? 1 : -1;
    }

    public Pawn(PieceColor teamColor, ChessCoordinate location) {
        this.hasMoved = false;
        this.lastMoveTurn = -1;
        this.color = teamColor;
        this.location = location;
        this.type = PieceType.PAWN;
        direction = (color == PieceColor.WHITE) ? 1 : -1;
    }

    public Pawn(PieceColor teamColor, ChessCoordinate location, boolean hasMoved) {
        this.hasMoved = hasMoved;
        this.lastMoveTurn = -1;
        this.color = teamColor;
        this.location = location;
        this.type = PieceType.PAWN;
        direction = (color == PieceColor.WHITE) ? 1 : -1;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
    
    @Override
    public boolean move(ChessCoordinate coordinates, Piece[][] board, Move lastOpponentMove){
       
       
        if(isValidMove(coordinates, board))
        {
            this.location = coordinates;
            this.hasMoved = true;
            return true;
        }
       
        return false;

    }
    
    @Override
    public boolean isValidMove(ChessCoordinate coordinates, Piece[][] board) {
        // Invert Y coordinate for board access
        int boardY = 7 - coordinates.getY();
        int currentBoardY = 7 - location.getY();
        
        // Check if destination is within board bounds
        if (coordinates.getX() < 0 || coordinates.getX() > 7 || 
            coordinates.getY() < 0 || coordinates.getY() > 7) {
            return false;
        }
    
        Piece destinationPiece = board[boardY][coordinates.getX()];
    
        // Normal move -- pawn can move up and down same column and no piece is in way
        boolean returnValue = normalMove(coordinates, destinationPiece, board);
    
        if (!returnValue) {
            // Capturing a piece -- moving diagonally
            returnValue = diagonalMove(coordinates, destinationPiece, board);
        }
    
        //En Passant move - needs access to the last move made by oppenent -- may have to change arguments in isValidMove method
        
        /* 
        //check if move is moving left or right one space and up or down one space
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction && destinationPiece == null) {
            // Check if opponent pawn moved two spaces next to this pawn
            if (lastMoveEnd.getY() == startRow && Math.abs(lastMoveEnd.getX() - startCol) == 1) {
                // get the piece
                Piece lastMovedPiece = board[lastMoveEnd.getY()][lastMoveEnd.getX()];
                //check if piece is pawn and has moved two spaces
                if (lastMovedPiece instanceof Pawn && lastMoveStart.getY() == startRow + 2 * direction) {
                    board[lastMoveEnd.getY()][lastMoveEnd.getX()] = null; // Capture the pawn
                    return true;
                }
            }
        }
        */

        return returnValue;
    }

    private boolean normalMove(ChessCoordinate coordinates, Piece destinationPiece, Piece[][] board) {    
        // Check if moving in same column
        if (location.getX() == coordinates.getX() && destinationPiece == null) {
            // For one space move
            if (coordinates.getY() == location.getY() + direction) {
                return true;
            }
            
            // For two space move
            if (!hasMoved && 
                coordinates.getY() == location.getY() + (2 * direction)) {
                // Check if the path is clear
                int intermediateY = 7 - (location.getY() + direction);
                if (board[intermediateY][location.getX()] == null) {
                    return true;
                }
            }
        }
        
        return false;
    }

    private boolean diagonalMove(ChessCoordinate coordinates, Piece destinationPiece, Piece[][] board)
    {
        //Capturing a piece -- moving diagonally
        if(Math.abs(location.getX() - coordinates.getX()) == 1 && coordinates.getY() == location.getY() + direction){
            if((destinationPiece != null) && !(destinationPiece.getColor().equals(this.color))){
                //lastMoveTurn = currentTurn;
                destinationPiece = null;
                return true;
            }
        }
        return false;
    }

    public boolean canPromote(ChessCoordinate end){
        if(color.equals(PieceColor.valueOf("WHITE")) && end.getY() == 0){
            return true;
        }
        if(!color.equals(PieceColor.valueOf("WHITE")) && end.getY() == 7){
            return true;
        }
        return false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
}