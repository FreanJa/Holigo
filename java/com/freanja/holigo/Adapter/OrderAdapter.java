package com.freanja.holigo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.freanja.holigo.Model.OrderBean;
import com.freanja.holigo.R;

import java.util.ArrayList;

public class OrderAdapter extends PagerAdapter {

    private ArrayList<OrderBean> orderBeanList;
    private Context context;
    private OrderBean bean;

    public OrderAdapter(Context context, ArrayList<OrderBean> list) {
        orderBeanList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderBeanList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_ticket, container, false);

        initViews(view, position);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void initViews(View view, int position) {
        ImageView ticket_iv = view.findViewById(R.id.ticket_iv);
        TextView subTitle = view.findViewById(R.id.brief_intro);
        TextView rating = view.findViewById(R.id.ratings);
        TextView location = view.findViewById(R.id.place_location);

        TextView checkIn = view.findViewById(R.id.check_in_tv);
        TextView checkOut = view.findViewById(R.id.check_out_tv);
        TextView people = view.findViewById(R.id.people);

        TextView adultNum = view.findViewById(R.id.adult_num);
        TextView childNum = view.findViewById(R.id.child_num);
        TextView infantNum = view.findViewById(R.id.infant_num);

        TextView adultPrice = view.findViewById(R.id.adult_price);
        TextView childPrice = view.findViewById(R.id.child_price);
        TextView infantPrice = view.findViewById(R.id.infant_price);

        TextView totalPrice = view.findViewById(R.id.total_price);

        bean = orderBeanList.get(position);
        ticket_iv.setImageBitmap(bean.headImg);
        subTitle.setText(bean.subTitle);
        rating.setText(bean.ratings);
        location.setText(bean.location);
        checkIn.setText(bean.checkIn);
        checkOut.setText(bean.checkOut);
        people.setText(bean.people);
        adultNum.setText(bean.adultNum);
        adultPrice.setText(bean.adultPrice);
        childNum.setText(bean.childNum);
        childPrice.setText(bean.childPrice);
        infantNum.setText(bean.infantNum);
        infantPrice.setText(bean.infantPrice);
        totalPrice.setText(bean.totalPrice);
    }

    public ArrayList<OrderBean> getData() {
        return orderBeanList;
    }
}
