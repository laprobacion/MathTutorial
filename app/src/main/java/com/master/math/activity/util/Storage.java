package com.master.math.activity.util;

import android.content.Context;

import com.master.math.activity.model.User;

import java.io.FileOutputStream;

/**
 * Created by Laher on 1/20/2018.
 */

public class Storage {
    private static String FILENAME = "storage";
    public static void save(Context context, User user) {

        try {
            FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(user.getUsername().getBytes());
            fos.close();
            AppCache.getInstance().setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
