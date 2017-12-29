package com.master.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.lcd.LCDProcessor;
import com.master.math.activity.lcdmain.LCDMainProcessor;
import com.master.math.activity.util.Util;

public class LCDMainActivity extends AppCompatActivity {

    private LCDProcessor processor;
    private TextView getLCD;
    private Intent intent;
    String lcd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lcdmain);
        this.processor = new LCDProcessor(this);
        LCDCache.get().setFinished(false);
        getLCD = Util.getTextViewWithFont(this, R.id.getLCD);
        getLCD.setTextSize(30);
        intent = new Intent(this, LCDActivity.class);
        intent.putExtra(LCDActivity.NUM_1,Util.getTextViewWithFont(this, R.id.denom1).getText().toString());
        intent.putExtra(LCDActivity.NUM_2,Util.getTextViewWithFont(this, R.id.denom2).getText().toString());
        intent.putExtra(LCDActivity.NUM_3,Util.getTextViewWithFont(this, R.id.denom3).getText().toString());
        lcd = String.valueOf(Util.findLCD(
                Integer.valueOf(Util.getTextViewWithFont(this, R.id.denom1).getText().toString()),
                Integer.valueOf(Util.getTextViewWithFont(this, R.id.denom2).getText().toString()),
                Integer.valueOf(Util.getTextViewWithFont(this, R.id.denom3).getText().toString()),0
        ));
        intent.putExtra(LCDActivity.LCD,lcd);

        if(lcd.equals("0")){
            Util.showWithText(getLCD,"Error. Please Try Again!");
        }else{
            Util.showWithText(getLCD,"Get LCD");
        }
        getLCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getLCD.getText().toString().equals("Get LCD")){
                    startActivity(intent);
                }else{
                    finish();
                }
            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if( LCDCache.get().isFinished()){
            Util.hide(getLCD);
            new LCDMainProcessor(this,lcd);
        }
    }
}
