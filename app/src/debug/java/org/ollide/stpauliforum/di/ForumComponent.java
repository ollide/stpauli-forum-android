package org.ollide.stpauliforum.di;

import org.ollide.stpauliforum.BaseForumApp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class
        }
)
public interface ForumComponent {

    void inject(BaseForumApp target);

}
