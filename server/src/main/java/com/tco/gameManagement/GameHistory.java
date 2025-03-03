package com.tco.gamemanagement;

import com.tco.gameplaying.Move;
import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    private List<Move> moves; 

    public GameHistory() {
        this.moves = new ArrayList<>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void clearHistory() {
        moves.clear();
    }

    public int getNumberOfMoves() {
        return moves.size();
    }

    public Move getLastMove()
    {
        if(getNumberOfMoves() == 0)
        {
            return null;
        }

        return moves.get(getNumberOfMoves()-1);
    }

    // extra functionality maybe for serialization/deserialization purposes , keeping track ; can be removed 
    //public String serializeHistory() {
       // return String.join(";", moves);
    //}

    //public void deserializeHistory(String serializedHistory) {
       // moves = new ArrayList<>(List.of(serializedHistory.split(";")));
    //}
}