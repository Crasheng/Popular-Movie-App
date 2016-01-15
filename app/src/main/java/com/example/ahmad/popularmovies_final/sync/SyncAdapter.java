package com.example.ahmad.popularmovies_final.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by crasheng on 25/12/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    Context context;
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }


    //run in background thread already.
    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult) {
        //Things you should implement yourself..SyncAdapter does not automate it.
        //Connecting to a server
        //Downloading and uploading data
        //Handling data conflicts or determining how current the data is
        //Clean up. -- close connections to a server and clean up temp files and caches

        /*should call this from main thread not worker thread!.*/
        //Toast.makeText(getContext().getApplicationContext(),"sync", Toast.LENGTH_SHORT).show();

        //log
        Log.d("sync", "onPerformSync");
    }

}
