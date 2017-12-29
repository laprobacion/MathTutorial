package com.master.math.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.master.math.R;
import com.master.math.activity.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Util.setAsset(getAssets());
        Button btnCompareFraction = (Button) findViewById(R.id.btnCompareFraction);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/EraserDust.ttf");
        btnCompareFraction.setTypeface(typeface);
        btnCompareFraction.setText("Compare Fractions");
        btnCompareFraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FractionActivity.class));
            }
        });

        Button btnLCD = (Button) findViewById(R.id.btnLCD);
        btnLCD.setTypeface(typeface);
        btnLCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LCDMainActivity.class));
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setTypeface(typeface);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdditionActivity.class));
            }
        });

        Button multiply = (Button) findViewById(R.id.multiply);
        multiply.setTypeface(typeface);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MultiplyActivity.class));
            }
        });

        Button arrange = (Button) findViewById(R.id.arrange);
        arrange.setTypeface(typeface);
        arrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ArrangeFractionsActivity.class));
            }
        });

        Button divide = (Button) findViewById(R.id.divide);
        divide.setTypeface(typeface);
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DivideActivity.class));
            }
        });
    }
}
