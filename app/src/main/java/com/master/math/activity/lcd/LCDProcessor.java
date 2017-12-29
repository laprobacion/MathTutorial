package com.master.math.activity.lcd;


import android.app.Activity;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.util.Util;

import java.util.ArrayList;
import java.util.List;

public class LCDProcessor {

    private Activity activity;

    private TextView num1,denom1,num2,denom2,num3,denom3,finalDenom1,finalDenom2,finalDenom3;
    public LCDProcessor(Activity activity){
        this.activity = activity;
        num1 = Util.getTextViewWithFont(activity, R.id.num1);
        denom1 = Util.getTextViewWithFont(activity, R.id.denom1);
        finalDenom1 = Util.getTextViewWithFontInvisible(activity, R.id.finalDenom1);
        num2 = Util.getTextViewWithFont(activity, R.id.num2);
        denom2 = Util.getTextViewWithFont(activity, R.id.denom2);
        finalDenom2 = Util.getTextViewWithFontInvisible(activity, R.id.finalDenom2);
        num3 = Util.getTextViewWithFont(activity, R.id.num3);
        denom3 = Util.getTextViewWithFont(activity, R.id.denom3);
        finalDenom3 = Util.getTextViewWithFontInvisible(activity, R.id.finalDenom3);
        setFractions();
    }
    private void setFractions(){
        String [][] fractions = Util.generateProperFraction();
        Util.showWithTextUnderlined(num1,fractions[0][0]);
        Util.showWithText(denom1,fractions[0][1]);
        Util.showWithTextUnderlined(num2,fractions[1][0]);
        Util.showWithText(denom2,fractions[1][1]);
        Util.showWithTextUnderlined(num3,fractions[2][0]);
        Util.showWithText(denom3,fractions[2][1]);
    }

}
