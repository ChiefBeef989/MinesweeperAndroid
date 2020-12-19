package com.example.minesweeper;

public class MinesweeperField {
    private boolean isMine;
    private boolean isFlagged;
    private boolean isMined;
    private int adjacentMines;

    private float startX, stopX, startY, stopY;

    public MinesweeperField(){}

    public void setCoordinates(float startX, float stopX, float startY, float stopY){
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isMined() {
        return isMined;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public float getStartX() {
        return startX;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopY() {
        return stopY;
    }


}
