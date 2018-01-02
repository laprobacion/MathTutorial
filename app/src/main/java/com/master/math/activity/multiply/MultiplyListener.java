package com.master.math.activity.multiply;

import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.base.DragListener;

public class MultiplyListener extends DragListener{

    MultiplyProcessor processor;

    public MultiplyListener(MultiplyProcessor processor, MultiplyValidator validator){
        super(validator);
        this.processor = processor;
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
                //((TextView) v).setVisibility(View.INVISIBLE);
                v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                ((TextView) v).setVisibility(View.VISIBLE);
                v.invalidate();
                break;
            case DragEvent.ACTION_DROP:
                if(getValidator().isFinished()){
                    break;
                }
                if(draggedItem.size() == 2) {
                    if(!getValidator().validate(draggedItem)){
                        processor.renderPopupWindow(null,false);
                        break;
                    }
                    if(processor.setTopNum(draggedItem)){
                        processor.renderPopupWindow(null, false);
                        draggedItem.clear();
                    }else if(processor.setTopNum3(draggedItem)){
                        processor.renderPopupWindow(null, false);
                        draggedItem.clear();
                    }else {
                        if(draggedItem.getItem(0).getId() == R.id.ans1 && draggedItem.getItem(1).getId() == R.id.totalAns1){
                            processor.setTotalAns1(draggedItem);
                        }else if(draggedItem.getItem(0).getId() == R.id.ans2 && draggedItem.getItem(1).getId() == R.id.totalAns2){
                            processor.setTotalAns2(draggedItem);
                        }else if(draggedItem.getItem(0).getId() == R.id.ans1 && draggedItem.getItem(1).getId() == R.id.totalAns3){
                            processor.setTotalAns3(draggedItem);
                        }else{
                            if((getValidator().getActionStep().getStep() == ActionStep.STEP_8 || getValidator().getActionStep().getStep() == ActionStep.STEP_9)
                                    && getValidator().get(R.id.topNum3).getText().toString().indexOf("0") == 0){
                                if(getValidator().getActionStep().getStep() == ActionStep.STEP_8){
                                    processor.setTotalAns4(draggedItem);
                                }
                                processor.renderPopupWindow(null, false);
                            }else{
                                processor.renderPopupWindow(draggedItem, true);
                            }

                        }
                    }
                }else{
                    if(draggedItem.getItem(0).getId() == R.id.ans1 || draggedItem.getItem(0).getId() == R.id.ans2){
                        ((TextView) v).setVisibility(View.VISIBLE);
                        v.invalidate();
                    }
                    processor.renderPopupWindow(null, false);
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
