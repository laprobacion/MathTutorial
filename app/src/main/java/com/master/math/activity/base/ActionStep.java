package com.master.math.activity.base;

public class ActionStep {
    private int step;
    public static final int STEP_1 = 1;
    public static final int STEP_2 = 2;
    public static final int STEP_3 = 3;
    public static final int STEP_4 = 4;
    public static final int STEP_5 = 5;
    public static final int STEP_6 = 6;
    public static final int STEP_7 = 7;
    public static final int STEP_8 = 8;
    public static final int STEP_9 = 9;
    public static final int STEP_10 = 10;
    public static final int STEP_11 = 11;
    public static final int STEP_12 = 12;
    public ActionStep(){
        step = 1;
    }
    public void increment(){
        step++;
    }
    public int getStep(){
        return step;
    }
    public void setStep(int step){
        this.step = step;
    }
}
