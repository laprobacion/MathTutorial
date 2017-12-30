package com.master.math.activity.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.master.math.activity.ArrangeFractionsActivity;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator {
    protected ActionStep step;
    private List<TextView> draggableItems;
    protected DraggedItem draggedItem;
    public Validator(){
        this.step = new ActionStep();
        draggableItems = new ArrayList<TextView>();
    }

    public ActionStep getActionStep() {        return step;    }

    public void removeListeners(){
        for(TextView v : draggableItems){
            if(v != null){
                v.setOnTouchListener(null);
                v.setOnDragListener(null);
            }
        }
    }
    protected TextView get1(){
        return this.draggedItem.getItem(0);
    }
    protected TextView get2(){
        return this.draggedItem.getItem(1);
    }
    protected int get1Id(){return this.draggedItem.getItem(0).getId(); }
    protected int get2Id(){
        return this.draggedItem.getItem(1).getId();
    }
    private boolean invalidateFalse(){
        get1().setVisibility(View.VISIBLE);
        get1().invalidate();
        get2().setVisibility(View.VISIBLE);
        get2().invalidate();
        return false;
    }
    public TextView get(int id){
        for(TextView v : draggableItems){
            if(v != null && v.getId() == id){
                return v;
            }
        }
        return null;
    }
    public boolean validate(DraggedItem draggedItem){
        if(isFinished()){
            return false;
        }
        this.draggedItem = draggedItem;
        if(startValidate()){
            return true;
        }else{
            return invalidateFalse();
        }
    }
    public void addDraggableItems(TextView... i){
        for(TextView num : i){
            draggableItems.add(num);
        }
    }

    public abstract boolean isFinished();
    public abstract boolean startValidate();
}
