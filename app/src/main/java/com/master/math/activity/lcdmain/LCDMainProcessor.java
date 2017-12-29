package com.master.math.activity.lcdmain;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.base.Processor;
import com.master.math.activity.util.DraggedItem;
import com.master.math.activity.util.Util;

public class LCDMainProcessor implements Processor{
    private LCDValidator validator;
    private Initializer initializer;
    private TextView denom1,finalDenom1,denom2,finalDenom2,denom3,finalDenom3;
    private Activity activity;
    public LCDMainProcessor(Activity activity, String lcd){
        this.activity = activity;
        this.validator = new LCDValidator();
        denom1 = Util.getTextViewWithFont(activity, R.id.denom1);
        finalDenom1 = Util.getTextViewWithFont(activity, R.id.finalDenom1);
        denom2 = Util.getTextViewWithFont(activity, R.id.denom2);
        finalDenom2 = Util.getTextViewWithFont(activity, R.id.finalDenom2);
        denom3 = Util.getTextViewWithFont(activity, R.id.denom3);
        finalDenom3 = Util.getTextViewWithFont(activity, R.id.finalDenom3);
        initializer = new Initializer(new LCDMainListener(this,validator));
        initializer.setDraggables(denom1,finalDenom1,denom2,finalDenom2,denom3,finalDenom3);
        Util.showWithText(finalDenom1,lcd);
        Util.showWithText(finalDenom2,lcd);
        Util.showWithText(finalDenom3,lcd);

    }
    public void allowDrop(DraggedItem draggedItem){
        Util.showWithText(draggedItem.getItem(1),draggedItem.getItem(0).getText().toString());
        Util.hide(draggedItem.getItem(0));
        finish();
    }
    private void finish(){
        if(finalDenom1.getVisibility() == View.INVISIBLE &&
                finalDenom2.getVisibility() == View.INVISIBLE &&
                finalDenom3.getVisibility() == View.INVISIBLE){
            validator.getActionStep().increment();
            validator.removeListeners();
        }
    }
}
