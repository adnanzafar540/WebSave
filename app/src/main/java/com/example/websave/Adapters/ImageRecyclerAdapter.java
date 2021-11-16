package com.example.websave.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.websave.Entities.Images;
import com.example.websave.R;
import com.example.websave.ShowImageViewActivity;
import com.example.websave.SupportClass.BitmapToString;
import com.example.websave.ViewHolder.MyViewHolder;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List arrayList;
    Context context;
    final int THUMBSIZE = 100;


    public ImageRecyclerAdapter(List arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.gallery_item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Images images=(Images)arrayList.get(position);
        int id=position;
      if(images.getUrl()!=null) {
            Glide.with(context)
                    .load(images.getUrlthumbnail()) // Uri of the picture
                    .into(holder.imageViews);
            holder.textView.setText(images.getImage_txt());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageViewActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);

                }
            });
        }
        else{
            holder.itemView.setVisibility(View.INVISIBLE);        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
