package com.master.math.activity.util;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.ArrangeFractionsActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
    public static final String DOUBLE_SPACES = "  ";
    private static Util _this;
    private static AssetManager instanceAsset;
    private static TranslateAnimation animate;
    private Util(){}
    public static Util getInstance(){
        if(_this == null){
            _this = new Util();
            TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
            shake.setDuration(500);
            shake.setInterpolator(new CycleInterpolator(7));
            _this.animate = shake;
        }
        return _this;
    }

    public static String generateRandomNumbers(boolean includeZero){
        Random rand = new Random();
        Integer  n = 0;
        if(!includeZero){
            n = rand.nextInt(9) + 1;
        }else{
            n = rand.nextInt(9);
        }
        return n.toString();
    }
    public static String generate2DigsRandomNumbers(int max){
        Random rand = new Random();
        Integer  n = 0;
        n = rand.nextInt(max) + 1;
        return n.toString();
    }
    public static String generateCompositeNumbers(int max){
        int num = Integer.valueOf(generate2DigsRandomNumbers(max));
        while(true) {
            int count = 0;
            for (int i = 1; i <= num; i++) {
                if ((num % i) == 0) {
                    count++;
                }
            }
            if(count > 2){
                break;
            }else{
                num = Integer.valueOf(generate2DigsRandomNumbers(max));
            }
        }
        return String.valueOf(num);
    }
    private static List<String> generateDenominators(){
        List<String> denoms = new ArrayList<String>();
        denoms.add(generateCompositeNumbers(18));
        denoms.add(generateCompositeNumbers(18));
        denoms.add(generateCompositeNumbers(18));
        Collections.sort(denoms);
        return denoms;
    }
    public static String[] generate1ProperFraction(){
        String denom = generateCompositeNumbers(15);
        String [] fractions = new String [2];
        while(true){
            String numerator = generate2DigsRandomNumbers(10);
            if(Integer.valueOf(numerator) < Integer.valueOf(denom) ){
                fractions[0] = numerator;
                fractions[1]= denom;
                break;
            }
        }
        return fractions;
    }

    public static String[][] generateProperFraction(){
        List<String> denoms = generateDenominators();
        String [][] fractions = new String [3][2];
        int i = 0;
        for(String s : denoms){
            while(true){
                int j = 0;
                String numerator = generate2DigsRandomNumbers(10);
                if(Integer.valueOf(numerator) < Integer.valueOf(s) ){
                    fractions[i][j] = numerator;
                    fractions[i][j+1] = s;
                    break;
                }
            }
            i++;
        }
        return fractions;
    }
    public static String[] generateProperFractions(){
        String numerator = generateRandomNumbers(false);
        String denominator = generate2DigsRandomNumbers(70);
        while(true){
            if(Integer.valueOf(numerator) > Integer.valueOf(denominator)){
                denominator = generate2DigsRandomNumbers(75);
            }else{
                break;
            }
        }
        return new String[]{numerator,denominator};
    }
    public static void setAsset(AssetManager asset){
        instanceAsset = asset;
    }
    public static EditText getEditTextWithFont(Activity activity,int id){
        EditText et = (EditText) activity.findViewById(id);
        et.setTypeface(Typeface.createFromAsset(instanceAsset,"fonts/EraserDust.ttf"));
        return et;
    }
    public static TextView getTextViewWithFont(Activity activity,int id){
        TextView tv =(TextView) activity.findViewById(id);
        tv.setTypeface(Typeface.createFromAsset(instanceAsset,"fonts/EraserDust.ttf"));
        tv.setTextColor(Color.argb(255, 255, 255, 255));
        return tv;
    }

    public static TextView getTextViewWithFont(TextView tv){
        tv.setTypeface(Typeface.createFromAsset(instanceAsset,"fonts/EraserDust.ttf"));
        tv.setTextColor(Color.argb(255, 255, 255, 255));
        return tv;
    }
    public static TextView getTextViewWithFontInvisible(Activity activity,int id){
        TextView tv = getTextViewWithFont(activity, id);
        tv.setVisibility(View.INVISIBLE);
        return tv;
    }
    public static void showWithText(TextView tv, String txt){
        tv.setText(txt == null ? tv.getText().toString() : txt);
        tv.setTextColor(Color.argb(255, 255, 255, 255));
        tv.setVisibility(View.VISIBLE);
        tv.setBackground(null);
        tv.invalidate();
    }
    public static void showWithFadedText(TextView tv, String txt){
        tv.setText(txt == null ? tv.getText().toString() : txt);
        tv.setTextColor(Color.argb(150, 255, 255, 255));
        tv.setVisibility(View.VISIBLE);
        tv.setBackground(null);
        tv.invalidate();
    }
    public static void showWithTextUnderlined(TextView tv, String txt){
        SpannableString content = new SpannableString(txt);
        content.setSpan(new UnderlineSpan(), 0, txt.length(), 0);
        tv.setText(content);
        tv.setTextColor(Color.argb(255, 255, 255, 255));
        tv.setVisibility(View.VISIBLE);
        tv.setBackground(null);
        tv.invalidate();
    }
    public static void showWithBG(TextView tv, Activity activity){
        showWithText(tv,DOUBLE_SPACES);
        tv.setBackground(activity.getResources().getDrawable(R.drawable.text_bg));
        tv.invalidate();
    }
    public static void showWithTextBG(TextView tv,Activity activity){
        showWithText(tv,tv.getText().toString());
        tv.setBackground(activity.getResources().getDrawable(R.drawable.text_bg));
        tv.invalidate();
    }
    public static void removeListeners(TextView tv){
        tv.setOnClickListener(null);
        tv.setOnTouchListener(null);
        tv.setOnDragListener(null);
    }
    public static void hide(TextView tv){
        tv.setVisibility(View.INVISIBLE);
        tv.invalidate();
    }
    public static void hide(TextView... tvs){
        for(TextView tv: tvs){
            hide(tv);
        }
    }
    public static void show(TextView... tvs){
        for(TextView tv: tvs){
            showWithText(tv,null);
        }
    }
    public static void blur(TextView... tvs){
        for(TextView tv: tvs){
            if(tv.getVisibility() == View.VISIBLE) {
                float radius = tv.getTextSize() / 10;
                BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.INNER);
                tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                tv.getPaint().setMaskFilter(filter);
            }
        }
    }
    public static void removeBlur(TextView... tvs){
        for(TextView tv: tvs){
            if(tv.getVisibility() == View.VISIBLE) {
                float radius = tv.getTextSize() / 10;
                BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.INNER);
                tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                tv.getPaint().setMaskFilter(null);
            }
        }
    }
    public static TranslateAnimation shakeError(View v) {
        if(v.getVisibility() == View.VISIBLE){
            v.startAnimation(Util.getInstance().animate);
        }
        return Util.getInstance().animate;
    }

    private static List<Integer> generatePossible(int num){
        List<Integer> denomMul = new ArrayList<Integer>();
        for(int i=1; i<=40; i++){
            denomMul.add(i * num);
        }
        return denomMul;
    }
    public static int findLCD(int num1, int num2, int num3, int num4){
        int lcd = 0;
        denom1: for(int i1 : generatePossible(num1)){
            denom2:for(int i2 : generatePossible(num2)){
                if(i1 == i2){
                    denom3:for(int i3 : generatePossible(num3)){
                        if(i2 == i3){
                            if(num4 != 0){
                                denom4:for(int i4 : generatePossible(num4)){
                                    if(i4 == i3){
                                        lcd = i3;
                                        break denom1;
                                    }
                                }
                            }else{
                                lcd = i3;
                                break denom1;
                            }

                        }
                    }
                }
            }
        }
        return lcd;
    }

    public static RelativeLayout createRelativeLayout(int id, int top, int left,RelativeLayout parentLayout, Activity activity ){
        RelativeLayout rl = new RelativeLayout(activity);
        rl.setBackground(activity.getResources().getDrawable(R.drawable.text_bg));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = 200;
        lp.width = 200;
        lp.setMargins(left,top,0,0);

        rl.setLayoutParams(lp);
        parentLayout.addView(rl);
        return rl;
    }
    public static TextView createTextView(int id, String str, int textSize, int top, int left,boolean isNumerator, RelativeLayout layout,Activity activity){
        TextView tv = Util.getTextViewWithFont(new TextView(activity));
        tv.setId(id);
        tv.setTextSize(textSize);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left,top,0,0);
        tv.setLayoutParams(lp);
        if(isNumerator){
            Util.showWithTextUnderlined(tv,str);
        }else{
            Util.showWithText(tv,str);
        }
        layout.addView(tv);
        return tv;
    }
    public static boolean isLCDTooLarge(String denom1, String denom2, String denom3, String denom4, int lcd){
        int d1 = Integer.valueOf(denom1);
        int d2 = Integer.valueOf(denom2);
        int d3 = Integer.valueOf(denom3);
        int d4 = 0;
        int[] ints = null;
        if(denom4 != null){
            d4 = Integer.valueOf(denom4);
            ints = new int[4];
            ints[0] = d1;
            ints[1] = d2;
            ints[2] = d3;
            ints[3] = d4;
        }else{
            ints = new int[3];
            ints[0] = d1;
            ints[1] = d2;
            ints[2] = d3;
        }

        Arrays.sort(ints);
        int divisible = (lcd / ints[0]);
        return !(divisible > 1 && divisible < 15);
    }
}
