package com.example.ahmad.popularmovies_final.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Ahmad on 10/10/2015.
 */
public class AuthenticatorService extends Service {


    private Authenticator mAuthenticator = null;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
