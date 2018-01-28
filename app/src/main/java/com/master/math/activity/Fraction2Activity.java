package com.master.math.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.master.math.R;
import com.master.math.activity.fraction.CompareFractionProcessor;

public class Fraction2Activity extends AppCompatActivity {
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    public static final String ACTIVITY_LESSON = "ACTIVITY_LESSON";
    public static final String ACTIVITY_SEATWORK = "ACTIVITY_SEATWORK";
    public static final String LESSON_1 = "LESSON_1";
    public static final String LESSON_2 = "LESSON_2";
    public static final String LESSON_3 = "LESSON_3";

    CompareFractionProcessor processor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction2);
        String type = getIntent().getStringExtra(ACTIVITY_TYPE);
        if(type.equals(ACTIVITY_LESSON)){
            if(getIntent().getStringExtra(ACTIVITY_LESSON).equals(LESSON_1)){
                this.processor = new CompareFractionProcessor(this, true);
                this.processor.setLesson1();
            }else if(getIntent().getStringExtra(ACTIVITY_LESSON).equals(LESSON_2)){
                this.processor = new CompareFractionProcessor(this, true);
                this.processor.setLesson2();
            }else{
                this.processor = new CompareFractionProcessor(this, false);
                this.processor.setLesson3();
            }
        }else{
            this.processor = new CompareFractionProcessor(this, false);
            this.processor.setSeatwork(getIntent().getStringExtra(ACTIVITY_LESSON));
        }
    }
}
