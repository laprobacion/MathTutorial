package com.master.math.activity.fraction;


import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;

import static com.master.math.activity.util.Util.shakeError;

public class CompareFractionValidator extends Validator{


    public CompareFractionValidator(){    }


    public boolean startValidate(){
        if(step.getStep() == ActionStep.STEP_1){
            boolean flag = validateStep(R.id.num1, R.id.denom2);
            if(flag){
                return true;
            }else{
                get(R.id.num1).startAnimation(shakeError());
                get(R.id.denom2).startAnimation(shakeError());
            }
        }
        if(step.getStep() == ActionStep.STEP_2){
            boolean flag = validateStep(R.id.num2, R.id.denom1);
            if(flag){
                return true;
            }else{
                get(R.id.num2).startAnimation(shakeError());
                get(R.id.denom1).startAnimation(shakeError());
            }
        }
        if(step.getStep() == ActionStep.STEP_3){
            boolean flag1 = false,flag2 = false,flag3 = false;
            if(get1Id() == R.id.greaterSign && get2Id() != R.id.compareLine){
                get1().startAnimation(shakeError());
                get(R.id.compareLine).startAnimation(shakeError());
                flag1 = true;
            }
            if(get1Id() == R.id.greaterSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(get1Id() == R.id.lessSign && get2Id() != R.id.compareLine){
                get1().startAnimation(shakeError());
                get(R.id.compareLine).startAnimation(shakeError());
                flag2 = true;
            }
            if(get1Id() == R.id.lessSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(get1Id() == R.id.equalSign && get2Id() != R.id.compareLine){
                get1().startAnimation(shakeError());
                get(R.id.compareLine).startAnimation(shakeError());
                flag3 = true;
            }
            if(get1Id() == R.id.equalSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(!flag1 && !flag2 && !flag3){
                get(R.id.greaterSign).startAnimation(shakeError());
                get(R.id.lessSign).startAnimation(shakeError());
                get(R.id.equalSign).startAnimation(shakeError());
            }
            if(get1Id() == R.id.compareLine){
                return false;
            }
        }
        return false;
    }

    private boolean validateStep(int num, int denom){
        if(get1Id() == num && get2Id() == denom){
            return true;
        }
        if(get1Id() == num){
            get1().startAnimation(shakeError());
            get(denom).startAnimation(shakeError());
        }
        if(get1Id() == denom && get2Id() == num){
            return true;
        }
        if(get1Id() == denom){
            get1().startAnimation(shakeError());
            get(num).startAnimation(shakeError());
        }
        return false;
    }

    @Override
    public boolean isFinished() {
        return this.step.getStep() == ActionStep.STEP_4;
    }
}
