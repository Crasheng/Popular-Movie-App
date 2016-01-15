package com.example.ahmad.popularmovies_final;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ahmad.popularmovies_final.Intenet.RESTAdapter;
import com.example.ahmad.popularmovies_final.data.MoviesContract;
import com.example.ahmad.popularmovies_final.data.MoviesContract.MoviesEntry;
import com.example.ahmad.popularmovies_final.pojos.Movies.MovieResponse;

import javax.security.auth.callback.Callback;

import retrofit.Call;
import retrofit.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesStageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, Callback, FetchDataInternet.FetchedDataReady {

    //LOG AN EVENT.
    public static final String TAG = "check_populating";
    //Cursor identifier.
    static final int CUR_LOADER_ID = 0;
    //constants
    private static final String POPULAR_MOVIES = "Popular Movies";
    private static final String MOST_RATED_MOVIES = "Most Rated Movies";
    private static final String FAVOURITES = "favourites";
    private static final String MY_PREFERENCE = "my_preference";
    private static final String SORT_TYPE = "sort_type";
    private static final String DEFAULT_SORT_TYPE = "popularity.DESC";
    private static final String SORT_VOTE_TYPE = "vote_count.DESC";
    private static boolean is_there_data = true;

    //FLAG TO KNOW WHICH DEVICE WORKING RIGHT NOW.
    //private static boolean TABLET_FLAG = false;
    private static boolean INTERNET_STATUE_OPENED = true;

    //Column that you need to populate the stage screen.
    private static String[] projections = {
            MoviesEntry.TABLE_NAME + "." + MoviesEntry._ID,
            MoviesEntry.MOV_COL_ID,
            MoviesEntry.MOV_COL_POSTER
    };

    private static String arrangement_flag = null;
    private static SharedPreferences preferences;

    //Custom Cursor Adapter for movies data
    MovieCardAdapter movie_adapter_data;
    private boolean fetch_internet_flag = false;

    // The account type
    private static final String ACCOUNT_TYPE = "android.ahmedadel.pw";

    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    public MoviesStageFragment() {
    }


    //to be removed
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (isNetworkAvailable(activity)) {
            INTERNET_STATUE_OPENED = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Log.d(TAG, "on create");

        //check if there is any data before in the tables or open for first time
        //TODO
        //Decompose what this method do.
        Cursor c = getActivity().getContentResolver().query(MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (c.getCount() == 0) {
            fetch_internet_flag = true;
            askInternetForMoviesAndShowProgressBar(getActivity());
        }

        mAccount = CreateSyncAccount(getActivity());

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "on create view");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Pass the cursor null, because it does not exist yet,
        movie_adapter_data = new MovieCardAdapter(getActivity(), null);

        GridView gridview = (GridView) view.findViewById(R.id.grid_stage);
        gridview.setAdapter(movie_adapter_data);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        Log.d(TAG, "on act created");


        //check the user preferences and if has been set yet use the default (Popularity)
        //TODO
        //check its point on the list on the ui
        preferences = getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        arrangement_flag = preferences.getString(MY_PREFERENCE, DEFAULT_SORT_TYPE);
        Log.d(TAG, "Arrangment : " + getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE).getString(SORT_TYPE, "Default"));

        // Prepare the loader.
        // Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(CUR_LOADER_ID, null, this);


        //Reference to the grid view.
        GridView gridview = (GridView) getActivity().findViewById(R.id.grid_stage);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri movie_detail_uri = null;
                //Cursor never return null.
                //but this method return Object type, and you just have to cast it to
                //whatever u are predicting the adapter is using.
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {

                    //getting the clicked movie id.
                    int id_col_index = cursor.getColumnIndex(projections[1]);
                    int umovie_id = cursor.getInt(id_col_index);

                    //get the new URI constructed pointing to the clicked movie row.
                    movie_detail_uri = MoviesEntry.buildMovieWithUniIdUri(umovie_id);
                }
                //register that event happened.
                ((onMovieClick) getActivity()).movieHasBeenClicked(movie_detail_uri);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }


    //This method needs to be deprecated because now we are using Retrofit 2.0 Library.
    @Override
    public void RequestedDataReady(ContentValues[] fetched_movies, int mode) {

        if (fetched_movies != null && mode == UtilityMovieData.REQUEST_MOVIES) {
            getActivity().getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, fetched_movies);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        //must be called after these items added to the menu.
        menu.setGroupCheckable(R.id.menu_group, true, true);

        //depends on preferred movies sort
        setActivityLabel(menu);
    }


    //TODO
    //try to re factor the code and decompose and make it looks sexy.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pop_movies:
                item.setChecked(true);
                arrangement_flag = MoviesEntry.MOV_COL_POPULARITY + "." + MoviesEntry.SORT_ORDER.trim();
                preferences = getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
                preferences.edit().putString(SORT_TYPE, arrangement_flag).commit();
                getActivity().setTitle(POPULAR_MOVIES);
                //new FetchDataInternet(this, UtilityMovieData.REQUEST_MOVIES).execute(arrangement_flag);
                askInternetForMoviesAndShowProgressBar(getActivity());
                getLoaderManager().restartLoader(CUR_LOADER_ID, null, this);
                return true;

            case R.id.most_rated:
                item.setChecked(true);
                arrangement_flag = MoviesEntry.MOV_COL_VOTE_COUNTS + "." + MoviesEntry.SORT_ORDER.trim();
                preferences = getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
                preferences.edit().putString(SORT_TYPE, arrangement_flag).commit();
                getActivity().setTitle(MOST_RATED_MOVIES);
//                new FetchDataInternet(this, UtilityMovieData.REQUEST_MOVIES).execute(arrangement_flag);
                askInternetForMoviesAndShowProgressBar(getActivity());
                getLoaderManager().restartLoader(CUR_LOADER_ID, null, this);
                return true;

            case R.id.fav_movies:
                item.setChecked(true);
                arrangement_flag = FAVOURITES;
                preferences = getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
                preferences.edit().putString(SORT_TYPE, arrangement_flag).commit();
                getActivity().setTitle(FAVOURITES);
                getLoaderManager().restartLoader(CUR_LOADER_ID, null, this);
            case R.id.refresh:
                INTERNET_STATUE_OPENED = isNetworkAvailable(getActivity());
                // Pass the settings flags by inserting them in a bundle
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                /*
                 * Request the sync for the default account, authority, and
                 * manual sync settings
                 */
                ContentResolver.requestSync(mAccount,
                        getResources().getString(R.string.content_authority).toString(),
                        settingsBundle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "on create loader");

        // Log.d("prefer", "onCreateLoader " + arrangement_flag.toString());
        Uri populate_stage_uri;
        //check the user preferred sort type to decide which table you going to query.
        if (arrangement_flag.equals("popularity.DESC")) {
            populate_stage_uri = MoviesEntry.buildMovieWithSortUri(MoviesEntry.MOV_COL_POPULARITY + MoviesEntry.SORT_ORDER);
        } else if (arrangement_flag.equals(FAVOURITES)) {
            populate_stage_uri = MoviesContract.FavouriteEntry.CONTENT_URI;
        } else {
            populate_stage_uri = MoviesEntry.buildMovieWithSortUri(MoviesEntry.MOV_COL_VOTE_COUNTS + MoviesEntry.SORT_ORDER);
        }

        Log.d(TAG, "onCreateLoader  : " + populate_stage_uri.toString());
        return new CursorLoader(getActivity(), populate_stage_uri, projections,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //TODO
        //Improve this to check wither there is data in the database to load it or
        //wait until the internet connection open and re-query again.
        if (data.getCount() == 0) {
            getLoaderManager().getLoader(CUR_LOADER_ID).stopLoading();
            is_there_data = false;
            Log.d("loader p", "onLoadFinished Called!");
            getLoaderManager().restartLoader(CUR_LOADER_ID, null, this);
        }
        movie_adapter_data.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movie_adapter_data.swapCursor(null);
    }

    private boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void askInternetForMoviesAndShowProgressBar(Activity activity) {
        RESTAdapter adapter = new RESTAdapter("http://api.themoviedb.org/");
        Call<MovieResponse> movies_response = adapter.getInternetGate().getMoviesToStage(arrangement_flag, getResources().getString(R.string.api_key).toString());
        final ProgressDialog mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        Log.d(TAG, "Arrangment : " + arrangement_flag);
        Log.d(TAG, "Arrangment : " + getActivity().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE).getString(SORT_TYPE, "Default"));


        mProgressDialog.show();

        movies_response.enqueue(new retrofit.Callback<MovieResponse>() {
            @Override
            public void onResponse(Response<MovieResponse> response) {
                INTERNET_STATUE_OPENED = true;
                ContentValues[] movies_data = UtilityMovieData.makeMoviesDataBullk(response.body());
                getActivity().getContentResolver().bulkInsert(MoviesEntry.CONTENT_URI, movies_data);
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                INTERNET_STATUE_OPENED = false;
                GridView gv = (GridView) getActivity().findViewById(R.id.grid_stage);
                gv.setVisibility(View.GONE);

                TextView tv = (TextView) getActivity().findViewById(R.id.stage_text);
                tv.setVisibility(View.VISIBLE);
                tv.setText(getResources().getString(R.string.noInternet).toString());
            }
        });
    }

    //Set the label of the activity depending on the preferred user movies sort type.
    //The MoviesSta geActivity will implement this interface.
    void setActivityLabel(Menu menu) {
        MenuItem pop_mov_item = menu.findItem(R.id.pop_movies);
        MenuItem most_rated_item = menu.findItem(R.id.most_rated);
        if (pop_mov_item.isChecked())
            getActivity().setTitle(POPULAR_MOVIES);
        else if (most_rated_item.isChecked()) {
            getActivity().setTitle(MOST_RATED_MOVIES);
        }
    }

    public interface onMovieClick {
        void movieHasBeenClicked(Uri clicked_movie);
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
    return newAccount;
    }
}
