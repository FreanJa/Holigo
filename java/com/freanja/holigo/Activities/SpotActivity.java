package com.freanja.holigo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.freanja.holigo.Adapter.PlaceAdapter;
import com.freanja.holigo.Model.LocationBean;
import com.freanja.holigo.Model.SpotInfo;
import com.freanja.holigo.Model.SpotPlaceBean;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.BitmapUtil;
import com.freanja.holigo.Utils.DatabaseUtil;
import com.freanja.holigo.Utils.OnSwipeTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class SpotActivity extends AppCompatActivity implements OnMapReadyCallback {
    private LinearLayout bottom_sheet;
    private ImageView back_btn;
    private ListView trip_place_lv;
    private ArrayList<SpotPlaceBean> spotPlaceBeanList;
    private DatabaseUtil databaseUtil;
    private SharedPreferences sp;

    private SpotInfo spotInfo;

    private RelativeLayout background;
    private TextView title;
    private TextView location_d;
    private TextView location_b;
    private TextView intro_d;
    private TextView intro_b;
    private TextView people;
    private TextView time;
    private TextView rating;
    private TextView price;
    private ImageView fav;
    private ImageView call;

    private RoundedImageView roundedImageView1;
    private RoundedImageView roundedImageView2;
    private RoundedImageView roundedImageView3;
    private RoundedImageView roundedImageView4;

    private String spotId;

    private Button btn_next;

    private SupportMapFragment supportMapFragment;
    private GoogleMap mMap;
    private ArrayList<LocationBean> locationBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        Intent intent = getIntent();
        spotId = intent.getStringExtra("spotId");

        back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booking();
            }
        });

        bottom_sheet = findViewById(R.id.bottom_sheet);
        bottom_sheet.setY(2250);
        bottom_sheet.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeTop() {
//                Toast.makeText(SpotActivity.this, "top", Toast.LENGTH_SHORT).show();
                bottom_sheet.animate()
                        .y(350);
            }
            public void onSwipeBottom() {
//                Toast.makeText(SpotActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                bottom_sheet.animate()
                        .y(2250);
            }
        });

        trip_place_lv = findViewById(R.id.trip_place_lv);
        loadPlace();

        background = findViewById(R.id.img_background);
        title = findViewById(R.id.spot_title_tv);
        location_d = findViewById(R.id.spot_location_tv);
        fav = findViewById(R.id.iv_fav);
        call = findViewById(R.id.btn_call);
        location_b = findViewById(R.id.tv_location);
        intro_d = findViewById(R.id.detailed_intro);
        intro_b = findViewById(R.id.brief_intro);
        people = findViewById(R.id.tv_user_limit);
        time = findViewById(R.id.tv_length);
        rating = findViewById(R.id.ratings);
        price = findViewById(R.id.tv_price);

        roundedImageView1 = findViewById(R.id.iv_spot1);
        roundedImageView2 = findViewById(R.id.iv_spot2);
        roundedImageView3 = findViewById(R.id.iv_spot3);
        roundedImageView4 = findViewById(R.id.iv_spot4);
        loadImage();

        loadInfo();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    private void loadImage() {
        roundedImageView1.setImageBitmap(BitmapUtil.getBitmapFromFile("/spotImage/" + spotId + "-1.png"));
        roundedImageView2.setImageBitmap(BitmapUtil.getBitmapFromFile("/spotImage/" + spotId + "-2.png"));
        roundedImageView3.setImageBitmap(BitmapUtil.getBitmapFromFile("/spotImage/" + spotId + "-3.png"));
        roundedImageView4.setImageBitmap(BitmapUtil.getBitmapFromFile("/spotImage/" + spotId + "-4.png"));
    }

    @SuppressLint("SetTextI18n")
    private void loadInfo() {
        String[] info = databaseUtil.spotInfo(spotId);
        sp = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (info != null){
            spotInfo = new SpotInfo(spotId, info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], databaseUtil.selectFav(sp.getString("uid", ""), spotId));
        }
        else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
            return;
        }

        background.setBackground(new BitmapDrawable(spotInfo.background));
        title.setText(spotInfo.title);
        location_d.setText(spotInfo.location);

        fav.setImageResource(spotInfo.fav ? R.drawable.ic_vector : R.drawable.ic_vector_empty);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click");
                toggleFav();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPermission();
            }
        });

        location_b.setText(spotInfo.brief_location);
        intro_d.setText(spotInfo.detailed_info);
        intro_b.setText(spotInfo.brief_info);
        people.setText(spotInfo.people);
        time.setText(spotInfo.time);
        rating.setText(spotInfo.rating + " ratings");
        price.setText(spotInfo.price);

    }

    private void callPermission() {
        System.out.println("call permission");
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if ( permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 1);
        }
        else {
            call();
        }
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void loadPlace() {
        spotPlaceBeanList = new ArrayList<>();
        trip_place_lv.invalidateViews();
        spotPlaceBeanList = readListData();

        PlaceAdapter placeAdapter = new PlaceAdapter(this, spotPlaceBeanList);
        placeAdapter.notifyDataSetChanged();
        trip_place_lv.setAdapter(placeAdapter);
    }

    private ArrayList<SpotPlaceBean> readListData() {
        spotPlaceBeanList = new ArrayList<>();
        databaseUtil = new DatabaseUtil(this);
        Cursor cursor = databaseUtil.readSpotPlace(spotId);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(0) + ": " + cursor.getString(1));
                spotPlaceBeanList.add(new SpotPlaceBean(cursor.getString(2), cursor.getString(3), cursor.getString(6), cursor.getString(4), cursor.getString(5), cursor.getString(0), cursor.getString(1)));
            }
        }

        return spotPlaceBeanList;
    }

    private void booking() {
        Intent bookingIntent = new Intent(this, BookingActivity.class);
        bookingIntent.putExtra("spotId", spotId);
        startActivity(bookingIntent);

        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void toggleFav() {
        spotInfo.fav = databaseUtil.toggleFav(sp.getString("uid", ""), spotId);

        if (spotInfo.fav)
            fav.setImageResource(R.drawable.ic_vector);
        else
            fav.setImageResource(R.drawable.ic_vector_empty);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("map ready");

        // Add a marker in Sydney and move the camera
        locationBeanList = databaseUtil.getLocations(spotId);
        if (locationBeanList.size() == 0) {
            System.out.println("size 0");
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        else {
            System.out.println("size more than 0");
            LatLng first = new LatLng(locationBeanList.get(0).latitude, locationBeanList.get(0).longitude);
            locationBeanList.forEach((place) -> {
                LatLng sydney = new LatLng(place.latitude, place.longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(place.place)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_icon)));

            });
            mMap.setMinZoomPreference(10.0f);
            mMap.setMaxZoomPreference(16.0f);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
        }
    }
}