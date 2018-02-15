package com.master.math.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ListView listTopScores;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayTopScores;
    private int rankNumber;
    int top = 200;
    int maxList = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        retScores(this);
        listTopScores = (ListView) findViewById(R.id.listTopScores);
        arrayTopScores = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayTopScores){
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getColor(R.color.colorPrimaryDark));
                return view;
            }
        };
        listTopScores.setAdapter(adapter);
    }

    private void retScores(Activity activity){
        final TextView errorMsg = (TextView) activity.findViewById(R.id.loginError);
        Service service = new Service("Loading list...", activity, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject resp) {
                try {
                    int counter = 0;
                    for(User u : createTopUsers(resp)){
                        counter++;
                        if(counter <= maxList){
                            rankNumber++;
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
        arrayTopScores.add("#" + rankNumber + " " + user.getUsername() + " = " + user.getScore());
        adapter.notifyDataSetChanged();
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
