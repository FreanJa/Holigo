package com.freanja.holigo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freanja.holigo.Model.SpotPlaceBean;
import com.freanja.holigo.R;

import java.util.List;

public class PlaceAdapter extends BaseAdapter {

    private List<SpotPlaceBean> spotPlaceBeanList;
    private LayoutInflater inflater;

    public PlaceAdapter(Context context, List<SpotPlaceBean> list) {
        spotPlaceBeanList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return spotPlaceBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return spotPlaceBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trip_place, null);
        }

        ImageView imageView = view.findViewById(R.id.iv_trip_place);
        TextView title = view.findViewById(R.id.place_title);
        TextView rating = view.findViewById(R.id.ratings);
        TextView location = view.findViewById(R.id.place_location);

        SpotPlaceBean bean = spotPlaceBeanList.get(i);

        imageView.setImageBitmap(bean.placeBitmap);
        title.setText(bean.placeTitle);
        rating.setText(bean.placeRating);
        location.setText(bean.placeLocation);

        return view;
    }

    public List<SpotPlaceBean> getData() {
        return spotPlaceBeanList;
    }

}
