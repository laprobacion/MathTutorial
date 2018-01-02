package com.master.math.activity.multiply;


import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;
import com.master.math.activity.util.DraggedItem;
import java.util.ArrayList;
import java.util.List;

import static com.master.math.activity.util.Util.DOUBLE_SPACES;
import static com.master.math.activity.util.Util.shakeError;

public class MultiplyValidator extends Validator{

    public MultiplyValidator(){}

    public boolean startValidate(){
        if(step.getStep() == ActionStep.STEP_1){
            if(get1Id() == R.id.num2 && get2Id() == R.id.num3){
                return true;
            }
            shakeError(get(R.id.num2));
            shakeError(get(R.id.num3));
        }else if(step.getStep() == ActionStep.STEP_2){
            if(isBoxedEmpty()){
                if(get1Id() == R.id.num1 && get2Id() == R.id.num3){
                    return true;
                }
                shakeError(get(R.id.num1));
                shakeError(get(R.id.num3));
            }else{
                if(get1Id() == R.id.ans2 && get(R.id.topNum).getBackground() !=null && get2Id() == R.id.topNum){
                    return true;
                }
                boolean flag1 = false;
                boolean flag2 = false;
                if(get1Id() == R.id.ans2){
                    shakeError(get(R.id.ans2));
                    shakeError(get(R.id.topNum));
                    flag1 = true;
                }
                if(get1Id() == R.id.ans1 && get2Id() == R.id.totalAns1){
                    return true;
                }
                if(get1Id() == R.id.ans1){
                    shakeError(get(R.id.ans1));
                    shakeError(get(R.id.totalAns1));
                    flag1 = true;
                }
                if(!flag1 && !flag2){
                    shakeError(get(R.id.ans2));
                    shakeError(get(R.id.ans1));
                    if(get(R.id.topNum).getText().toString().equals(DOUBLE_SPACES)){
                        shakeError(get(R.id.topNum));
                    }
                    if(get(R.id.totalAns1).getText().toString().equals(DOUBLE_SPACES)){
                        shakeError(get(R.id.totalAns1));
                    }
                }
            }
        }else if(step.getStep() == ActionStep.STEP_3){
            if(firstCarryTopNumEmpty()){
                if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                    return true;
                }
                shakeError(get(R.id.ans2));
                shakeError(get(R.id.totalAns2));
            }else{
                if(get1Id() == R.id.ans2 && get2Id() == R.id.topNum2){
                    return true;
                }
                shakeError(get(R.id.topNum2));
            }
            if(get(R.id.totalAns2).getBackground() != null && get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                return true;
            }
            shakeError(get(R.id.ans2));
            shakeError(get(R.id.totalAns2));
        }else if(step.getStep() == ActionStep.STEP_4){
            if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                return true;
            }
            shakeError(get(R.id.ans2));
            shakeError(get(R.id.totalAns2));
        }else if(step.getStep() == ActionStep.STEP_5){
            if(get1Id() == R.id.num2 && get2Id() == R.id.num4){
                return true;
            }
            shakeError(get(R.id.num2));
            shakeError(get(R.id.num4));
        }else if(step.getStep() == ActionStep.STEP_6){
            if(isBoxedEmpty()){
                if(get1Id() == R.id.num1 && get2Id() == R.id.num4){
                    return true;
                }
                shakeError(get(R.id.num1));
                shakeError(get(R.id.num4));
            }else{
                if(get1Id() == R.id.ans2 && get(R.id.topNum3).getBackground() !=null && get2Id() == R.id.topNum3){
                    return true;
                }
                boolean flag1 = false;
                boolean flag2 = false;
                if(get1Id() == R.id.ans2 && get2Id() != R.id.topNum3){
                    shakeError(get(R.id.ans2));
                    shakeError(get(R.id.topNum3));

                    flag1 = true;
                }
                if(get1Id() == R.id.ans1 && get2Id() == R.id.totalAns3){
                    return true;
                }
                if(get1Id() == R.id.ans1 && get2Id() != R.id.totalAns3 ){
                    shakeError(get(R.id.ans1));
                    shakeError(get(R.id.totalAns3));
                    flag2 = true;
                }
                if(!flag1 && !flag2){
                    shakeError(get(R.id.ans2));
                    shakeError(get(R.id.topNum3));
                    shakeError(get(R.id.ans1));
                    shakeError(get(R.id.totalAns3));
                }
            }
        }else if(step.getStep() == ActionStep.STEP_7){
            if(get1Id() == R.id.num1 && get2Id() == R.id.num4){
                return true;
            }
            shakeError(get(R.id.num1));
            shakeError(get(R.id.num4));
        }else if(step.getStep() == ActionStep.STEP_8){
            if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns4){
                return true;
            }
            shakeError(get(R.id.ans2));
            shakeError(get(R.id.totalAns4));

        }
        return false;
    }

    public boolean isBoxedEmpty(){
        return (get(R.id.ans1).getVisibility() == View.INVISIBLE && get(R.id.ans2).getVisibility() == View.INVISIBLE);
    }
    private boolean firstCarryTopNumEmpty(){
        return get(R.id.topNum).getText().toString() == "0";
    }
    public boolean isFinished(){
        if(get(R.id.add).getVisibility() == View.VISIBLE){
            removeListeners();
            return true;
        }
        return false;
    }

}
