package com.example.websave.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.websave.Adapters.ViewPagerAdapter;
import com.example.websave.R;
import com.google.android.material.tabs.TabLayout;


public class Fragment_main extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_internet,
            R.drawable.ic_gallery,
            R.drawable.ic_pdf
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.main_fragment, container, false);
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.viewPager);
        setViewPager(viewPager,tabLayout);

        return view; }
    private void setViewPager(ViewPager viewPager,TabLayout tabLayout) {
        final ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(FragmentWebActivity.getInstance(),"WEB");
                viewPagerAdapter.addFragment(FragmentGalleryActivity.getInstance(),"GALLERY");
                viewPagerAdapter.addFragment(FragmentPdfActivity.getInstance(),"PDF");
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            }
        });


    }
}