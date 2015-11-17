package com.example.ahmad.popularmovies_final.Sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by Ahmad on 10/10/2015.
 */
public class MoviesSyncAdapter extends AbstractThreadedSyncAdapter {


    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver = null;

    public MoviesSyncAdapter(Context context, boolean autoInitialize) {
        /**
         * Set up the sync adapter
         */
        super(context, autoInitialize);
                /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver  = getContext().getContentResolver();
    }

    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
    */
    public MoviesSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver  = getContext().getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }
}
