package com.example.websave.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.websave.Database.SqliteDatabase;
import com.example.websave.Entities.Images;
import com.example.websave.R;
import com.example.websave.SupportClass.MyWebViewClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class FragmentWebActivity extends Fragment {
    TextView txtSS, txtPDF;
    WebView webView;
    SharedPreferences.Editor editor;
    OutputStream outputStream;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    FloatingActionButton btnAdd, btnPDF, btnSS;
    boolean isOpen = false;
    SqliteDatabase sqData;
    Images images;
    String url;
    int i;
    boolean isFilepdf = false;
    private ProgressBar spinner;
    private ProgressBar spinner2;
    boolean pageLoading = false;

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static FragmentWebActivity getInstance() {
        FragmentWebActivity fragmentWebActivity = new FragmentWebActivity();
        return fragmentWebActivity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_layout, container, false);
        webView = view.findViewById(R.id.webView);
        sqData = new SqliteDatabase(getActivity());
        btnAdd = view.findViewById(R.id.add_btn);
        btnPDF = view.findViewById(R.id.btnPDF);
        btnSS = view.findViewById(R.id.btnScreenShot);
        images = new Images();
        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        spinner.setVisibility(View.INVISIBLE);
        txtSS = view.findViewById(R.id.add_screen_shot_text);
        txtPDF = view.findViewById(R.id.addPDFText);
        rotateOpen = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate__open_anime);
        rotateClose = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_close_anime);
        fromBottom = AnimationUtils.loadAnimation(view.getContext(), R.anim.from_bottom);
        toBottom = AnimationUtils.loadAnimation(view.getContext(), R.anim.to_bottom);
        webView.setDrawingCacheEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        if (getArguments() != null) {
            String urlfromHome = getArguments().getString("home");
            String urlforreload = getArguments().getString("refresh");
            String urlforSearch = getArguments().getString("Searchurl");
            setHome_Refresh(webView, urlfromHome, urlforreload, urlforSearch);
        } else {
            SharedPreferences prefs = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            url = prefs.getString("url", "https:www.google.com");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pageLoading = true;
                spinner2.setVisibility(View.GONE);
                if (url != null) {
                    url = webView.getUrl();
                    try {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("pref", MODE_PRIVATE).edit();
                        editor.putString("url", url);
                        editor.apply();
                    } catch (Exception e) {
                    }
                    ;
                } else {
                    return;
                }
            }

        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // checkForReloding();
                onAddbuttonClicked();
            }
        });
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageLoading) {
                    takeScreenShot2(webView);
                    isFilepdf = true;
                    images.setPdfurlthumbnail(saveImage(screenShot(webView)));
                    try {

                        images.setPdfurl(getPdf(takeScreenShot2(webView)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    images.setImage_txt("IMG" + RandomGenerator() + ".pdf" + "\n" + getDateTime());
                    sqData.insertData(images);
                    getActivity().recreate();
                    pageLoading = false;
                     } else{
                    Toast t = Toast.makeText(getContext(), "Wait Page is Loading", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        btnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (pageLoading) {
                    images.setUrlthumbnail(saveImage(screenShot(webView)));
                    images.setUrl(saveImage(takeScreenShot2(webView)));
                    images.setImage_txt("IMG" + RandomGenerator() + ".jpeg" + "\n" + getDateTime());
                    sqData.insertData(images);
                    getActivity().recreate();
                    pageLoading = false;

                } else {
               Toast t=Toast.makeText(getContext(), "Wait Page is Loading", Toast.LENGTH_SHORT);
              t.show();
                }
            }
        });
        return view;

    }


    private Bitmap takeScreenShot2(WebView view) {
        spinner.setVisibility(View.VISIBLE);
        view.enableSlowWholeDocumentDraw();
        view.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
       // view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        view.draw(canvas);
        return bitmap;
    }


    private void onAddbuttonClicked() {
        if (isOpen) {
            btnAdd.startAnimation(rotateClose);
            btnPDF.startAnimation(toBottom);
            txtPDF.startAnimation(toBottom);
            btnSS.startAnimation(toBottom);
            txtSS.startAnimation(toBottom);
            btnPDF.setVisibility(View.INVISIBLE);
            txtPDF.setVisibility(View.INVISIBLE);
            btnSS.setVisibility(View.INVISIBLE);
            txtSS.setVisibility(View.INVISIBLE);
            btnSS.setClickable(false);
            btnPDF.setClickable(false);
            isOpen = false;
        } else {
            btnAdd.startAnimation(rotateOpen);
            btnPDF.startAnimation(fromBottom);
            txtPDF.startAnimation(fromBottom);
            btnSS.startAnimation(fromBottom);
            txtSS.startAnimation(fromBottom);
            btnPDF.setVisibility(View.VISIBLE);
            txtPDF.setVisibility(View.VISIBLE);
            btnSS.setVisibility(View.VISIBLE);
            txtSS.setVisibility(View.VISIBLE);
            btnPDF.setClickable(true);
            btnSS.setClickable(true);
            isOpen = true;
        }
    }

    public String saveImage(Bitmap finalBitmap) {
        File file1 = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                , "ScreenShot" + RandomGenerator() + ".jpeg");


        try {
            FileOutputStream out = new FileOutputStream(file1);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file1.getAbsolutePath();
    }


    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void setHome_Refresh(WebView webView, String homeurl, String Reloadurl, String Searchurl) {
        if (homeurl != null) {
            webView.loadUrl(homeurl);
        } else if (Reloadurl != null) {

            webView.loadUrl(Reloadurl);

        } else if (Searchurl != null) {
            webView.loadUrl(Searchurl);
        } else {
            return;
        }
    }

    private String getDateTime() {
        String daydate;
        DateFormat dateFormat = new SimpleDateFormat("MM/EEEE HH:mm aa");
        Random generator = new Random();
        Date date = new Date();
        int n = 1000;
        n = generator.nextInt(n);
        daydate = dateFormat.format(date);


        return daydate;
    }

    public String getPdf(Bitmap bitmap) throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageinfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageinfo);
        page.getCanvas().drawBitmap(bitmap, 0, 0, null);
        pdfDocument.finishPage(page);
        File file1 = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                , "ScreenShot" + RandomGenerator() + ".pdf");


        FileOutputStream outputStream1 = new FileOutputStream(file1);
        pdfDocument.writeTo(outputStream1);

        outputStream1.flush();


        pdfDocument.close();


        return file1.getAbsolutePath();

    }


    public int RandomGenerator() {

        Random generator = new Random();
        int n = 1000;
        n = generator.nextInt(n);
        return n;
    }




}
