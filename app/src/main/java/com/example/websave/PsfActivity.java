package com.example.websave;

import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.websave.SupportClass.MyWebViewClient;
import com.github.barteksc.pdfviewer.PDFView;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.io.File;

public class PsfActivity extends AppCompatActivity {
PDFView pdfView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfactivity);
        pdfView = findViewById(R.id.pdfView);
        pdfView.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/data/user/0/com.example.websave/files/Image-4362.jpg")).load();


    }

}
