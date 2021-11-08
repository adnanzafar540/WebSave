package com.example.websave.Database;

import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.websave.Dao.ImageDao;
import com.example.websave.Entities.Images;
import com.example.websave.SupportClass.BitmapToString;

@Database(entities = {Images.class},version = 1)
@TypeConverters(BitmapToString.class)
public abstract class DatabaseProviderImg extends RoomDatabase {
    public abstract ImageDao imageDao();
    private static DatabaseProviderImg databaseProviderImg=null;
    public static DatabaseProviderImg getDatabaseConnection(Context context)
    {
        if(databaseProviderImg==null)
        {
            databaseProviderImg= Room.databaseBuilder(context.getApplicationContext(),DatabaseProviderImg.class,"ScreenShot")
                    .allowMainThreadQueries()
                    .build();
        }

        return databaseProviderImg;
    }
}
