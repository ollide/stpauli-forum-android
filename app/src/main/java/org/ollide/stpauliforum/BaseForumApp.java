package org.ollide.stpauliforum;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import org.ollide.stpauliforum.api.ApiModule;
import org.ollide.stpauliforum.di.AndroidModule;
import org.ollide.stpauliforum.di.DaggerForumComponent;
import org.ollide.stpauliforum.di.ForumComponent;

public abstract class BaseForumApp extends Application {

    private static BaseForumApp instance;
    private ForumComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        init();

        instance = this;

        component = DaggerComponentInitializer.init();

        onAfterInjection();
    }

    public static ForumComponent component() {
        return instance.component;
    }

    protected abstract void onAfterInjection();

    protected abstract void init();

    public static BaseForumApp getInstance() {
        return instance;
    }

    public ForumComponent getComponent() {
        return component;
    }

    public final static class DaggerComponentInitializer {

        public static ForumComponent init() {
            return DaggerForumComponent.builder()
                    .androidModule(new AndroidModule())
                    .apiModule(new ApiModule())
                    .build();
        }
    }
}
