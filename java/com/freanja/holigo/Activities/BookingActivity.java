package com.freanja.holigo.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DateUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView month_tv;
    private ImageView prev;
    private ImageView next;
    private ImageView back;
    private CalendarView calendarView;

    private TextView startDate;
    private TextView startWeek;
    private TextView endDate;
    private TextView endWeek;
    private Button next_btn;

    private int cur_day;
    private int cur_month;
    private int cur_year;

    private String select_date;

    private int start_day;
    private int start_month;
    private int start_year;

    private int end_day;
    private int end_month;
    private int end_year;

    private int range;
    private String spotId;

    private DateUtil dateUtil = new DateUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Intent intent = getIntent();
        spotId = intent.getStringExtra("spotId");

        range = 7;

        month_tv = findViewById(R.id.current_month);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        startWeek = findViewById(R.id.start_week);
        endWeek = findViewById(R.id.end_week);

        prev = findViewById(R.id.prev_btn);
        prev.setVisibility(View.GONE);
        prev.setOnClickListener(this);

        next = findViewById(R.id.next_btn);
        next.setVisibility(View.GONE);
        next.setOnClickListener(this);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        next_btn = findViewById(R.id.btn_next);
        next_btn.setOnClickListener(this);

        calendarView = findViewById(R.id.calendar);

        cur_day = calendarView.getCurDay();
        cur_month = calendarView.getCurMonth();
        cur_year = calendarView.getCurYear();

        calendarView.setRange(cur_year, cur_month, cur_day + 1, 2023, 12, 31);

        select_date = calendarView.getSelectedCalendar().toString();

        month_tv.setText(dateUtil.int2Month(cur_month) + ", " + cur_year);
        startDate.setText(dateUtil.int2Month(cur_month) + " " + (cur_day + 1));
        endDate.setText(dateUtil.calcEndDate(select_date, range));

        startWeek.setText(dateUtil.date2Week(select_date, 1));
        endWeek.setText(dateUtil.date2Week(select_date, range));

        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {

                System.out.println("click");

                select_date = calendar.toString();

                System.out.println("select_date: " + select_date);
                startDate.setText(dateUtil.calcEndDate(select_date, 1));
                endDate.setText(dateUtil.calcEndDate(select_date, range));
                startWeek.setText(dateUtil.date2Week(select_date, 1));
                endWeek.setText(dateUtil.date2Week(select_date, 7));

                start_day = dateUtil.getDay(select_date, 1);
                start_month = dateUtil.getMonth(select_date, 1);
                start_year = dateUtil.getYear(select_date, 1);

                end_day = dateUtil.getDay(select_date, range);
                end_month = dateUtil.getMonth(select_date, range);
                end_year = dateUtil.getYear(select_date, range);


                calendarView.setSelectEndCalendar(end_year, end_month, end_day);

                System.out.println("start: " + start_year + " " + start_month + " " + start_day);
                System.out.println("end: " + end_year + " " + end_month + " " + end_day);
            }
        });

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                System.out.println("month change");
                month_tv.setText(dateUtil.int2Month(month) + ", " + year);
                cur_month = month;
                cur_year = year;

                prev.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.current_month:
                showCurrent();
                break;
            case R.id.prev_btn:
                changeMonth(false);
                break;
            case R.id.next_btn:
                changeMonth(true);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_next:
                toBooking2();
                break;
        }
    }

    private void toBooking2() {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("checkIn", select_date);
        editor.putString("checkOut", DateUtil.calcEndDate2(select_date, range));
        editor.apply();

        Intent intent = new Intent(this, BookingActivity2.class);
        intent.putExtra("spotId", spotId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void showCurrent() {

    }

    private void changeMonth(Boolean b) {
        if (b){
            prev.setVisibility(View.VISIBLE);
            calendarView.scrollToNext();
            cur_month += 1;
            if (cur_month > 12){
                cur_month = 1;
                cur_year += 1;
            }
        }
        else {
            if (cur_month == dateUtil.curMonth()){
                prev.setVisibility(View.GONE);
                return;
            }
            calendarView.scrollToPre();
            cur_month -= 1;
            if (cur_month < 1) {
                cur_month = 12;
                cur_year -= 1;
            }

            if (cur_month == dateUtil.curMonth()){
                prev.setVisibility(View.GONE);
                return;
            }
        }
        month_tv.setText(dateUtil.int2Month(cur_month) + ", " + cur_year);
    }
}