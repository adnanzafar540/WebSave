package com.example.websave.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websave.Adapters.ImageRecylerviewPdf;
import com.example.websave.Database.SqliteDatabase;
import com.example.websave.Entities.Images;
import com.example.websave.R;

import java.io.File;
import java.util.ArrayList;

public class FragmentPdfActivity extends Fragment {
    RecyclerView rv_gallery;
    ImageRecylerviewPdf imgAdap;
    SqliteDatabase sqdata;
    ImageView img;
    ArrayList<Images> FiterData;
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
        imgAdap=new ImageRecylerviewPdf(checkForDeleteFiles(FiterData),getActivity());
        rv_gallery.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv_gallery.setAdapter(imgAdap);
        imgAdap.notifyDataSetChanged();
        return view;
    }
    public ArrayList<Images> checkForDeleteFiles(ArrayList<Images> Filter) {
        ArrayList<Images> FiterData_Deletion;
        FiterData_Deletion = new ArrayList<>();
        try {
            for (Images img1 : Filter)
                if (img1.getPdfurl() != null) {
                    File Delfile = new File(img1.getPdfurl());
                    if (Delfile.exists()) {
                        FiterData_Deletion.add(img1);
                    }
                }

        } catch (Exception e) {
        }

        return FiterData_Deletion;
    }

    }
