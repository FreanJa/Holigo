package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

public class ModifyUsernameActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private Boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_username);

        initViews();
    }

    private void initViews() {
        editText = findViewById(R.id.name);

        TextView save_btn = findViewById(R.id.sava);
        save_btn.setOnClickListener(this);

        TextView cancel_btn = findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sava:
                saveModify();
                break;
            case R.id.cancel:
                onBackPressed();
                break;
        }
    }

    private void saveModify() {
        String username = String.valueOf(editText.getText());
        if (username.isEmpty()) {
            Toast.makeText(this, "The Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseUtil databaseUtil = new DatabaseUtil(this);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean flag = databaseUtil.accountModify(0, sp.getString("uid", ""), username);

        if (flag) {
            Toast.makeText(this, "Modify user name success", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putString("userName", username);
            editor.apply();
            tag = true;
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }

    private void backPreActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        setResult(tag ? RESULT_OK : RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backPreActivity();
    }
}