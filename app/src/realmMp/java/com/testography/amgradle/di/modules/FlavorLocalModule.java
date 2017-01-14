package com.testography.amgradle.di.modules;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.testography.amgradle.data.managers.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FlavorLocalModule {
    private static final String TAG = "REALM_MP";

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Log.e(TAG, "provideRealmManager init: ");
        Stetho.initializeWithDefaults(context);
        return new RealmManager();
    }
}
