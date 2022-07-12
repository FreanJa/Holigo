package com.freanja.holigo.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.Adapter.CardAdapter;
import com.freanja.holigo.Adapter.RecommendedAdapter;
import com.freanja.holigo.Model.CardBean;
import com.freanja.holigo.Model.RecommendBean;
import com.freanja.holigo.Model.SpotItem;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{
    private ListView recommended_lv;
    private ArrayList<RecommendBean> recommendBeanList;
    private RecommendedAdapter recommendedAdapter;

    private ViewPager popularViewPager;
    private ArrayList<CardBean> cardBeanArrayList;
    private CardAdapter cardAdapter;

    private LinearLayout searchView;

    private DatabaseUtil databaseUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initViews(View view) {
        popularViewPager = view.findViewById(R.id.popularViewPager);
        recommended_lv = view.findViewById(R.id.recommended_lv);

        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = "com.holigo.intent.search.ACTION_START";
                Intent intent = new Intent(action);
                startActivity(intent);
            }
        });


        TextView title = view.findViewById(R.id.sub_tittlw);

        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        title.setText("Hi, " + sp.getString("userName", "") + ".");
        loadCards();
        loadRecommended();
    }

    private void loadRecommended() {
        recommendBeanList = new ArrayList<>();
        recommended_lv.invalidateViews();
        recommendBeanList = readBriefData();
        recommendedAdapter = new RecommendedAdapter(getContext(), recommendBeanList);
        recommendedAdapter.notifyDataSetChanged();
        recommended_lv.setAdapter(recommendedAdapter);
    }

    private void loadCards() {
        cardBeanArrayList = new ArrayList<>();
        cardBeanArrayList = readData();
        cardAdapter = new CardAdapter(getContext(), cardBeanArrayList);
        popularViewPager.setAdapter(cardAdapter);
        popularViewPager.setOffscreenPageLimit(1);
        popularViewPager.setPadding(240, 60, 240, 0);
    }

    private ArrayList<RecommendBean> readBriefData() {
        recommendBeanList = new ArrayList<>();
        databaseUtil = new DatabaseUtil(getContext());
        Cursor cursor = databaseUtil.readBriefData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
//                System.out.println(cursor.getString(0) + ": " + cursor.getString(1));
                recommendBeanList.add(new RecommendBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3)));
            }
        }
        return recommendBeanList;
    }

    private ArrayList<CardBean> readData() {
        cardBeanArrayList = new ArrayList<>();
        databaseUtil = new DatabaseUtil(getContext());
        Cursor cursor = databaseUtil.readDetailedData();
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(0) + ": " + cursor.getString(1));
                System.out.println(databaseUtil.selectFav(sp.getString("uid", ""), cursor.getString(0)));

                cardBeanArrayList.add(new CardBean(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), databaseUtil.selectFav(sp.getString("uid", ""), cursor.getString(0))));
            }
        }
        return cardBeanArrayList;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("on resume");
        loadCards();
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}