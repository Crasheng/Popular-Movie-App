package com.example.ahmad.popularmovies_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class  MovieDetailActivity extends AppCompatActivity {

    private static final String MDFTAG = "movie_detail_fragment_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_container, new MovieDetailFragment(), MDFTAG)
                    .commit();
        }

    }
}
