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
import com.master.math.activity.arrange.RelativeLayoutListener;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.divide.DivideCache;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.lcdmain.LCDMainProcessor;
import com.master.math.activity.multiply.MultiplyCache;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import java.util.Arrays;
import java.util.Comparator;

import static com.master.math.activity.util.Util.createTextView;

public class ArrangeFractionsActivity extends AppCompatActivity {

    private RelativeLayout arrangeLayout;
    private RelativeLayout ansLayout,groupLayout1,groupLayout2,groupLayout3,groupLayout4,arrange1,arrange2,arrange3,arrange4;
    private int numTop = 150;
    private int denomTop = 300;
    private int left1;
    private int left2;
    private int left3;
    private int left4;
    private int ansLeft;
    private Intent intent;
    public static int num1Id=1000,num2Id=2000,num3Id=3000,num4Id=4000;
    public static int denom1Id=1001,denom2Id=2001,denom3Id=3001,denom4Id=4001;
    public static int lcd1Id=1002,lcd2Id=2002,lcd3Id=3002,lcd4Id=4002;
    public static final int lcm1Id=1003,lcm2Id=2003,lcm3Id=3003,lcm4Id=4003;
    public static int arrange1Id=1004,arrange2Id=2004,arrange3Id=3004,arrange4Id=4004;
    public static int groupLayout1Id,groupLayout2Id,groupLayout3Id,groupLayout4Id;
    public static int tvAnsId;
    private TextView tvAns,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,lcdDenom1,lcdDenom2,lcdDenom3,lcdDenom4,num1,denom1,num2,denom2,num3,denom3,num4,denom4;
    private TextView label1,label2,label3,label4,getLCD;
    private Initializer initializer;
    private ArrangeValidator validator;
    private ArrangeProcessor processor;
    private RelativeLayoutListener layoutListener;
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

        ansLayout = new RelativeLayout(this);
        ansLayout.setBackground(this.getResources().getDrawable(R.drawable.text_bg));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = 200;
        lp.width = 200;
        lp.setMargins(ansLeft - 70,1300,0,0);

        ansLayout.setLayoutParams(lp);
        arrangeLayout.addView(ansLayout);

        tvAns = Util.getTextViewWithFont(new TextView(this));
        tvAnsId = View.generateViewId();
        tvAns.setId(tvAnsId);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvAns.setLayoutParams(lpt);
        tvAns.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ansLayout.addView(tvAns);
        arrangeLayout.invalidate();
        getLCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setIntent());
            }
        });
    }

    private RelativeLayout createRelativeLayout(int left, int top,boolean withBG){
        RelativeLayout layout = new RelativeLayout(this);
        if(withBG){
            layout.setBackground(this.getResources().getDrawable(R.drawable.text_bg));
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = 300;
        lp.width = 200;
        lp.setMargins(left,top,0,0);

        layout.setLayoutParams(lp);
        arrangeLayout.addView(layout);
        return layout;
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
            createCustomTextView4(Util.generate1ProperFraction());
        }
        createCustomTextView1(Util.generate1ProperFraction());
        createCustomTextView2(Util.generate1ProperFraction());
        createCustomTextView3(Util.generate1ProperFraction());
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
        Util.showWithTextUnderlined(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
        Util.hide(draggedItem.getItem(0));
        validator.getActionStep().increment();
        if((validator.getActionStep().getStep() == ActionStep.STEP_10 && denom4 == null) || validator.getActionStep().getStep() == ActionStep.STEP_13){
            setArrange();
            validator.getActionStep().setStep(ActionStep.STEP_13);
            validator.removeListeners();
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
        layoutListener = new RelativeLayoutListener(validator.getActionStep());
        initializer = new Initializer(new ArrangeListener(processor,validator));
        initializer.setDraggables(num1,num2,num3,num4,denom1,denom2,denom3,denom4,lcdDenom1,
                lcdDenom2,lcdDenom3,lcdDenom4,lcmDenom1,lcmDenom2,lcmDenom3,lcmDenom4,tvAns);

    }

    private void setArrange(){
        int top = 650;
        int labelTop = top - 60;
        int arrangeLeft = 60;
        label1 = createTextView(1, "1", 20,labelTop,left1,false,arrangeLayout,this);
        arrange1 = createRelativeLayout(left1-arrangeLeft,top,true) ;
        arrange1Id = View.generateViewId();
        arrange1.setId(arrange1Id);
        label2 = createTextView(2, "2", 20,labelTop,left2,false,arrangeLayout,this);
        arrange2 = createRelativeLayout(left2-arrangeLeft,top,true) ;
        arrange2Id = View.generateViewId();
        arrange2.setId(arrange2Id);

        label3 = createTextView(3, "3", 20,labelTop,left3,false,arrangeLayout,this);
        arrange3 = createRelativeLayout(left3-arrangeLeft,top,true) ;
        arrange3Id = View.generateViewId();
        arrange3.setId(arrange3Id);
        if(denom4 != null){
            label4 = createTextView(4, "4", 20,labelTop,left4,false,arrangeLayout,this);
            arrange4 = createRelativeLayout(left4-arrangeLeft,top,true) ;
            arrange4Id = View.generateViewId();
            arrange4.setId(arrange4Id);
        }
        refreshNumDenom();
        validator.getActionStep().increment();
        initializer.setListeners(layoutListener,arrange1,arrange2,arrange3,arrange4, groupLayout1,groupLayout2,groupLayout3,groupLayout4);
        layoutListener.addDraggables(arrange1,arrange2,arrange3,arrange4, groupLayout1,groupLayout2,groupLayout3,groupLayout4);
    }
    private void refreshNumDenom(){
        sortFractions();
    }
    private TextView[] sortOrder(){
        TextView[] tvs = null;
        if(denom4 != null){
            tvs = new TextView[4];
            tvs[3] = lcmDenom4;
        }else{
            tvs = new TextView[3];
        }
        tvs[0] = lcmDenom1;
        tvs[1] = lcmDenom2;
        tvs[2] = lcmDenom3;

        Arrays.sort(tvs,new TVTextComparator());
        return tvs;
    }
    class TVTextComparator implements Comparator<TextView> {
        @Override
        public int compare(TextView lhs, TextView rhs) {
            return Integer.valueOf(lhs.getText().toString()).compareTo(Integer.valueOf(rhs.getText().toString()));
        }
    }

    private TextView[] setNumDenoms(){
        TextView [] lcms = null;
        if(denom4 != null){
            lcms = new TextView[4];
            lcms[3] = denom4;
        }else{
            lcms = new TextView[3];
        }
        lcms[0] = denom1;
        lcms[1] = denom2;
        lcms[2] = denom3;
        return lcms;
    }
    private void sortFractions(){
        RelativeLayout [] lcms = new RelativeLayout[4];
        TextView [] sorted = sortOrder();
        int i = 0;
        sort : for(TextView tv1 : sorted){
            sort2 : for( TextView tv2 : setNumDenoms()){
                        int id = tv1.getId();
                        if( id == lcm1Id ){
                            lcms[i] = groupLayout1;
                            i++;
                            break sort2;
                        }else if(id == lcm2Id){
                            lcms[i] = groupLayout2;
                            i++;
                            break sort2;
                        }else if(id == lcm3Id){
                            lcms[i] = groupLayout3;
                            i++;
                            break sort2;
                        }else if(id == lcm4Id){
                            lcms[i] = groupLayout4;
                            i++;
                            break sort2;
                        }
                }
            }
        layoutListener.setSorted(lcms);
    }

    private void createCustomTextView1(String[] fraction){
        groupLayout1 = createRelativeLayout(left1-50, numTop,false);
        groupLayout1Id = View.generateViewId();
        groupLayout1.setId(groupLayout1Id);
        num1 = Util.getTextViewWithFont(new TextView(this));
        num1.setTextSize(50);
        Util.showWithTextUnderlined(num1,fraction[0]);
        num1Id = View.generateViewId();
        num1.setId(num1Id);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        num1.setLayoutParams(lpt);
        num1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout1.addView(num1);

        denom1 = Util.getTextViewWithFont(new TextView(this));
        denom1.setTextSize(50);
        Util.showWithText(denom1,fraction[1]);
        denom1Id = View.generateViewId();
        denom1.setId(denom1Id);
        RelativeLayout.LayoutParams lpd = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpd.topMargin = 130;
        denom1.setLayoutParams(lpd);
        denom1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout1.addView(denom1);

    }

    private void createCustomTextView2(String[] fraction){
        groupLayout2 = createRelativeLayout(left2-50, numTop,false);
        groupLayout2Id = View.generateViewId();
        groupLayout2.setId(groupLayout2Id);
        num2 = Util.getTextViewWithFont(new TextView(this));
        num2.setTextSize(50);
        Util.showWithTextUnderlined(num2,fraction[0]);
        num2Id = View.generateViewId();
        num2.setId(num2Id);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        num2.setLayoutParams(lpt);
        num2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout2.addView(num2);

        denom2 = Util.getTextViewWithFont(new TextView(this));
        denom2.setTextSize(50);
        Util.showWithText(denom2,fraction[1]);
        denom2Id = View.generateViewId();
        denom2.setId(denom2Id);
        RelativeLayout.LayoutParams lpd = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpd.topMargin = 130;
        denom2.setLayoutParams(lpd);
        denom2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout2.addView(denom2);
    }
    private void createCustomTextView3(String[] fraction){
        groupLayout3 = createRelativeLayout(left3-50, numTop,false);
        groupLayout3Id = View.generateViewId();
        groupLayout3.setId(groupLayout3Id);
        num3 = Util.getTextViewWithFont(new TextView(this));
        num3.setTextSize(50);
        Util.showWithTextUnderlined(num3,fraction[0]);
        num3Id = View.generateViewId();
        num3.setId(num3Id);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        num3.setLayoutParams(lpt);
        num3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout3.addView(num3);

        denom3 = Util.getTextViewWithFont(new TextView(this));
        denom3.setTextSize(50);
        Util.showWithText(denom3,fraction[1]);
        denom3Id = View.generateViewId();
        denom3.setId(denom3Id);
        RelativeLayout.LayoutParams lpd = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpd.topMargin = 130;
        denom3.setLayoutParams(lpd);
        denom3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout3.addView(denom3);
    }
    private void createCustomTextView4(String[] fraction){
        groupLayout4 = createRelativeLayout(left4-50, numTop, false);
        groupLayout4Id = View.generateViewId();
        groupLayout4.setId(groupLayout4Id);
        num4 = Util.getTextViewWithFont(new TextView(this));
        num4.setTextSize(50);
        Util.showWithTextUnderlined(num4,fraction[0]);
        num4Id = View.generateViewId();
        num4.setId(num4Id);
        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        num4.setLayoutParams(lpt);
        num4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout4.addView(num4);

        denom4 = Util.getTextViewWithFont(new TextView(this));
        denom4.setTextSize(50);
        Util.showWithText(denom4,fraction[1]);
        denom4Id = View.generateViewId();
        denom4.setId(denom4Id);
        RelativeLayout.LayoutParams lpd = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpd.topMargin = 130;
        denom4.setLayoutParams(lpd);
        denom4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        groupLayout4.addView(denom4);
    }
}
