package com.master.math.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;

import com.master.math.R;
import com.master.math.activity.model.User;
import com.master.math.activity.service.Service;
import com.master.math.activity.service.ServiceResponse;
import com.master.math.activity.service.UserService;
import com.master.math.activity.util.Storage;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final TextView errorMsg = (TextView) findViewById(R.id.errorMsg);

        final TextView username = (TextView) findViewById(R.id.txtUsernameLogin);
        final TextView password = (TextView) findViewById(R.id.txtPasswordReg);
        final TextView confirmPass = (TextView) findViewById(R.id.txtPasswordRegCon);
        final TextView age = (TextView) findViewById(R.id.numAge);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rGroupGender);

        Button btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String err = validate(username.getText().toString(), password.getText().toString(),
                        confirmPass.getText().toString(), age.getText().toString(), radioGroup.getCheckedRadioButtonId());
                if(err != null){
                    errorMsg.setTextColor(Color.RED);
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setText(err);
                } else {
                    final RadioButton gender = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    //errorMsg.setVisibility(View.INVISIBLE);
                    final User user = new User();
                    user.setUsername(username.getText().toString());
                    user.setAdmin(false);
                    user.setPassword(password.getText().toString());
                    user.setActive(true);
                    user.setAge(age.getText().toString());
                    user.setGender(gender.getText().toString());
                    register(user);
                }
            }
        });
    }
    private void register(final User user) {
        final TextView errorMsg = (TextView) findViewById(R.id.errorMsg);
        Service service = new Service("Adding user...", RegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject resp) {
                try {
                    if (resp.getString("message").equals("User was created.")) {
                        Storage.save(RegisterActivity.this.getApplicationContext(), user);
                        finish();
                    }else{
                        errorMsg.setTextColor(Color.RED);
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(resp.getString("message"));
                    }
                } catch (Exception e){e.printStackTrace(); }
            }
        });
        UserService.register(user, service);
    }

    private String validate(final String username, String password, String confirmPass, String age, int gender){
        if(username.trim().equals("")){
            return "Username cannot be empty";
        }
        if(password.trim().equals("") || confirmPass.trim().equals("")){
            return "Password cannot be empty";
        }
        if(!password.equals(confirmPass)){
            return "Password did not match.";
        }
        if(age.trim().equals("")){
            return "Please input age";
        }
        if(gender==-1){
            return "Please choose a gender";
        }
        return null;
    }
}
