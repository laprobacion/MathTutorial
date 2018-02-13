package com.master.math.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.model.User;
import com.master.math.activity.service.Service;
import com.master.math.activity.service.ServiceResponse;
import com.master.math.activity.service.UserService;
import com.master.math.activity.util.AppCache;
import com.master.math.activity.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopScoreActivity extends AppCompatActivity {
    RelativeLayout topScoreLayout;
    int top = 200;
    int maxList = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);
        updateScore(this);
        topScoreLayout = (RelativeLayout) findViewById(R.id.topScoreLayout);
    }

    private void updateScore(Activity activity){
        final TextView errorMsg = (TextView) activity.findViewById(R.id.loginError);
        Service service = new Service("Saving...", activity, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject resp) {
                try {
                    int counter = 0;
                    for(User u : createTopUsers(resp)){
                        counter++;
                        if(counter <= maxList){
                            renderList(u);
                        }else{
                            break;
                        }
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });
        UserService.searchTopUsers(service);

    }
    private void renderList(User user){
        Util.createTextView(0,user.getUsername() + " = " + user.getScore(),20,top,100,false,topScoreLayout,this).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        top += 60;
    }
    private List<User> createTopUsers(JSONObject resp){
        List<User> users = new ArrayList<User>();
        JSONArray arr = resp.optJSONArray("records");
        if(arr == null){
            return  null;
        }
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.optJSONObject(i);
            User u = new User();
            u.setUsername(obj.optString("username"));
            u.setScore(obj.optInt("score"));
            users.add(u);
        }
        return users;
    }
}
