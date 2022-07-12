package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.Model.SpotInfo;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;
import com.freanja.holigo.Utils.DateUtil;


public class BookingActivity2 extends AppCompatActivity implements View.OnClickListener {
    private String spotId;

    private DatabaseUtil databaseUtil;

    private Button next_btn;
    private ImageView back;

    private ImageView btn_sub_1;
    private ImageView btn_sub_2;
    private ImageView btn_sub_3;

    private ImageView btn_add_1;
    private ImageView btn_add_2;
    private ImageView btn_add_3;

    private TextView adult_num_com;
    private TextView child_num_com;
    private TextView infant_num_com;

    private TextView adult_num_tv;
    private TextView child_num_tv;
    private TextView infant_num_tv;

    private TextView adult_price_tv;
    private TextView child_price_tv;
    private TextView infant_price_tv;

    private int adult_num;
    private int children_num;
    private int infant_num;

    private int adult_price;
    private int children_price;
    private int infant_price;

    private int people_limit;

    private static double childDiscount = 0.6;
    private static double infantDiscount = 0.3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        initViews();
    }

    private void initViews() {
        adult_num = 1;
        children_num = 0;
        infant_num = 0;
        people_limit = 7;

        Intent intent = getIntent();
        spotId = intent.getStringExtra("spotId");

        databaseUtil = new DatabaseUtil(this);

        next_btn = findViewById(R.id.btn_next);
        next_btn.setOnClickListener(this);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        btn_add_1 = findViewById(R.id.btn_add_1);
        btn_add_1.setOnClickListener(this);
        btn_add_2 = findViewById(R.id.btn_add_2);
        btn_add_2.setOnClickListener(this);
        btn_add_3 = findViewById(R.id.btn_add_3);
        btn_add_3.setOnClickListener(this);

        btn_sub_1 = findViewById(R.id.btn_sub_1);
        btn_sub_1.setOnClickListener(this);
        btn_sub_2 = findViewById(R.id.btn_sub_2);
        btn_sub_2.setOnClickListener(this);
        btn_sub_3 = findViewById(R.id.btn_sub_3);
        btn_sub_3.setOnClickListener(this);

        adult_price_tv = findViewById(R.id.adult_price);
        child_price_tv = findViewById(R.id.child_price);
        infant_price_tv = findViewById(R.id.infant_price);

        adult_num_com = findViewById(R.id.adult_num_com);
        child_num_com = findViewById(R.id.child_num_com);
        infant_num_com = findViewById(R.id.infant_num_com);

        adult_num_tv = findViewById(R.id.adult_num);
        child_num_tv = findViewById(R.id.child_num);
        infant_num_tv = findViewById(R.id.infant_num);

        loadInfo();
    }

    private void loadInfo() {
        adult_price = databaseUtil.getPrice(spotId);
        people_limit = databaseUtil.getPeople(spotId);

        if (adult_price == -1){
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
            return;
        }

        children_price = (int) (adult_price * childDiscount);
        infant_price = (int) (adult_price * infantDiscount);

        adult_num_com.setText(String.valueOf(adult_num));
        child_num_com.setText(String.valueOf(children_num));
        infant_num_com.setText(String.valueOf(infant_num));

        adult_num_tv.setText("x" + adult_num);
        child_num_tv.setText("x" + children_num);
        infant_num_tv.setText("x" + infant_num);

        adult_price_tv.setText("$" + (adult_price * adult_num));
        child_price_tv.setText("$" + (children_price * children_num));
        infant_price_tv.setText("$" + (infant_price * infant_num));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                getTheTicket();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_add_1:
                clickAdd(1);
                break;
            case R.id.btn_add_2:
                clickAdd(2);
                break;
            case R.id.btn_add_3:
                clickAdd(3);
                break;
            case R.id.btn_sub_1:
                clickSub(1);
                break;
            case R.id.btn_sub_2:
                clickSub(2);
                break;
            case R.id.btn_sub_3:
                clickSub(3);
                break;

        }
    }

    private void clickSub(int i) {
        if ((adult_num + children_num + infant_num) == 1) {
            Toast.makeText(this, "Can't less!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adult_num == 1 && i == 1){
            Toast.makeText(this, "Adult number can't less!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (children_num == 0 && i == 2){
            Toast.makeText(this, "Children number can't less!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (infant_num == 0 && i == 3){
            Toast.makeText(this, "Infant number can't less!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (i) {
            case 1:
                adult_num -= 1;
                adult_num_com.setText(String.valueOf(adult_num));
                adult_num_tv.setText("x" + adult_num);
                adult_price_tv.setText("$" + (adult_price * adult_num));
                break;
            case 2:
                children_num -= 1;
                child_num_com.setText(String.valueOf(children_num));
                child_num_tv.setText("x" + children_num);
                child_price_tv.setText("$" + (children_price * children_num));
                break;
            case 3:
                infant_num -= 1;
                infant_num_com.setText(String.valueOf(infant_num));
                infant_num_tv.setText("x" + infant_num);
                infant_price_tv.setText("$" + (infant_price * infant_num));
                break;
        }
    }

    private void clickAdd(int i) {
        if ((adult_num + children_num + infant_num) == people_limit) {
            Toast.makeText(this, "Can't more!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (i) {
            case 1:
                adult_num += 1;
                adult_num_com.setText(String.valueOf(adult_num));
                adult_num_tv.setText("x" + adult_num);
                adult_price_tv.setText("$" + (adult_price * adult_num));
                break;
            case 2:
                children_num += 1;
                child_num_com.setText(String.valueOf(children_num));
                child_num_tv.setText("x" + children_num);
                child_price_tv.setText("$" + (children_price * children_num));
                break;
            case 3:
                infant_num += 1;
                infant_num_com.setText(String.valueOf(infant_num));
                infant_num_tv.setText("x" + infant_num);
                infant_price_tv.setText("$" + (infant_price * infant_num));
                break;
        }
    }

    private void getTheTicket() {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putInt("adult_num", adult_num);
        editor.putInt("child_num", children_num);
        editor.putInt("infant_num", infant_num);

        editor.putInt("adult_price", adult_num * adult_price);
        editor.putInt("child_price", children_num * children_price);
        editor.putInt("infant_price", infant_num * infant_price);

        editor.apply();

        Intent intent = new Intent(this, BookingActivity3.class);
        intent.putExtra("spotId", spotId);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}