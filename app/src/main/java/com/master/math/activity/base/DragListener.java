package com.master.math.activity.base;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.master.math.activity.util.DraggedItem;

public abstract class DragListener implements View.OnDragListener, View.OnTouchListener{
    protected DraggedItem draggedItem;
    private Validator validator;

    public Validator getValidator() {        return validator;    }
    public DragListener(Validator validator){
        draggedItem = new DraggedItem();
        this.validator = validator;
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        draggedItem.add(0,(TextView) view);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", ((TextView)view).getText().toString());
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }
}
