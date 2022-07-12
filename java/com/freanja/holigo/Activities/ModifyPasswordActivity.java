package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

import java.time.Year;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText password;
    private EditText re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        initViews();
    }

    private void initViews() {
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);

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
        String password_1 = String.valueOf(password.getText());
        String password_2 = String.valueOf(re_password.getText());
        if (password_1.isEmpty()) {
            Toast.makeText(this, "The password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password_2.isEmpty()){
            Toast.makeText(this,"Verify password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password_1.equals(password_2)) {
            Toast.makeText(this, "The passwords are different!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseUtil databaseUtil = new DatabaseUtil(this);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean flag = databaseUtil.accountModify(2, sp.getString("uid", ""), password_1);

        if (flag) {
            Toast.makeText(this, "Modify password success", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }
}