package com.master.math.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.divide.DivideCache;
import com.master.math.activity.lcd.LCDCache;
import com.master.math.activity.util.Util;

import com.master.math.activity.multiply.MultiplyCache;

import static com.master.math.activity.util.Util.shakeError;

public class FormActivity extends AppCompatActivity {

    ConstraintLayout form;
    TextView formula,open;
    EditText userAns;
    private int num1;
    private int num2;
    public static final String MULTIPLY_NUM_1 = "NUM1";
    public static final String MULTIPLY_NUM_2 = "NUM2";
    public static final String DIVIDE_NUM_1 = "NUM1";
    public static final String DIVIDE_NUM_2 = "NUM2";
    public static final String LCD_ANS = "LCD_ANS";
    public static final String LCD_DENOMINATORS = "LCD_DENOMINATORS";
    public static final String OPERATION = "OPERATION";
    public static final String OPERATION_LCD = "LCD";
    public static final String OPERATION_MULTIPLY = "MULTIPLY";
    public static final String OPERATION_DIVIDE = "DIVIDE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form);
        form = (ConstraintLayout) findViewById(R.id.parentForm);
        formula = Util.getTextViewWithFont(this,R.id.formula);
        open = Util.getTextViewWithFontInvisible(this,R.id.open);
        userAns = Util.getEditTextWithFont(this,R.id.userAns);
        MultiplyCache.getInstance().setFinalAns(null);
        DivideCache.get().clear();
        if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
            setMultiply();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
            setLCD();
        }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
            setDivide();
        }

        //XXX: IMPORTANT! - FOR FASTER DEMO PURPOSE ONLY. - COMMENT OUT THIS CODE WHEN GO LIVE.
        setHint();
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
        }
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
        setFormClick();
        Util.showWithText(open,"Open Scratch.");
        open.setTextSize(30);
        setOpenOnClick();
    }
    private void setMultiply(){
        Intent intent = getIntent();
        num1 = Integer.valueOf(intent.getStringExtra(MULTIPLY_NUM_1));
        num2 = Integer.valueOf(intent.getStringExtra(MULTIPLY_NUM_2));
        Util.showWithText(formula, num1 + " x " + num2 + "  = ");
        setFormClick();
        if(intent.getStringExtra(MULTIPLY_NUM_1).length() == 2 || intent.getStringExtra(MULTIPLY_NUM_2).length() == 2){
            Util.showWithText(open,"Open Scratch.");
            open.setTextSize(30);
            setOpenOnClick();
        }
    }

    private void setOpenOnClick(){
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
                    setMultiplyOnClick();
                }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
                    setLCDOpenOnClick();
                }
                finish();
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
    private void setFormClick(){
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect()){
                    if(getIntent().getStringExtra(OPERATION).equals(OPERATION_MULTIPLY)){
                        MultiplyCache.getInstance().setFinalAns(String.valueOf(getUserAns()));
                    }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_LCD)){
                        LCDCache.get().setFinished(true);
                    }else if(getIntent().getStringExtra(OPERATION).equals(OPERATION_DIVIDE)){
                        DivideCache.get().setAns(Integer.valueOf(userAns.getText().toString().trim()));
                    }
                    finish();
                }else{
                    userAns.startAnimation(shakeError());
                }
            }
        });
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
