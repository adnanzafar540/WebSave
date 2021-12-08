package com.example.websave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class introViewPageter extends PagerAdapter   {
    Context mContext;
    List<screeen_item> mlistscreen;

    public introViewPageter(Context mContext, List<screeen_item> mlistscreen) {
        this.mContext = mContext;
        this.mlistscreen = mlistscreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen=inflater.inflate(R.layout.layout_screen,null);
        ImageView imageView=layoutScreen.findViewById(R.id.intro_imag);
        TextView titel=layoutScreen.findViewById(R.id.intro_titel);
        TextView description=layoutScreen.findViewById(R.id.intro_description);

        titel.setText(mlistscreen.get(position).getTitel());
        description.setText(mlistscreen.get(position).getDescription());
        imageView.setImageResource(mlistscreen.get(position).getScreenImg());
        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mlistscreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
