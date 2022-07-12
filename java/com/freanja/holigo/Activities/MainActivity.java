package com.freanja.holigo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.freanja.holigo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean first = true;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNeedPermissions();
        initViews();
    }

    private void initViews() {
        Button startBtn = (Button) findViewById(R.id.btn2login);
        startBtn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
//                    initVideoPath();
                    System.out.println(grantResults.length);
                    System.out.println("get Permission success");
                }
                else {
                    Toast.makeText(this, "You have denied the permission and cannot use this application", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkNeedPermissions() {
        int permission_write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        if (permission_write!= PackageManager.PERMISSION_GRANTED
                || permission_read!=PackageManager.PERMISSION_GRANTED
                || permission_storage!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn2login:
                getStart();
                break;
            default:
                break;
        }
    }

    private void getStart() {
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        Intent intent;
        if (sp.getBoolean("online", false)) {
            intent = new Intent(this, HomeActivity.class);
        }
        else {
            intent = new Intent(this, SignInActivity.class);
        }
        startActivity(intent);
    }
}