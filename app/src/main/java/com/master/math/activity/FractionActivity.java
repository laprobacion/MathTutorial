package com.master.math.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;

import com.master.math.activity.fraction.CompareFractionProcessor;

public class FractionActivity extends AppCompatActivity {
    CompareFractionProcessor processor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fraction);
        this.processor = new CompareFractionProcessor(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(this.processor != null && this.processor.isReady()){
            if(this.processor.getStep() == ActionStep.STEP_1 || this.processor.getStep() == ActionStep.STEP_2){
                this.processor.execute();
            }
        }
    }
}
