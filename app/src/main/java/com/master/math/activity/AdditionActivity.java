package com.master.math.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.master.math.R;
import com.master.math.activity.addition.AdditionCache;
import com.master.math.activity.addition.AdditionProcessor;

public class AdditionActivity extends AppCompatActivity {
    private ConstraintLayout parentAddition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addition);

        parentAddition = (ConstraintLayout) findViewById(R.id.parentAddition);
        final AdditionProcessor processor = new AdditionProcessor(this);

        parentAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(processor.isPopupOpen()){
                    if(processor.isUserAnsCorrect()){
                        processor.showPopup(null,false);
                        processor.setWindowAnswers();
                    }else{
                        processor.shakeUserAns();
                    }
                }
            }
        });
    }
}
