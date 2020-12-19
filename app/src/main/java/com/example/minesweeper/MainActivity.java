package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button resetBtn, changeModeBtn;
    MinesweeperView minesweeperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBtn = (Button)findViewById(R.id.resetBtn);
        changeModeBtn = (Button)findViewById(R.id.changeModeBtn);

        minesweeperView = (MinesweeperView)findViewById(R.id.minesweeper);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minesweeperView.minesToPlace = 20;
                minesweeperView.init();
                minesweeperView.gamestatus = MinesweeperView.GAMESTATUS.STARTUP;
                minesweeperView.invalidate();
            }
        });
    }
}