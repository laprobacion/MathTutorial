package com.master.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.SaveState;

import static com.master.math.activity.util.Util.showGif;

public class ComparingActivity extends AppCompatActivity {
    Button similarDenominators, similarNumerators, dissimilarFractions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing);

        similarDenominators = (Button) findViewById(R.id.similarDenominators);
        similarNumerators = (Button) findViewById(R.id.similarNumerators);
        dissimilarFractions = (Button) findViewById(R.id.dissimilarFractions);
        setFirstOnclick();
        setSecondOnclick();
        setThirdOnclick();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setFirstOnclick();
        setSecondOnclick();
        setThirdOnclick();
        SaveState.get(this).clearAns();
    }

    private void setFirstOnclick(){
        similarDenominators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_1);
                startActivity(lesson1Intent);
                showGif(0,ComparingActivity.this);
            }
        });
    }

    private void setSecondOnclick(){
        if(SaveState.get(this).isSimilarDenominatorDone()){
            similarNumerators.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_2);
                    startActivity(lesson1Intent);
                    showGif(0,ComparingActivity.this);
                }
            });
        }
    }

    private void setThirdOnclick(){
        if(SaveState.get(this).isSimilarNumeratorDone()){
            dissimilarFractions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_3);
                    startActivity(lesson1Intent);
                    showGif(0,ComparingActivity.this);
                }
            });
        }
    }
}
