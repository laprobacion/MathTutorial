package com.master.math.activity.arrange;

public class ProperFraction {
    private String numerator;
    private String denominator;
    public ProperFraction(String [] fraction){
        this.numerator = fraction[0];
        this.denominator = fraction[1];
    }

    public String getNumerator() {
        return numerator;
    }

    public String getDenominator() {
        return denominator;
    }

}
