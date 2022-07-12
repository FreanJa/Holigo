package com.freanja.holigo.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email_et;
    private EditText password_et;
    private TextView tip_tv;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();
    }

    private void initViews() {
        TextView signUpBtn = findViewById(R.id.tv2signup);
        signUpBtn.setOnClickListener(this);

        Button signInBtn = findViewById(R.id.btn_try_login);
        signInBtn.setOnClickListener(this);

        email_et = findViewById(R.id.email);
        password_et = findViewById(R.id.password);
        tip_tv = findViewById(R.id.tips);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv2signup:
                toSignUp();
                break;
            case R.id.btn_try_login:
                trySignIn();
                break;
            default:
                break;
        }
    }

    private void trySignIn() {
        email = String.valueOf(email_et.getText());
        String passwd = String.valueOf(password_et.getText());
        tip_tv.setText("");

        if (email.isEmpty()) {
            tip_tv.setText("The E-MAIL cannot be empty!");
            return;
        }
        else if (passwd.isEmpty()) {
            tip_tv.setText("The PASSWORD cannot be empty!");
            return;
        }

        DatabaseUtil databaseUtil = new DatabaseUtil(SignInActivity.this);
        switch (databaseUtil.verifySignIn(email, passwd)) {
            case -2:
                tip_tv.setText("Unregistered email, please sign up.");
                break;
            case -1:
                Toast.makeText(this, "No account in database, please create.", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                tip_tv.setText("Wrong password!");
                break;
            case 1:
                success(databaseUtil);
                break;
            default:
                Toast.makeText(this, "unknown error.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void success(DatabaseUtil databaseUtil) {
        String[] userInfo = databaseUtil.getUserInfo(email);
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("uid", userInfo[0]);
        editor.putString("userName", userInfo[1]);
        editor.putString("userEmail", userInfo[2]);
        editor.putBoolean("online", true);
        editor.apply();

        String action = "com.holigo.intent.main.ACTION_START";
        Intent intent = new Intent(action);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void toSignUp() {
        String action = "com.holigo.intent.signup.ACTION_START";
        Intent intent = new Intent(action);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("resultCode: " + resultCode);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    System.out.println(data.getStringExtra("email") + data.getStringExtra("password"));
                    email_et.setText(data.getStringExtra("email"));
                    password_et.setText(data.getStringExtra("password"));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}