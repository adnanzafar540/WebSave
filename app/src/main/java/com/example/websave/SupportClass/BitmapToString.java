package com.example.websave.SupportClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class BitmapToString {

    @TypeConverter
    public static byte [] getStringFromBitmap(Bitmap bitmapPicture)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG,0,byteArrayOutputStream);
        byte[] b=byteArrayOutputStream.toByteArray();
        return b;
    }

    @TypeConverter
    public static Bitmap getBitmapToString(byte [] string)
    {
        return BitmapFactory.decodeByteArray(string,0,string.length);
    }
}
