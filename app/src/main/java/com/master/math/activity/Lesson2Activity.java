package com.master.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.master.math.R;

public class Lesson2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);

        Button comparing = (Button) findViewById(R.id.comparing);
        comparing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lesson2Activity.this, Comparing2Activity.class));
            }
        });

        Button arranging = (Button) findViewById(R.id.arranging);
        arranging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lesson2Activity.this, ArrangeFractionsActivity.class));
            }
        });
    }
}
