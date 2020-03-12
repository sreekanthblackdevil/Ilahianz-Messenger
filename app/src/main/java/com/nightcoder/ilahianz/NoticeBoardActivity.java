package com.nightcoder.ilahianz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nightcoder.ilahianz.NoticeBoardFragments.AllFragment;
import com.nightcoder.ilahianz.NoticeBoardFragments.FragmentListener;
import com.nightcoder.ilahianz.NoticeBoardFragments.MyNoticeFragment;

import java.util.ArrayList;

public class NoticeBoardActivity extends AppCompatActivity {

    protected MyApp myApp;
    private TabLayout tab;
    private ViewPager viewPager;
    private ImageButton composeButton;
    private ImageButton starredButton;

    private FragmentListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        init();

        tab.setupWithViewPager(viewPager);
        setTab();
        composeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoticeBoardActivity.this, ComposeNoticeActivity.class));
            }
        });

        starredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoticeBoardActivity.this, NoticeStarredActivity.class));
            }
        });

    }

    private void setTab() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AllFragment(this), "All");
        viewPagerAdapter.addFragment(new MyNoticeFragment(this), "My Notice");
        //viewPagerAdapter.addFragment(new AllFragment(this), "College");

        listener = (FragmentListener) viewPagerAdapter.getFragment(0);

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        listener.onSyncData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myApp.setCurrentActivity(this);
        myApp.setOnline();
    }

    private void clearReference() {
        Activity activity = myApp.getCurrentActivity();
        if (this.equals(activity)) {
            myApp.setCurrentActivity(this);
        }
    }
    private void init() {
        tab = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        composeButton = findViewById(R.id.compose_button);
        starredButton = findViewById(R.id.refresh_button);
        myApp = (MyApp) this.getApplicationContext();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        Fragment getFragment(int pos) {
            return fragments.get(pos);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }
}
