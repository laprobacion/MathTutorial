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
        setContentView(R.layout.activity_comparing2);

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
                showGif(R.drawable.outro,ComparingActivity.this);
                Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_1);
                startActivity(lesson1Intent);
                showGif(R.drawable.sdstep,ComparingActivity.this);
                showGif(R.drawable.sdintro,ComparingActivity.this);
            }
        });
    }

    private void setSecondOnclick(){
        if(SaveState.get(this).isSimilarDenominatorDone()){
            similarNumerators.setBackgroundColor(similarNumerators.getContext().getResources().getColor(R.color.colorPrimaryDark));
            similarNumerators.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showGif(R.drawable.outro,ComparingActivity.this);
                    Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_2);
                    startActivity(lesson1Intent);
                    showGif(R.drawable.snstep,ComparingActivity.this);
                    showGif(R.drawable.snintro,ComparingActivity.this);
                }
            });
        } else {
            similarNumerators.setBackgroundColor(similarNumerators.getContext().getResources().getColor(R.color.colorPrimaryDarkFaded));
        }
    }

    private void setThirdOnclick(){
        if(SaveState.get(this).isSimilarNumeratorDone()){
            dissimilarFractions.setBackgroundColor(dissimilarFractions.getContext().getResources().getColor(R.color.colorPrimaryDark));
            dissimilarFractions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showGif(R.drawable.outro,ComparingActivity.this);
                    Intent lesson1Intent = new Intent(ComparingActivity.this, FractionActivity.class);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,FractionActivity.ACTIVITY_LESSON);
                    lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_3);
                    startActivity(lesson1Intent);
                    showGif(R.drawable.dfstep1,ComparingActivity.this);
                    showGif(R.drawable.dfintro,ComparingActivity.this);
                }
            });
        } else {
            dissimilarFractions.setBackgroundColor(dissimilarFractions.getContext().getResources().getColor(R.color.colorPrimaryDarkFaded));
        }
    }
}
