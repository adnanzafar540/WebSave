package com.example.websave.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import com.example.websave.Entities.Images;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Database";
        public static final String TABLE_NAME = "image_table";
        private static final int DATABASE_VERSION = 3;
        public static final String COL_1 = "imagepath";


        public SqliteDatabase(Context context) {
            super(context, DATABASE_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String Table = "CREATE TABLE " + TABLE_NAME +
                    " (id integer primary key," +
                    COL_1 + " VARCHAR);";
            db.execSQL(Table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(Images model) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, model.getUrl());
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    public ArrayList<Images> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<Images> list = new ArrayList<>();
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        while ((cursor.moveToNext())) {
            Images Model = new Images();
            Model.setUrl(cursor.getString(1));
            list.add(Model);
        }
        return list;
    }

    }
