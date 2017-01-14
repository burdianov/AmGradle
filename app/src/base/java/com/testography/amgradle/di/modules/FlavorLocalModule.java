package com.testography.amgradle.di.modules;

import android.content.Context;

import com.testography.amgradle.data.managers.PreferencesManager;
import com.testography.amgradle.data.managers.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FlavorLocalModule {
    public static final String TAG = "BASE";

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Log.e(TAG, "provideRealmManager init: ");
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(RealmInspectorModulesProvider
                        .builder(context).build()).build());
        return new RealmManager();
    }
}
