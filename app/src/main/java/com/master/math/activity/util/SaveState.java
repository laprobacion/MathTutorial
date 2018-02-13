package com.master.math.activity.util;


import android.app.Activity;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.FractionActivity;
import com.master.math.activity.LoginRegisterActivity;
import com.master.math.activity.MainActivity;
import com.master.math.activity.service.Service;
import com.master.math.activity.service.ServiceResponse;
import com.master.math.activity.service.UserService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SaveState implements Serializable{
    private Map<String, String> storage;
    private int correctAns;
    private int multiplicationMistakes;
    private int skippedItems;

    private int seatworkNumber;

    private int lessonMax = 4;
    private int seatworkLesson1Max = 1;//5;
    private int seatworkLesson2Max = 1;//5;
    private int seatworkLesson3Max = 1;//10;
    private int seatworkMax = seatworkLesson1Max + seatworkLesson2Max + seatworkLesson3Max;
    //setSeatworkMax(seatworkMax);

    private List<Item> items;
    public Date startTime;
    public Date endTime;
    public void addItem(Item item){
        getItems().add(item);
    }
    public List<Item> getItems(){
        return items;
    }

    private int getMultiplicationMistakes(){return multiplicationMistakes;}

    private int getSkippedItems() {return skippedItems;}

    private int getSeatworkNumber() {return seatworkNumber;}

    public int getSeatworkMax(){ return this.seatworkMax;}
    //private void setSeatworkMax(int seatworkMax){this.seatworkMax = seatworkMax;}
    public void startSeatwork(){
        clearAllCount();
        items = new ArrayList<Item>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date));
        startTime = date;//dateFormat.format(date);
        endTime = null;
    }
    public void endSeatwork(Activity activity){
        endTime = new Date();
        int score = save(activity);
        updateScore(score, activity);
        clearAllCount();
    }
    private void updateScore(int score, Activity activity){
        final TextView errorMsg = (TextView) activity.findViewById(R.id.loginError);
        Service service = new Service("Saving...", activity, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject resp) {
                try {
                String s = "";
                }catch (Exception e){e.printStackTrace();}
            }
        });
        if(score > AppCache.getInstance().getUser().getScore()){
            AppCache.getInstance().getUser().setScore(score);
            UserService.updateScore(AppCache.getInstance().getUser(), service);
        }

    }
    public boolean isComplete(String activityType, String lesson){
        if(activityType.equals(FractionActivity.ACTIVITY_LESSON)){
            return lessonMax != getCorrectAns();
        }else {
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
    private int getCorrectAns() {        return correctAns;    }

    public void incrementCorrectAns() {        this.correctAns++;    }
    public void incrementMultiplicationMistakes() {        this.multiplicationMistakes++;    }
    public void incrementSkippedItems() {
        this.skippedItems++;
    }
    public void incrementSeatworkNumber() {this.seatworkNumber++;}
    private void clearAllCount(){
        multiplicationMistakes = 0;
        skippedItems = 0;
        seatworkNumber = 1;
        clearAns();
    }
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
        clearAns();
        save(context);
    }
    public void setSimilarNumeratorDone(Context context){
        isSimilarNumeratorDone = true;
        clearAns();
        save(context);
    }
    public void setDissimilarFractionDone(Context context){
        isDissimilarFractionDone = true;
        clearAns();
        save(context);
    }
    public boolean isSimilarDenominatorDone(){
        isSimilarDenominatorDone = Boolean.valueOf(this.storage.get("isSimilarDenominatorDone"));
        if(this.storage.get("isSimilarDenominatorDone") != null){
            return Boolean.valueOf(this.storage.get("isSimilarDenominatorDone"));
        }
        return isSimilarDenominatorDone;
    }
    public boolean isSimilarNumeratorDone(){
        isSimilarNumeratorDone = Boolean.valueOf(this.storage.get("isSimilarNumeratorDone"));
        if(this.storage.get("isSimilarNumeratorDone") != null){
            return Boolean.valueOf(this.storage.get("isSimilarNumeratorDone"));
        }
        return isSimilarNumeratorDone;
    }
    public boolean isDissimilarFractionDone(){
        isDissimilarFractionDone = Boolean.valueOf(this.storage.get("isDissimilarFractionDone"));
        if(this.storage.get("isDissimilarFractionDone") != null){
            return Boolean.valueOf(this.storage.get("isDissimilarFractionDone"));
        }
        return isDissimilarFractionDone;
    }

    public static SaveState get(Context context){
        if(_this == null){
            _this = new SaveState();
        }
        _this.load(context);
        return _this;
    }
    private static String FILENAME = "storage_math";
    public String getTimeSpentString(){
        if(this.storage.get("getTimeSpent") != null){
            return this.storage.get("getTimeSpent");
        }
        return "";
    }
    public int getMultiplicationMistakeCount(){
        if(this.storage.get("getMultiplicationMistakes") != null){
            return Integer.valueOf(this.storage.get("getMultiplicationMistakes"));
        }
        return 0;
    }
    public int getSkippedItemsCount(){
        if(this.storage.get("getSkippedItems") != null){
            return Integer.valueOf(this.storage.get("getSkippedItems"));
        }
        return 0;
    }
    public int getSeatworkNumberCount(){
        return seatworkNumber;
    }
    public int getCorrectAnswerCount(){
        if(this.storage.get("getNumberOfCorrectAnswers") != null){
            return Integer.valueOf(this.storage.get("getNumberOfCorrectAnswers"));
        }
        return 0;
    }
    private int getNumberOfCorrectAnswers(){
        int ctr = 0;
        if(this.getItems() == null){
            return 0;
        }
        for(Item i : this.getItems()){
            if(i.isCorrect()){
                ctr++;
            }
        }
        return ctr;
    }
    private String getTimeSpent(){
        if (this.startTime == null || this.endTime == null) {
            return "";
        }
        Date start = this.startTime;
        Date end = this.endTime;
        long diff = end.getTime() - start.getTime();

        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return " " + Math.abs(diffSeconds) + " seconds";
    }

    public int save(Context context) {
        int score = 0;
        load(context);
        try {
            FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            score = getNumberOfCorrectAnswers();
            fos.write(String.valueOf("isSimilarDenominatorDone;" + String.valueOf(isSimilarDenominatorDone) + "\n").getBytes());
            fos.write(String.valueOf("isSimilarNumeratorDone;" + String.valueOf(isSimilarNumeratorDone)+ "\n").getBytes());
            fos.write(String.valueOf("isDissimilarFractionDone;" + String.valueOf(isDissimilarFractionDone)+ "\n").getBytes());
            fos.write(String.valueOf("getNumberOfCorrectAnswers;" + String.valueOf(score)+ "\n").getBytes());
            fos.write(String.valueOf("getMultiplicationMistakes;" + String.valueOf(getMultiplicationMistakes())+ "\n").getBytes());
            fos.write(String.valueOf("getSkippedItems;" + String.valueOf(getSkippedItems())+ "\n").getBytes());
            fos.write(String.valueOf("getTimeSpent;" + getTimeSpent()).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }

    public Map<String, String> load(Context context) {
        Map<String, String> storage = new Hashtable<String, String>();
        try {
            FileInputStream fis = context.getApplicationContext().openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                storage.put(line.split(";")[0],line.split(";")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.storage = storage;
        return storage;
    }
}
