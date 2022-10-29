package com.example.firebasephoneauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebasephoneauthentication.R;
import com.example.firebasephoneauthentication.adapter.MyAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        fab = (FloatingActionButton) findViewById(R.id.viewContactActivity);



        toolbar.inflateMenu(R.menu.menu);
        toolbar.setTitle("Whatsapp");

        toolbar.setTitleTextColor(Color.WHITE);



            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if (id == R.id.menu_item_profile) {
                        startActivity(new Intent(HomeActivity.this, ViewProfile.class));
                        finish();

                    }

                    if (id == R.id.menu_item_settings) {
                        startActivity(new Intent(HomeActivity.this, Settings.class));
                        finish();


                    }

                    return true;
                }
            });
            tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
            tabLayout.addTab(tabLayout.newTab().setText("STATUS"));
            tabLayout.addTab(tabLayout.newTab().setText("CALLS"));

            final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            //start Contact Activity
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this, ViewContacts.class));
                }
            });

        }// End onCreate method



    }
