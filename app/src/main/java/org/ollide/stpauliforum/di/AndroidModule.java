package org.ollide.stpauliforum.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.ollide.stpauliforum.BaseForumApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    Context provideAppContext() {
        return BaseForumApp.getInstance().getApplicationContext();
    }

    @Provides
    SharedPreferences providePreferenceManager(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
