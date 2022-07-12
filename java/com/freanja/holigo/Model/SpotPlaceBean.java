package com.freanja.holigo.Model;

import android.graphics.Bitmap;

import com.freanja.holigo.Utils.BitmapUtil;

public class SpotPlaceBean {
    public Bitmap placeBitmap;
    public String placeTitle;
    public String placeRating;
    public String placeLongitude;
    public String placeLatitude;
    public String placeLocation;
    public String spotId;
    public String placeId;


    public SpotPlaceBean(String placeTitle, String placeLocation, String placeRating, String placeLongitude, String placeLatitude, String spotId, String placeId) {
        this.placeBitmap = BitmapUtil.getBitmapFromFile("tripPlace/" + spotId + "-" + placeId + ".png");
        this.placeTitle = placeTitle;
        this.placeLocation = placeLocation;
        this.placeRating = placeRating;
        this.placeLongitude = placeLongitude;
        this.placeLatitude = placeLatitude;
        this.spotId = spotId;
        this.placeId = placeId;
    }
}
