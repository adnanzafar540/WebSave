package com.example.websave.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websave.Adapters.ImageRecyclerAdapter;
import com.example.websave.Database.DatabaseProviderImg;
import com.example.websave.Database.SqliteDatabase;
import com.example.websave.R;

public class FragmentPdfActivity extends Fragment {
    RecyclerView rv_gallery;
    ImageRecyclerAdapter imgAdap;
    DatabaseProviderImg databaseProviderImg;
    SqliteDatabase sqdata;
    ImageView img;
    public static FragmentPdfActivity getInstance()
    {
        FragmentPdfActivity fragmentPdfActivity=new FragmentPdfActivity();
        return fragmentPdfActivity;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.for_tstin_purpose,container,false);
        rv_gallery=view.findViewById(R.id.recyclerView);
        img=view.findViewById(R.id.test_image);
        img.setImageBitmap(BitmapFactory.decodeFile("/data/user/0/com.example.websave/files/Image-2794.jpg"));
        sqdata=new SqliteDatabase(getActivity());
        //imgAdap=new ImageRecyclerAdapter(sqdata.readAllData(),getActivity());
     //   rv_gallery.setLayoutManager(new LinearLayoutManager(getContext()));
     //   rv_gallery.setAdapter(imgAdap);
        return view;

    }

}
