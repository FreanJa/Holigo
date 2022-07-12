package com.freanja.holigo.Fragment;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
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
import android.view.ViewParent;
import android.widget.Toast;

import com.freanja.holigo.Adapter.OrderAdapter;
import com.freanja.holigo.Model.OrderBean;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

import java.util.ArrayList;

public class CollectionFragment extends Fragment implements View.OnClickListener {

    private ViewPager ticketViewPager;
    private ArrayList<OrderBean> orderBeanArrayList;
    private OrderAdapter orderAdapter;

    private DatabaseUtil databaseUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);

        initTickets(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initTickets(View view) {
        ticketViewPager = view.findViewById(R.id.ticketViewPager);
        orderBeanArrayList = new ArrayList<>();
        orderBeanArrayList = readData();
        orderAdapter = new OrderAdapter(getContext(), orderBeanArrayList);
        ticketViewPager.setAdapter(orderAdapter);
        ticketViewPager.setPadding(100, 60, 100, 0);
    }

    private ArrayList<OrderBean> readData() {
        orderBeanArrayList = new ArrayList<>();
        databaseUtil = new DatabaseUtil(getContext());
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        orderBeanArrayList = databaseUtil.getOrders(sp.getString("uid", ""));
        if (orderBeanArrayList == null) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        }
        return orderBeanArrayList;
    }

    @Override
    public void onClick(View view) {

    }
}