package com.master.math.activity.fraction;


import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;
import com.master.math.activity.util.DraggedItem;

import static com.master.math.activity.util.Util.shakeError;

public class CompareFractionValidator extends Validator{


    public CompareFractionValidator(){    }


    public boolean startValidate(){
        if(step.getStep() == ActionStep.STEP_1 || step.getStep() == ActionStep.STEP_2){
            if(get1Id() == R.id.num1 && get2Id() == R.id.denom2){
                return true;
            }
            if(get1Id() == R.id.denom2 && get2Id() == R.id.num1){
                return true;
            }
            if(get1Id() == R.id.num2 && get2Id() == R.id.denom1){
                return true;
            }
            if(get1Id() == R.id.denom1 && get2Id() == R.id.num2){
                return true;
            }
            if (get1Id() == R.id.denom1) {
                shakeError(get(R.id.num2));
            }
            if (get1Id() == R.id.num2) {
                shakeError(get(R.id.denom1));
            }
            if (get1Id() == R.id.num1) {
                shakeError(get(R.id.denom2));
            }
            if (get1Id() == R.id.denom2) {
                shakeError(get(R.id.num1));
            }
        }
        if(step.getStep() == ActionStep.STEP_3){
            boolean flag1 = false,flag2 = false,flag3 = false;
            if(get1Id() == R.id.greaterSign && get2Id() != R.id.compareLine){
                shakeError(get1());
                shakeError(get(R.id.compareLine));
                flag1 = true;
            }
            if(get1Id() == R.id.greaterSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(get1Id() == R.id.lessSign && get2Id() != R.id.compareLine){
                shakeError(get1());
                shakeError(get(R.id.compareLine));
                flag2 = true;
            }
            if(get1Id() == R.id.lessSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(get1Id() == R.id.equalSign && get2Id() != R.id.compareLine){
                shakeError(get1());
                shakeError(get(R.id.compareLine));
                flag3 = true;
            }
            if(get1Id() == R.id.equalSign && get2Id() == R.id.compareLine){
                return true;
            }
            if(!flag1 && !flag2 && !flag3){
                shakeError(get(R.id.greaterSign));
                shakeError(get(R.id.lessSign));
                shakeError(get(R.id.equalSign));
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
            shakeError(get1());
            shakeError(get(denom));
        }
        if(get1Id() == denom && get2Id() == num){
            return true;
        }
        if(get1Id() == denom){
            shakeError(get1());
            shakeError(get(num));
        }
        return false;
    }

    @Override
    public boolean isFinished() {
        return this.step.getStep() == ActionStep.STEP_4;
    }

    public DraggedItem getDraggedItem(){
        return this.draggedItem;
    }
}
