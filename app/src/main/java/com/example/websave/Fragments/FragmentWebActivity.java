package com.example.websave.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.websave.Database.DatabaseProviderImg;
import com.example.websave.Database.SqliteDatabase;
import com.example.websave.Entities.Images;
import com.example.websave.R;
import com.example.websave.SupportClass.BitmapToString;
import com.example.websave.SupportClass.MyWebViewClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Random;

public class FragmentWebActivity extends Fragment {
    TextView txtSS, txtPDF;
    WebView webView;
    OutputStream outputStream;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    FloatingActionButton btnAdd, btnPDF, btnSS;
    boolean isOpen = false;
    SqliteDatabase sqData;
    Images images;
    String url;
    String url_refresh;

    public Bitmap screenShot;

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
        txtSS = view.findViewById(R.id.add_screen_shot_text);
        txtPDF = view.findViewById(R.id.addPDFText);
        rotateOpen = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate__open_anime);
        rotateClose = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_close_anime);
        fromBottom = AnimationUtils.loadAnimation(view.getContext(), R.anim.from_bottom);
        toBottom = AnimationUtils.loadAnimation(view.getContext(), R.anim.to_bottom);
        webView.setWebViewClient(new MyWebViewClient());
        if (getArguments() != null) {
            String urlfromHome = getArguments().getString("home");
            String urlforreload = getArguments().getString("refresh");
            setHome_Refresh(webView, urlfromHome, urlforreload);
        }else {
            SharedPreferences prefs = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            url = prefs.getString("url", "https:www.google.com");//"No name defined" is the default value.
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
          //  prefs.edit().remove("url").commit();
        }

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                url=webView.getUrl();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("pref", MODE_PRIVATE).edit();
                editor.putString("url", url);
                editor.apply();


            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddbuttonClicked();
            }
        });
        btnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
               /* webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        takeScreenShot(view);
                //setContentView(webView);
                    }
                });**/
                images.setUrl(saveImage(takeScreenShot2(webView)).getAbsolutePath());
              //  webView.loadUrl(webView.getUrl().toString());


                //   webView.loadUrl( "javascript:window.location.reload( true )" );
               // webView.loadUrl(url1);
              //
                //  webUrl(webView);
                sqData.insertData(images);
               /* Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("WebFragment");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();**/
             //   getActivity().recreate();

                getActivity().recreate();


            }
        });
        return view;

    }


    private Bitmap takeScreenShot2(WebView view) {
        view.enableSlowWholeDocumentDraw();
        view.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

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

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public File saveImage(Bitmap finalBitmap) {
        String root = getActivity().getFilesDir().getAbsolutePath();
        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdir();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        images.setImage_txt(fname);
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

   /* public Bitmap takeScreenshot_Advanced(View v) {
        Bitmap bitmap = getScreenBitmap(v); // Get the bitmap
        return bitmap;       // Save it to the external storage device.
    }**/


    public Bitmap getScreenBitmap(WebView v) {
        v.enableSlowWholeDocumentDraw();
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), null);
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    private Bitmap takeScreenshot_Advanced2(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public Bitmap takeScreenshots(WebView iv) {
        //ScrollView iv = (ScrollView) findViewById(R.id.scrollView);
        Bitmap bitmap = Bitmap.createBitmap(
                iv.getChildAt(0).getWidth(),
                iv.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        iv.getChildAt(0).draw(c);
        return bitmap;
    }

    public Bitmap ss(WebView webView) {

        webView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(webView.getDrawingCache(false));
        webView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    private Bitmap captureScreen() {

        // create bitmap screen capture
        Bitmap bitmap;
        View v1 = webView.getRootView();
        v1.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        v1.setDrawingCacheEnabled(false);
        return bitmap;


    }
    public static Bitmap screenshot(WebView webView) {
        try {
            float scale = webView.getScale();
            int height = (int) (webView.getContentHeight() * scale + 0.5);
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public void setHome_Refresh(WebView webView,String homeurl,String Reloadurl){
        if(homeurl!=null){
            webView.loadUrl(homeurl);
        }
        else if(Reloadurl!=null){

            webView.loadUrl(Reloadurl);

        }
        else
            return;

     }

}
