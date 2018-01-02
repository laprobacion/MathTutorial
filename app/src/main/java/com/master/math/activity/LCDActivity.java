package com.master.math.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.master.math.R;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.master.math.activity.util.Util.shakeError;

public class LCDActivity extends AppCompatActivity {

    public static final String NUM_1 = "NUM1";
    public static final String NUM_2 = "NUM2";
    public static final String NUM_3 = "NUM3";
    public static final String NUM_4 = "NUM4";
    public static final String LCD = "LCD";
    private int mul1 = 1;
    private int mulTop1 = 150;
    private int mulLeft1;
    private int mul2 = 1;
    private int mulTop2 = 150;
    private int mulLeft2;
    private int mul3 = 1;
    private int mulTop3 = 150;
    private int mulLeft3;
    private int mul4 = 1;
    private int mulTop4 = 150;
    private int mulLeft4;
    private TextView selected1,selected2,selected3,selected4,lcd,denom1,denom2,denom3,denom4;
    private RelativeLayout lcdLayout;
    private int intLCD;
    private TextView clickedItem;
    private void setLeft(boolean isThree){
        if(isThree){
            mulLeft1 = 250;
            mulLeft2 = 470;
            mulLeft3 = 740;
        }else{
            mulLeft1 = 190;
            mulLeft2 = 400;
            mulLeft3 = 620;
            mulLeft4 = 840;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lcd);
        LCDCache.get().setFinished(false);
        lcdLayout = (RelativeLayout) findViewById(R.id.lcdLayout);
        lcd = Util.getTextViewWithFont(this, R.id.lcd);
        initializeDenominators();
        if(getIntent().getStringExtra(LCD) != null){
            intLCD = Integer.valueOf(getIntent().getStringExtra(LCD));
        }else{
            intLCD = Util.findLCD(
                    Integer.valueOf(denom1.getText().toString()),
                    Integer.valueOf(denom2.getText().toString()),
                    Integer.valueOf(denom3.getText().toString()),
                    Integer.valueOf(denom4.getText().toString())
            );
        }

        setDenom1OnClick();
        setDenom2OnClick();
        setDenom3OnClick();
        setDenom4OnClick();
        isTooLarge();
    }

    private void initializeDenominators(){
        if(getIntent().getStringExtra(NUM_4) != null){
            setLeft(false);
            denom4 = createTextView(getIntent().getStringExtra(NUM_4),mulTop4-50,mulLeft4);
            Util.showWithTextUnderlined(denom4,denom4.getText().toString());
            denom4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openScratch(denom4.getText().toString(), mul4);
                    clickedItem = denom4;
                }
            });
        }else{
            setLeft(true);
        }
        denom1 = createTextView(getIntent().getStringExtra(NUM_1),mulTop1-50,mulLeft1);
        Util.showWithTextUnderlined(denom1,denom1.getText().toString());
        denom2 = createTextView(getIntent().getStringExtra(NUM_2),mulTop2-50,mulLeft2);
        Util.showWithTextUnderlined(denom2,denom2.getText().toString());
        denom3 = createTextView(getIntent().getStringExtra(NUM_3),mulTop3-50,mulLeft3);
        Util.showWithTextUnderlined(denom3,denom3.getText().toString());
        denom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScratch(denom1.getText().toString(), mul1);
                clickedItem = denom1;
            }
        });
        denom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScratch(denom2.getText().toString(), mul2);
                clickedItem = denom2;
            }
        });
        denom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScratch(denom3.getText().toString(), mul3);
                clickedItem = denom3;
            }
        });

    }
    private void openScratch(String denom, int multiplier){
        int ans = Integer.valueOf(denom) * multiplier;
        Intent intent = new Intent(this,FormActivity.class);
        intent.putExtra(FormActivity.OPERATION, FormActivity.OPERATION_LCD_NEXT);
        intent.putExtra(FormActivity.LCD_NEXT_ANS, String.valueOf(ans));
        intent.putExtra(FormActivity.LCD_NEXT_CURRENT_SKIPCOUNT, String.valueOf(Integer.valueOf(denom) * (multiplier - 1)));
        intent.putExtra(FormActivity.LCD_NEXT_DENOMINATOR, denom);
        startActivity(intent);
    }
    private TextView createTextView(String str,int top, int left){
        TextView tv = Util.getTextViewWithFont(new TextView(LCDActivity.this));
        tv.setTextSize(50);
        Util.showWithText(tv,str);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left,top,0,0);
        tv.setLayoutParams(lp);
        lcdLayout.addView(tv);
        return tv;
    }
    private void isTooLarge(){
        int d1 = Integer.valueOf(denom1.getText().toString());
        int d2 = Integer.valueOf(denom2.getText().toString());
        int d3 = Integer.valueOf(denom3.getText().toString());
        int d4 = 0;
        int[] ints = null;
        String strDenom4 = null;
        if(denom4 != null){
            strDenom4 = denom4.getText().toString();
            d4 = Integer.valueOf(denom4.getText().toString());
            ints = new int[4];
            ints[0] = d1;
            ints[1] = d2;
            ints[2] = d3;
            ints[3] = d4;
        }else{
            ints = new int[3];
            ints[0] = d1;
            ints[1] = d2;
            ints[2] = d3;
        }

        Arrays.sort(ints);

        if(Util.isLCDTooLarge(denom1.getText().toString(),denom2.getText().toString(),denom3.getText().toString(),strDenom4,intLCD)){
            Util.showWithText(lcd,"warning too large.");
        }else{
            Util.hide(lcd);
        }
    }
    private void createOnClickListener(final TextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewGroup.LayoutParams lp = tv.getLayoutParams();
                int left = tv.getLeft();
                if(left == mulLeft1){
                    if(selected1 != null){
                        selected1.setTextColor(Color.argb(255, 255, 255, 255));
                    }
                    selected1 = tv;
                    tv.setTextColor(Color.argb(255, 255, 255, 0));
                }else if(left == mulLeft2){
                    if(selected2 != null){
                        selected2.setTextColor(Color.argb(255, 255, 255, 255));
                    }
                    selected2 = tv;
                    tv.setTextColor(Color.argb(255, 255, 255, 0));
                }else if(left == mulLeft3){
                    if(selected3 != null ){
                        selected3.setTextColor(Color.argb(255, 255, 255, 255));
                    }
                    selected3 = tv;
                    tv.setTextColor(Color.argb(255, 255, 255, 0));
                }else if(left == mulLeft4){
                    if(selected4 != null ){
                        selected4.setTextColor(Color.argb(255, 255, 255, 255));
                    }
                    selected4 = tv;
                    tv.setTextColor(Color.argb(255, 255, 255, 0));
                }
                if(isAllSelected()){
                    if(isSelectedCorrect()){
                        LCDCache.get().setFinished(true);
                        finish();
                    }else{
                        shakeErrorSelected(selected1);
                        selected1 = null;
                        shakeErrorSelected(selected2);
                        selected2 = null;
                        shakeErrorSelected(selected3);
                        selected3 = null;
                        if(denom4 != null){
                            shakeErrorSelected(selected4);
                            selected4 = null;
                        }
                        Toast.makeText(LCDActivity.this," Invalid LCD ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void shakeErrorSelected(TextView tv){
        shakeError(tv);
        tv.setTextColor(Color.argb(255, 255, 255, 255));
    }
    private boolean isSelectedCorrect(){
        if(denom4 != null){
            return Integer.valueOf(selected1.getText().toString()) == intLCD &&
                    Integer.valueOf(selected2.getText().toString()) == intLCD
                    && Integer.valueOf(selected3.getText().toString()) == intLCD
                    && Integer.valueOf(selected4.getText().toString()) == intLCD;
        }else{
            return Integer.valueOf(selected1.getText().toString()) == intLCD &&
                    Integer.valueOf(selected2.getText().toString()) == intLCD
                    && Integer.valueOf(selected3.getText().toString()) == intLCD;
        }
    }
    private boolean isAllSelected(){
        if(denom4 != null){
            return selected1 != null && selected2 != null && selected3 != null && selected4 != null;
        }else{
            return selected1 != null && selected2 != null && selected3 != null;
        }
    }
    private void setDenom1OnClick(){
        TextView tv = Util.getTextViewWithFont(new TextView(LCDActivity.this));
        tv.setTextSize(40);
        int num = Integer.valueOf(denom1.getText().toString()) * mul1;
        Util.showWithText(tv,String.valueOf(num));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(mulLeft1,mulTop1+=100,0,0);
        mul1++;
        tv.setLayoutParams(lp);
        lcdLayout.addView(tv);
        createOnClickListener(tv);
    }

    private void setDenom2OnClick(){
        TextView tv = Util.getTextViewWithFont(new TextView(LCDActivity.this));
        tv.setTextSize(40);
        int num = Integer.valueOf(denom2.getText().toString()) * mul2;
        Util.showWithText(tv,String.valueOf(num));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(mulLeft2,mulTop2+=100,0,0);
        mul2++;
        tv.setLayoutParams(lp);
        lcdLayout.addView(tv);
        createOnClickListener(tv);
    }

    private void setDenom3OnClick(){
        TextView tv = Util.getTextViewWithFont(new TextView(LCDActivity.this));
        tv.setTextSize(40);
        int num = Integer.valueOf(denom3.getText().toString()) * mul3;
        Util.showWithText(tv,String.valueOf(num));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(mulLeft3,mulTop3+=100,0,0);
        mul3++;
        tv.setLayoutParams(lp);
        lcdLayout.addView(tv);
        createOnClickListener(tv);
    }
    private void setDenom4OnClick(){
        if(denom4 == null){
            return;
        }
        TextView tv = Util.getTextViewWithFont(new TextView(LCDActivity.this));
        tv.setTextSize(40);
        int num = Integer.valueOf(denom4.getText().toString()) * mul4;
        Util.showWithText(tv,String.valueOf(num));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(mulLeft4,mulTop4+=100,0,0);
        mul4++;
        tv.setLayoutParams(lp);
        lcdLayout.addView(tv);
        createOnClickListener(tv);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(LCDCache.get().isLCDNextFinished() && clickedItem != null){
            if(clickedItem == denom1){
                setDenom1OnClick();
            }else if(clickedItem == denom2){
                setDenom2OnClick();
            }else if(clickedItem == denom3){
                setDenom3OnClick();
            }else if(clickedItem == denom4){
                setDenom4OnClick();
            }
            LCDCache.get().setLCDNextFinished(false);
        }
    }
}
