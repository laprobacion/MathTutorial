package com.master.math.activity.addition;


import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.base.Processor;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import static com.master.math.activity.util.Util.shakeError;

public class AdditionProcessor implements Processor{
    private Activity activity;
    private Initializer initializer;
    private AdditionValidator validator;
    private TextView mulDown, mulUp, mulSymbol, numUp1,numUp2,numUp3,numUp4,
            numDown1,numDown2,numDown3,numDown4,plus,lineAdd,topNum1,
            topNum2,topNum3,ans1,ans2,ans3,ans4,winAns1,winAns2,formula, done;
    private EditText userAns;
    private int formulaAns;
    private RelativeLayout popup;
    private String finalAnswer;
    public AdditionProcessor(Activity activity){
        this.activity = activity;
        popup = (RelativeLayout) activity.findViewById(R.id.popup);
        formula = Util.getTextViewWithFont(activity, R.id.formula);
        userAns = Util.getEditTextWithFont(activity, R.id.userAns);
        mulDown = Util.getTextViewWithFont(activity, R.id.mulDown);
        mulUp = Util.getTextViewWithFont(activity, R.id.mulUp);
        mulSymbol = Util.getTextViewWithFont(activity, R.id.mulSymbol);
        numUp1 = Util.getTextViewWithFont(activity, R.id.numUp1);
        numUp2 = Util.getTextViewWithFont(activity, R.id.numUp2);
        numUp3 = Util.getTextViewWithFont(activity, R.id.numUp3);
        numUp4 = Util.getTextViewWithFont(activity, R.id.numUp4);
        numDown1 = Util.getTextViewWithFont(activity, R.id.numDown1);
        numDown2 = Util.getTextViewWithFont(activity, R.id.numDown2);
        numDown3 = Util.getTextViewWithFont(activity, R.id.numDown3);
        numDown4 = Util.getTextViewWithFont(activity, R.id.numDown4);
        plus = Util.getTextViewWithFont(activity, R.id.plus);
        lineAdd = Util.getTextViewWithFont(activity, R.id.lineAdd);
        done = Util.getTextViewWithFontInvisible(activity, R.id.done);
        topNum1 = Util.getTextViewWithFontInvisible(activity, R.id.topNum1);
        topNum2 = Util.getTextViewWithFontInvisible(activity, R.id.topNum2);
        topNum3 = Util.getTextViewWithFontInvisible(activity, R.id.topNum3);
        ans1 = Util.getTextViewWithFontInvisible(activity, R.id.ans1);
        ans2 = Util.getTextViewWithFontInvisible(activity, R.id.ans2);
        ans3 = Util.getTextViewWithFontInvisible(activity, R.id.ans3);
        ans4 = Util.getTextViewWithFontInvisible(activity, R.id.ans4);
        winAns1 = Util.getTextViewWithFontInvisible(activity, R.id.winAns1);
        winAns2 = Util.getTextViewWithFontInvisible(activity, R.id.winAns2);
        validator = new AdditionValidator();
        this.initializer = new Initializer(new AdditionListener(this,validator));
        this.initializer.setDraggables(numUp1,numUp2,numUp3,numUp4,
                numDown1,numDown2,numDown3,numDown4,
                topNum1,topNum2,topNum3,
                ans1,ans2,ans3,ans4,winAns1,winAns2);
        explodeNumbers();
        showPopup(null,false);
    }
    private void setMultiplyTable(){
        if(AdditionCache.get().getUpperMultiply() != null){
            Util.showWithText(mulUp,AdditionCache.get().getUpperMultiply());
        }else{
            Util.hide(mulUp);
            Util.hide(mulSymbol);
        }
        if(AdditionCache.get().getLowerMultiply() != null){
            Util.showWithText(mulDown,AdditionCache.get().getLowerMultiply());
        }else{
            Util.hide(mulDown);
        }

    }
    private void explodeNumbers(){
        setMultiplyTable();
        Util.showWithText(numUp1, getUpperString(3));
        Util.showWithText(numUp2, getUpperString(2));
        Util.showWithText(numUp3, getUpperString(1));
        Util.showWithText(numUp4, getUpperString(0));
        Util.showWithText(numDown1, getLowerString(3));
        Util.showWithText(numDown2, getLowerString(2));
        Util.showWithText(numDown3, getLowerString(1));
        Util.showWithText(numDown4, getLowerString(0));
    }
    private String getUpperString(int index){
        String numbers = AdditionCache.get().getUpperNumbers();
        String s = numbers != null ? String.valueOf(AdditionCache.get().getUpperNumbers().charAt(index)) : Util.generateRandomNumbers(true);
        if(s.equals(" ")){
            return "0";
        }
        return s;
    }
    private String getLowerString(int index){
        String numbers = AdditionCache.get().getLowerNumbers();
        String s = numbers != null ? String.valueOf(AdditionCache.get().getLowerNumbers().charAt(index)) : Util.generateRandomNumbers(true);
        if(s.equals(" ")){
            return "0";
        }
        return s;
    }
    public void showPopup(DraggedItem draggedItem, boolean show){
        if(show){
            if(isStepAdding()){
                fillFormula(draggedItem);
                popup.setVisibility(View.VISIBLE);
                formula.setVisibility(View.VISIBLE);
                userAns.setVisibility(View.VISIBLE);
                showBaseTable(!show);
            }else{
                allowDrop(draggedItem);
            }
        }else{
            popup.setVisibility(View.INVISIBLE);
            formula.setVisibility(View.INVISIBLE);
            userAns.setVisibility(View.INVISIBLE);
            showBaseTable(!show);
        }
    }
    private void allowDrop(DraggedItem draggedItem){
        if(validator.getActionStep().getStep() == ActionStep.STEP_8){
            finalAnswer = draggedItem.getItem(0).getText().toString() + ans3.getText().toString() + ans2.getText().toString() + ans1.getText().toString();
            Util.hide(draggedItem.getItem(0),ans1,ans2,ans3,ans4);
            Util.showWithText(ans2,finalAnswer);
            AdditionCache.get().setFinalAnswer(finalAnswer);
            Util.showWithText(done,"Done");
            done.setTextSize(30);
            addOnClick();
            return;
        }
        Util.showWithText(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
        Util.hide(draggedItem.getItem(0));
        if(isWindowAnsEmpty()){
            validator.getActionStep().increment();
        }
    }
    private void addOnClick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
    private boolean isWindowAnsEmpty(){
        return winAns1.getVisibility() == View.INVISIBLE && winAns2.getVisibility() == View.INVISIBLE;
    }
    private boolean isStepAdding(){
        return validator.getActionStep().getStep() == ActionStep.STEP_1 || validator.getActionStep().getStep() == ActionStep.STEP_3
                || validator.getActionStep().getStep() == ActionStep.STEP_5 || validator.getActionStep().getStep() == ActionStep.STEP_7;
    }
    public boolean isPopupOpen(){
      return popup.getVisibility() == View.VISIBLE;
    }
    public void shakeUserAns(){
        shakeError(userAns);
    }
    public void setWindowAnswers(){
        String strAns = String.valueOf(formulaAns);
        if(strAns.length() == 2){
            if(validator.getActionStep().getStep() == ActionStep.STEP_7){
                Util.showWithText(winAns2,strAns);
            }else{
                Util.showWithText(winAns2,String.valueOf(strAns.charAt(0)));
                Util.showWithText(winAns1,String.valueOf(strAns.charAt(1)));
            }
        }else{
            if(validator.getActionStep().getStep() == ActionStep.STEP_7){
                Util.showWithText(winAns2,strAns);
            }else{
                Util.showWithText(winAns1,String.valueOf(strAns.charAt(0)));
            }
        }
        showDrop();
    }
    private void showDrop(){
        String strAns = String.valueOf(formulaAns);
        if(validator.getActionStep().getStep() == ActionStep.STEP_1){
            if(strAns.length() == 2){
                Util.showWithBG(topNum1,activity);
            }
            Util.showWithBG(ans1,activity);
            validator.getActionStep().increment();
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_3){
            if(strAns.length() == 2){
                Util.showWithBG(topNum2,activity);
            }
            Util.showWithBG(ans2,activity);
            validator.getActionStep().increment();
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_5){
            if(strAns.length() == 2){
                Util.showWithBG(topNum3,activity);
            }
            Util.showWithBG(ans3,activity);
            validator.getActionStep().increment();
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_7){
            Util.showWithBG(ans4,activity);
            validator.getActionStep().increment();
        }
    }
    public boolean isUserAnsCorrect(){
        int intUserAns = 0;
        try{
            intUserAns = Integer.valueOf(userAns.getText().toString());
        }catch (NumberFormatException e){
            intUserAns = 0;
        }
        return intUserAns == formulaAns;
    }
    private void fillFormula(DraggedItem draggedItem){
        String strFormula = "";
        userAns.setText("");
        if(validator.getActionStep().getStep() == ActionStep.STEP_1){
            strFormula = setFormulaAns(null, draggedItem);
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_3){
            strFormula = setFormulaAns(topNum1, draggedItem);
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_5){
            strFormula = setFormulaAns(topNum2, draggedItem);
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_7){
            strFormula = setFormulaAns(topNum3, draggedItem);
        }
        Util.showWithText(formula,strFormula);
        formula.setTextSize(40);
    }

    private String getBaseFormula(DraggedItem draggedItem){
        return draggedItem.getItem(0).getText().toString() + " + " + draggedItem.getItem(1).getText().toString() + " =    ";
    }
    private String getStringCarryOver(TextView tv){
        if(tv != null && tv.getVisibility() == View.VISIBLE){
            return tv.getText().toString() + " + ";
        }
        return "";
    }
    private int getIntCarryOver(TextView tv){
        if(tv != null && tv.getVisibility() == View.VISIBLE){
            return Integer.valueOf(tv.getText().toString());
        }
        return 0;
    }
    private String setFormulaAns(TextView tv,DraggedItem draggedItem){
        formulaAns = Integer.valueOf(getIntCarryOver(tv)) + Integer.valueOf(draggedItem.getItem(0).getText().toString()) + Integer.valueOf(draggedItem.getItem(1).getText().toString());
        return getStringCarryOver(tv)+getBaseFormula(draggedItem);
    }

    private void showBaseTable(boolean show){
        if(show){
            Util.show(numDown1,numDown2,numDown3,numDown4,numUp1,numUp2,numUp3,numUp4,lineAdd,plus);
        }else{
            Util.hide(numDown1,numDown2,numDown3,numDown4,numUp1,numUp2,numUp3,numUp4,lineAdd,plus);
        }
    }

}
