package com.killer.storytime;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoryFragment extends Fragment {
    Parcelable state;
    private long mLastClickTime = 0;
    private FirebaseRecyclerAdapter adapter;
    private String language;
    private final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    private ImageView imageView;
    private TextView title;
    private CardView ncv;
    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final RecyclerView storyRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_story, container, false);

        storyRecycler.setHasFixedSize(true);
        if (!(isNetworkConnected() && isOnline()))
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();


        Bundle bundle = getArguments();
        if (bundle != null) {
            language = bundle.getString("lang");
        }

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(language);


        final FirebaseRecyclerOptions<StoryData> options =
                new FirebaseRecyclerOptions.Builder<StoryData>()
                        .setQuery(query, StoryData.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<StoryData, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ncv = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.story_segments, parent, false);
                return new ViewHolder(ncv);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, final StoryData model) {
                final CardView ncardView = holder.ncardView;
                imageView = ncardView.findViewById(R.id.story_image);
                Picasso.get().load(model.getImageUrl()).into(imageView);
                title = ncardView.findViewById(R.id.story_title);
                title.setText(model.getTitle());

                ncardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                            return;

                        mLastClickTime = SystemClock.elapsedRealtime();

                        Intent intent = new Intent(ncardView.getContext(), Detailed.class);
                        intent.putExtra(Detailed.EXTRA_PHOTOURL, model.getImageUrl());
                        intent.putExtra(Detailed.EXTRA_STORY_HEADING, model.getTitle());
                        intent.putExtra(Detailed.EXTRA_STORY, model.getStory());
                        intent.putExtra(Detailed.EXTRA_MORAL, model.getMoral());

                        ncardView.getContext().startActivity(intent);

                    }

                });

            }

        };

        storyRecycler.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        storyRecycler.setAdapter(adapter);
        adapter.startListening();

        return storyRecycler;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.startListening();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException d) {
            d.printStackTrace();
        }

        return false;

    }

}



