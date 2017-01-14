package com.testography.amgradle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.testography.amgradle.App;

import rx.Observable;

public class NetworkStatusChecker {
    public static Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Observable<Boolean> isInternetAvailable() {
        return Observable.just(isNetworkAvailable());
    }
}
