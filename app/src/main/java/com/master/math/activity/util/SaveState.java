package com.master.math.activity.util;


import android.app.Activity;
import android.app.FragmentManagerNonConfig;
import android.content.Context;

import com.master.math.activity.FractionActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveState implements Serializable{
    private int correctAns;
    private int multiplicationMistakes;
    private List<Item> items;
    public Date startTime;
    public Date endTime;
    public void addItem(Item item){
        getItems().add(item);
    }
    public List<Item> getItems(){
        return items;
    }
    public int getMultiplicationMistakes(){return multiplicationMistakes;}
    public void startSeatwork(){
        multiplicationMistakes = 0;
        items = new ArrayList<Item>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date));
        startTime = date;//dateFormat.format(date);
        endTime = null;
    }
    public void endSeatwork(Activity activity){
        endTime = new Date();
        clearAns();
        save(activity);
    }
    public boolean isComplete(String activityType, String lesson){
        int lessonMax = 3;
        int seatworkLesson1Max = 5;
        int seatworkLesson2Max = 5;
        int seatworkLesson3Max = 10;
        if(activityType.equals(FractionActivity.ACTIVITY_LESSON)){
            return lessonMax != getCorrectAns();
        }else{
            if(lesson.equals(FractionActivity.LESSON_1)){
                return seatworkLesson1Max != getCorrectAns();
            }else if(lesson.equals(FractionActivity.LESSON_2)){
                return seatworkLesson2Max != getCorrectAns();
            }else if(lesson.equals(FractionActivity.LESSON_3)){
                return seatworkLesson3Max != getCorrectAns();
            }
        }
        return true;
    }
    public int getCorrectAns() {        return correctAns;    }

    public void incrementCorrectAns() {        this.correctAns++;    }
    public void incrementMultiplicationMistakes() {        this.multiplicationMistakes++;    }
    public void clearAns() {        this.correctAns = 0;    }
    private SaveState(){}
    private boolean isSimilarDenominatorDone;
    private boolean isSimilarNumeratorDone;
    private boolean isDissimilarFractionDone;

    private boolean isSimilarDenominatorDone_Seatwork;
    private boolean isSimilarNumeratorDone_Seatwork;
    private boolean isDissimilarFractionDone_Seatwork;

    public boolean isSimilarDenominatorDone_Seatwork() {        return isSimilarDenominatorDone_Seatwork;    }
    public boolean isSimilarNumeratorDone_Seatwork() {        return isSimilarNumeratorDone_Seatwork;    }
    public boolean isDissimilarFractionDone_Seatwork() {        return isDissimilarFractionDone_Seatwork;    }

    public void setSimilarDenominatorDone_Seatwork() {        isSimilarDenominatorDone_Seatwork = true;    }

    public void setSimilarNumeratorDone_Seatwork() {        isSimilarNumeratorDone_Seatwork = true;    }

    public void setDissimilarFractionDone_Seatwork() {        isDissimilarFractionDone_Seatwork = true;    }

    private static SaveState _this;
    public void setSimilarDenominatorDone(Context context){
        isSimilarDenominatorDone = true;
        correctAns = 0;
        save(context);
    }
    public void setSimilarNumeratorDone(Context context){
        isSimilarNumeratorDone = true;
        correctAns = 0;
        save(context);
    }
    public void setDissimilarFractionDone(Context context){
        isDissimilarFractionDone = true;
        correctAns = 0;
        save(context);
    }
    public boolean isSimilarDenominatorDone(){
        return isSimilarDenominatorDone;
    }
    public boolean isSimilarNumeratorDone(){
        return isSimilarNumeratorDone;
    }
    public boolean isDissimilarFractionDone(){
        return isDissimilarFractionDone;
    }

    public static SaveState get(Context context){
        if(_this == null){
            SaveState e = load(context);
            if(e == null){
                _this = new SaveState();
            }else{
                _this = e;
            }
        }
        return _this;
    }

    public void save(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("Storage", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
            fos.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
    }

    private static SaveState load(Context context){
        SaveState e = null;
        try {
            FileInputStream fis = context.openFileInput("Storage");
            ObjectInputStream in = new ObjectInputStream(fis);
            e = (SaveState) in.readObject();
            in.close();
            fis.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
        return e;
    }
}
