package com.master.math.activity.arrange;


import android.content.ClipData;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.activity.ArrangeFractionsActivity;
import com.master.math.activity.base.ActionStep;
import com.master.math.activity.util.DraggedRelativeLayout;

import java.util.ArrayList;
import java.util.List;
import static com.master.math.activity.util.Util.shakeError;

public class RelativeLayoutListener implements View.OnDragListener, View.OnTouchListener {
    private RelativeLayout[] sorted;
    private List<RelativeLayout> draggables;

    public void addDraggables(RelativeLayout... rls){
        for(RelativeLayout rl : rls){
            if(rl != null){
                draggables.add(rl);
            }
        }
    }
    private RelativeLayout get(int id){
        for(RelativeLayout rl : draggables){
            if(rl.getId() == id){
                return rl;
            }
        }
        return null;
    }
    public void setSorted(RelativeLayout[] sorted) {
        this.sorted = sorted;
    }

    DraggedRelativeLayout draggedItem;
    ActionStep actionStep;
    ArrangeProcessor processor;
    public RelativeLayoutListener(ActionStep actionStep,ArrangeProcessor processor){
        draggables = new ArrayList<RelativeLayout>();
        draggedItem = new DraggedRelativeLayout();
        this.actionStep = actionStep;
        this.processor = processor;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        draggedItem.add(0,(RelativeLayout) view);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "data frank");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(data, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                draggedItem.add(1,(RelativeLayout) v);
                ((RelativeLayout) v).setVisibility(View.INVISIBLE);
                v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                ((RelativeLayout) v).setVisibility(View.VISIBLE);
                v.invalidate();
                break;
            case DragEvent.ACTION_DROP:
                if(draggedItem.size() == 2) {
                    if(validate()){
                        RelativeLayout fromRL = draggedItem.getItem(0);
                        TextView [] tempView = new TextView[2];
                        tempView[0] = (TextView)fromRL.getChildAt(0);
                        tempView[1] = (TextView)fromRL.getChildAt(1);
                        fromRL.removeAllViews();
                        RelativeLayout toRL = draggedItem.getItem(1);
                        toRL.removeAllViews();
                        toRL.addView(tempView[0]);
                        toRL.addView(tempView[1]);
                        toRL.setVisibility(View.VISIBLE);
                        toRL.invalidate();
                        actionStep.increment();
                    }else{
                        draggedItem.getItem(0).setVisibility(View.VISIBLE);
                        draggedItem.getItem(1).setVisibility(View.VISIBLE);
                    }
                }else{
                    ((RelativeLayout) v).setVisibility(View.VISIBLE);
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

    private boolean validate(){
        if(actionStep.getStep() == ActionStep.STEP_13){
            int id = ArrangeFractionsActivity.arrange1Id;
            if(draggedItem.getItem(0).getId() == sorted[0].getId() && draggedItem.getItem(1).getId() == id){
                return true;
            }
            shakeError(sorted[0]);
            shakeError(get(id));
        }else if(actionStep.getStep() == ActionStep.STEP_14){
            int id = ArrangeFractionsActivity.arrange2Id;
            if(draggedItem.getItem(0).getId() == sorted[1].getId() && draggedItem.getItem(1).getId() == id){
                return true;
            }
            shakeError(sorted[1]);
            shakeError(get(ArrangeFractionsActivity.arrange2Id));
        }else if(actionStep.getStep() == ActionStep.STEP_15){
            int id = ArrangeFractionsActivity.arrange3Id;
            if(draggedItem.getItem(0).getId() == sorted[2].getId() && draggedItem.getItem(1).getId() == id){
                return true;
            }
            shakeError(sorted[2]);
            shakeError(get(ArrangeFractionsActivity.arrange3Id));
        }else if(actionStep.getStep() == ActionStep.STEP_16){
            int id = ArrangeFractionsActivity.arrange4Id;
            if(sorted[3] != null) {
                if (draggedItem.getItem(0).getId() == sorted[3].getId() && draggedItem.getItem(1).getId() == id) {
                    this.processor.finish();
                    return true;
                }
                shakeError(sorted[3]);
                shakeError(get(ArrangeFractionsActivity.arrange4Id));
            }
        }
        return false;
    }
}
