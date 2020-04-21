package com.killer.storytime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

public class FragmentAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_LANGUAGE = "english";
    private NavigationView navigationView;
    private ActionBarDrawerToggle t;
    private DrawerLayout drawerLayout;
    private String language;
    private Boolean exit = false;
    private AdView mAdView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fragment);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        checkFirstRun();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);



        if (savedInstanceState == null) {


            language = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("shared_language", "english");
            Bundle bundle = new Bundle();
            bundle.putString("lang", language);
            if (language.equals("hindi"))
                getSupportActionBar().setTitle("हिंदी कहानियाँ");
            else if (language.equals("punjabi"))
                getSupportActionBar().setTitle("ਪੰਜਾਬੀ ਕਹਾਣੀਆਂ");
            else
                getSupportActionBar().setTitle("English Stories");
            StoryFragment obj = new StoryFragment();
            obj.setArguments(bundle);
            obj.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, obj).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(false);
        int id = menuItem.getItemId();
        Intent intent = null;
        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here

        if (id == R.id.select_language) {
            intent = new Intent(this, SelectLanguage.class);
            menuItem.setChecked(false);
        }
        if (id == R.id.contact_us) {
            intent = new Intent(this, ContactActivity.class);
            menuItem.setChecked(false);
        }
        if (id == R.id.share_button) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you StoryTime app containing English, Hindi and Punjabi Stories for Kids\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }

        if (intent != null) {
            startActivity(intent);
        }
        menuItem.setChecked(false);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (t.onOptionsItemSelected(item))
            return true;




        return super.onOptionsItemSelected(item);
    }


    private void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(this, SelectLanguage.class));

        } else {

            language = getIntent().getStringExtra(EXTRA_LANGUAGE);


        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }

}

