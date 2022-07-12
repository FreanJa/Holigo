package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.freanja.holigo.R;

public class CusDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_dialog);

        Button backHome = findViewById(R.id.btn_back_home);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = "com.holigo.intent.main.ACTION_START";
                Intent intent = new Intent(action);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

    }
}