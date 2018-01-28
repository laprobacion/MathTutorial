package com.master.math.activity.util;

import java.io.Serializable;

public class Item implements Serializable{
    private int num1;
    private int num2;
    private int denom1;
    private int denom2;
    private boolean isCorrect;
    public Item(int num1, int num2, int denom1, int denom2){
        this.num1 = num1;
        this.num2 = num2;
        this.denom1 = denom1;
        this.denom2 = denom2;
    }
    public void setCorrect(boolean isCorrect){
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {        return isCorrect;    }
}
