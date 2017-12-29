package com.master.math.activity.addition;


import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.DragListener;
import com.master.math.activity.util.Util;

public class AdditionListener extends DragListener{
    AdditionProcessor processor;

    public AdditionListener(AdditionProcessor processor, AdditionValidator validator){
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
                ((TextView) v).setVisibility(View.INVISIBLE);
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
                        Util.showWithText(draggedItem.getItem(1),null);
                        processor.showPopup(draggedItem,true);
                        break;
                    }
                }else{
                    if(draggedItem.getItem(0).getId() == R.id.winAns1 || draggedItem.getItem(0).getId() == R.id.winAns2){
                        ((TextView) v).setVisibility(View.VISIBLE);
                        v.invalidate();
                    }
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
