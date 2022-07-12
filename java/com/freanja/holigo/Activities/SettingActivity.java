package com.freanja.holigo.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private String uid;
    private DatabaseUtil databaseUtil;
    private SharedPreferences sp;
    private TextView userName;
    private TextView email;
    private Boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViews();
    }

    private void initViews() {
        RelativeLayout userNameBlock = findViewById(R.id.user_name_block);
        userNameBlock.setOnClickListener(this);

        RelativeLayout emailBlock = findViewById(R.id.email_block);
        emailBlock.setOnClickListener(this);

        RelativeLayout password = findViewById(R.id.password_block);
        password.setOnClickListener(this);

        databaseUtil = new DatabaseUtil(this);
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        userName = findViewById(R.id.user_name_tv);
        email = findViewById(R.id.email);

        userName.setText(sp.getString("userName", ""));
        email.setText(sp.getString("userEmail", ""));
        uid = sp.getString("uid", "0");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_name_block:
                modifyUserName();
                break;
            case R.id.email_block:
                modifyEmail();
                break;
            case R.id.password_block:
                modifyPasswd();
                break;
            default:
                break;
        }
    }

    private void modifyPasswd() {
        Intent intent = new Intent(this, ModifyPasswordActivity.class);
        startActivity(intent);
    }

    private void modifyEmail() {
        Intent intent = new Intent(this, ModifyEmailActivity.class);
        startActivityForResult(intent, 1);
    }

    private void modifyUserName() {
        Intent intent = new Intent(this, ModifyUsernameActivity.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            tag = true;
            switch (requestCode) {
                case 0:
                    userName.setText(sp.getString("userName", ""));
                case 1:
                    email.setText(sp.getString("userEmail", ""));
            }
        }
    }

    private void backPreActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(tag ? RESULT_OK : RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backPreActivity();
    }
}