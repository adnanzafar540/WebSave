package com.example.websave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.websave.Fragments.FragmentGalleryActivity;
import com.example.websave.Fragments.FragmentPdfActivity;
import com.example.websave.Fragments.Fragment_main;
import com.example.websave.Fragments.FragmentWebActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    androidx.appcompat.widget.SearchView searchView;

    FragmentWebActivity fw;
    public static  String urlhome;
    public static  String urlreload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setNavListener(nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new Fragment_main()).commit();

    }
    //Initializing all declared objects and also setting up the Toolbar
    private void initialize() {
        setContentView(R.layout.activity_main);
        Toolbar tool=findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        fw=new FragmentWebActivity();
        nav=findViewById(R.id.nav_menu);
        drawerLayout=findViewById(R.id.drawer);

        nav.bringToFront();
        toggle=new ActionBarDrawerToggle(this,drawerLayout,tool,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    //Setting the Navigation View and Drawer Layout
    private void setNavListener(NavigationView nav) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.saved_images:
                        Toast.makeText(getApplicationContext(), "Saved Images", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FragmentGalleryActivity fg=new FragmentGalleryActivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fg).commit();



                        break;
                    case R.id.saved_pdf:
                        Toast.makeText(getApplicationContext(), "Saved PDF", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FragmentPdfActivity fd=new FragmentPdfActivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fd).commit();

                        break;

                    case R.id.share:
                        Intent shareintent = new Intent();
                        shareintent.setAction(Intent.ACTION_SEND);
                        shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?1d-h1");
                        shareintent.setType("Text/Plain");
                        startActivity(Intent.createChooser(shareintent, "share via"));

                        Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.rate:
                        Intent intent=new Intent(MainActivity.this,rattig_bar.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Rate", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.about_us:
                        Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:

                Bundle bundle = new Bundle();
                bundle.putString("home", "https:www.google.com");
                FragmentWebActivity fragobj = new FragmentWebActivity();
                fragobj.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragobj).commit();

                Toast.makeText(this, "Go->Home", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.refresh:
                SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                String url = prefs.getString("url", "https:www.google.com");
                Bundle bundle2 = new Bundle();bundle2.putString("refresh",url);
                FragmentWebActivity fragobj2 = new FragmentWebActivity();
                fragobj2.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragobj2).commit();


                // viewPager.

                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search:
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

                searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        String search_url;
                            if(query!=null) {
                                if(query.contains("com")){
                                    search_url="https://"+query;
                                }
                                else {
                                    search_url = "https://www.google.com/search?q=" + query;
                                }
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("Searchurl", search_url);
                                FragmentWebActivity fragobj3 = new FragmentWebActivity();
                                fragobj3.setArguments(bundle3);
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragobj3).commit();
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

    }

                return super.onOptionsItemSelected(item);
        }


    }
