package com.master.math.activity.multiply;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.AdditionActivity;
import com.master.math.activity.addition.AdditionCache;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.base.Processor;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import static com.master.math.activity.util.Util.generateRandomNumbers;

public class MultiplyProcessor implements Processor {
    private TextView num1,num2,num3,num4,multiply,line,formulaPop, ans1, ans2,topNum,topNum2,topNum3, totalAns1, totalAns2,totalAns3,totalAns4, finalAnswerGroup1, add;
    private EditText userAns;
    private RelativeLayout popupMultiply,parentMultiply;
    private Initializer initializer;
    private Integer popupAnswer;
    private Activity activity;
    private int formulaPopAnswer;
    private String topNum3Holder = "empty";
    private String totalAns3Holder = "empty";
    private MultiplyValidator validator;
    public MultiplyProcessor(Activity activity, AssetManager asset, int... i){
        this.activity = activity;
        userAns = Util.getEditTextWithFont(activity, R.id.userAns);
        finalAnswerGroup1 = Util.getTextViewWithFont(activity, R.id.finalAnswerGroup1);
        num1 = Util.getTextViewWithFont(activity, R.id.num1);
        num2 = Util.getTextViewWithFont(activity, R.id.num2);
        num3 = Util.getTextViewWithFont(activity, R.id.num3);
        num4 = Util.getTextViewWithFont(activity, R.id.num4);
        topNum = Util.getTextViewWithFontInvisible(activity, R.id.topNum);
        topNum2 = Util.getTextViewWithFontInvisible(activity, R.id.topNum2);
        topNum3 = Util.getTextViewWithFontInvisible(activity, R.id.topNum3);
        multiply = Util.getTextViewWithFont(activity, R.id.multiply);
        line = Util.getTextViewWithFont(activity, R.id.line);
        ans1 = Util.getTextViewWithFont(activity, R.id.ans1);
        ans2 = Util.getTextViewWithFont(activity, R.id.ans2);
        add = Util.getTextViewWithFont(activity, R.id.add);
        totalAns1 = Util.getTextViewWithFontInvisible(activity, R.id.totalAns1);
        totalAns2 = Util.getTextViewWithFontInvisible(activity, R.id.totalAns2);
        totalAns3 = Util.getTextViewWithFontInvisible(activity, R.id.totalAns3);
        totalAns4 = Util.getTextViewWithFontInvisible(activity, R.id.totalAns4);
        formulaPop = Util.getTextViewWithFontInvisible(activity, R.id.formulaPop);
        popupMultiply = (RelativeLayout) activity.findViewById(R.id.popupMultiply);
        parentMultiply = (RelativeLayout) activity.findViewById(R.id.parentMultiply);
        validator = new MultiplyValidator();
        this.initializer = new Initializer(new MultiplyListener(this,validator));
        this.initializer.setDraggables(num1,num2,num3,ans1,ans2,num4,topNum,topNum2,topNum3,totalAns1,totalAns2,totalAns3,totalAns4,finalAnswerGroup1,add);
        setNumbers(i);
        renderPopupWindow(null, false);
        renderAnswerWindow(false);
    }
    private void setFormulaPopAnswer(int ans){
        this.formulaPopAnswer = ans;
    }
    public boolean isPopupMultiplyVisible(){
        return popupMultiply.getVisibility() == View.VISIBLE;
    }

    public void renderPopupWindow(DraggedItem draggedItem, boolean show){
        if(show){
            setFormulaPop(draggedItem);
            popupMultiply.setVisibility(View.VISIBLE);
            formulaPop.setVisibility(View.VISIBLE);
            userAns.setVisibility(View.VISIBLE);
        }else{
            userAns.setVisibility(View.INVISIBLE);
            popupMultiply.setVisibility(View.INVISIBLE);
            formulaPop.setVisibility(View.INVISIBLE);
        }
        maskStepTopNum3TotalAns3(!show);
        fadeBackground(show);
    }

    private void maskStepTopNum3TotalAns3(boolean mask){
        if(this.validator.getActionStep().getStep() == ActionStep.STEP_9){
            if(mask){
                if(topNum3.getVisibility() == View.VISIBLE){
                    Util.showWithText(topNum3, topNum3Holder);
                }
                Util.showWithText(totalAns3, totalAns3Holder);
                if(isUpperOrLowerZero()){
                    MultiplyCache.getInstance().setFinalAns(getFinalValue());
                    Util.showWithText(add,"Done!");
                    setAddOnClick();
                }else{
                    Util.showWithText(add,"add");
                    setAddOnClick();
                }
                this.validator.removeListeners();
            }
        }
    }
    private boolean isUpperOrLowerZero(){
        int upper = Integer.valueOf(finalAnswerGroup1.getText().toString().trim());
        int lower = Integer.valueOf(totalAns3.getText().toString().trim());
        return upper == 0 || lower == 0;
    }
    private String getFinalValue(){
        int upper = Integer.valueOf(finalAnswerGroup1.getText().toString().trim());
        int lower = Integer.valueOf(totalAns3.getText().toString().trim());
        int fin = upper + lower;
        return String.valueOf(fin);
    }
    private void setAddOnClick(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MultiplyCache.getInstance().getFinalAns() == null){
                    AdditionCache.get().clear();
                    AdditionCache.get().setNumbers(addString(finalAnswerGroup1.getText().toString()),addString(totalAns3.getText().toString()));
                    AdditionCache.get().setMultiplyNumbers(num1.getText().toString() + num2.getText().toString(),num4.getText().toString() + num3.getText().toString());
                    activity.startActivity(new Intent(activity, AdditionActivity.class));
                }else{
                    activity.finish();
                }

            }
        });
    }
    private String addString(String num){
        if(num.length() == 3){
            return " " + num;
        }
        return num;
    }
    private void fadeBackground(boolean isFade){
        if(isFade){
            num1.setVisibility(View.INVISIBLE);
            num2.setVisibility(View.INVISIBLE);
            num3.setVisibility(View.INVISIBLE);
            num4.setVisibility(View.INVISIBLE);
            multiply.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
        }else{
            num1.setVisibility(View.VISIBLE);
            num2.setVisibility(View.VISIBLE);
            num3.setVisibility(View.VISIBLE);
            if(!num4.getText().toString().equals("0")) {
                num4.setVisibility(View.VISIBLE);
            }
            multiply.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
    }
    public void setFormulaPop(DraggedItem draggedItem){
        userAns.setText("");
        formulaPop.setText("");
        int n1 = Integer.valueOf(draggedItem.getItem(0).getText().toString());
        int n2 = draggedItem.getItem(1).getText().toString() == Util.DOUBLE_SPACES ? 0 : Integer.valueOf(draggedItem.getItem(1).getText().toString());
        Integer ans = n1 * n2;
        String formula = "";
        if(this.validator.getActionStep().getStep() == ActionStep.STEP_1 ||
                this.validator.getActionStep().getStep() == ActionStep.STEP_2 ||
                this.validator.getActionStep().getStep() == ActionStep.STEP_5 ||
                this.validator.getActionStep().getStep() == ActionStep.STEP_7 ) {
            formula = n1 + " x " + n2 + " =      " ;
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_8){
            ans = Integer.valueOf(n1) + Integer.valueOf(topNum3.getText().toString().trim());
            formula = n1 + " + " + topNum3.getText().toString() + " =      ";
            setTotalAns4(draggedItem, ans);
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_3){
            ans = Integer.valueOf(n1) + Integer.valueOf(topNum.getText().toString().trim());
            formula = n1 + " + " + topNum.getText().toString() + " =      ";
            setTopNum2(draggedItem);
        }
        setFormulaPopAnswer(ans);
        Util.showWithText(formulaPop,formula);
    }

    public boolean isFormulaPopAnsCorrect(){
        return this.formulaPopAnswer == Integer.valueOf(userAns.getText().toString());
    }
    private void setTopNum2(DraggedItem draggedItem){
        if(draggedItem.getItem(1).getId() == R.id.topNum2){
            Util.hide(draggedItem.getItem(0));
            Util.showWithText(topNum2, draggedItem.getItem(0).getText().toString());
        }
    }
    public boolean setTopNum(DraggedItem draggedItem){
        if(draggedItem.getItem(1).getId() == R.id.topNum){
            Util.showWithText(topNum, draggedItem.getItem(0).getText().toString());
            Util.hide(draggedItem.getItem(0));
            return true;
        }
        return false;
    }
    public boolean setTopNum3(DraggedItem draggedItem){
        if(draggedItem.getItem(1).getId() == R.id.topNum3){
            Util.hide(draggedItem.getItem(0));
            topNum3.setTextSize(33);
            Util.showWithText(topNum3, draggedItem.getItem(0).getText().toString());
            if(this.validator.isBoxedEmpty()){
                this.validator.getActionStep().increment();
            }
            return true;
        }
        return false;
    }
    private void setNumbers(int... i){
        if(i.length != 0){
            num1.setText(String.valueOf(i[0]));
            num2.setText(String.valueOf(i[1]));
            num3.setText(String.valueOf(i[2]));
            if(i.length == 4){
                setNum4(String.valueOf(i[3]));
            }
        }else{
            num1.setText(generateRandomNumbers(false));
            num2.setText(generateRandomNumbers(false));
            num3.setText(generateRandomNumbers(false));
            setNum4("");
        }
    }
    private void setNum4(String n){
        int num = n.equals("") ? Integer.valueOf(generateRandomNumbers(true)) : Integer.valueOf(n);
        if(num != 0){
            num4.setVisibility(View.VISIBLE);
        }else{
            num4.setVisibility(View.INVISIBLE);
        }
        num4.setText(String.valueOf(num));
        num4.invalidate();
    }


    private void populateAnsBox(){
        String strAns = popupAnswer.toString();
        if(this.validator.getActionStep().getStep() == ActionStep.STEP_1) {
            if (strAns.length() == 2) {
                Util.showWithText(ans1, Character.toString(strAns.charAt(1)));
                Util.showWithText(ans2, Character.toString(strAns.charAt(0)));
                Util.showWithBG(topNum, activity);
            } else {
                Util.showWithText(ans1, Character.toString(strAns.charAt(0)));
            }
            Util.showWithBG(totalAns1, activity);
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_2){
            if (!topNum.getText().equals("0")) {
                Util.showWithBG(topNum2, activity);
            }else{
                Util.showWithBG(totalAns2, activity);
            }
            Util.showWithText(ans2, strAns);
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_3){
            Util.showWithBG(totalAns2,activity);
            Util.showWithText(ans2, strAns);
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_5){
            if (strAns.length() == 2) {
                Util.showWithText(ans1, Character.toString(strAns.charAt(1)));
                Util.showWithText(ans2, Character.toString(strAns.charAt(0)));
                Util.showWithBG(topNum3,activity);
            } else {
                Util.showWithText(ans1, Character.toString(strAns.charAt(0)));
            }
            Util.showWithBG(totalAns3,activity);
        }else if(this.validator.getActionStep().getStep() == ActionStep.STEP_7){
            Util.showWithText(ans2, strAns);
            Util.showWithBG(totalAns4,activity);
        }

    }
    public void renderAnswerWindow(boolean show){
        if(show){
            popupAnswer = Integer.valueOf(userAns.getText().toString().trim());
            ans2.setText("");
            ans1.setText("");
            if((this.validator.getActionStep().getStep() >= ActionStep.STEP_1 && this.validator.getActionStep().getStep() <= ActionStep.STEP_5)
                    || this.validator.getActionStep().getStep() ==  ActionStep.STEP_7){
                if (popupAnswer != null) {
                    populateAnsBox();
                    this.validator.getActionStep().increment();
                }
            }
        }else{
            ans1.setVisibility(View.INVISIBLE);
            ans2.setVisibility(View.INVISIBLE);
        }
    }


    public void setTotalAns1(DraggedItem draggedItem){
        Util.hide(draggedItem.getItem(0));
        Util.showWithText(totalAns1, draggedItem.getItem(0).getText().toString());
    }
    public void setTotalAns2(DraggedItem draggedItem){
        setFin(Integer.valueOf(draggedItem.getItem(0).getText().toString() + totalAns1.getText().toString()));
        Util.hide(draggedItem.getItem(0));
        Util.hide(totalAns1);
        Util.hide(totalAns2);
        this.validator.getActionStep().setStep(ActionStep.STEP_5);
        if(num4.getVisibility() == View.INVISIBLE){
            MultiplyCache.getInstance().setFinalAns(finalAnswerGroup1.getText().toString());
            Util.showWithText(add,"Done!");
            setAddOnClick();
            this.validator.removeListeners();
        }
    }
    public void setTotalAns3(DraggedItem draggedItem){
        String txt = "";
        if(finalAnswerGroup1.getText().toString().length() == 3){
            txt = " " + draggedItem.getItem(0).getText() + "0";
        }else{
            txt = draggedItem.getItem(0).getText() + "0";
        }
        Util.showWithText(totalAns3, txt);
        Util.hide(draggedItem.getItem(0));
        if(this.validator.isBoxedEmpty()){
            this.validator.getActionStep().increment();
        }
    }
    public void setTotalAns4(DraggedItem draggedItem, int ans){
        topNum3Holder = topNum3.getText().toString() + " + " + draggedItem.getItem(0).getText().toString() + " = " + ans;
        totalAns3Holder = String.valueOf(ans) + totalAns3.getText().toString().trim();
        topNum3.setText(topNum3.getText().toString() + " + " + draggedItem.getItem(0).getText().toString() + " = ");
        String above = finalAnswerGroup1.getText().toString().trim();
        int diff = totalAns3Holder.length() - above.length();
        String spaces = "";
        for(int i=0; i < diff; i++){
            spaces += " ";
        }
        finalAnswerGroup1.setText(spaces + above);
        Util.hide(totalAns4);
        topNum3.setTextSize(20);
        topNum3.invalidate();
        Util.hide(draggedItem.getItem(0));
        this.validator.getActionStep().increment();
    }
    public void setTotalAns4(DraggedItem draggedItem){
        String above = finalAnswerGroup1.getText().toString().trim();
        String under = draggedItem.getItem(0).getText().toString()  + totalAns3.getText().toString().trim();
        int diff = under.length() - above.length();
        String spaces = "";
        for(int i=0; i < diff; i++){
            spaces += " ";
        }
        finalAnswerGroup1.setText(spaces + above);
        totalAns3Holder = under;
        Util.hide(totalAns4);
        Util.hide(draggedItem.getItem(0));
        this.validator.getActionStep().increment();
    }
    private void setFin(int fin){
        String txt = "";
        if(String.valueOf(fin).length() == 2){
            txt = " " + String.valueOf(fin);
        }else{
            txt = String.valueOf(fin);
        }
        Util.showWithText(finalAnswerGroup1, txt);
        Util.removeListeners(finalAnswerGroup1);
    }

}
