package com.master.math.activity.multiply;


import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;
import com.master.math.activity.util.DraggedItem;
import java.util.ArrayList;
import java.util.List;

import static com.master.math.activity.util.Util.shakeError;

public class MultiplyValidator extends Validator{

    public MultiplyValidator(){}

    public boolean startValidate(){
        if(step.getStep() == ActionStep.STEP_1){
            if(get1Id() == R.id.num2 && get2Id() == R.id.num3){
                return true;
            }
            get(R.id.num2).startAnimation(shakeError());
            get(R.id.num3).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_2){
            if(isBoxedEmpty()){
                if(get1Id() == R.id.num1 && get2Id() == R.id.num3){
                    return true;
                }
                get(R.id.num1).startAnimation(shakeError());
                get(R.id.num3).startAnimation(shakeError());
            }else{
                if(get1Id() == R.id.ans2 && get(R.id.topNum).getBackground() !=null && get2Id() == R.id.topNum){
                    return true;
                }
                boolean flag1 = false;
                boolean flag2 = false;
                if(get1Id() == R.id.ans2){
                    get(R.id.ans2).startAnimation(shakeError());
                    if(get(R.id.topNum).getVisibility() == View.VISIBLE){
                        get(R.id.topNum).startAnimation(shakeError());
                    }
                    flag1 = true;
                }
                if(get1Id() == R.id.ans1 && get2Id() == R.id.totalAns1){
                    return true;
                }
                if(get1Id() == R.id.ans1){
                    get(R.id.ans1).startAnimation(shakeError());
                    get(R.id.totalAns1).startAnimation(shakeError());
                    flag1 = true;
                }
                if(!flag1 && !flag2){
                    get(R.id.ans2).startAnimation(shakeError());
                    if(get(R.id.topNum).getVisibility() == View.VISIBLE){
                        get(R.id.topNum).startAnimation(shakeError());
                    }
                    if(get(R.id.ans1).getVisibility() == View.VISIBLE){
                        get(R.id.ans1).startAnimation(shakeError());
                    }
                    get(R.id.totalAns1).startAnimation(shakeError());
                }
            }
        }
        if(step.getStep() == ActionStep.STEP_3){
            if(firstCarryTopNumEmpty()){
                if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                    return true;
                }
                get(R.id.ans2).startAnimation(shakeError());
                get(R.id.totalAns2).startAnimation(shakeError());
            }else{
                if(get1Id() == R.id.ans2 && get2Id() == R.id.topNum2){
                    return true;
                }
                if(get(R.id.topNum2).getVisibility() == View.VISIBLE){
                    get(R.id.topNum2).startAnimation(shakeError());
                }
            }
            if(get(R.id.totalAns2).getBackground() != null && get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                return true;
            }
            if(get(R.id.totalAns2).getBackground() != null && get1Id() == R.id.ans2 ){
                if(get(R.id.ans2).getVisibility() == View.VISIBLE){
                    get(R.id.ans2).startAnimation(shakeError());
                }
                get(R.id.totalAns2).startAnimation(shakeError());
            }
        }
        if(step.getStep() == ActionStep.STEP_4){
            if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns2){
                return true;
            }
            get(R.id.ans2).startAnimation(shakeError());
            get(R.id.totalAns2).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_5){
            if(get1Id() == R.id.num2 && get2Id() == R.id.num4){
                return true;
            }
            get(R.id.num2).startAnimation(shakeError());
            get(R.id.num4).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_6){
            if(isBoxedEmpty()){
                if(get1Id() == R.id.num1 && get2Id() == R.id.num4){
                    return true;
                }
                get(R.id.num1).startAnimation(shakeError());
                get(R.id.num4).startAnimation(shakeError());
            }else{
                if(get1Id() == R.id.ans2 && get(R.id.topNum3).getBackground() !=null && get2Id() == R.id.topNum3){
                    return true;
                }
                boolean flag1 = false;
                boolean flag2 = false;
                if(get1Id() == R.id.ans2 && get2Id() != R.id.topNum3){
                    get(R.id.ans2).startAnimation(shakeError());
                    if(get(R.id.topNum3).getVisibility() == View.VISIBLE){
                        get(R.id.topNum3).startAnimation(shakeError());
                    }
                    flag1 = true;
                }
                if(get1Id() == R.id.ans1 && get2Id() == R.id.totalAns3){
                    return true;
                }
                if(get1Id() == R.id.ans1 && get2Id() != R.id.totalAns3 ){
                    get(R.id.ans1).startAnimation(shakeError());
                    get(R.id.totalAns3).startAnimation(shakeError());
                    flag2 = true;
                }
                if(!flag1 && !flag2){
                    get(R.id.ans2).startAnimation(shakeError());
                    if(get(R.id.topNum3).getVisibility() == View.VISIBLE){
                        get(R.id.topNum3).startAnimation(shakeError());
                    }
                    get(R.id.ans1).startAnimation(shakeError());
                    get(R.id.totalAns3).startAnimation(shakeError());
                }
            }
        }
        if(step.getStep() == ActionStep.STEP_7){
            if(get1Id() == R.id.num1 && get2Id() == R.id.num4){
                return true;
            }
            get(R.id.num1).startAnimation(shakeError());
            get(R.id.num4).startAnimation(shakeError());
        }
        if(step.getStep() == ActionStep.STEP_8){
            if(get1Id() == R.id.ans2 && get2Id() == R.id.totalAns4){
                return true;
            }
            get(R.id.ans2).startAnimation(shakeError());
            get(R.id.totalAns4).startAnimation(shakeError());
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
