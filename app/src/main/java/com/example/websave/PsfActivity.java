package com.example.websave;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.websave.Database.SqliteDatabase;
import com.example.websave.SupportClass.MyWebViewClient;
import com.github.barteksc.pdfviewer.PDFView;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.io.File;

public class PsfActivity extends AppCompatActivity {
    PDFView pdfView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfactivity);
        Bundle extras = getIntent().getExtras();

    }
}
