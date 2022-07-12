package com.freanja.holigo.Model;

import android.graphics.Bitmap;

import com.freanja.holigo.Utils.BitmapUtil;
import com.freanja.holigo.Utils.DateUtil;

public class OrderBean {
    public Bitmap headImg;
    public String location;
    public String ratings;
    public String subTitle;

    public String people;
    public String totalPrice;

    public String recordId;
    public String spotId;
    public String checkIn;
    public String checkOut;
    public String adultNum;
    public String childNum;
    public String infantNum;
    public String adultPrice;
    public String childPrice;
    public String infantPrice;

    public OrderBean(String recordId, String spotId, String spotTitle, String location, String ratings, String checkIn, String checkOut, String adultNum, String childNum, String infantNum, String adultPrice, String childPrice, String infantPrice) {
        this.headImg = BitmapUtil.getBitmapFromFile("background/t" + spotId + ".png");
        this.recordId = recordId;
        this.spotId = spotId;

        this.subTitle = spotTitle;
        this.location = location;
        this.ratings = ratings;


        this.checkIn = DateUtil.calcEndDate(checkIn, 1);
        this.checkOut = DateUtil.calcEndDate(checkOut, 1);

        this.adultNum = "Adult x " + adultNum;
        this.childNum = "Children x " + childNum;
        this.infantNum = "Infant x " + infantNum;
        this.people = (Integer.parseInt(adultNum) + Integer.parseInt(childNum) + Integer.parseInt(infantNum)) + " Person";

        this.adultPrice = "$ " + adultPrice;
        this.childPrice = "$ " + childPrice;
        this.infantPrice = "$ " + infantPrice;
        this.totalPrice = "$ " + (Integer.parseInt(adultPrice) + Integer.parseInt(childPrice) + Integer.parseInt(infantPrice));
    }
}
