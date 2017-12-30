package com.master.math.activity.base;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.activity.arrange.RelativeLayoutListener;

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
    public void setListeners(RelativeLayoutListener listeners, RelativeLayout... layouts){
        for(RelativeLayout rl : layouts){
            if(rl != null){
                rl.setOnTouchListener(listeners);
                rl.setOnDragListener(listeners);
            }
        }
    }
}
