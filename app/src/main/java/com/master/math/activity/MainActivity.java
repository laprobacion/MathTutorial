package com.master.math.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.SaveState;
import com.master.math.activity.util.Util;

public class MainActivity extends AppCompatActivity {
    Button seatwork, stats, topScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        Util.setAsset(getAssets());
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/EraserDust.ttf");

        Button lesson = (Button) findViewById(R.id.lesson);
        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LessonActivity.class));
            }
        });

        seatwork = (Button) findViewById(R.id.seatwork);
        stats = (Button) findViewById(R.id.stats);
        topScore = (Button) findViewById(R.id.topScore);
        setSeatWorkClick();

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
            }
        });
        topScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopScoreActivity.class));
            }
        });
    }

    public void setSeatWorkClick(){
        if(SaveState.get(this).isSimilarNumeratorDone() && SaveState.get(this).isSimilarDenominatorDone() && SaveState.get(this).isDissimilarFractionDone()){
            seatwork.setBackgroundColor(seatwork.getContext().getResources().getColor(R.color.colorPrimaryDark));
            seatwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SeatworkActivity.class));
                }
            });
        } else {
            seatwork.setBackgroundColor(seatwork.getContext().getResources().getColor(R.color.colorPrimaryDarkFaded));
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        setSeatWorkClick();
    }
}
