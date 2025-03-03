package com.tco.gameplaying;
public class ChessCoordinate {
    private int x;
    private int y;

    public ChessCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters & Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    // Other utility methods could be added here
}