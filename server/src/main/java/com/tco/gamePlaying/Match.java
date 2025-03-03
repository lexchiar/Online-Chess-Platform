package com.tco.gameplaying;

import com.tco.usermanagement.User;
import com.tco.gamemanagement.Notification;
import com.tco.gamemanagement.GameHistory;
import com.tco.gamemanagement.MatchStatistics;
import com.tco.gamemanagement.MatchStatus;
import java.util.ArrayList;
import java.time.*;

public class Match {
    private int matchId;

    private int[] players;
    private int turnCount;
    public Piece[][] board;
    public BoardSetup setup;

    public GameHistory moveHistory;
    public MatchStatistics stats;

    private PieceColor black = PieceColor.BLACK;
    private PieceColor white = PieceColor.WHITE;

    // Create a match from the database information
    public Match(int match_id, int player1, int player2, int turn_count, MatchStatistics matchStatistics) 
    {
        this.matchId = match_id;
        this.players = new int[] { player1, player2 };
        this.turnCount = turn_count;
        this.stats = matchStatistics;
        setup = new BoardSetup();
        this.board = setup.makeBoard();
        this.moveHistory = new GameHistory();
    }

    public Match(int player1, int player2) {
        this(-1, player1, player2, 0, new MatchStatistics());
    }

    public boolean applyMove(int player, Move move) {
        int playerIndex = (player == players[0]) ? 0 : 1;
        if (playerIndex != currentPlayerIndex() || !moveBelongToPlayer(move)) {
            return false;
        }

        Piece piece = move.getPiece();
        ChessCoordinate endLocation = move.getEndLocation();

        if (piece.move(endLocation, board, moveHistory.getLastMove())) {
            board[endLocation.getX()][endLocation.getY()] = piece;
            moveHistory.addMove(move);
            switchTurn();
            return true;
        }
        return false;
    }

    private void switchTurn() {
        turnCount = (turnCount + 1) % 2;
    }

    private boolean isPlayerTurn(int player) {
        return players[turnCount] == player;
    }

    public PieceColor getCurrentPlayerColor() {
        return turnCount == 0 ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public GameHistory getGameHistory() {
        return moveHistory;
    }

    public void removePlayer(int player) {
        if(stats.getStatus() == MatchStatus.PLAYING) //can only remove while playing
        {
            if(this.players[0] == player) {
                this.stats.setLooser(players[0]);
                this.stats.setWinner(players[1]);
            }
            else {
                this.stats.setLooser(players[1]);
                this.stats.setWinner(players[0]);
            }
            this.stats.setEndTime(LocalDateTime.now());
            this.stats.setStatus(MatchStatus.ABANDONED);
        }
    }

    public void matchEnd() {
        if(stats.getStatus() == MatchStatus.PLAYING) //can only end while playing
        {
            int winningPlayer = turnCount % 2; //last player to take turn is winner
            int losingPlayer = Math.abs((turnCount - 1) % 2); //previous turn belongs to loser

            this.stats.setWinner(players[winningPlayer]);
            this.stats.setLooser(players[losingPlayer]);

            this.stats.setEndTime(LocalDateTime.now());
            this.stats.setStatus(MatchStatus.FINISHED);
        }
    }
    
    private int currentPlayerIndex() {
        return turnCount % 2;
    }

    private boolean moveBelongToPlayer(Move move) {
        int index = currentPlayerIndex();

        return isBlackPlayerTurn(index, move) || isWhitePlayerTurn(index, move);

    }

    private boolean isBlackPlayerTurn(int index, Move move) {
        return index == 1 & move.getPiece().getColor() == PieceColor.BLACK;
    }

    private boolean isWhitePlayerTurn(int index, Move move) {
        return index == 0 & move.getPiece().getColor() == PieceColor.WHITE;
    }

    public MatchStatistics getStatistics()
    {
        return stats;
    }

    @Override
    public String toString()
    {
      return "\n Match " + this.matchId + "\n Player " + this.players[0] + " vs Player " + this.players[1] + "\n Turn Count: " + this.turnCount +  "\n ";
    }

    public void populateBoard(ArrayList<Piece> pieces) {
        
        this.board = new Piece[8][8];

        for (int i = 0; i < pieces.size() ; i++) 
        {
            this.board[pieces.get(i).getLocation().getX()][pieces.get(i).getLocation().getY()] = pieces.get(i);
        }

        
    }

    public void populateHistory(ArrayList<Move> moves) 
    {
        if(moves != null)
        {
            this.moveHistory = new GameHistory();

            for (int i = 0; i < moves.size() ; i++) 
            {
                moveHistory.addMove(moves.get(i));
            }
        }
        
    }

    public int getWhitePlayerID()
    {
        return players[0];
    }

    public int getBlackPlayerID()
    {
        return players[1];
    }

    public void setId(int id){
        if(this.matchId == -1) {
            this.matchId = id;
        }
    }

    public int getId()
    {
        return this.matchId;
    }
}