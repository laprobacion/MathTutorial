package com.master.math.activity.divide;


public class DivideCache {
    private static DivideCache _this;
    private Integer ans;
    private DivideCache(){}
    public static DivideCache get(){
        if(_this == null){
            _this = new DivideCache();
        }
        return _this;
    }
    public void clear(){
        this.ans = null;
    }

    public Integer getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }
}
