package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static MainActivity INSTANCE;
    Button resetBtn, changeModeBtn;
    TextView totalMinesTxt, flagsPlacedTxt;
    MinesweeperView minesweeperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INSTANCE = this;

        resetBtn = (Button)findViewById(R.id.resetBtn);
        changeModeBtn = (Button)findViewById(R.id.changeModeBtn);

        totalMinesTxt = (TextView)findViewById(R.id.totalMinesText);
        flagsPlacedTxt = (TextView)findViewById(R.id.flagsSetText);

        minesweeperView = (MinesweeperView)findViewById(R.id.minesweeper);

        changeModeBtn.setText(minesweeperView.currentMode.toString());

        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minesweeperView.currentMode == MinesweeperView.touchModes.UNCOVER)
                    minesweeperView.currentMode = MinesweeperView.touchModes.FLAG;
                else
                    minesweeperView.currentMode = MinesweeperView.touchModes.UNCOVER;
                changeModeBtn.setText(minesweeperView.currentMode.toString());
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minesweeperView.init();
                minesweeperView.gamestatus = MinesweeperView.GAMESTATUS.STARTUP;
                minesweeperView.invalidate();
            }
        });
    }

    public void refreshTextViews(){
        totalMinesTxt.setText(minesweeperView.totalMines + " Mines");
        flagsPlacedTxt.setText("Flags set: " + minesweeperView.flagsPlaced);
    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }
}