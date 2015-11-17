package com.example.ahmad.popularmovies_final.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Define a Service that returns an IBinder for the
 * sync adapter class, allowing the sync adapter framework to call
 * onPerformSync().
 */
public class SyncService extends Service {

    // Storage for an instance of the sync adapter
    private static MoviesSyncAdapter sSyncAdapter = null;

    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        super.onCreate();
        synchronized(sSyncAdapter){
            if (sSyncAdapter == null) {
                sSyncAdapter = new MoviesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        return sSyncAdapter.getSyncAdapterBinder();
    }


}
