package com.freanja.holigo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.freanja.holigo.Adapter.CardAdapter;
import com.freanja.holigo.Adapter.RecommendedAdapter;
import com.freanja.holigo.Fragment.AccountFragment;
import com.freanja.holigo.Fragment.CollectionFragment;
import com.freanja.holigo.Fragment.FavFragment;
import com.freanja.holigo.Fragment.HomeFragment;
import com.freanja.holigo.Model.CardBean;
import com.freanja.holigo.Model.RecommendBean;
import com.freanja.holigo.R;
import com.freanja.holigo.Utils.DatabaseUtil;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.collection:
                        fragment = new CollectionFragment();
                        break;
                    case R.id.favorites:
                        fragment = new FavFragment();
                        break;
                    case R.id.settings:
                        fragment = new AccountFragment();
                        break;
                }

                if (fragment != null){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                            )
                            .replace(R.id.container, fragment)
                            .addToBackStack(null)
                            .commit();

                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}