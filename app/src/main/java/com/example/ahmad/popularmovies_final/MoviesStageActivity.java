package com.example.ahmad.popularmovies_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

//TODOs
    //1- Factor the Fetch data Class to be configurable
    //2- You have to be sure of the internet connectivity and determine the test cases
    //3- Check the configuration of the Picasso or use Glide and check its configuration too!

public class MoviesStageActivity extends AppCompatActivity implements MoviesStageFragment.onMovieClick {

    public static String DF_TAG = "detail_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void movieHasBeenClicked(Uri clicked_movie) {
        //check which XML file has been used depends on the screen size of the device.
        if (findViewById(R.id.headset_main_fragment) == null) {

            //add a bundle to the fragment.
            MovieDetailFragment fragment = (MovieDetailFragment) fragmentWithUri(new MovieDetailFragment(),
                    MovieDetailFragment.DETAIL_DATA_URI, clicked_movie);

            //Log the event.
            Log.d(MoviesStageFragment.TAG, "Clicked Movie uri");

            //replace or add if it is null.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,fragment , DF_TAG)
                    .commit();
        }
        else
        {
            //Create a new intent to launch the detail activity
            //because we are now on small screen device.
            Intent intent = new Intent(this, MovieDetailActivity.class).setData(clicked_movie);
            startActivity(intent);
        }

    }

    //this method to apply a bundle for a fragment adding to it URI for data being populated.
    private Fragment fragmentWithUri(Fragment fragment, String key, Uri uri)
    {
        //create new bundle instance
        Bundle args = new Bundle();
        args.putParcelable(key, uri);
        fragment.setArguments(args);
        //return the same fragment has been received attached to it a bundle with a data
        return fragment;
    }
}
