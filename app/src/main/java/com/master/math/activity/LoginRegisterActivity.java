package com.master.math.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.master.math.R;
import com.master.math.activity.model.User;
import com.master.math.activity.service.Service;
import com.master.math.activity.service.ServiceResponse;
import com.master.math.activity.service.UserService;
import com.master.math.activity.util.AppCache;
import com.master.math.activity.util.Storage;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginRegisterActivity extends AppCompatActivity {
    public static final String MODE = "MODE";

    public static final String ONLINE_MODE = "ONLINE_MODE";
    public static final String OFFLINE_MODE = "OFFLINE_MODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        final TextView signUsername = (TextView) findViewById(R.id.txtUsernameLogin);
        final TextView signPassword = (TextView) findViewById(R.id.txtPasswordLogin);
        final TextView signInErrMsg = (TextView) findViewById(R.id.loginError);
        final TextView title = (TextView) findViewById(R.id.txtTitle);
        final Button signInButton = (Button) findViewById(R.id.btnLogin);
        final Button modeButton = (Button) findViewById(R.id.btnMode);
        final Button registerButton = (Button) findViewById(R.id.btnRegister);
        modeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                if (modeButton.getText()=="Offline\nMode") {
                    // OFFLINE MODE
                    modeButton.setText("Online\nMode");
                    title.setText("Offline");
                    signUsername.setHint("name");
                    signPassword.setVisibility(View.INVISIBLE);
                    signInButton.setText("Offline Mode");
                    registerButton.setVisibility(View.INVISIBLE);

                } else {
                    modeButton.setText("Offline\nMode");
                    title.setText("Login");
                    signUsername.setHint("username");
                    signPassword.setVisibility(View.VISIBLE);
                    signInButton.setText("LOGIN");
                    registerButton.setVisibility(View.VISIBLE);
                }

            }
        });
        signInButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String err = validate(signUsername.getText().toString(), signPassword.getText().toString());
                if (title.getText()=="Offline") {
                    err = validate(signUsername.getText().toString());
                    if (err == null){
                        AppCache.getInstance().setUsername(signUsername.getText().toString());
                        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        signInErrMsg.setTextColor(Color.RED);
                        signInErrMsg.setVisibility(View.VISIBLE);
                        signInErrMsg.setText(err);
                    }
                    return;
                }
                if (err == null) {
                    User user = new User();
                    user.setUsername(signUsername.getText().toString());
                    user.setPassword(signPassword.getText().toString());
                    login(user);
                } else {
                    signInErrMsg.setTextColor(Color.RED);
                    signInErrMsg.setVisibility(View.VISIBLE);
                    signInErrMsg.setText(err);
                }

            }
        });
    }
    private void login(final User user){
        final TextView errorMsg = (TextView) findViewById(R.id.loginError);
        Service service = new Service("Signing in...", LoginRegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject resp) {
                try {
                    if (resp.optString("message") != null && resp.optString("message").equals("Incorrect username or password.")) {
                        errorMsg.setTextColor(Color.RED);
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(resp.getString("message"));
                    }else{
                        user.setId(resp.optString("id"));
                        user.setUsername(resp.optString("username"));
                        user.setActive(resp.optInt("isActive") == 1);
                        user.setAdmin(resp.optInt("isAdmin") == 1);
                        Storage.save(LoginRegisterActivity.this.getApplicationContext(),user);
                        AppCache.getInstance().setUsername(resp.optString("username"));
                        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });
        UserService.login(user, service);
    }

    //Validate the fields.
    private String validate(String username, String password){
        if(username.trim().equals("")){
            return "Username cannot be empty";
        }
        if(password.trim().equals("")){
            return "Password cannot be empty";
        }
        return null;
    }
    private String validate(String username) {
        if (username.trim().equals("")) {
            return "Username cannot be empty";
        }
        return null;
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
