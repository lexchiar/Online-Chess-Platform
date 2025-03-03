package com.tco.gameplaying;

public class BoardSetup {
    private Piece[][] board;

    public Piece[][] makeBoard() {
        this.board = new Piece[8][8];
        rookMaker();
        knightMaker();
        bishopMaker();
        queenMaker();
        kingMaker();
        pawnMaker();
        return this.board;
    }

    private void rookMaker() {
        this.board[0][0] = PieceFactory.getPiece(PieceType.ROOK, PieceColor.WHITE, new ChessCoordinate(0, 0));
        this.board[7][0] = PieceFactory.getPiece(PieceType.ROOK, PieceColor.WHITE, new ChessCoordinate(7, 0));
        this.board[0][7] = PieceFactory.getPiece(PieceType.ROOK, PieceColor.BLACK, new ChessCoordinate(0, 7));
        this.board[7][7] = PieceFactory.getPiece(PieceType.ROOK, PieceColor.BLACK, new ChessCoordinate(7, 7));
    }

    private void knightMaker() {
        this.board[1][0] = PieceFactory.getPiece(PieceType.KNIGHT, PieceColor.WHITE, new ChessCoordinate(1, 0));
        this.board[6][0] = PieceFactory.getPiece(PieceType.KNIGHT, PieceColor.WHITE, new ChessCoordinate(6, 0));
        this.board[1][7] = PieceFactory.getPiece(PieceType.KNIGHT, PieceColor.BLACK, new ChessCoordinate(1, 7));
        this.board[6][7] = PieceFactory.getPiece(PieceType.KNIGHT, PieceColor.BLACK, new ChessCoordinate(6, 7));
    }

    private void bishopMaker() {
        this.board[2][0] = PieceFactory.getPiece(PieceType.BISHOP, PieceColor.WHITE, new ChessCoordinate(2, 0));
        this.board[5][0] = PieceFactory.getPiece(PieceType.BISHOP, PieceColor.WHITE, new ChessCoordinate(5, 0));
        this.board[2][7] = PieceFactory.getPiece(PieceType.BISHOP, PieceColor.BLACK, new ChessCoordinate(2, 7));
        this.board[5][7] = PieceFactory.getPiece(PieceType.BISHOP, PieceColor.BLACK, new ChessCoordinate(5, 7));
    }

    private void queenMaker() {
        this.board[3][0] = PieceFactory.getPiece(PieceType.QUEEN, PieceColor.WHITE, new ChessCoordinate(3, 0));
        this.board[3][7] = PieceFactory.getPiece(PieceType.QUEEN, PieceColor.BLACK, new ChessCoordinate(3, 7));
    }

    private void kingMaker() {
        this.board[4][0] = PieceFactory.getPiece(PieceType.KING, PieceColor.WHITE, new ChessCoordinate(4, 0));
        this.board[4][7] = PieceFactory.getPiece(PieceType.KING, PieceColor.BLACK, new ChessCoordinate(4, 7));
    }

    private void pawnMaker() {
        for(int i = 0; i < 8; ++i) {
            this.board[i][1] = PieceFactory.getPiece(PieceType.PAWN, PieceColor.WHITE, new ChessCoordinate(i, 1));

            this.board[i][6] = PieceFactory.getPiece(PieceType.PAWN, PieceColor.BLACK, new ChessCoordinate(i, 6));
        }
    }
}
