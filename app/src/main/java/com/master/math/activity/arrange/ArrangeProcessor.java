package com.master.math.activity.arrange;


import android.app.Activity;
import android.content.Intent;

import com.master.math.activity.ArrangeFractionsActivity;
import com.master.math.activity.FormActivity;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.util.DraggedItem;

public class ArrangeProcessor {

    private ArrangeFractionsActivity activity;
    private ArrangeValidator validator;

    public ArrangeFractionsActivity getActivity() {        return activity;    }

    public ArrangeProcessor(ArrangeFractionsActivity activity, ArrangeValidator validator){
        this.activity = activity;
        this.validator = validator;
    }
    public void allowDrag(DraggedItem draggedItem){
        if(validator.getActionStep().getStep() == ActionStep.STEP_1 || validator.getActionStep().getStep() == ActionStep.STEP_4 || validator.getActionStep().getStep() == ActionStep.STEP_7){
            Intent intent = new Intent(activity, FormActivity.class);
            intent.putExtra(FormActivity.OPERATION, FormActivity.OPERATION_DIVIDE);
            intent.putExtra(FormActivity.DIVIDE_NUM_1, draggedItem.getItem(0).getText().toString());
            intent.putExtra(FormActivity.DIVIDE_NUM_2, draggedItem.getItem(1).getText().toString());
            activity.startActivity(intent);
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_2 || validator.getActionStep().getStep() == ActionStep.STEP_5 || validator.getActionStep().getStep() == ActionStep.STEP_8){
            Intent intent = new Intent(activity, FormActivity.class);
            intent.putExtra(FormActivity.OPERATION, FormActivity.OPERATION_MULTIPLY);
            intent.putExtra(FormActivity.MULTIPLY_NUM_1, draggedItem.getItem(0).getText().toString());
            intent.putExtra(FormActivity.MULTIPLY_NUM_2, draggedItem.getItem(1).getText().toString());
            activity.startActivity(intent);
        }else if(validator.getActionStep().getStep() == ActionStep.STEP_3 || validator.getActionStep().getStep() == ActionStep.STEP_6 || validator.getActionStep().getStep() == ActionStep.STEP_9){
            activity.setLCM(draggedItem);
        }
    }
}
