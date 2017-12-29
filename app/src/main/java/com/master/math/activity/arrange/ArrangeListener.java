package com.master.math.activity.arrange;


import com.master.math.activity.ArrangeFractionsActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.DragListener;
import com.master.math.activity.util.Util;

public class ArrangeListener extends DragListener{

    ArrangeValidator validator;
    ArrangeProcessor processor;
    public ArrangeListener(ArrangeProcessor arrangeProcessor, ArrangeValidator validator){
        super(validator);
        this.processor = arrangeProcessor;
        this.validator = validator;

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        String t = ((TextView)v).getText().toString();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                draggedItem.add(1,(TextView) v);
                ((TextView) v).setVisibility(View.INVISIBLE);
                if(((TextView) v).getBackground() != null){
                    ((TextView) v).setBackground(null);
                }
                v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                ((TextView) v).setVisibility(View.VISIBLE);
                v.invalidate();
                break;
            case DragEvent.ACTION_DROP:
                if(draggedItem.size() == 2) {
                    if(!getValidator().validate(draggedItem)){
                        break;
                    }else{
                        if((validator.getActionStep().getStep() == ActionStep.STEP_2 || validator.getActionStep().getStep() == ActionStep.STEP_5 || validator.getActionStep().getStep() == ActionStep.STEP_8 || validator.getActionStep().getStep() == ActionStep.STEP_11)){
                            Util.showWithTextUnderlined(draggedItem.getItem(1),draggedItem.getItem(1).getText().toString());
                        }else if((validator.getActionStep().getStep() == ActionStep.STEP_1 || validator.getActionStep().getStep() == ActionStep.STEP_4 || validator.getActionStep().getStep() == ActionStep.STEP_7|| validator.getActionStep().getStep() == ActionStep.STEP_10)){
                            Util.showWithText(draggedItem.getItem(1),null);
                        }
                        processor.allowDrag(draggedItem);
                        break;
                    }
                }else{
                    ((TextView) v).setVisibility(View.VISIBLE);
                    v.invalidate();
                    draggedItem.clear();
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }
}
