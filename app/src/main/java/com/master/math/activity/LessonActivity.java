package com.master.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.SaveState;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);

        Button comparing = (Button) findViewById(R.id.comparing);
        comparing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonActivity.this, ComparingActivity.class));
            }
        });

        Button arranging = (Button) findViewById(R.id.arranging);
        if(false) { //TO BE UPDATED
            arranging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LessonActivity.this, ArrangeFractionsActivity.class));
                }
            });
        } else {
            arranging.setBackgroundColor(arranging.getContext().getResources().getColor(R.color.colorPrimaryDarkFaded));
        }
    }
}
