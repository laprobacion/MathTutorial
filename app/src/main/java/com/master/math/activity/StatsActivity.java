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
        correctAnswers.setText("Number of correct answers: " + getNumberOfCorrectAnswers());
        TextView multiplicationMistakes = (TextView) findViewById(R.id.multiplicationMistakes);
        multiplicationMistakes.setText("Multiplication Mistakes: " + getMultiplicationMistakes());
        TextView timeSpent = (TextView) findViewById(R.id.timeSpent);
        timeSpent.setText("Time Spent: " + getTimeSpent());
    }

    private int getNumberOfCorrectAnswers(){
        int ctr = 0;
        if(SaveState.get(this).getItems() == null){
            return 0;
        }
        for(Item i : SaveState.get(this).getItems()){
            if(i.isCorrect()){
                ctr++;
            }
        }
        return ctr;
    }

    private int getMultiplicationMistakes(){
        return SaveState.get(this).getMultiplicationMistakes();
    }

    private String getTimeSpent(){
        if (SaveState.get(this).startTime == null || SaveState.get(this).endTime == null) {
            return "";
        }
        Date start = SaveState.get(this).startTime;
        Date end = SaveState.get(this).endTime;
        long diff = start.getTime() - end.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return " " + diffSeconds + " seconds";
    }
}
