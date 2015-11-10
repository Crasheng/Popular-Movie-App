package com.example.ahmad.popularmovies_final;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ahmad.popularmovies_final.Data.MoviesContract;
import com.example.ahmad.popularmovies_final.Intenet.RESTAdapter;
import com.example.ahmad.popularmovies_final.POJOs.Reviews.ReviewsResponse;
import com.example.ahmad.popularmovies_final.POJOs.Videos.VideosResponse;
import com.example.ahmad.popularmovies_final.POJOs.Videos.VideosResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, FetchDataInternet.FetchedDataReady, View.OnClickListener {


    private static final String RELATED_MOVIE_ID = "clicked_movie";
    private static final Integer FAV_BUTTON_ACTIVATED = 1;
    private static final Integer FAV_BUTTON_NOT_ACTIVATED = 0;
    private static final String TAG = "fav_button";
    private static Integer fav_button_mode;
    private boolean favorite_movie;


    Uri uriOfClickedMovie ;

    public static final String DETAIL_DATA_URI = "content_uri";
    private static final int GENERAL_MOVIE_LOADER_ID = 1;
    public RatingBar movie_rating;
    public TextView movie_rating_ratio;
    public ImageView movie_poster;
    public TextView movie_overview;
    public ImageView movie_backdrop;
    public TextView movie_release_date;
    public TextView movie_original_title;
    public ImageButton video_button;
    public TextView numberOfReviews;
    public Button lanuchReviewsButton;
    public ImageButton fav_button;
    String movie_id;

    final static String[] projections_of_movies = {
            MoviesContract.MoviesEntry.TABLE_NAME + "." + MoviesContract.MoviesEntry._ID,
            MoviesContract.MoviesEntry.MOV_COL_ID,
            MoviesContract.MoviesEntry.MOV_COL_TITLE,
            MoviesContract.MoviesEntry.MOV_COL_OVERVIEW,
            MoviesContract.MoviesEntry.MOV_COL_FAVOURITE,
            MoviesContract.MoviesEntry.MOV_COL_ORIGINAL_TITLE,
            MoviesContract.MoviesEntry.MOV_COL_POSTER,
            MoviesContract.MoviesEntry.MOV_COL_BACKDROP,
            MoviesContract.MoviesEntry.MOV_COL_RELEASE_DATE,
            MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE,

    };

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        uriOfClickedMovie = getUri(savedInstanceState);
        movie_id = uriOfClickedMovie.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
        //recommended to be deleted
        //replace it with the retrofit library.
       askInternetForReviews(Integer.valueOf(movie_id));
        //new FetchDataInternet(MovieDetailFragment.this, UtilityMovieData.REQUEST_REVIEWS).execute(movie_id);
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //Helper Method to set the fields of the fragment view.
        setLayoutFields(view);

        //Register on click list. to these buttons
        lanuchReviewsButton.setOnClickListener(this);
        video_button.setOnClickListener(this);
        fav_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(GENERAL_MOVIE_LOADER_ID, savedInstanceState, this);
        Log.d(TAG, "onActivityCreated : " + movie_id);
        if (isItFavourite(movie_id)  == true) {
            Log.d(TAG, "onActivityCreated : it is a favourite movie");
            //You have to check if that movie in Favourite database or not!
            //then you apply the suitable Tag to it.
            fav_button.setImageResource(R.drawable.fav_true);
            fav_button.setTag(FAV_BUTTON_ACTIVATED);

        }
        else
        {
            Log.d(TAG, "onActivityCreated : it is not there");
            fav_button.setImageResource(R.drawable.default_fav);
            fav_button.setTag(FAV_BUTTON_NOT_ACTIVATED);

        }
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri;
        uri = getUri(args);
        return new CursorLoader(getActivity(),
                uri,
                projections_of_movies,
                null,
                null,
                null);
    }

    private Uri getUri(Bundle args) {
        args = getArguments();
        if (args != null) {
            return args.getParcelable(DETAIL_DATA_URI);
        } else {
            return getActivity().getIntent().getData();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();
        //Base Url for movie related images.
        final String POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185";
        final String BACKDROP_BASE_URI = "http://image.tmdb.org/t/p/w500";


        //get the relative path to each movies' data
        String poster = data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_POSTER));
        String backdrop = data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_BACKDROP));


        //Applying all Required Data to the View
        Picasso.with(getActivity()).load(POSTER_BASE_URI + poster).noFade().into(movie_poster);
        Picasso.with(getActivity()).load(BACKDROP_BASE_URI + backdrop).noFade().into(movie_backdrop);

        movie_rating.setRating((float) data.getDouble(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE)));
        movie_rating_ratio.setText(" (" + String.valueOf(data.getDouble(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE))) + ")");
        movie_release_date.setText("Release Date: " + data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_RELEASE_DATE)));
        movie_overview.setText(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_OVERVIEW)));
        movie_original_title.setText(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_TITLE)));

        //set Title of host activity to movie name.
        getActivity().setTitle(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_TITLE)));
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @Override
    public void RequestedDataReady(ContentValues[] fetched_data, int mode) {
        if (mode == UtilityMovieData.REQUEST_MOVIE_VIDEO) {
            //piece of code when the requested data is ready..
            //and the data has been requested is the trailers videos.
            List<String> name_list = new ArrayList<>();
            List<String> key_list = new ArrayList<>();
            for (ContentValues cv : fetched_data) {
                String video_name = cv.getAsString(UtilityMovieData.VIDEO_NAME);
                String video_key = cv.getAsString(UtilityMovieData.VIDEO_KEY);
                name_list.add(video_name);
                key_list.add(video_key);
            }
            ArrayList<String> name_array = new ArrayList<>(name_list);
            ArrayList<String> key_array = new ArrayList<>(key_list);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("name", name_array);
            bundle.putStringArrayList("key", key_array);
            final AvailableTrailersFragment dialog = new AvailableTrailersFragment();
            dialog.setArguments(bundle);
            dialog.show(getActivity().getSupportFragmentManager(), "Videos_name");
        }
        else if (fetched_data != null && mode == UtilityMovieData.REQUEST_REVIEWS) {
            TextView textReviewsNumber = (TextView) getActivity().findViewById(R.id.numberOfReviews);
            Button reviewsButton = (Button) getActivity().findViewById(R.id.reviews_button);
            if (fetched_data.length == 1) {
                textReviewsNumber.setText("("+String.valueOf(fetched_data.length)+")");
                reviewsButton.setText(getString(R.string.reviews_button_single));
            }else{
                textReviewsNumber.setText("("+String.valueOf(fetched_data.length)+")");
                reviewsButton.setText(getString(R.string.reviews_button));
            }
            getActivity().getContentResolver().bulkInsert(MoviesContract.ReviewsEntry.CONTENT_URI, fetched_data);
        }

    }


    private void setLayoutFields(View view) {
        numberOfReviews = (TextView) view.findViewById(R.id.numberOfReviews);
        lanuchReviewsButton = (Button) view.findViewById(R.id.reviews_button);
        movie_rating = (RatingBar) view.findViewById(R.id.movie_rating_stars);
        movie_rating_ratio = (TextView) view.findViewById(R.id.vote_ratio);
        movie_poster = (ImageView) view.findViewById(R.id.poster_image);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_backdrop = (ImageView) view.findViewById(R.id.backdrop_image);
        movie_release_date = (TextView) view.findViewById(R.id.movie_release_date);
        movie_original_title = (TextView) view.findViewById(R.id.movie_original_name);
        video_button = (ImageButton) view.findViewById(R.id.video_button);
        fav_button = (ImageButton) view.findViewById(R.id.fav_button);

    }

    @Override
    public void onClick(View v) {
        Bundle arguments = getArguments();
        Uri uri = getUri(arguments);
        String movie_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
        switch (v.getId())
        {
            case R.id.reviews_button:
                ReviewsPopupFragment reviews = new ReviewsPopupFragment();
                Bundle args = new Bundle();
                args.putParcelable(RELATED_MOVIE_ID, uri);
                reviews.setArguments(args);
                reviews.show(getActivity().getSupportFragmentManager(), "reviews_list");
                break;
            case R.id.video_button:
                askInternetForTrailers(Integer.valueOf(movie_id));
                new FetchDataInternet(MovieDetailFragment.this, UtilityMovieData.REQUEST_MOVIE_VIDEO).execute(movie_id);
                break;
            case R.id.fav_button:
                ContentValues fav_movie = new ContentValues();
                fav_movie.put(MoviesContract.FavouriteEntry.RELATED_MOVIE_COL, Integer.valueOf(movie_id));
                if ( fav_button.isPressed() == true && fav_button.getTag().equals(FAV_BUTTON_NOT_ACTIVATED)) {
                    fav_button.setImageResource(R.drawable.fav_true);
                    fav_button.setTag(FAV_BUTTON_ACTIVATED);

                    Uri c = getActivity().getContentResolver().insert(MoviesContract.FavouriteEntry.CONTENT_URI, fav_movie);
                    Log.d(TAG, "onClick insert this movie"  + c.toString());
                }
                else
                {
                    fav_button.setImageResource(R.drawable.default_fav);
                    fav_button.setTag(FAV_BUTTON_NOT_ACTIVATED);
                    int c = getActivity().getContentResolver().delete(MoviesContract.FavouriteEntry.CONTENT_URI, MoviesContract.FavouriteEntry.RELATED_MOVIE_COL + "=" + movie_id, null);
                    Log.d(TAG, "onClick deleted movie " + String.valueOf(c));
                }
                break;
            default:
                throw new UnsupportedOperationException("Wrong View");
        }
    }

    boolean isItFavourite(String movie_id)
    {
        Log.d(TAG, "isItFavourite : " + String.valueOf(movie_id));

        Uri uriWithParameter = MoviesContract.FavouriteEntry.CONTENT_URI.buildUpon().appendQueryParameter(MoviesContract.FavouriteEntry.RELATED_MOVIE_COL, movie_id).build();

        Cursor c = getActivity().getContentResolver().query(uriWithParameter, new String[] {MoviesContract.FavouriteEntry.RELATED_MOVIE_COL}, null, null, null, null);

        Log.d(TAG, "isItFavourite cursor " + c.getCount());
        boolean there = c.moveToFirst();
        return there;
    }

    public static class AvailableTrailersFragment extends DialogFragment {

        ArrayList<String> videos_name = null;
        ArrayList<String> videos_key = null;


        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.title_video_dialog);

            Bundle bundle = getArguments();
            videos_name = bundle.getStringArrayList("name");
            videos_key = bundle.getStringArrayList("key");


            CharSequence[] cs = videos_name.toArray(new CharSequence[videos_name.size()]);

            builder.setItems(cs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri video_uri = UtilityMovieData.buildUriForTheVideo(videos_key.get(which));
                    Intent intent = new Intent(Intent.ACTION_VIEW, video_uri);
                    startActivity(intent);

                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    public static class ReviewsPopupFragment extends DialogFragment implements  LoaderManager.LoaderCallbacks<Cursor>{

        ReviewsAdapter adapter;

        ListView reviews_list = null;
        final int LOADER_ID = 1;
        Uri uriOfClickedMovie;

        final static String[] projections_of_reviews = {
                MoviesContract.ReviewsEntry.TABLE_NAME + "." + MoviesContract.ReviewsEntry._ID,
                MoviesContract.ReviewsEntry.REV_COL_AUTHOR,
                MoviesContract.ReviewsEntry.REV_COL_CONTENT
        };

        @Override
        public void setStyle(int style, @StyleRes int theme) {
            super.setStyle(style, theme);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.reviews_fragment, null);
            builder.setView(view);
            reviews_list = (ListView) view.findViewById(R.id.reviews_list);
            adapter = new ReviewsAdapter(getActivity(), null);
            reviews_list.setAdapter(adapter);

            //fetch the movie id from bundle.
            uriOfClickedMovie = getArguments().getParcelable(RELATED_MOVIE_ID);

            //init the loader.
            getLoaderManager().initLoader(LOADER_ID,null,this);
            return builder.create();
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String movie_id = uriOfClickedMovie.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
            Uri uri = MoviesContract.ReviewsEntry.buildMovieReviewsUri(movie_id);
            return new CursorLoader(getActivity(),
                    uri,
                    projections_of_reviews,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    }

    void askInternetForTrailers(final int movieId)
    {
        RESTAdapter adapter = new RESTAdapter("http://api.themoviedb.org/");
        Call<VideosResponse> videos_repsonse = adapter.getInternetGate()
                .getVideosOfMovie(movieId, getResources().getString(R.string.api_key).toString());
        videos_repsonse.enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(Response<VideosResponse> response) {
                //set the dialog fragment data to links of the videos
                VideosResponse mResponse = response.body();
                ArrayList<VideosResult> results = (ArrayList<VideosResult>) mResponse.getResults();
                Bundle bundle = new Bundle();
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<String> keys = new ArrayList<String>();
                for ( VideosResult result: results) {
                    names.add(result.getName());
                    keys.add(result.getKey());
                }
                bundle.putStringArrayList("name", names);
                bundle.putStringArrayList("name", keys);
                final AvailableTrailersFragment dialog = new AvailableTrailersFragment();
                dialog.setArguments(bundle);
                //dialog.show(getActivity().getSupportFragmentManager(), "Videos_name");
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void askInternetForReviews(Integer movie_id) {
        RESTAdapter adapter = new RESTAdapter("http://api.themoviedb.org/");
        Call<ReviewsResponse> response = adapter.getInternetGate()
                .getReviewsAboutMovie(movie_id, getResources().getString(R.string.api_key).toString());
        response.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Response<ReviewsResponse> response) {
                ReviewsResponse review_response = response.body();
                if (review_response.getResults().size() > 0) {
                    TextView textReviewsNumber = (TextView) getActivity().findViewById(R.id.numberOfReviews);
                    Button reviewsButton = (Button) getActivity().findViewById(R.id.reviews_button);
                    if (review_response.getResults().size() == 1) {
                        textReviewsNumber.setText("("+String.valueOf(review_response.getResults().size())+")");
                        reviewsButton.setText(getString(R.string.reviews_button_single));
                    }else{
                        textReviewsNumber.setText("("+String.valueOf(review_response.getResults().size())+")");
                        reviewsButton.setText(getString(R.string.reviews_button));
                    }
                    ContentValues[] fetched_data = UtilityMovieData.prepareReviewsBulkData(response.body());
                    getActivity().getContentResolver().bulkInsert(MoviesContract.ReviewsEntry.CONTENT_URI, fetched_data);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
