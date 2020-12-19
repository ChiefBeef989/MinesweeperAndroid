package com.example.minesweeper;

public class MinesweeperField {
    private boolean isMine;
    private boolean isFlagged;
    private boolean isMined;
    private int adjacentMines;

    private float startX, stopX, startY, stopY;

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

    public MinesweeperField(float startX, float stopX, float startY, float stopY){
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
    }
}
