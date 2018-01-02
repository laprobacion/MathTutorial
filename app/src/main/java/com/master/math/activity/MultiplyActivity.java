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
    TextView formulaPop;
    EditText userAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multiply);
        userAns = (EditText) findViewById(R.id.userAns);
        formulaPop = Util.getTextViewWithFont(this,R.id.formulaPop);
        AdditionCache.get().clear();
        //MultiplyCache.getInstance().setNums(1,0,0,1);
        if(MultiplyCache.getInstance().getNums() != null){
            processor = new MultiplyProcessor(this,getAssets(),MultiplyCache.getInstance().getNums());
        }else{
            processor = new MultiplyProcessor(this,getAssets());
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(processor.isReady()){
            processor.next();
        }
    }

    @Override
    public void onBackPressed() {
        MultiplyCache.getInstance().clear();
        super.onBackPressed();
    }
}
