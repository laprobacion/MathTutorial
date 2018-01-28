package com.master.math.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.util.Item;
import com.master.math.activity.util.SaveState;

import java.util.Date;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        TextView correctAnswers = (TextView) findViewById(R.id.correctAnswers);
        correctAnswers.setText("Number of correct answers: " + SaveState.get(this).getCorrectAnswerCount());
        TextView multiplicationMistakes = (TextView) findViewById(R.id.multiplicationMistakes);
        multiplicationMistakes.setText("Multiplication Mistakes: " + SaveState.get(this).getMultiplicationMistakeCount());
        TextView timeSpent = (TextView) findViewById(R.id.timeSpent);
        timeSpent.setText("Time Spent: " + SaveState.get(this).getTimeSpentString());
    }






}
