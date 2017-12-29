package com.master.math.activity.addition;

public class AdditionCache {
    private static AdditionCache _this;
    private String finalAnswer;
    private AdditionCache(){ }

    public static AdditionCache get(){
        if(_this == null){
            _this = new AdditionCache();
        }
        return _this;
    }

    private String upperNumbers;
    private String lowerNumbers;
    private String upperMultiply;
    private String lowerMultiply;

    public String getUpperMultiply() {
        return upperMultiply;
    }

    public String getLowerMultiply() {
        return lowerMultiply;
    }

    public void setMultiplyNumbers(String upperMultiply, String lowerMultiply){
        this.upperMultiply = upperMultiply;
        this.lowerMultiply = lowerMultiply;
    }
    public void setNumbers(String upperNumbers, String lowerNumbers){
        this.upperNumbers = upperNumbers;
        this.lowerNumbers = lowerNumbers;
    }

    public String getUpperNumbers(){
        return _this.upperNumbers;
    }
    public String getLowerNumbers(){
        return _this.lowerNumbers;
    }

    public void clear(){
        this.upperNumbers = null;
        this.lowerNumbers = null;
        this.upperMultiply = null;
        this.lowerMultiply = null;
        setFinalAnswer(null);
    }
    public void setFinalAnswer(String finalAnswer){
        this.finalAnswer = finalAnswer;
    }

    public String getFinalAnswer() {
        return finalAnswer;
    }
}
