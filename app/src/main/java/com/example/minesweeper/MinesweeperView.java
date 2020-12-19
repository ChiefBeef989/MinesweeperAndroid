package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MinesweeperView extends View {

    float cHeight;
    float cWidth;
    float fieldHeight;
    float fieldWidth;

    boolean gameOver;

    public enum touchModes {FLAG, UNCOVER};
    public enum GAMESTATUS {STARTUP, PLAYING};
    public touchModes currentMode = touchModes.UNCOVER;
    public GAMESTATUS gamestatus = GAMESTATUS.STARTUP;

    int totalMines = 2;
    int minesLeftToPlace;
    int flagsPlaced = 0;

    Paint lines, minedPaint, minePaint, flagPaint, unminedPaint, text;

    MinesweeperField[][] fields = new MinesweeperField[10][10];

    MinesweeperField lastTouchedField;

    public MinesweeperView(Context context) {
        super(context);
        init();
    }

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MinesweeperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        gamestatus = GAMESTATUS.STARTUP;
        gameOver = false;

        flagsPlaced = 0;
        minesLeftToPlace = totalMines;

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new MinesweeperField();
            }
        }

        while (minesLeftToPlace > 0){
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[i].length; j++) {
                    if(Math.random() > 0.8d && minesLeftToPlace > 0){
                        fields[i][j].setMine(true);
                        minesLeftToPlace--;
                        Log.i("MINE PLACED", minesLeftToPlace + "");
                    }
                }
            }
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if(fields[i][j].isMine())
                    Log.i("[" + i + "]" + "[" + j + "]","MINED");
            }
        }

        lines = new Paint();
        lines.setColor(Color.WHITE);
        lines.setStrokeWidth(4f);

        minedPaint = new Paint();
        minedPaint.setStyle(Paint.Style.FILL);
        minedPaint.setColor(Color.GRAY);

        minePaint = new Paint();
        minePaint.setStyle(Paint.Style.FILL);
        minePaint.setColor(Color.RED);

        flagPaint = new Paint();
        flagPaint.setStyle(Paint.Style.FILL);
        flagPaint.setColor(Color.YELLOW);

        unminedPaint = new Paint();
        unminedPaint.setStyle(Paint.Style.FILL);
        unminedPaint.setColor(Color.BLACK);

        text = new Paint();
        text.setColor(Color.WHITE);
        text.setStyle(Paint.Style.FILL);
        text.setTextSize(100);
        text.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(gameOver)
            return false;

        float touchDownX = event.getX();
        float touchDownY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TouchX", touchDownX + "");
                Log.i("TouchY", touchDownY + "");

                lastTouchedField = getTouchedField(touchDownX, touchDownY);

                if(lastTouchedField == null || lastTouchedField.isMined())
                    return false;

                switch (currentMode) {
                    case UNCOVER:
                        lastTouchedField.setMined(true);
                        invalidate();
                        break;
                    case FLAG:
                        if(lastTouchedField.isFlagged()) {
                            lastTouchedField.setFlagged(false);
                            flagsPlaced--;
                        }else{
                            lastTouchedField.setFlagged(true);
                            flagsPlaced++;
                            if(victoryCheck()) {
                                gameOver = true;
                                MainActivity.getInstance().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"GAME WON", Toast.LENGTH_SHORT);
                                    }
                                });
                                Log.i("VICTORY", "GAME WON");
                            }
                        }
                        MainActivity.getInstance().refreshTextViews();
                        invalidate();
                        break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.BLACK);

        cHeight = canvas.getHeight();
        cWidth = canvas.getWidth();

        fieldHeight = cHeight / 10;
        fieldWidth = cWidth / 10;

        for (int i = 1; i < 10; i++) {
            canvas.drawLine(i * fieldWidth, 0f, i * fieldWidth, cHeight, lines);
            canvas.drawLine(0, i * fieldHeight, cWidth, i * fieldHeight, lines);
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                switch(gamestatus){
                    case STARTUP:
                        fields[i][j].setCoordinates(i * fieldWidth + 2, (i + 1) * fieldWidth - 2, j * fieldHeight + 2, (j + 1) * fieldHeight - 2);
                        break;
                    case PLAYING:
                        if(fields[i][j].isMine() && fields[i][j].isMined()){
                            canvas.drawRect(fields[i][j].getStartX(),fields[i][j].getStartY(),fields[i][j].getStopX(),fields[i][j].getStopY(),minePaint);
                            canvas.drawText("M",fields[i][j].getStopX() - fieldWidth/2,fields[i][j].getStopY()-fieldHeight/2,text);
                            gameOver = true;
                            Log.i("MINE RENDERED", j + " " + i);
                        }
                        else if(fields[i][j].isMined() && !fields[i][j].isMine()){
                            canvas.drawRect(fields[i][j].getStartX(),fields[i][j].getStartY(),fields[i][j].getStopX(),fields[i][j].getStopY(),minedPaint);
                        }
                        else if(fields[i][j].isFlagged() && !fields[i][j].isMined())
                            canvas.drawRect(fields[i][j].getStartX(),fields[i][j].getStartY(),fields[i][j].getStopX(),fields[i][j].getStopY(),flagPaint);
                        else if(!fields[i][j].isFlagged() && !fields[i][j].isMined())
                            canvas.drawRect(fields[i][j].getStartX(),fields[i][j].getStartY(),fields[i][j].getStopX(),fields[i][j].getStopY(),unminedPaint);
                        break;
                }
            }
        }
        gamestatus = GAMESTATUS.PLAYING;
        MainActivity.getInstance().refreshTextViews();
    }

    private MinesweeperField getTouchedField(float x, float y){
        for(int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if(fields[i][j].getStartX() < x && fields[i][j].getStopX() > x && fields[i][j].getStartY() < y && fields[i][j].getStopY() > y)
                    return fields[i][j];
            }
        }
        return null;
    }

    private boolean victoryCheck(){
        for(int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if((fields[i][j].isMine() && !fields[i][j].isFlagged()) || flagsPlaced != totalMines)
                    return false;
            }
        }
        return true;
    }
}
