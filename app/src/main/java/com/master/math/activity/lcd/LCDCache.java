package com.master.math.activity.lcd;

import java.util.ArrayList;
import java.util.List;

public class LCDCache {
    private static LCDCache _this;
    private boolean isFinished;
    private LCDCache(){}
    private List<String[]> fractions;
    public static LCDCache get(){
        if(_this == null){
            _this = new LCDCache();
            _this.fractions = new ArrayList<String[]>();
        }
        return _this;
    }

    public boolean isFinished() {        return isFinished;    }

    public void setFinished(boolean finished) {        isFinished = finished;    }

    public List<String[]> getFractions() {
        return fractions;
    }

    public void setFractions(List<String[]> fractions) {
        this.fractions = fractions;
    }
}
