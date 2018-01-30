package com.master.math.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.master.math.R;

public class HelpActivity extends AppCompatActivity {

    public static final String  GIF_ID = "GIF_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
        Button closeBtn = (Button) findViewById(R.id.closeBtn);
        pl.droidsonroids.gif.GifImageView gif = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.gif);
        int gifID = R.drawable.cat;
        if(getIntent().getStringExtra(GIF_ID) != null && Integer.valueOf(getIntent().getStringExtra(GIF_ID)) != 0){
            gifID = Integer.valueOf(getIntent().getStringExtra(GIF_ID));
        }
        gif.setImageResource(gifID);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}