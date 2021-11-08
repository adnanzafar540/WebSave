package com.example.websave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.websave.Adapters.ViewPagerAdapter;
import com.example.websave.Fragments.FragmentGalleryActivity;
import com.example.websave.Fragments.FragmentPdfActivity;
import com.example.websave.Fragments.FragmentWebActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentWebActivity fw;
    public static  String urlhome;
    public static  String urlreload;

    private int[] tabIcons = {
            R.drawable.web,
            R.drawable.saved_image,
            R.drawable.saved_pdf
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setNavListener(nav);
        setViewPager(viewPager,tabLayout);
    }



    private void setViewPager(ViewPager viewPager,TabLayout tabLayout) {
        final ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(FragmentWebActivity.getInstance(),"WEB");
                viewPagerAdapter.addFragment(FragmentGalleryActivity.getInstance(),"GALLERY");
                viewPagerAdapter.addFragment(FragmentPdfActivity.getInstance(),"PDF");
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            }
        });


    }

    //Initializing all declared objects and also setting up the Toolbar
    private void initialize() {
        setContentView(R.layout.activity_main);
        Toolbar tool=findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        fw=new FragmentWebActivity();
        nav=findViewById(R.id.nav_menu);
        drawerLayout=findViewById(R.id.drawer);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,tool,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    //Setting the Navigation View and Drawer Layout
    private void setNavListener(NavigationView nav) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.picture_quality:
                        Toast.makeText(getApplicationContext(), "Picture Quality", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.saved_images:
                        Toast.makeText(getApplicationContext(), "Saved Images", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.saved_pdf:
                        Toast.makeText(getApplicationContext(), "Saved PDF", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ads_free_version:
                        Toast.makeText(getApplicationContext(), "Ads Free Version", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.share:
                        Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.rate:
                        Toast.makeText(getApplicationContext(), "Rate", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.more:
                        Toast.makeText(getApplicationContext(), "More", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.about_us:
                        Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.exit:
                        Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
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
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Bundle bundle = new Bundle();
                bundle.putString("url", "https:www.google.com");
                FragmentWebActivity fragobj = new FragmentWebActivity();
                fragobj.setArguments(bundle);
                viewPager.setCurrentItem(0);
                return true;
            case R.id.refresh:
                SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                String url = prefs.getString("url", "https:www.google.com");
                Bundle bundle2 = new Bundle();
                bundle2.putString("url2",url);
                FragmentWebActivity fragobj2 = new FragmentWebActivity();
                fragobj2.setArguments(bundle2);

               viewPager.setCurrentItem(0);
              // viewPager.

                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}