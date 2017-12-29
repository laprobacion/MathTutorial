package com.master.math.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.addition.AdditionCache;
import com.master.math.activity.util.Util;

import com.master.math.activity.multiply.MultiplyCache;
import com.master.math.activity.multiply.MultiplyProcessor;

import static com.master.math.activity.util.Util.shakeError;

public class MultiplyActivity extends AppCompatActivity  {

    MultiplyProcessor processor;
    RelativeLayout parentMultiply;
    TextView formulaPop;
    EditText userAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multiply);
        userAns = (EditText) findViewById(R.id.userAns);
        parentMultiply = (RelativeLayout) findViewById(R.id.parentMultiply);
        formulaPop = Util.getTextViewWithFont(this,R.id.formulaPop);
        AdditionCache.get().clear();
        //MultiplyCache.getInstance().setNums(1,0,0,1);
        if(MultiplyCache.getInstance().getNums() != null){
            processor = new MultiplyProcessor(this,getAssets(),MultiplyCache.getInstance().getNums());
        }else{
            processor = new MultiplyProcessor(this,getAssets());
        }

        parentMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(processor.isPopupMultiplyVisible()) {
                    if(validateInput() && processor.isFormulaPopAnsCorrect()) {
                        processor.renderPopupWindow(null,false);
                        processor.renderAnswerWindow(true);
                    }else{
                        userAns.startAnimation(shakeError());
                    }
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(AdditionCache.get().getFinalAnswer() !=null){
            finish();
        }
    }

    private boolean validateInput(){
        try{
            if(userAns.getText().toString().trim().equals("")) {
               return false;
            }
            int i = 1 + Integer.valueOf(userAns.getText().toString());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
