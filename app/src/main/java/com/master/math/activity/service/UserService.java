package com.master.math.activity.service;

import com.loopj.android.http.RequestParams;
import com.master.math.activity.model.User;
import com.master.math.activity.util.Encryptor;
import com.master.math.activity.util.Util;

/**
 * Created by Laher on 1/17/2018.
 */

public class UserService {

    public static void register(final User user, Service service){
        RequestParams params = new RequestParams();
        user.setId(Util.generateId());
        params.put("id", user.getId());
        params.put("username", user.getUsername());
        String pass = Encryptor.encrypt(user.getPassword());
        params.put("password", pass);
        params.put("isAdmin", user.isAdmin());
        params.put("isActive", user.isActive());
        //params.put("score", 0);
        params.put("age", user.getAge());
        params.put("gender", user.getGender());
        service.post("http://jabahan.com/storybook/user/create.php", params);
        service.execute();
    }

    public static void login(final User user, Service service){
        RequestParams params = new RequestParams();
        params.put("id", Util.generateId());
        params.put("username", user.getUsername());
        String pass = Encryptor.encrypt(user.getPassword());
        params.put("password", pass);
        params.put("isAdmin", user.isAdmin());
        params.put("isActive", user.isActive());
        String url = "http://jabahan.com/storybook/user/login.php?username="+user.getUsername()+"&password="+pass;
        service.get(url, params);
        service.execute();
    }
    public static void updateScore(final User user, Service service){
        RequestParams params = new RequestParams();
        params.put("username", user.getUsername());
        params.put("score", user.getScore());
        String url = "http://jabahan.com/storybook/user/updateScore.php?username="+user.getUsername()+"&score="+user.getScore();
        service.get(url, params);
        service.execute();
    }

    public static void searchTopUsers(Service service){
        RequestParams params = new RequestParams();
        String url = "http://jabahan.com/storybook/user/searchTopUsers.php";
        service.get(url, params);
        service.execute();
    }
}
