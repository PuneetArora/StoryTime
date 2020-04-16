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

import com.google.android.material.navigation.NavigationView;

public class FragmentAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_LANGUAGE = "english";
    NavigationView navigationView;
    private ActionBarDrawerToggle t;
    private DrawerLayout drawerLayout;
    private String language;
    private Bundle bundle;
    private Toolbar toolbar;
    private StoryFragment obj;
    private Boolean exit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fragment);
        checkFirstRun();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        if (savedInstanceState == null) {


            language = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("shared_language", "english");
            bundle = new Bundle();
            bundle.putString("lang", language);
            if (language.equals("hindi"))
                getSupportActionBar().setTitle("हिंदी कहानियाँ");
            else if (language.equals("punjabi"))
                getSupportActionBar().setTitle("ਪੰਜਾਬੀ ਕਹਾਣੀਆਂ");
            else
                getSupportActionBar().setTitle("English Stories");
            obj = new StoryFragment();
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

        if (id == R.id.select_language)
            intent = new Intent(this, SelectLanguage.class);
        if (id == R.id.contact_us) {
        }
        if (intent != null) {
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    public void checkFirstRun() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(this, SelectLanguage.class));

        } else {

            language = getIntent().getStringExtra(EXTRA_LANGUAGE);


        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

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

