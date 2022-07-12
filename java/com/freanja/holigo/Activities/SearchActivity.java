package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.freanja.holigo.Adapter.RecommendedAdapter;
import com.freanja.holigo.Model.CardBean;
import com.freanja.holigo.Model.RecommendBean;
import com.freanja.holigo.Model.SpotItem;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private DatabaseUtil databaseUtil;
    private ArrayList<RecommendBean> recommendBeanArrayList;
    private RecommendedAdapter recommendedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }

    private void initViews() {
        databaseUtil = new DatabaseUtil(this);
        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        listView = findViewById(R.id.search_lv);
        listView.invalidateViews();
        recommendBeanArrayList = readData();
        recommendedAdapter = new RecommendedAdapter(this, recommendBeanArrayList);
        recommendedAdapter.notifyDataSetChanged();
        listView.setAdapter(recommendedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                RecommendBean bean = recommendBeanArrayList.get(position);
                int spotId = bean.spotId;
                System.out.println(spotId);
                if (spotId > 20000) {
                    Toast.makeText(SearchActivity.this, "Look forward to the details page", Toast.LENGTH_SHORT).show();
                }
                else {
                    String action = "com.holigo.intent.details.ACTION_START";
                    Intent intent = new Intent(action);
                    intent.putExtra("spotId", String.valueOf(spotId));
                    System.out.println("put extra: spot id = " + spotId);
                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    private ArrayList<RecommendBean> readData() {
        recommendBeanArrayList = new ArrayList<>();
        Cursor cursor = databaseUtil.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                recommendBeanArrayList.add(new RecommendBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3)));
            }
        }
        return recommendBeanArrayList;
    }

    private void filterList(String s) {
        List<RecommendBean> recommendBeanList = new ArrayList<>();
        for (RecommendBean item : databaseUtil.getSpots()) {
            if (item.cardTitle.toLowerCase().contains(s.toLowerCase())) {
                recommendBeanList.add(item);
            }
        }

        if (recommendBeanList.isEmpty()){
            Toast.makeText(this, "No place found", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            recommendedAdapter.setFilteredList(recommendBeanList);
        }
    }

}