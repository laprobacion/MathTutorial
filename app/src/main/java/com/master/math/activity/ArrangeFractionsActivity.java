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

import java.util.Arrays;

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
    public static final int arrange1Id=1004,arrange2Id=2004,arrange3Id=3004,arrange4Id=4004;
    public static int tvAnsId;
    private TextView tvAns,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,lcdDenom1,lcdDenom2,lcdDenom3,lcdDenom4,num1,denom1,num2,denom2,num3,denom3,num4,denom4;
    private TextView arrange1,arrange2,arrange3,arrange4,label1,label2,label3,label4,getLCD;
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
        lp.setMargins(ansLeft - 70,1300,0,0);

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
        if(validator.getActionStep().getStep() > ActionStep.STEP_10){
            Util.showWithText(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
            draggedItem.getItem(1).setTextSize(40);
        }else{
            Util.showWithText(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
        }
        Util.showWithTextUnderlined(draggedItem.getItem(1),draggedItem.getItem(1).getText().toString());
        Util.hide(draggedItem.getItem(0));
        validator.getActionStep().increment();
        if((validator.getActionStep().getStep() == ActionStep.STEP_10 && denom4 == null) || validator.getActionStep().getStep() == ActionStep.STEP_13){
            setArrange();
            validator.getActionStep().setStep(ActionStep.STEP_13);
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
        if(ans.length() == 2){
            tvAns.setTextSize(50);
        }else{
            tvAns.setTextSize(70);
        }

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
        initializer.setDraggables(num1,num2,num3,num4,denom1,denom2,denom3,denom4,lcdDenom1,
                lcdDenom2,lcdDenom3,lcdDenom4,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,tvAns);

    }

    private void setArrange(){
        int top = 700;
        int labelTop = top - 60;
        int arrangeLeft = 60;
        label1 = createTextView(1, "1", 20,labelTop,left1,false,arrangeLayout,this);
        arrange1 = createTextView(arrange1Id, " ", 70,top,left1-arrangeLeft,false,arrangeLayout,this);
        Util.showWithBG(arrange1,this);
        label2 = createTextView(2, "2", 20,labelTop,left2,false,arrangeLayout,this);
        arrange2 = createTextView(arrange2Id, " ", 70,top,left2-arrangeLeft,false,arrangeLayout,this);
        Util.showWithBG(arrange2,this);
        label3 = createTextView(3, "3", 20,labelTop,left3,false,arrangeLayout,this);
        arrange3 = createTextView(arrange3Id, " ", 70,top,left3-arrangeLeft,false,arrangeLayout,this);
        Util.showWithBG(arrange3,this);
        if(denom4 != null){
            label4 = createTextView(4, "4", 20,labelTop,left4,false,arrangeLayout,this);
            arrange4 = createTextView(arrange4Id, " ", 70,top,left4-arrangeLeft,false,arrangeLayout,this);
            Util.showWithBG(arrange4,this);
        }
        initializer.setDraggables(arrange1,arrange2,arrange3,arrange4);
        refreshNumDenom();
        validator.getActionStep().increment();
    }
    private void refreshNumDenom(){
        Util.showWithText(denom1,num1.getText().toString() + "/" + denom1.getText().toString());
        denom1.setTextSize(40);
        denom1.setLeft(left1 - 20);
        Util.showWithText(denom2,num2.getText().toString() + "/" + denom2.getText().toString());
        denom2.setTextSize(40);
        denom2.setLeft(left2 - 20);
        Util.showWithText(denom3,num3.getText().toString() + "/" + denom3.getText().toString());
        denom3.setTextSize(40);
        denom3.setLeft(left3 - 20);
        Util.hide(num1);
        Util.hide(num2);
        Util.hide(num3);
        if(denom4 != null){
            Util.hide(num4);
            Util.showWithText(denom4,num4.getText().toString() + "/" + denom4.getText().toString());
            denom4.setTextSize(40);
            denom4.setLeft(left4 - 20);
        }
        sortFractions();
    }
    private int[] sortOrder(){
        int d1 = Integer.valueOf(lcmDenom1.getText().toString());
        int d2 = Integer.valueOf(lcmDenom2.getText().toString());
        int d3 = Integer.valueOf(lcmDenom3.getText().toString());
        int d4 = 0;
        int[] ints = null;
        if(denom4 != null){
            d4 = Integer.valueOf(lcmDenom4.getText().toString());
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
        return ints;
    }
    private TextView[] setLCMDenoms(){
        TextView [] lcms = new TextView[4];
        lcms[0] = lcmDenom1;
        lcms[1] = lcmDenom2;
        lcms[2] = lcmDenom3;
        lcms[3] = lcmDenom4;
        return lcms;
    }
    private TextView[] setNumDenoms(){
        TextView [] lcms = new TextView[4];
        lcms[0] = denom1;
        lcms[1] = denom2;
        lcms[2] = denom3;
        lcms[3] = denom4;
        return lcms;
    }
    private void sortFractions(){
        TextView [] lcms = new TextView[4];
        int i = 0;
        sort : for(int num : sortOrder()){
            for( TextView tv : setLCMDenoms()){
                if(tv != null && String.valueOf(num).equals(tv.getText().toString())){
                    int id = tv.getId();
                    if( id == lcm1Id ){
                        lcms[i] = denom1;
                    }else if(id == lcm2Id){
                        lcms[i] = denom2;
                    }else if(id == lcm3Id){
                        lcms[i] = denom3;
                    }else if(id == lcm4Id){
                        lcms[i] = denom4;
                    }
                    if(i >= 3){
                        break sort;
                    }
                    i++;
                }
            }
        }
        validator.setSorted(lcms);
    }
}
