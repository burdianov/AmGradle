package com.testography.amgradle.di.modules;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.testography.amgradle.data.managers.RealmManager;
import com.testography.amgradle.utils.ConstantsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import rx.Observable;
import rx.Subscriber;

@Module
public class FlavorLocalModule {
    private static final String TAG = "REALM_MP";

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Log.e(TAG, "provideRealmManager init: ");
        Stetho.initializeWithDefaults(context);

        Observable.create(new Observable.OnSubscribe<SyncUser>() {
            @Override
            public void call(Subscriber<? super SyncUser> subscriber) {
                SyncCredentials syncCredentials = SyncCredentials
                        .usernamePassword(
                                ConstantsManager.REALM_USER,
                                ConstantsManager.REALM_PASSWORD,
                                false);
                // create observer from sync result
                if (!subscriber.isUnsubscribed()) {
                    try {
                        subscriber.onNext(SyncUser.login(syncCredentials,
                                ConstantsManager.REALM_AUTH_URL));
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }

                // create observer from callback result
                /*if (!subscriber.isUnsubscribed()) {
                    SyncUser.loginAsync(syncCredentials, ConstantsManager
                            .REALM_AUTH_URL, new SyncUser.Callback() {
                        @Override
                        public void onSuccess(SyncUser user) {
                            subscriber.onNext(user);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(ObjectServerError error) {
                            subscriber.onError(error);
                        }
                    });
                }*/
            }
        });

        return new RealmManager();
    }
}
