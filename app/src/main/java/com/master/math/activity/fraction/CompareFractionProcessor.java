package com.master.math.activity.fraction;


import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.FormActivity;
import com.master.math.activity.addition.AdditionCache;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.base.Processor;
import com.master.math.activity.multiply.MultiplyCache;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import static com.master.math.activity.util.Util.shakeError;


public class CompareFractionProcessor implements Processor{

    private TextView num1,denom1,line1,multiplyFormula1,multiplyAns1,
            num2,denom2,line2,multiplyFormula2,multiplyAns2,
            compareLine,greaterSign,lessSign,equalSign;
    private Activity activity;
    private Initializer initializer;
    private boolean isFirst;
    private DraggedItem draggedItem;
    private boolean isReady;
    private CompareFractionValidator validator;
    public boolean isReady() {        return isReady;    }
    public void setReady(boolean ready) {        isReady = ready;    }

    public boolean isFirst() {       return isFirst;    }

    public CompareFractionProcessor(Activity activity){
        this.activity = activity;
        num1 = Util.getTextViewWithFont(activity, R.id.num1);
        num2 = Util.getTextViewWithFont(activity, R.id.num2);
        denom1 = Util.getTextViewWithFont(activity, R.id.denom1);
        denom2 = Util.getTextViewWithFont(activity, R.id.denom2);
        line1 = Util.getTextViewWithFont(activity, R.id.line1);
        line2 = Util.getTextViewWithFont(activity, R.id.line2);
        compareLine = Util.getTextViewWithFont(activity, R.id.compareLine);
        multiplyFormula1 = Util.getTextViewWithFontInvisible(activity, R.id.multiplyFormula1);
        multiplyAns1 = Util.getTextViewWithFontInvisible(activity, R.id.multiplyAns1);
        multiplyFormula2 = Util.getTextViewWithFontInvisible(activity, R.id.multiplyFormula2);
        multiplyAns2 = Util.getTextViewWithFontInvisible(activity, R.id.multiplyAns2);
        greaterSign = Util.getTextViewWithFont(activity, R.id.greaterSign);
        lessSign = Util.getTextViewWithFont(activity, R.id.lessSign);
        equalSign = Util.getTextViewWithFont(activity, R.id.equalSign);
        this.validator = new CompareFractionValidator();
        this.initializer = new Initializer(new CompareFractionListener(this, validator));
        this.initializer.setDraggables(num1,num2,denom1,denom2,greaterSign,lessSign,equalSign,compareLine);
        this.validator.addDraggableItems();
        setFractions();
    }

    public void showPopup(DraggedItem draggedItem){
        this.draggedItem = draggedItem;
        if(validator.getActionStep().getStep() == ActionStep.STEP_1 || validator.getActionStep().getStep() == ActionStep.STEP_2){
            isFirst = isFirst(draggedItem);
            Intent intent = new Intent(activity, FormActivity.class);
            intent.putExtra(FormActivity.OPERATION,FormActivity.OPERATION_MULTIPLY);
            intent.putExtra(FormActivity.MULTIPLY_NUM_1,draggedItem.getItem(0).getText().toString());
            intent.putExtra(FormActivity.MULTIPLY_NUM_2,draggedItem.getItem(1).getText().toString());
            this.activity.startActivity(intent);
            setReady(true);
        }else{
            int sign = 0;
            if(Integer.valueOf(multiplyAns1.getText().toString()) > Integer.valueOf(multiplyAns2.getText().toString())){
                sign = R.id.greaterSign;
            }
            if(Integer.valueOf(multiplyAns1.getText().toString()) < Integer.valueOf(multiplyAns2.getText().toString())){
                sign = R.id.lessSign;
            }
            if(Integer.valueOf(multiplyAns1.getText().toString()) == Integer.valueOf(multiplyAns2.getText().toString())){
                sign = R.id.equalSign;
            }
            if(sign == draggedItem.getItem(0).getId()){
                Util.showWithText(compareLine,"  "+draggedItem.getItem(0).getText().toString()+" ");
                validator.getActionStep().increment();
            }else{
                greaterSign.startAnimation(shakeError());
                lessSign.startAnimation(shakeError());
                equalSign.startAnimation(shakeError());
            }
        }

    }

    public int getStep(){
        return validator.getActionStep().getStep();
    }
    public void execute(){
        String formula = draggedItem.getItem(0).getText().toString() + " x " + draggedItem.getItem(1).getText().toString() + " = ";
        int ans = Integer.valueOf(draggedItem.getItem(0).getText().toString()) * Integer.valueOf(draggedItem.getItem(1).getText().toString());
        if(MultiplyCache.getInstance().getFinalAns() == null && AdditionCache.get().getFinalAnswer() == null){
            return;
        }
        if(isFirst){
            Util.showWithText(multiplyFormula1, formula);
            Util.showWithText(multiplyAns1, String.valueOf(ans));
            validator.getActionStep().increment();
        }else{
            Util.showWithText(multiplyFormula2, formula);
            Util.showWithText(multiplyAns2, String.valueOf(ans));
            validator.getActionStep().increment();
        }
    }

    private boolean isFirst(DraggedItem draggedItem){
        if(draggedItem.getItem(0).getId() == R.id.num1 || draggedItem.getItem(1).getId() == R.id.num1){
            return true;
        }
        return false;
    }

    private void setFractions(){
        String [] first = Util.generateProperFractions();
        Util.showWithText(num1, first[0]);
        Util.showWithText(denom1,first[1]);
        String [] sec = Util.generateProperFractions();
        Util.showWithText(num2, sec[0]);
        Util.showWithText(denom2,sec[1]);
    }
}
