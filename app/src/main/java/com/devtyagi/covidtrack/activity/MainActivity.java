package com.devtyagi.covidtrack.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.devtyagi.covidtrack.R;
import com.devtyagi.covidtrack.adapter.ViewPagerAdapter;
import com.devtyagi.covidtrack.fragment.StatewiseFragment;
import com.devtyagi.covidtrack.fragment.WorldWideFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatewiseFragment(), "India");
        adapter.addFragment(new WorldWideFragment(), "Global");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setUpToolbar();

        /*getSupportFragmentManager().beginTransaction().replace(R.id.frame, new StatewiseFragment()).commit();
        getSupportActionBar().setTitle("India Statewise");*/
    }

    public void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolBarTitle.setText("COVID-19 Tracker");
    }


}
