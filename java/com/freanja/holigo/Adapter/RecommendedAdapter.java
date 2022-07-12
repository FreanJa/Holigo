package com.freanja.holigo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.freanja.holigo.Model.RecommendBean;
import com.freanja.holigo.R;

import java.util.List;

public class RecommendedAdapter extends BaseAdapter {

    private List<RecommendBean> recommendBeanList;
    private LayoutInflater inflater;

    public RecommendedAdapter(Context context, List<RecommendBean> list) {
        recommendBeanList = list;
        inflater = LayoutInflater.from(context);
    }

    public void setFilteredList(List<RecommendBean> filteredList){
        this.recommendBeanList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recommendBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return recommendBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recommended_card, null);
        }

        ImageView imageView = view.findViewById(R.id.recommended_iv);
        TextView placeTitle = view.findViewById(R.id.place_title);
        TextView placeLocation = view.findViewById(R.id.place_location);
        TextView placeScore = view.findViewById(R.id.ratings);
        TextView placePrice = view.findViewById(R.id.place_price);

        RecommendBean bean = recommendBeanList.get(i);

        imageView.setImageBitmap(bean.cardBitmap);
        placeTitle.setText(bean.cardTitle);
        placeLocation.setText(bean.cardLocation);
        placePrice.setText(bean.cardPrice);
        placeScore.setText(bean.cardScore);

        return view;
    }

    public List<RecommendBean> getData(){
        return recommendBeanList;
    }
}
