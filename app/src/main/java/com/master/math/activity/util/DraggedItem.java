package com.master.math.activity.util;

import android.widget.TextView;

public class DraggedItem {
    private TextView[] dragItems;
    public DraggedItem(){
        clear();
    }
    public void clear(){
        dragItems = new TextView[2];
    }
    public void add(int index, TextView view){
        dragItems[index] = view;
    }
    public TextView getItem(int index){
       return dragItems[index];
    }
    public int size(){
        return dragItems.length;
    }
    public TextView[] getDragItems(){
        return dragItems;
    }
}
