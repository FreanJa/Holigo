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

public class ModifyEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_email);

        initViews();
    }

    private void initViews() {
        editText = findViewById(R.id.email);

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
        String email = String.valueOf(editText.getText());
        if (email.isEmpty()) {
            Toast.makeText(this, "The E-MAIL cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseUtil databaseUtil = new DatabaseUtil(this);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean flag = databaseUtil.accountModify(1, sp.getString("uid", ""), email);

        if (flag) {
            Toast.makeText(this, "Modify Email success", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putString("userEmail", email);
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