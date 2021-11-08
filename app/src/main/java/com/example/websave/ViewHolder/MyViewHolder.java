package com.example.websave.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websave.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViews;
   public TextView textView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViews=itemView.findViewById(R.id.imgRecycle);
        textView=itemView.findViewById(R.id.txt_name);
    }
}
