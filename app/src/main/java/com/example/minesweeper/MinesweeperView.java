package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.Duration;

public class MinesweeperView extends View {

    float cHeight;
    float cWidth;
    float fieldHeight;
    float fieldWidth;

    MinesweeperField[][] fields = new MinesweeperField[10][10];

    Paint p;

    public MinesweeperView(Context context) {
        super(context);
    }

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MinesweeperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.BLACK);

        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(4f);

        Paint b = new Paint();
        b.setColor(Color.GRAY);

        cHeight = canvas.getHeight();
        cWidth = canvas.getWidth();

        fieldHeight = cHeight / 10;
        fieldWidth = cWidth / 10;

        for(int i = 1; i < 10; i++){
            canvas.drawLine(i*fieldWidth,0f,i*fieldWidth,cHeight,p);
            canvas.drawLine(0,i*fieldHeight,cWidth,i*fieldHeight,p);
        }


    }
}
