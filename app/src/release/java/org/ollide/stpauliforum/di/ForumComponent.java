package org.ollide.stpauliforum.di;

import org.ollide.stpauliforum.BaseForumApp;
import org.ollide.stpauliforum.MainActivity;
import org.ollide.stpauliforum.api.ApiModule;
import org.ollide.stpauliforum.api.LoginClient;
import org.ollide.stpauliforum.ui.ForumActivity;
import org.ollide.stpauliforum.ui.TopicActivity;
import org.ollide.stpauliforum.ui.WebViewActivity;

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

    void inject(TopicActivity target);

    void inject(LoginClient loginClient);

    void inject(WebViewActivity webViewActivity);

}
