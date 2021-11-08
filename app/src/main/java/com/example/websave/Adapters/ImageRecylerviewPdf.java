package com.example.websave.Adapters;

import static android.graphics.Color.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websave.Entities.Images;
import com.example.websave.R;
import com.example.websave.ViewHolder.MyViewHolder;
import com.google.api.Page;


import org.w3c.dom.Document;

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
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View v=layoutInflater.inflate(R.layout.gallery_item_layout,parent,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Images images=(Images)arrayList.get(position);
            // holder.imageView.setImageBitmap(BitmapToString.getBitmapToString(images.getImages()));
            //images.setImages(images.getImages());
          //  holder.imageView.(getPdf(images.getUrl()));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public PdfDocument getPdf (String path)  {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            PdfDocument pdfDocument=new PdfDocument();
            PdfDocument.PageInfo pageinfo=new PdfDocument.PageInfo.Builder(100,100,1).create();
            PdfDocument.Page page=pdfDocument.startPage(pageinfo);
            page.getCanvas().drawBitmap(bitmap,0,0,null);
            pdfDocument.finishPage(page);
            ///pdfDocument.close();
            return pdfDocument;
        }
    }


