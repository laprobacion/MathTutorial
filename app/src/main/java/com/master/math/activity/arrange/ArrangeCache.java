package com.master.math.activity.arrange;

import com.master.math.activity.util.DraggedItem;

public class ArrangeCache {
    private static ArrangeCache _this;
    private DraggedItem draggedItem;
    private ArrangeCache(){}
    public static ArrangeCache get(){
        if(_this == null){
            _this = new ArrangeCache();
        }
        return _this;
    }

    public void clear(){
        this.draggedItem = null;
    }
    public DraggedItem getDraggedItem() {
        return draggedItem;
    }

    public void setDraggedItem(DraggedItem draggedItem) {
        this.draggedItem = draggedItem;
    }
}
