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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websave.Adapters.ImageRecyclerAdapter;
import com.example.websave.Adapters.ImageRecylerviewPdf;
import com.example.websave.Database.DatabaseProviderImg;
import com.example.websave.Database.SqliteDatabase;
import com.example.websave.Entities.Images;
import com.example.websave.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentPdfActivity extends Fragment {
    RecyclerView rv_gallery;
    ImageRecylerviewPdf imgAdap;

    DatabaseProviderImg databaseProviderImg;
    SqliteDatabase sqdata;
    ImageView img;
    List<Images> FiterData;
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
        View view=inflater.inflate(R.layout.pdf_layout,container,false);
        rv_gallery=view.findViewById(R.id.rv_pdf);
        sqdata=new SqliteDatabase(getActivity());
        FiterData=new ArrayList<>();
        for(Images img: sqdata.readAllData())
            if(img.getPdfurl()!=null){
                FiterData.add(img);
            }
        imgAdap=new ImageRecylerviewPdf(FiterData,getActivity());
        rv_gallery.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv_gallery.setAdapter(imgAdap);
        imgAdap.notifyDataSetChanged();
        return view;
    }

}
