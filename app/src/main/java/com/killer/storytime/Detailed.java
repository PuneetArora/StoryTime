package com.killer.storytime;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {
    public static final String EXTRA_PHOTOURL = "newsId", EXTRA_STORY_HEADING = "heading", EXTRA_STORY = "STORY", EXTRA_MORAL = "MORAL";
    private String photoURL, story, heading, moral;
    private ImageView imageView;
    private TextView textViewHeading, textViewstory, textViewmoral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        photoURL = (String) getIntent().getExtras().get(EXTRA_PHOTOURL);
        imageView = findViewById(R.id.storyDetailImageView);
        Picasso.get().load(photoURL).into(imageView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textViewHeading = findViewById(R.id.heading_news);
        textViewstory = findViewById(R.id.story);
        textViewmoral = findViewById(R.id.moral);


        story = (String) getIntent().getExtras().get(EXTRA_STORY);
        textViewstory.setText(story);
        heading = (String) getIntent().getExtras().get(EXTRA_STORY_HEADING);
        textViewHeading.setText(heading);
        getSupportActionBar().setTitle(heading);

        moral = (String) getIntent().getExtras().get(EXTRA_MORAL);
        textViewmoral.setText(moral);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}