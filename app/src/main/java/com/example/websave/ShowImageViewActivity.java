package com.example.websave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.websave.Database.SqliteDatabase;

public class ShowImageViewActivity  extends AppCompatActivity {
ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image);
        imageView=findViewById(R.id.img);
        Bundle extras = getIntent().getExtras();
            int id = extras.getInt("id");
            id=id+1;
            SqliteDatabase database = new SqliteDatabase(this);
            Glide.with(this)
                    .load( database.checkid_GetPicture(id).getUrl()) // Uri of the picture
                    .into(imageView);
    }
}
