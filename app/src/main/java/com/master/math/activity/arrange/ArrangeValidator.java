package com.master.math.activity.arrange;


import android.widget.TextView;

import com.master.math.activity.ArrangeFractionsActivity;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.Validator;

import static com.master.math.activity.util.Util.shakeError;

public class ArrangeValidator extends Validator{


    @Override
    public boolean startValidate() {
        if(getActionStep().getStep() == ActionStep.STEP_1){
            if(get1Id() == ArrangeFractionsActivity.lcd1Id && get2Id() == ArrangeFractionsActivity.denom1Id){
                return true;
            }
            get(ArrangeFractionsActivity.lcd1Id).startAnimation(shakeError());
            get(ArrangeFractionsActivity.denom1Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_2){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.num1Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.num1Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_3){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.lcm1Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.lcm1Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_4){
            if(get1Id() == ArrangeFractionsActivity.lcd2Id && get2Id() == ArrangeFractionsActivity.denom2Id){
                return true;
            }
            get(ArrangeFractionsActivity.lcd2Id).startAnimation(shakeError());
            get(ArrangeFractionsActivity.denom2Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_5){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.num2Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.num2Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_6){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.lcm2Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.lcm2Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_7){
            if(get1Id() == ArrangeFractionsActivity.lcd3Id && get2Id() == ArrangeFractionsActivity.denom3Id){
                return true;
            }
            get(ArrangeFractionsActivity.lcd3Id).startAnimation(shakeError());
            get(ArrangeFractionsActivity.denom3Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_8){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.num3Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.num3Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_9){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.lcm3Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.lcm3Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_10){
            if(get1Id() == ArrangeFractionsActivity.lcd4Id && get2Id() == ArrangeFractionsActivity.denom4Id){
                return true;
            }
            get(ArrangeFractionsActivity.lcd4Id).startAnimation(shakeError());
            get(ArrangeFractionsActivity.denom4Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_11){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.num4Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.num4Id).startAnimation(shakeError());
        }else if(getActionStep().getStep() == ActionStep.STEP_12){
            if(get1Id() == ArrangeFractionsActivity.tvAnsId && get2Id() == ArrangeFractionsActivity.lcm4Id){
                return true;
            }
            get(ArrangeFractionsActivity.tvAnsId).startAnimation(shakeError());
            get(ArrangeFractionsActivity.lcm4Id).startAnimation(shakeError());
        }
        return false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
