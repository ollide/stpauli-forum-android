package org.ollide.stpauliforum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.ollide.stpauliforum.api.ForumService;
import org.ollide.stpauliforum.model.xml.TopicXmlList;

import javax.inject.Inject;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Inject
    ForumService forumService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ForumApp) getApplicationContext()).getComponent().inject(this);

        Call<TopicXmlList> topicsInForum = forumService.getTopicsInForum(4, 30);
    }
}
