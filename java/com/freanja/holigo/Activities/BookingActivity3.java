package com.freanja.holigo.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.Model.SpotInfo;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.BitmapUtil;
import com.freanja.holigo.Utils.DatabaseUtil;
import com.freanja.holigo.Utils.DateUtil;

public class BookingActivity3 extends AppCompatActivity implements View.OnClickListener {
    private Button next_btn;
    private ImageView back;

    private ImageView ticket_iv;
    private TextView brief_intro;
    private TextView ratings;
    private TextView place_location;

    private TextView check_in_tv;
    private TextView check_out_tv;
    private TextView total_people;

    private TextView adult_num;
    private TextView child_num;
    private TextView infant_num;

    private TextView adult_price;
    private TextView child_price;
    private TextView infant_price;
    private TextView total_price;

    private int an;
    private int cn;
    private int in;

    private int ap;
    private int cp;
    private int ip;

    private String checkIn;
    private String checkOut;

    private String spotId;
    private String userId;
    private DatabaseUtil databaseUtil;
    private SharedPreferences sp;

    private SpotInfo spotInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking3);

        initViews();
    }

    private void initViews() {
        next_btn = findViewById(R.id.btn_next);
        next_btn.setOnClickListener(this);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        ticket_iv = findViewById(R.id.ticket_iv);
        brief_intro = findViewById(R.id.brief_intro);
        ratings = findViewById(R.id.ratings);
        place_location = findViewById(R.id.place_location);

        check_in_tv = findViewById(R.id.check_in_tv);
        check_out_tv = findViewById(R.id.check_out_tv);
        total_people = findViewById(R.id.people);

        adult_num = findViewById(R.id.adult_num);
        child_num = findViewById(R.id.child_num);
        infant_num = findViewById(R.id.infant_num);

        adult_price = findViewById(R.id.adult_price);
        child_price = findViewById(R.id.child_price);
        infant_price = findViewById(R.id.infant_price);
        total_price = findViewById(R.id.total_price);

        loadSpotInfo();
        loadTicketInfo();
    }

    @SuppressLint("SetTextI18n")
    private void loadSpotInfo() {
        Intent intent = getIntent();
        databaseUtil = new DatabaseUtil(this);
        spotId = intent.getStringExtra("spotId");

        String[] info = databaseUtil.spotInfo(spotId);
        System.out.println(info);

        sp = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sp.getString("uid", "");

        if (info != null){
            spotInfo = new SpotInfo(spotId, info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], databaseUtil.selectFav(sp.getString("uid", ""), spotId));
        }
        else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
            return;
        }

        ticket_iv.setImageBitmap(BitmapUtil.getBitmapFromFile("background/t" + spotId + ".png"));
        brief_intro.setText(spotInfo.brief_info);
        ratings.setText(spotInfo.rating);
        place_location.setText(spotInfo.location);


    }

    @SuppressLint("SetTextI18n")
    private void loadTicketInfo() {


        an = sp.getInt("adult_num", 0);
        cn = sp.getInt("child_num", 0);
        in = sp.getInt("infant_num", 0);
        adult_num.setText("Adult x " + an);
        child_num.setText("Children x " + cn);
        infant_num.setText("Infant x " + in);
        total_people.setText((an + cn + in) + " Person");

        ap = sp.getInt("adult_price", 0);
        cp = sp.getInt("child_price", 0);
        ip = sp.getInt("infant_price", 0);
        adult_price.setText("$ " + ap);
        child_price.setText("$ " + cp);
        infant_price.setText("$ " + ip);
        total_price.setText("$ " + (ap + cp + ip));

        checkIn = sp.getString("checkIn", "20220607");
        checkOut = sp.getString("checkOut", "20220607");

        check_in_tv.setText(DateUtil.calcEndDate(checkIn, 1));
        check_out_tv.setText(DateUtil.calcEndDate(checkOut, 1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                getTheTicket();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTheTicket() {
        Intent intent = new Intent(this, HomeActivity.class);
        String CHANNEL_ID="MY_CHANNEL";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
            System.out.println("set");
        }
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String msg = spotInfo.title + " tour from " +  DateUtil.calcEndDate(checkIn, 1) + " to " + DateUtil.calcEndDate(checkOut, 1) + " ✈️";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pi)
                .setContentTitle("Your new trip!")
                .setContentText(msg)
                .setWhen(System.currentTimeMillis())
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        System.out.println(notification);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(1,notification);

        databaseUtil.setOrder(spotId, userId, checkIn, checkOut, an, cn, in, ap, cp, ip, this);
        String action = "com.holigo.intent.dialog.ACTION_START";
        Intent intent2 = new Intent(action);
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        startActivity(intent2);
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}