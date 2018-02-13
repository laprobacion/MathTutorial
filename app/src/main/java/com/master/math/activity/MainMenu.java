package com.master.math.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.SaveState;
import com.master.math.activity.util.Util;
//MAIN MENU ACTIVITY
public class MainMenu extends AppCompatActivity {
    Button seatwork, stats,topScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.setAsset(getAssets());
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/EraserDust.ttf");

        Button lesson = (Button) findViewById(R.id.lesson);
        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Lesson2Activity.class));
            }
        });

        seatwork = (Button) findViewById(R.id.seatwork);
        stats = (Button) findViewById(R.id.stats);
        topScore = (Button) findViewById(R.id.topScore);
        setSeatWorkClick();

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, StatsActivity.class));
            }
        });
        topScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, TopScoreActivity.class));
            }
        });

    }

    public void setSeatWorkClick(){
        if(SaveState.get(this).isSimilarNumeratorDone() && SaveState.get(this).isSimilarDenominatorDone() && SaveState.get(this).isDissimilarFractionDone()){
            seatwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainMenu.this, SeatworkActivity.class));
                }
            });
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        setSeatWorkClick();
    }
}
