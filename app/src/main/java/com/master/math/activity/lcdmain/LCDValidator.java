package com.master.math.activity.lcdmain;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;

import static com.master.math.activity.util.Util.shakeError;

public class LCDValidator extends Validator{

    @Override
    public boolean startValidate() {
        if(getActionStep().getStep() == ActionStep.STEP_1){
            if(get1Id() == R.id.finalDenom1 && get2Id() == R.id.denom1){
                return true;
            }
            if(get1Id() == R.id.finalDenom1 && get2Id() != R.id.denom1){
                get(R.id.denom1).startAnimation(shakeError());
            }

            if(get1Id() == R.id.finalDenom2 && get2Id() == R.id.denom2){
                return true;
            }
            if(get1Id() == R.id.finalDenom2 && get2Id() != R.id.denom2){
                get(R.id.denom2).startAnimation(shakeError());
            }

            if(get1Id() == R.id.finalDenom3 && get2Id() == R.id.denom3){
                return true;
            }
            if(get1Id() == R.id.finalDenom3 && get2Id() != R.id.denom3){
                get(R.id.denom3).startAnimation(shakeError());
            }
        }
        return false;
    }

    @Override
    public boolean isFinished() {
        return ActionStep.STEP_2 == getActionStep().getStep();
    }
}
