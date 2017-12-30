package com.master.math.activity.util;


import android.widget.RelativeLayout;

public class DraggedRelativeLayout {
    private RelativeLayout[] dragItems;
    public DraggedRelativeLayout(){
        clear();
    }
    public void clear(){
        dragItems = new RelativeLayout[2];
    }
    public void add(int index, RelativeLayout view){
        dragItems[index] = view;
    }
    public RelativeLayout getItem(int index){
        return dragItems[index];
    }
    public int size(){
        return dragItems.length;
    }
    public RelativeLayout[] getDragItems(){
        return dragItems;
    }
}
