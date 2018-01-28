package com.master.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.SaveState;

public class SeatworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatwork);
        Button comparing = (Button) findViewById(R.id.comparing);
        SaveState.get(this).clearAns();
        SaveState.get(this).startSeatwork();
        comparing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatworkActivity.this, FractionActivity.class);
                intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_SEATWORK);
                intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SaveState.get(this).clearAns();
    }
}
