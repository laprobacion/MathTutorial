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

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        final TextView signUsername = (TextView) findViewById(R.id.txtUsernameLogin);
        final TextView signPassword = (TextView) findViewById(R.id.txtPasswordLogin);
        final TextView signInErrMsg = (TextView) findViewById(R.id.loginError);
        Button signInButton = (Button)findViewById(R.id.btnLogin);
        signInButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String err = validate(signUsername.getText().toString(), signPassword.getText().toString());
                if( err == null){
                    User user = new User();
                    user.setUsername(signUsername.getText().toString());
                    user.setPassword(signPassword.getText().toString());
                    login(user);
                }else{
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

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //go to another activity
    @Override
    public void finish() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.finish();
    }
}
