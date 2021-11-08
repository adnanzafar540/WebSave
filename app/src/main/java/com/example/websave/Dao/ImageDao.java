package com.example.websave.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.websave.Entities.Images;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insert(Images...image);

    @Query("SELECT * FROM Images")
    List<Images>getAllImages();
}
