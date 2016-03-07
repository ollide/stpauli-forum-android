package org.ollide.stpauliforum;

import timber.log.Timber;

public class ForumApp extends BaseForumApp {

    @Override
    protected void onAfterInjection() {
    }

    @Override
    protected void init() {
        Timber.plant(new Timber.DebugTree());
    }

}
