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
    Canvas playingField;
    boolean startUp = true;

    public enum touchModes {FLAG, UNCOVER};
    public enum GAMESTATUS {STARTUP, PLAYING};
    public touchModes currentMode = touchModes.UNCOVER;
    public GAMESTATUS gamestatus = GAMESTATUS.STARTUP;

    MinesweeperField[][] fields = new MinesweeperField[10][10];

    MinesweeperField lastTouchedField;

    Paint p;

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

    private void init() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new MinesweeperField();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchDownX = event.getX();
        float touchDownY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TouchX", touchDownX + "");
                Log.i("TouchY", touchDownY + "");
                lastTouchedField = getTouchedField(touchDownX, touchDownY);
                switch (currentMode) {
                    case UNCOVER:
                        lastTouchedField.setMined(true);
                        invalidate();
                        break;
                    case FLAG:
                        lastTouchedField.setFlagged(true);
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
        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(4f);

        Paint minedPaint = new Paint();
        minedPaint.setColor(Color.GRAY);


        cHeight = canvas.getHeight();
        cWidth = canvas.getWidth();

        fieldHeight = cHeight / 10;
        fieldWidth = cWidth / 10;

        for (int i = 1; i < 10; i++) {
            canvas.drawLine(i * fieldWidth, 0f, i * fieldWidth, cHeight, p);
            canvas.drawLine(0, i * fieldHeight, cWidth, i * fieldHeight, p);
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                switch(gamestatus){
                    case STARTUP:
                        fields[i][j].setCoordinates(i * fieldWidth + 2, (i + 1) * fieldWidth - 2, j * fieldHeight + 2, (j + 1) * fieldHeight - 2);
                        break;
                    case PLAYING:
                        if(fields[i][j].isMined())
                            canvas.drawRect(fields[i][j].getStartX(),fields[i][j].getStartY(),fields[i][j].getStopX(),fields[i][j].getStopY(),minedPaint);
                        break;
                }
            }
        }
        gamestatus = GAMESTATUS.PLAYING;

        if(currentMode == touchModes.UNCOVER && lastTouchedField != null)
        {
            canvas.drawRect(lastTouchedField.getStartX(), lastTouchedField.getStartY(), lastTouchedField.getStopX(), lastTouchedField.getStopY(), minedPaint);
            lastTouchedField = null;
        }
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
}
