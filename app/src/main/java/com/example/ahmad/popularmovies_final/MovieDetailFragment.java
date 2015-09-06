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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ahmad.popularmovies_final.Data.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, FetchDataInternet.FetchedDataReady {

    private static final int GENERAL_MOVIE_LOADER_ID = 1;
    private static final int REVIEWS_MOVIE_LOADER_ID = 2;

    public static final String DETAIL_DATA_URI = "content_uri";
    private DetailViewHolder viewHolder ;

    ReviewsAdapter review_adapter;

    public ListView movie_reviews ;
    public RatingBar movie_rating ;
    public TextView movie_rating_ratio ;
    public ImageView movie_poster ;
    public TextView movie_overview ;
    public ImageView movie_backdrop ;
    public TextView movie_release_date ;
    public TextView movie_original_title ;
    public ImageButton video_button;

    String[] projections_of_movies = {
            MoviesContract.MoviesEntry.TABLE_NAME+"."+ MoviesContract.MoviesEntry._ID,
            MoviesContract.MoviesEntry.MOV_COL_ID,
            MoviesContract.MoviesEntry.MOV_COL_TITLE,
            MoviesContract.MoviesEntry.MOV_COL_OVERVIEW,
            MoviesContract.MoviesEntry.MOV_COL_FAVORITE,
            MoviesContract.MoviesEntry.MOV_COL_ORIGINAL_TITLE,
            MoviesContract.MoviesEntry.MOV_COL_POSTER,
            MoviesContract.MoviesEntry.MOV_COL_BACKDROP,
            MoviesContract.MoviesEntry.MOV_COL_RELEASE_DATE,
            MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE,

    };

    String[] projections_of_reviews = {
            MoviesContract.ReviewsEntry.TABLE_NAME+"."+ MoviesContract.ReviewsEntry._ID,
            MoviesContract.ReviewsEntry.REV_COL_AUTHOR,
            MoviesContract.ReviewsEntry.REV_COL_CONTENT
    };


    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            Uri uri ;
            if (args != null) {
                uri = args.getParcelable(DETAIL_DATA_URI);
            }
            else
                uri = getActivity().getIntent().getData();
            String movie_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
            new FetchDataInternet(this, UtilityMovieData.REQUEST_REVIEWS).execute(movie_id);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setLayoutFields(view);
        video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                Bundle args = getArguments();
                if (args != null){
                    uri = args.getParcelable(DETAIL_DATA_URI);
                }else
                {
                    uri = getActivity().getIntent().getData();
                }
                String movie_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
                new FetchDataInternet(MovieDetailFragment.this, UtilityMovieData.REQUEST_MOVIE_VIDEO).execute(movie_id);
            }
        });

        review_adapter = new ReviewsAdapter(getActivity(),null);
        movie_reviews.setAdapter(review_adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(GENERAL_MOVIE_LOADER_ID, savedInstanceState, this);
        if (savedInstanceState != null) {
            getLoaderManager().restartLoader(REVIEWS_MOVIE_LOADER_ID, savedInstanceState, this);
        }
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri;
        args = getArguments();
        if (args != null){
            uri = args.getParcelable(DETAIL_DATA_URI);
        }else
        {
            uri = getActivity().getIntent().getData();
        }
        String movie_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
        String[] projos;
        switch (id){
            case GENERAL_MOVIE_LOADER_ID:
                //use the coming uri
                projos = projections_of_movies;
                break;
            case REVIEWS_MOVIE_LOADER_ID:
                uri = MoviesContract.ReviewsEntry.buildMovieReviewsUri(movie_id);
                projos = projections_of_reviews;
                break;
            default:
                throw new UnsupportedOperationException("Invalid Loader ID");
        }
        return new CursorLoader(getActivity(),
                uri,
                projos,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int loaderID = loader.getId();
        if (loaderID == REVIEWS_MOVIE_LOADER_ID) {
             review_adapter.swapCursor(data);

        }
        else {
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
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        int loaderId = loader.getId();
        if (loaderId == REVIEWS_MOVIE_LOADER_ID) {
            review_adapter.swapCursor(null);
        }
    }

    @Override
    public void RequestedDataReady(ContentValues[] fetched_data, int mode) {
        if (fetched_data != null && mode == UtilityMovieData.REQUEST_REVIEWS) {
            getActivity().getContentResolver().bulkInsert(MoviesContract.ReviewsEntry.CONTENT_URI, fetched_data);
            getLoaderManager().restartLoader(REVIEWS_MOVIE_LOADER_ID, null, this);

        }
        if (mode == UtilityMovieData.REQUEST_MOVIE_VIDEO) {
            List<String> name_list=   new ArrayList<>();
            List<String> key_list=   new ArrayList<>();
            for (ContentValues cv:fetched_data) {
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
            final FireMissilesDialogFragment dialog = new FireMissilesDialogFragment();
            dialog.setArguments(bundle);
            dialog.show(getActivity().getSupportFragmentManager(), "Videos_name");
        }

    }


    private void setLayoutFields(View view) {
        movie_reviews = (ListView) view.findViewById(R.id.reviews_list);
        movie_rating = (RatingBar) view.findViewById(R.id.movie_rating_stars);
        movie_rating_ratio = (TextView) view.findViewById(R.id.vote_ratio);
        movie_poster = (ImageView) view.findViewById(R.id.poster_image);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_backdrop = (ImageView) view.findViewById(R.id.backdrop_image);
        movie_release_date = (TextView) view.findViewById(R.id.movie_release_date);
        movie_original_title = (TextView) view.findViewById(R.id.movie_original_name);
        video_button = (ImageButton) view.findViewById(R.id.video_button);
    }

    public static class  FireMissilesDialogFragment extends DialogFragment {

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
    public static class DetailViewHolder{

        public ListView movie_reviews ;
        public RatingBar movie_rating ;
        public TextView movie_rating_ratio ;
        public ImageView movie_poster ;
        public TextView movie_overview ;
        public ImageView movie_backdrop ;
        public TextView movie_release_date ;
        public TextView movie_original_title ;
        public ImageButton video_button;

        DetailViewHolder(View view) {
            movie_reviews = (ListView) view.findViewById(R.id.reviews_list);
            movie_rating = (RatingBar) view.findViewById(R.id.movie_rating_stars);
            movie_rating_ratio = (TextView) view.findViewById(R.id.vote_ratio);
            movie_poster = (ImageView) view.findViewById(R.id.poster_image);
            movie_overview = (TextView) view.findViewById(R.id.movie_overview);
            movie_backdrop = (ImageView) view.findViewById(R.id.backdrop_image);
            movie_release_date = (TextView) view.findViewById(R.id.movie_release_date);
            movie_original_title = (TextView) view.findViewById(R.id.movie_original_name);
            video_button = (ImageButton) view.findViewById(R.id.video_button);
        }
    }
}
