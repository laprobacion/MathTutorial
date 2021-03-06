package com.master.math.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.addition.AdditionCache;
import com.master.math.activity.divide.DivideCache;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.util.SaveState;
import com.master.math.activity.util.Util;

import com.master.math.activity.multiply.MultiplyCache;

import static com.master.math.activity.util.Util.shakeError;
import static com.master.math.activity.util.Util.showGif;

public class FormActivity extends AppCompatActivity {

    RelativeLayout form;ConstraintLayout form2;
    TextView formula,open;
    Button doneClick;
    EditText userAns;
    private int num1;
    private int num2;
    public static final String MULTIPLY_NUM_1 = "MULTIPLY_NUM_1";
    public static final String MULTIPLY_NUM_2 = "MULTIPLY_NUM_2";
    public static final String ADDITION_NUM_1 = "ADDITION_NUM_1";
    public static final String ADDITION_NUM_2 = "ADDITION_NUM_2";
    public static final String DIVIDE_NUM_1 = "DIVIDE_NUM_1";
    public static final String DIVIDE_NUM_2 = "DIVIDE_NUM_2";
    public static final String LCD_ANS = "LCD_ANS";
    public static final String LCD_DENOMINATORS = "LCD_DENOMINATORS";
    public static final String LCD_NEXT_ANS = "LCD_NEXT_ANS";
    public static final String LCD_NEXT_DENOMINATOR = "LCD_NEXT_DENOMINATOR";
    public static final String LCD_NEXT_CURRENT_SKIPCOUNT = "LCD_NEXT_CURRENT_SKIPCOUNT";
    public static final String OPERATION = "OPERATION";
    public static final String OPERATION_LCD_NEXT = "LCDNEXT";
    public static final String OPERATION_LCD = "LCD";
    public static final String OPERATION_MULTIPLY = "MULTIPLY";
    public static final String OPERATION_DIVIDE = "DIVIDE";
    public static final String OPERATION_ADDITION = "ADDITION";
    public static final String SEATWORK_ANSWER = "SEATWORK_ANSWER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form2);
        form = (RelativeLayout)findViewById(R.id.parentForm);//form2 = (ConstraintLayout) findViewById(R.id.parentForm);
        formula = Util.getTextViewWithFont(this,R.id.formula);
        doneClick = (Button)findViewById(R.id.doneClick);

        //Util.showWithFadedText(doneClick,"OK");

        open = Util.getTextViewWithFontInvisible(this,R.id.open);
        userAns = Util.getEditTextWithFont(this,R.id.userAns);
        userAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAns.setTextColor(getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userAns.getText().toString().trim().length() > 0){
                    //Util.showWithText(doneClick,"OK");
                }else{
                    //Util.showWithFadedText(doneClick,"OK");
                }
            }
        });
        //setPosition();
        MultiplyCache.getInstance().setFinalAns(null);
        DivideCache.get().clear();

        if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
            setMultiply();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
            setLCD();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
            setDivide();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD_NEXT)){
            LCDCache.get().setLCDNextFinished(false);
            setLCDNext();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_ADDITION)){
            AdditionCache.get().clear();
            setAddition();
        }

        //XXX: IMPORTANT! - FOR FASTER DEMO PURPOSE ONLY. - COMMENT OUT THIS CODE WHEN GO LIVE.
        //setHint();
    }
    private void setPosition(){
        ((RelativeLayout.LayoutParams) userAns.getLayoutParams()).setMargins(720,450,0,0);
        ((RelativeLayout.LayoutParams) formula.getLayoutParams()).setMargins(100,500,0,0);
        ((RelativeLayout.LayoutParams) doneClick.getLayoutParams()).setMargins(750,400,0,0);
        ((RelativeLayout.LayoutParams) open.getLayoutParams()).setMargins(300,700,0,0);

    }
    private void setAddition(){
        Intent intent = getIntent();
        num1 = Integer.valueOf(intent.getStringExtra(ADDITION_NUM_1));
        num2 = Integer.valueOf(intent.getStringExtra(ADDITION_NUM_2));
        Util.showWithText(formula, num1 + " + " + num2 + "  = ");
        setFormClick();
    }
    private void setHint(){
        if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
            userAns.setHint(String.valueOf(num1 * num2));
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
            userAns.setHint(getIntent().getStringExtra(LCD_ANS));
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
            int ans = 0;
            if(Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1)) > Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2))){
                ans = Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1)) / Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2));
            }else{
                ans = Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2)) / Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1));
            }
            userAns.setHint(String.valueOf(ans));
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD_NEXT)){
            userAns.setHint(getIntent().getStringExtra(LCD_NEXT_ANS));
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_ADDITION)){
            userAns.setHint(String.valueOf(num1 + num2));
        }
    }
    private void setLCDNext(){
        Intent intent = getIntent();
        String formulaTxt = "";
        formulaTxt = "Skip count by " + intent.getStringExtra(LCD_NEXT_DENOMINATOR) + " \n next to " + intent.getStringExtra(LCD_NEXT_CURRENT_SKIPCOUNT) + " is ? ";
        Util.showWithText(formula, formulaTxt + " = ");
        formula.setTextSize(30);
        ((RelativeLayout.LayoutParams) userAns.getLayoutParams()).setMargins(740,450,0,0);
        Util.hide(open);
        setFormClick();
    }

    private void setDivide(){
        Intent intent = getIntent();
        String formulaTxt = "";
        if(Integer.valueOf(intent.getStringExtra(DIVIDE_NUM_1)) > Integer.valueOf(intent.getStringExtra(DIVIDE_NUM_2))){
            formulaTxt = intent.getStringExtra(DIVIDE_NUM_1) + " ÷ " + intent.getStringExtra(DIVIDE_NUM_2);
        }else{
            formulaTxt = intent.getStringExtra(DIVIDE_NUM_2) + " ÷ " + intent.getStringExtra(DIVIDE_NUM_1);
        }
        Util.showWithText(formula, formulaTxt + " = ");
        Util.hide(open);
        setFormClick();
    }

    private void setLCD(){
        Intent intent = getIntent();
        Util.showWithText(formula, intent.getStringExtra(LCD_DENOMINATORS) + " = ");
        ((RelativeLayout.LayoutParams) doneClick.getLayoutParams()).setMargins(830,400,0,0);
        ((RelativeLayout.LayoutParams) userAns.getLayoutParams()).setMargins(820,450,0,0);
        setFormClick();
        Util.showWithText(open,"Open Scratch.");
        if(intent.getStringExtra(LCD_DENOMINATORS).length() > 10){
            formula.setTextSize(50);
        }else{
            formula.setTextSize(60);
        }
        open.setTextSize(30);
        setOpenOnClick();
    }
    private void setMultiply(){
        Intent intent = getIntent();
        num1 = Integer.valueOf(intent.getStringExtra(MULTIPLY_NUM_1));
        num2 = Integer.valueOf(intent.getStringExtra(MULTIPLY_NUM_2));
        Util.showWithText(formula, num1 + " x " + num2 + "  = ");
        //setFormClick();
        /*if(intent.getStringExtra(MULTIPLY_NUM_1).length() == 2 || intent.getStringExtra(MULTIPLY_NUM_2).length() == 2){
            Util.showWithText(open,"Open Scratch.");
            open.setTextSize(30);
            setOpenOnClick();
        }*/
        setDoneClickOnClick();
    }

    private void setOpenOnClick(){
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
                    setMultiplyOnClick();
                    showGif(0,FormActivity.this);
                }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
                    setLCDOpenOnClick();
                    showGif(0,FormActivity.this);
                }
                finish();
            }
        });
    }
    private void setDoneClickOnClick(){
        doneClick.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void setMultiplyOnClick(){
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean isUpperFilled = false;
        if(String.valueOf(num1).length() == 2){
            i1 = Integer.valueOf(String.valueOf(String.valueOf(num1).charAt(0)));
            i2 = Integer.valueOf(String.valueOf(String.valueOf(num1).charAt(1)));
            isUpperFilled = true;
        }
        if(isUpperFilled){
            i3 = Integer.valueOf(String.valueOf(String.valueOf(num2).charAt(0)));
            if(String.valueOf(num2).length() == 2){
                i4 = Integer.valueOf(String.valueOf(String.valueOf(num2).charAt(1)));
            }
        }else{
            i1 = Integer.valueOf(String.valueOf(String.valueOf(num2).charAt(0)));
            i2 = Integer.valueOf(String.valueOf(String.valueOf(num2).charAt(1)));
            i3 = Integer.valueOf(String.valueOf(String.valueOf(num1).charAt(0)));
            if(String.valueOf(num1).length() == 2){
                i4 = Integer.valueOf(String.valueOf(String.valueOf(num1).charAt(1)));
            }
        }
        MultiplyCache.getInstance().clear();
        MultiplyCache.getInstance().setNums(i1,i2,i3,i4);
        startActivity(new Intent(FormActivity.this, MultiplyActivity.class));
    }
    private void setLCDOpenOnClick(){
        Intent intent = new Intent(this,LCDActivity.class);
        String [] denoms = getIntent().getStringExtra(LCD_DENOMINATORS).split(", ");
        intent.putExtra(LCDActivity.NUM_1,denoms[0]);
        intent.putExtra(LCDActivity.NUM_2,denoms[1]);
        intent.putExtra(LCDActivity.NUM_3,denoms[2]);

        if(denoms.length == 4){
            intent.putExtra(LCDActivity.NUM_4,denoms[3]);
        }
        intent.putExtra(LCDActivity.LCD,getIntent().getStringExtra(LCD_ANS));
        startActivity(intent);

    }
    private void validate(){
        if(getIntent().getStringExtra(FractionActivity.ACTIVITY_TYPE).equals(FractionActivity.ACTIVITY_SEATWORK)){
            Intent i = new Intent(this,FractionActivity.class);
            i.putExtra(SEATWORK_ANSWER,String.valueOf(getUserAns()));
            setResult(RESULT_OK,i);
        }
        if(isCorrect()){
            if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
                MultiplyCache.getInstance().setFinalAns(String.valueOf(getUserAns()));
            }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
                LCDCache.get().setFinished(true);
            }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
                DivideCache.get().setAns(Integer.valueOf(userAns.getText().toString().trim()));
            }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD_NEXT)){
                LCDCache.get().setLCDNextFinished(true);
            }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_ADDITION)){
                AdditionCache.get().setFinalAnswer(userAns.getText().toString().trim());
            }
            finish();
        }else{
            if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY) && getIntent().getStringExtra(FractionActivity.ACTIVITY_TYPE).equals(FractionActivity.ACTIVITY_SEATWORK)){
                SaveState.get(this).incrementMultiplicationMistakes();
                MultiplyCache.getInstance().setFinalAns(String.valueOf(getUserAns()));
                finish();
            }
            if(getIntent().getStringExtra(FractionActivity.ACTIVITY_TYPE).equals(FractionActivity.ACTIVITY_LESSON)){
                userAns.setTextColor(Color.RED);
                shakeError(userAns);
            }

        }
    }
    private void setFormClick(){
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        /*doneClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });*/
    }

    private boolean isCorrect(){
        if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
            int ans = num1 * num2;
            return ans == getUserAns();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
            return getIntent().getStringExtra(LCD_ANS).equals(userAns.getText().toString().trim());
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
            int ans = 0;
            if(Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1)) > Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2))){
                ans = Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1)) / Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2));
            }else{
                ans = Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_2)) / Integer.valueOf(getIntent().getStringExtra(DIVIDE_NUM_1));
            }
            return ans == getUserAns();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD_NEXT)){
            return Integer.valueOf(getIntent().getStringExtra(LCD_NEXT_ANS)) == getUserAns();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_ADDITION)){
            int ans = num1 + num2;
            return ans == getUserAns();
        }
        return false;
    }

    private int getUserAns(){
        int i = 0;
        try{
            i = Integer.valueOf(userAns.getText().toString().trim());
        }catch (NumberFormatException e){
            i = 0;
        }
        return i;
    }
}
