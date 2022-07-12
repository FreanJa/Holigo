package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name_et;
    private EditText email_et;
    private EditText passwd_et;
    private EditText passwd_et_2;
    private CheckBox checkBox;
    private TextView tip_tv;

    private String[] res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
    }

    private void initViews() {
        TextView signInBtn = findViewById(R.id.tv2signin);
        signInBtn.setOnClickListener(this);

        Button signUpBtn = findViewById(R.id.btn_try_signup);
        signUpBtn.setOnClickListener(this);

        tip_tv = findViewById(R.id.tips);

        name_et = findViewById(R.id.name);
        email_et = findViewById(R.id.email);
        passwd_et = findViewById(R.id.password);
        passwd_et_2 = findViewById(R.id.re_password);
        checkBox = findViewById(R.id.accept_cb);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv2signin:
                onBackPressed();
                break;
            case R.id.btn_try_signup:
                signUp();
                break;
            default:
                break;
        }
    }

    private void signUp() {
        String name = String.valueOf(name_et.getText());
        String email = String.valueOf(email_et.getText());
        String password = String.valueOf(passwd_et.getText());
        String password2 = String.valueOf(passwd_et_2.getText());

        if (name.isEmpty()) {
            tip_tv.setText("The Name cannot be empty!");
            return;
        }
        else if (email.isEmpty()){
            tip_tv.setText("The E-MAIL cannot be empty!");
            return;
        }
        else if (password.isEmpty()){
            tip_tv.setText("The password cannot be empty!");
            return;
        }
        else if (password2.isEmpty()){
            tip_tv.setText("Verify password cannot be empty!");
            return;
        }
        else if (!checkBox.isChecked()){
            tip_tv.setText("please accept term if service!");
            return;
        }

        if (!password.equals(password2)){
            tip_tv.setText("The passwords are different!");
            return;
        }

        DatabaseUtil databaseUtil = new DatabaseUtil(SignUpActivity.this);
        res = databaseUtil.createAccount(name, email, password, this);
        System.out.println(res.length);

        if (res.length == 0) {
            tip_tv.setText("Registration failed");
        }
        else {
            System.out.println("signUp: res info: " + res[0] + res[1]);
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (res != null){
            System.out.println(res[0] + res[1]);
            intent.putExtra("email", res[0]);
            intent.putExtra("password", res[1]);
        }
        setResult(res==null ? RESULT_CANCELED : RESULT_OK, intent);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}