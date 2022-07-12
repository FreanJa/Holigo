package com.freanja.holigo.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.BitmapUtil;

public class RecommendBean {
    public Bitmap cardBitmap;
    public String cardTitle;
    public String cardPrice;
    public String cardScore;
    public String cardLocation;
    public Integer spotId;

    public RecommendBean(Integer id, String cardTitle, String cardPrice, String cardScore, String cardLocation) {
        this.spotId = id;
        this.cardBitmap = BitmapUtil.getBitmapFromFile("recommend/" + id + ".png");
        this.cardTitle = cardTitle;
        this.cardPrice = "$" + cardPrice;
        this.cardScore = cardScore;
        this.cardLocation = cardLocation;
    }
}
