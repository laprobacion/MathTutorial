package com.master.math.activity.base;

import android.widget.TextView;

public class Initializer {
    private DragListener listener;
    public Initializer(DragListener listener){
        this.listener = listener;
    }


    public void setDraggables(TextView... view){
        for(TextView v : view){
            if(v != null){
                setListeners(v);
            }
        }
        listener.getValidator().addDraggableItems(view);
    }

    public void setListeners(TextView view){

        view.setOnTouchListener(listener);
        view.setOnDragListener(listener);
    }
}
