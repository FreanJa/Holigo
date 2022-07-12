package com.freanja.holigo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.freanja.holigo.Activities.SignInActivity;
import com.freanja.holigo.Model.CardBean;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.BitmapUtil;
import com.freanja.holigo.Utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends PagerAdapter{

    private ArrayList<CardBean> cardBeanList;
    private Context context;
    private ImageView fav;
    private DatabaseUtil databaseUtil;
    private Button btn_more;

    private CardBean bean;
    private SharedPreferences sp;

    private CardView cardView;

    public CardAdapter(Context context, ArrayList<CardBean> list) {
        cardBeanList = list;
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return cardBeanList.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_large, container, false);
        System.out.println("Card Adapter position: " + position);

        init(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void init(View view, int position) {
        RelativeLayout relativeLayout = view.findViewById(R.id.card_background);
        TextView placeTitle = view.findViewById(R.id.place_title);
        TextView placeLocation = view.findViewById(R.id.place_location);
        TextView placePrice = view.findViewById(R.id.place_price);
        TextView placeScore = view.findViewById(R.id.ratings);
        cardView = view.findViewById(R.id.card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked card position: " + position);
            }
        });

        fav = view.findViewById(R.id.vector);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFav(position);
            }
        });

        btn_more = view.findViewById(R.id.btn_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDetail(position);
            }
        });

        bean = cardBeanList.get(position);

        Bitmap bitmap = bean.cardBitmap;
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        databaseUtil = new DatabaseUtil(context);
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        relativeLayout.setBackground(bitmapDrawable);
        placeTitle.setText(bean.cardTitle);
        placeLocation.setText(bean.cardLocation);
        placePrice.setText(bean.cardPrice);
        placeScore.setText(bean.cardScore);

        if (bean.isFav)
            fav.setImageResource(R.drawable.ic_vector);
        else
            fav.setImageResource(R.drawable.ic_vector_empty);

    }

    private void setFavIcon(int positon) {
        System.out.println("bean title: " + cardBeanList.get(positon).cardTitle);
        if (cardBeanList.get(positon).isFav)
            fav.setImageResource(R.drawable.ic_vector);
        else
            fav.setImageResource(R.drawable.ic_vector_empty);
    }

    public ArrayList<CardBean> getData() {
        return cardBeanList;
    }

    private void toDetail(int position) {
        CardBean bean = cardBeanList.get(position);
        String action = "com.holigo.intent.details.ACTION_START";
        Intent intent = new Intent(action);
        intent.putExtra("spotId", bean.cardId);
        System.out.println("put extra: spot id = " + bean.cardId);

        ((Activity) context).startActivityForResult(intent, 1);
    }

    private void clickFav(int position) {
        CardBean bean = cardBeanList.get(position);
        System.out.println("spotName: " + bean.cardTitle + " spotID: " + bean.cardId);
        bean.isFav = databaseUtil.toggleFav(sp.getString("uid", ""), bean.cardId);
        setFavIcon(position);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("resultCode: " + resultCode + "\t" + requestCode);
        if (resultCode == -1) {
            System.out.println("get -1");
        }
    }
}
