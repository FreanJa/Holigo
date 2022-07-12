package com.freanja.holigo.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.Activities.MainActivity;

import com.freanja.holigo.Activities.SearchActivity;
import com.freanja.holigo.Activities.SettingActivity;
import com.freanja.holigo.R;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class AccountFragment extends Fragment implements View.OnClickListener{

    private TextView user_name;
    private SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initViews(View view) {
        LinearLayout sign_out = view.findViewById(R.id.btn_sign_out);
        sign_out.setOnClickListener(this);

        user_name = view.findViewById(R.id.user_name_tv);
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name.setText(sp.getString("userName", ""));

        TextView location = view.findViewById(R.id.location);
        location.setText("Zhejiang, China");

        RelativeLayout setting = view.findViewById(R.id.setting_block);
        setting.setOnClickListener(this);

        RelativeLayout help = view.findViewById(R.id.help_block);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_out:
                quit();
                break;
            case R.id.setting_block:
                toSetting();
                break;
            case R.id.help_block:
                showHelp();
                break;
        }
    }



    private void showHelp() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Contact me:")
                .setMessage("root@freanja.cn\ngithub.com/freanja")
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();

        dialog.show();
    }

    private void toSetting() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivityForResult(intent, 0);
    }

    private void quit() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putBoolean("online", false);
        editor.apply();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            user_name.setText(sp.getString("userName", ""));
        }
    }
}