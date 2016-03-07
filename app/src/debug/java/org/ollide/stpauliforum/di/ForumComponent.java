package org.ollide.stpauliforum.di;

import org.ollide.stpauliforum.BaseForumApp;
import org.ollide.stpauliforum.ui.ForumActivity;
import org.ollide.stpauliforum.MainActivity;
import org.ollide.stpauliforum.api.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules =  {
                AndroidModule.class,
                ApiModule.class
        }
)
public interface ForumComponent {

    void inject(BaseForumApp target);

    void inject(MainActivity target);

    void inject(ForumActivity target);
}
