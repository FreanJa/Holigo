package com.freanja.holigo.Model;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.freanja.holigo.R;
import com.freanja.holigo.Utils.BitmapUtil;

public class CardBean {
    public String cardTitle, cardPrice, cardLocation, cardScore;
    public Bitmap cardBitmap;
    public Boolean isFav;
    public String cardId;

    public CardBean(String id, String cardTitle, String cardPrice, String cardLocation, String cardScore, Boolean isFav) {
        this.cardId = id;
        this.cardTitle = cardTitle;
        this.cardPrice = "$" + cardPrice;
        this.cardLocation = cardLocation;
        this.cardScore = cardScore + " ratings";
//        this.cardBitmap = BitmapFactory.decodeResource(resource, R.drawable.hawaii1);
        this.cardBitmap = BitmapUtil.getBitmapFromFile("card/" + id + ".png");
//        System.out.println(cardBitmap);
//        this.isFav = isFav;
        this.isFav = isFav;
    }

    public void ToggleFav() {
        isFav = !isFav;
    }
}
