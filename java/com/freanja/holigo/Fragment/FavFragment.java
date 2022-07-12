package com.freanja.holigo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.freanja.holigo.R;

public class FavFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ListView listView = view.findViewById(R.id.fav_lv);
    }
}