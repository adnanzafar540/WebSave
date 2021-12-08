package com.example.websave;

import static android.media.MediaCodec.MetricsConstants.MODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends AppCompatActivity {
    private ViewPager screenpager;
    introViewPageter introViewPageter;
    TabLayout tab_indicator;
    Button btn_next;
    int position = 0;
    Button btn_get_Started, btn_skip;
    Animation btnanim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        btn_next = findViewById(R.id.btn_next);
        tab_indicator = findViewById(R.id.tab_indicator);
        btn_get_Started = findViewById(R.id.btn_get_Started);
        btnanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        btn_skip = findViewById(R.id.btn_skip);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }

        List<screeen_item> mList = new ArrayList<>();
        mList.add(new screeen_item("Safe & Secure", "", R.drawable.ic_security));
        mList.add(new screeen_item("Image to PDF", "", R.drawable.pdf_pdf));
        mList.add(new screeen_item("Web Page  LongShot", "", R.drawable.iccccccsssss));


        screenpager = findViewById(R.id.scren_viewpager);
        introViewPageter = new introViewPageter(this, mList);
        screenpager.setAdapter(introViewPageter);
        tab_indicator.setupWithViewPager(screenpager);

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screenpager.getCurrentItem() == mList.size() - 1) {

                    btn_get_Started.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.INVISIBLE);
                    btn_skip.setVisibility(View.INVISIBLE);
                    tab_indicator.setVisibility(View.INVISIBLE);

                } else {
                    position = screenpager.getCurrentItem();
                    if (position < mList.size()) {
                        position++;
                        screenpager.setCurrentItem(position);
                    }
                    if (position == mList.size()) {
                        loaddLastscreen();
                    }
                }


            }
        });

        tab_indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        btn_get_Started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingScreen.this, MainActivity.class);
                startActivity(intent);
                //savePrefsData();
                finish();
            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        Boolean isIntroActivityopnendBefore = pref.getBoolean("iSIntroOpnend", false);
        return isIntroActivityopnendBefore;

    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.apply();

    }

    private void loaddLastscreen() {
        btn_next.setVisibility(View.INVISIBLE);
        btn_get_Started.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.INVISIBLE);
        btn_get_Started.setAnimation(btnanim);

    }
}