package com.killer.storytime;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import static com.killer.storytime.FragmentAct.EXTRA_LANGUAGE;


public class SelectLanguage extends AppCompatActivity {

    private Intent intent;
    private Boolean exit = false;
    private long mLastClickTime = 0;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_select_language);
        getSupportActionBar().setTitle("Select Language");

        CardView ecv = findViewById(R.id.english_cardview);
        ecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    return;
                mLastClickTime = SystemClock.elapsedRealtime();


                intent = new Intent(SelectLanguage.this, FragmentAct.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setLanguage("english");
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("shared_language", getLanguage()).apply();
                intent.putExtra(EXTRA_LANGUAGE, getLanguage());
                startActivity(intent);
            }

        });


        CardView hcv = findViewById(R.id.hindi_cardview);
        hcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    return;
                mLastClickTime = SystemClock.elapsedRealtime();

                intent = new Intent(SelectLanguage.this, FragmentAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setLanguage("hindi");

                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("shared_language", getLanguage()).apply();
                intent.putExtra(EXTRA_LANGUAGE, getLanguage());
                startActivity(intent);
            }

        });

        CardView pcv = findViewById(R.id.punjabi_cardview);
        pcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    return;
                mLastClickTime = SystemClock.elapsedRealtime();

                intent = new Intent(SelectLanguage.this, FragmentAct.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setLanguage("punjabi");

                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("shared_language", getLanguage()).apply();

                intent.putExtra(EXTRA_LANGUAGE, getLanguage());
                startActivity(intent);
            }

        });


    }


    private String getLanguage() {
        return language;
    }

    private void setLanguage(String language) {
        this.language = language;
    }


}
