package com.freanja.holigo.Model;

import android.graphics.Bitmap;

import com.freanja.holigo.Utils.BitmapUtil;

public class SpotInfo {
    public String id;
    public String title;
    public String price;
    public String location;
    public String rating;
    public String time;
    public String people;
    public String brief_location;
    public String brief_info;
    public String detailed_info;
    public Boolean fav;
    public Bitmap background;


    public SpotInfo(String id, String title, String price, String location, String rating, String time, String people, String brief_location, String brief_info, String detailed_info, Boolean fav) {
        this.id = id;
        this.title = title;
        this.price = "$" + price;
        this.location = location;
        this.rating = rating;
        this.time = time + " Days";
        this.people = people + " People";
        this.brief_location = brief_location;
        this.brief_info = brief_info;
        this.detailed_info = detailed_info;
        this.fav = fav;
        this.background = BitmapUtil.getBitmapFromFile("background/" + id + ".png");;
    }
}
