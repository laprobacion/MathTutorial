package com.master.math.activity.addition;

import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;
import com.master.math.activity.util.DraggedItem;

import java.util.ArrayList;
import java.util.List;

import static com.master.math.activity.util.Util.shakeError;

public class AdditionValidator extends Validator{

    public AdditionValidator(){    }


    public boolean startValidate(){
        if(step.getStep() == ActionStep.STEP_1){
            if(get1Id() == R.id.numUp1 && get2Id() == R.id.numDown1){
                return true;
            }
            get(R.id.numUp1).startAnimation(shakeError());
            get(R.id.numDown1).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_2){
            if((get1Id() == R.id.winAns2 && get2Id() == R.id.topNum1) || (get1Id() == R.id.winAns1 && get2Id() == R.id.ans1)){
                return true;
            }
            shakeValidValues(R.id.winAns2,R.id.topNum1,R.id.winAns1,R.id.ans1);
        }
        if(step.getStep() == ActionStep.STEP_3){
            if(get1Id() == R.id.numUp2 && get2Id() == R.id.numDown2){
                return true;
            }
            get(R.id.numUp2).startAnimation(shakeError());
            get(R.id.numDown2).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_4){
            if((get1Id() == R.id.winAns2 && get2Id() == R.id.topNum2) || (get1Id() == R.id.winAns1 && get2Id() == R.id.ans2)){
                return true;
            }
            shakeValidValues(R.id.winAns2,R.id.topNum2,R.id.winAns1,R.id.ans2);
        }
        if(step.getStep() == ActionStep.STEP_5){
            if(get1Id() == R.id.numUp3 && get2Id() == R.id.numDown3){
                return true;
            }
            get(R.id.numUp3).startAnimation(shakeError());
            get(R.id.numDown3).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_6){
            if((get1Id() == R.id.winAns2 && get2Id() == R.id.topNum3) || (get1Id() == R.id.winAns1 && get2Id() == R.id.ans3)){
                return true;
            }
            shakeValidValues(R.id.winAns2,R.id.topNum3,R.id.winAns1,R.id.ans3);
        }
        if(step.getStep() == ActionStep.STEP_7){
            if(get1Id() == R.id.numUp4 && get2Id() == R.id.numDown4){
                return true;
            }
            get(R.id.numUp4).startAnimation(shakeError());
            get(R.id.numDown4).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_8){
            if(get1Id() == R.id.winAns2 && get2Id() == R.id.ans4){
                return true;
            }
            get(R.id.winAns2).startAnimation(shakeError());
            get(R.id.ans4).startAnimation(shakeError());
        }
        return false;
    }

    private void shakeValidValues(int winAns2,int topNum,int winAns1,int ans ){
        if(get1Id() == winAns2){
            get(winAns2).startAnimation(shakeError());
            get(topNum).startAnimation(shakeError());
            return;
        }
        if(get1Id() == winAns1){
            get(winAns1).startAnimation(shakeError());
            get(ans).startAnimation(shakeError());
            return;
        }
        if(get(winAns2).getVisibility() == View.VISIBLE){
            get(winAns2).startAnimation(shakeError());
        }
        if(get(topNum).getVisibility() == View.VISIBLE){
            get(topNum).startAnimation(shakeError());
        }
        get(winAns1).startAnimation(shakeError());
        get(ans).startAnimation(shakeError());
    }

    @Override
    public boolean isFinished() {
        return getActionStep().getStep() == ActionStep.STEP_9;
    }
}
