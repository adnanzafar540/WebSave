package com.example.websave.Adapters;

import static android.graphics.Color.*;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.websave.BuildConfig;
import com.example.websave.Entities.Images;
import com.example.websave.PsfActivity;
import com.example.websave.R;
import com.example.websave.ShowImageViewActivity;
import com.example.websave.ViewHolder.MyViewHolder;
import com.google.api.Page;


import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

    public class ImageRecylerviewPdf extends RecyclerView.Adapter<MyViewHolder> {
        List arrayList;
        Context context;

        public ImageRecylerviewPdf(List arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.gallery_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Images images = (Images) arrayList.get(position);
            int id = position;
            Glide.with(context)
                    .load(images.getPdfurlthumbnail()) // Uri of the picture
                    .into(holder.imageViews);
            holder.textView.setText(images.getImage_txt());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url= images.getPdfurl();
                    File file = new File(url);
                    Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pdfOpenintent.setDataAndType(uri, "application/pdf");
                    try {
                        context.startActivity(pdfOpenintent);
                    }
                    catch (Exception e) {
                        e.printStackTrace();

                    }


                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        private void loadDocInReader(String doc)
                throws ActivityNotFoundException, Exception {

            try {
                Intent intent = new Intent();
                File file = new File(doc);
                intent.setDataAndType(Uri.parse(file.toString()), "application/pdf");

                PackageManager pm = context.getPackageManager();
                List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
                if (activities.size() > 0) {
                    try {
                        context.startActivity(intent);
                    }catch (Exception e){
                        throw e;
                    }

                } else {
                    // Do something else here. Maybe pop up a Dialog or Toast
                }

            } catch (ActivityNotFoundException activityNotFoundException) {
                activityNotFoundException.printStackTrace();

                throw activityNotFoundException;
            } catch (Exception otherException) {
                otherException.printStackTrace();

                throw otherException;
            }


               }
        }


