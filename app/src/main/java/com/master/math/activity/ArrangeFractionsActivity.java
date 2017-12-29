package com.master.math.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.arrange.ArrangeCache;
import com.master.math.activity.arrange.ArrangeListener;
import com.master.math.activity.arrange.ArrangeProcessor;
import com.master.math.activity.arrange.ArrangeValidator;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.divide.DivideCache;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.lcdmain.LCDMainProcessor;
import com.master.math.activity.multiply.MultiplyCache;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import static com.master.math.activity.util.Util.createTextView;

public class ArrangeFractionsActivity extends AppCompatActivity {

    private RelativeLayout arrangeLayout;
    private RelativeLayout ansLayout;
    private int numTop = 150;
    private int denomTop = 300;
    private int left1;
    private int left2;
    private int left3;
    private int left4;
    private int ansLeft;
    private Intent intent;
    public static final int num1Id=1000,num2Id=2000,num3Id=3000,num4Id=4000;
    public static final int denom1Id=1001,denom2Id=2001,denom3Id=3001,denom4Id=4001;
    public static final int lcd1Id=1002,lcd2Id=2002,lcd3Id=3002,lcd4Id=4002;
    public static final int lcm1Id=1003,lcm2Id=2003,lcm3Id=3003,lcm4Id=4003;
    public static int tvAnsId;
    private TextView tvAns,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,lcdDenom1,lcdDenom2,lcdDenom3,lcdDenom4,num1,denom1,num2,denom2,num3,denom3,num4,denom4;
    private TextView arrange1,arrange2,arrange3,arrange4,getLCD;
    private Initializer initializer;
    private ArrangeValidator validator;
    private ArrangeProcessor processor;
    private static final String LCD_TEXT = "Too Large! Generate Again.";
    int lcd;
    private void setLeft(boolean isThree){
        if(isThree){
            left1 = 300;
            left2 = 525;
            left3 = 750;
            ansLeft = 525;
        }else{
            left1 = 190;
            left2 = 400;
            left3 = 620;
            left4 = 840;
            ansLeft = 510;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_arrange_fractions);
        LCDCache.get().setFinished(false);
        arrangeLayout = (RelativeLayout) findViewById(R.id.arrangeLayout);
        initializeFractions();
        reStartActivity();
        getLCD = Util.getTextViewWithFont(this,R.id.getLCD);

        RelativeLayout rl = new RelativeLayout(this);
        rl.setBackground(this.getResources().getDrawable(R.drawable.text_bg));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = 200;
        lp.width = 200;
        lp.setMargins(ansLeft - 30,1300,0,0);

        rl.setLayoutParams(lp);
        arrangeLayout.addView(rl);

        tvAns = Util.getTextViewWithFont(new TextView(this));
        tvAnsId = View.generateViewId();
        tvAns.setId(tvAnsId);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvAns.setLayoutParams(lpt);
        tvAns.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rl.addView(tvAns);
        arrangeLayout.invalidate();
        getLCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setIntent());
            }
        });
    }

    private void reStartActivity(){
        String d4 = null;
        if(denom4 != null){
            d4 = denom4.getText().toString();
        }
        if(Util.isLCDTooLarge(denom1.getText().toString(),denom2.getText().toString(),denom3.getText().toString(),d4,lcd)){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
    private Intent setIntent(){
        Intent intent = new Intent(this,FormActivity.class);
        String denoms = denom1.getText().toString() + ", " + denom2.getText().toString() + ", " + denom3.getText().toString();
        if(denom4 != null){
            denoms += ", " + denom4.getText().toString();
        }
        intent.putExtra(FormActivity.OPERATION,FormActivity.OPERATION_LCD);
        intent.putExtra(FormActivity.LCD_DENOMINATORS,denoms);
        intent.putExtra(FormActivity.LCD_ANS,String.valueOf(lcd));
        return intent;
    }
    private void initializeFractions(){
        if((Integer.valueOf(Util.generate2DigsRandomNumbers(10)) % 2) == 0){
            setLeft(true);
        }else{
            setLeft(false);
            String [] group4 = Util.generate1ProperFraction();
            num4 = createTextView(num4Id,group4[0],50, numTop, left4,true,arrangeLayout,this);
            denom4 = createTextView(denom4Id,group4[1],50, denomTop, left4,false,arrangeLayout,this);
        }
        String [] group1 = Util.generate1ProperFraction();
        num1 = createTextView(num1Id,group1[0], 50,numTop, left1,true,arrangeLayout,this);
        denom1 = createTextView(denom1Id,group1[1], 50,denomTop, left1,false,arrangeLayout,this);
        String [] group2 = Util.generate1ProperFraction();
        num2 = createTextView(num2Id,group2[0], 50,numTop, left2,true,arrangeLayout,this);
        denom2 = createTextView(denom2Id,group2[1],50, denomTop, left2,false,arrangeLayout,this);
        String [] group3 = Util.generate1ProperFraction();
        num3 = createTextView(num3Id,group3[0], 50,numTop, left3,true,arrangeLayout,this);
        denom3 = createTextView(denom3Id,group3[1],50, denomTop, left3,false,arrangeLayout,this);
        if(denom4 != null){
            lcd = Util.findLCD(
                    Integer.valueOf(denom1.getText().toString()),
                    Integer.valueOf(denom2.getText().toString()),
                    Integer.valueOf(denom3.getText().toString()),
                    Integer.valueOf(denom4.getText().toString())
            );
        }else{
            lcd = Util.findLCD(
                    Integer.valueOf(denom1.getText().toString()),
                    Integer.valueOf(denom2.getText().toString()),
                    Integer.valueOf(denom3.getText().toString()),
                    0
            );
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if( LCDCache.get().isFinished()){
            Util.hide(getLCD);
            setAns();
            addLCDs();
        }
    }
    public void setLCM(DraggedItem draggedItem){
        Util.showWithText(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
        Util.showWithTextUnderlined(draggedItem.getItem(1),draggedItem.getItem(1).getText().toString());
        Util.hide(draggedItem.getItem(0));
        validator.getActionStep().increment();
        if(validator.getActionStep().getStep() == ActionStep.STEP_10){

        }
    }
    private void setAns(){
        if(lcdDenom1 == null){ return;}
        String ans = "";
        if (DivideCache.get().getAns() != null) {
            ans = String.valueOf(DivideCache.get().getAns());
        } else {
            ans = MultiplyCache.getInstance().getFinalAns();
        }
        Util.showWithText(tvAns, ans);
        tvAns.setTextSize(70);
        //tvAns.setBackground(this.getResources().getDrawable(R.drawable.text_bg));
        validator.getActionStep().increment();
    }
    private void addLCDs(){
        if(lcdDenom1 != null){
            return;
        }
        int top = 1100;
        int lcmTop = 990;

        lcdDenom1 = createTextView(lcd1Id,String.valueOf(lcd),50,top,left1,false,arrangeLayout,this);
        lcdDenom2 = createTextView(lcd2Id,String.valueOf(lcd),50,top,left2,false,arrangeLayout,this);
        lcdDenom3 = createTextView(lcd3Id,String.valueOf(lcd),50,top,left3,false,arrangeLayout,this);
        lcmDenom1 = createTextView(lcm1Id,"___",50,lcmTop,left1,false,arrangeLayout,this);
        lcmDenom2 = createTextView(lcm2Id,"___",50,lcmTop,left2,false,arrangeLayout,this);
        lcmDenom3 = createTextView(lcm3Id,"___",50,lcmTop,left3,false,arrangeLayout,this);
        Util.hide(tvAns);
        if(denom4 != null){
            lcdDenom4 = createTextView(lcd4Id,String.valueOf(lcd),50,top,left4,false,arrangeLayout,this);
            lcmDenom4 = createTextView(lcm4Id,"__",50,lcmTop,left4,false,arrangeLayout,this);
        }
        validator = new ArrangeValidator();
        processor = new ArrangeProcessor(this,validator);
        initializer = new Initializer(new ArrangeListener(processor,validator));
        initializer.setDraggables(num1,num2,num3,num4,denom1,denom2,denom3,denom4,lcdDenom1,lcdDenom2,lcdDenom3,lcdDenom4,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,tvAns);

    }
}
