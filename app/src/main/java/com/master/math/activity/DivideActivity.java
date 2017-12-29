package com.master.math.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.master.math.R;

public class DivideActivity extends AppCompatActivity {

    public static final String NUM_1 = "NUM1";
    public static final String NUM_2 = "NUM2";
    public static final String NUM_3 = "NUM3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_divide);
    }
}
