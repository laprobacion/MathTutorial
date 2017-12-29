package com.master.math.activity.multiply;


public class MultiplyCache {

    private static MultiplyCache _this;
    private String finalAns;
    private int[] nums;
    public void clear(){
        finalAns = null;
        setNums(null);
    }
    public static MultiplyCache getInstance(){
        if(_this == null){
            _this = new MultiplyCache();
        }
        return _this;
    }
    private MultiplyCache(){}
    public void setNums(int... i){
        nums = i;
    }
    public int[] getNums(){
        return nums;
    }

    public String getFinalAns() {
        return finalAns;
    }

    public void setFinalAns(String finalAns) {
        this.finalAns = finalAns;
    }
}
