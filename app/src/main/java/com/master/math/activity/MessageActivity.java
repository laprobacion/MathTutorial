package com.master.math.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.util.SaveState;

import static com.master.math.activity.util.Util.showGif;

public class MessageActivity extends AppCompatActivity {
    public static final String MESSAGE = "MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText(getIntent().getStringExtra(MESSAGE));

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                String lesson = getIntent().getStringExtra(FractionActivity.ACTIVITY_LESSON);
                String activityType = getIntent().getStringExtra(FractionActivity.ACTIVITY_TYPE);
                if(SaveState.get(MessageActivity.this).isComplete(activityType,lesson)){
                    if(lesson.equals(FractionActivity.LESSON_1)){
                        Intent lesson1Intent = new Intent(MessageActivity.this, FractionActivity.class);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,activityType);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_1);
                        startActivity(lesson1Intent);
                    }else if(lesson.equals(FractionActivity.LESSON_2)){
                        Intent lesson1Intent = new Intent(MessageActivity.this, FractionActivity.class);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,activityType);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_2);
                        startActivity(lesson1Intent);
                    }else{
                        Intent lesson1Intent = new Intent(MessageActivity.this, FractionActivity.class);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,activityType);
                        lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_3);
                        startActivity(lesson1Intent);
                    }
                }else{
                    if(lesson.equals(FractionActivity.LESSON_1)){
                        if(activityType.equals(FractionActivity.ACTIVITY_LESSON)){
                            SaveState.get(MessageActivity.this).setSimilarDenominatorDone(MessageActivity.this);
                            showGif(R.drawable.outro,MessageActivity.this);
                        }else{
                            SaveState.get(MessageActivity.this).setSimilarDenominatorDone_Seatwork();
                            Intent lesson1Intent = new Intent(MessageActivity.this, FractionActivity.class);
                            lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,activityType);
                            lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_2);
                            startActivity(lesson1Intent);
                            SaveState.get(MessageActivity.this).clearAns();
                        }
                    }else if(lesson.equals(FractionActivity.LESSON_2)){
                        if(activityType.equals(FractionActivity.ACTIVITY_LESSON)){
                            SaveState.get(MessageActivity.this).setSimilarNumeratorDone(MessageActivity.this);
                            showGif(R.drawable.outro,MessageActivity.this);
                        }else{
                            SaveState.get(MessageActivity.this).setSimilarNumeratorDone_Seatwork();
                            Intent lesson1Intent = new Intent(MessageActivity.this, FractionActivity.class);
                            lesson1Intent.putExtra(FractionActivity.ACTIVITY_TYPE,activityType);
                            lesson1Intent.putExtra(FractionActivity.ACTIVITY_LESSON,FractionActivity.LESSON_3);
                            startActivity(lesson1Intent);
                            SaveState.get(MessageActivity.this).clearAns();
                        }
                    }else{
                        if(activityType.equals(FractionActivity.ACTIVITY_LESSON)){
                            SaveState.get(MessageActivity.this).setDissimilarFractionDone(MessageActivity.this);
                            showGif(R.drawable.outro,MessageActivity.this);
                        }else{
                            SaveState.get(MessageActivity.this).setDissimilarFractionDone_Seatwork();
                            SaveState.get(MessageActivity.this).endSeatwork(MessageActivity.this);
                            SaveState.get(MessageActivity.this).clearAns();
                            Intent i = new Intent(MessageActivity.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            startActivity(new Intent(MessageActivity.this,StatsActivity.class));

                        }
                    }
                }
            }
        },0);
    }
}
