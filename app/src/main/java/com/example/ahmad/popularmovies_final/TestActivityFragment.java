package com.example.ahmad.popularmovies_final;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ahmad.popularmovies_final.data.MoviesContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class TestActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    ReviewsAdapter radapter;
    Uri uri;

    String[] projections_of_reviews = {
            MoviesContract.ReviewsEntry.TABLE_NAME+"."+ MoviesContract.ReviewsEntry._ID,
            MoviesContract.ReviewsEntry.REV_COL_AUTHOR,
            MoviesContract.ReviewsEntry.REV_COL_CONTENT
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getActivity().getIntent().getData();
    }

    public TestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_test, container, false);
        ListView listview = (ListView) view.findViewById(R.id.listOfReviews);
        radapter = new ReviewsAdapter(getActivity(), null);
        listview.setAdapter(radapter);
        getLoaderManager().initLoader(1, savedInstanceState,this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String movie_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
        uri = MoviesContract.ReviewsEntry.buildMovieReviewsUri(movie_id);
        return new CursorLoader(getActivity(),
                uri,
                projections_of_reviews,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        radapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        radapter.swapCursor(null);
    }
}
