package com.master.math.activity.divide;

import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.base.Initializer;
import com.master.math.activity.util.Util;

public class DivideProcessor {

    private Activity activity;

    private Initializer initializer;

    public DivideProcessor(Activity activity){
        this.activity = activity;
        TextView num1 = Util.createTextView(1,"9",50,200,300,false,(RelativeLayout)activity.findViewById(R.id.divideLayout),activity);
        TextView divider = Util.createTextView(2,"|",50,200,320,false,(RelativeLayout)activity.findViewById(R.id.divideLayout),activity);
        TextView topDivider = Util.createTextView(3,"___",50,200,320,false,(RelativeLayout)activity.findViewById(R.id.divideLayout),activity);
        TextView num2 = Util.createTextView(4,"18",50,200,340,false,(RelativeLayout)activity.findViewById(R.id.divideLayout),activity);
    }
}
