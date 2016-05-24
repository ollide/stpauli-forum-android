package org.ollide.stpauliforum.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.ollide.stpauliforum.ForumApp;
import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.api.ForumService;
import org.ollide.stpauliforum.model.Forum;
import org.ollide.stpauliforum.model.Topic;
import org.ollide.stpauliforum.model.xml.TopicXmlList;
import org.ollide.stpauliforum.ui.adapter.DividerItemDecoration;
import org.ollide.stpauliforum.ui.adapter.TopicAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ForumActivity extends AppCompatActivity implements TopicAdapter.OnItemClickListener {

    public static final String EXTRA_FORUM_ID = "forumId";
    public static final String EXTRA_FORUM_NAME = "forumName";

    @Inject
    ForumService forumService;

    @BindView(R.id.topicRecyclerView)
    RecyclerView recyclerView;

    private int forumId;
    private String forumName;

    public static Intent startWithForumIntent(Context ctx, Forum forum) {
        Intent in = new Intent(ctx, ForumActivity.class);
        in.putExtra(EXTRA_FORUM_ID, forum.getId());
        in.putExtra(EXTRA_FORUM_NAME, forum.getName());
        return in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);

        ((ForumApp) getApplicationContext()).getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_FORUM_ID)) {
            forumId = extras.getInt(EXTRA_FORUM_ID);
            forumName = extras.getString(EXTRA_FORUM_NAME, "");
            actionBar.setTitle(forumName);
        } else {
            forumId = 4;
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final TopicAdapter adapter = new TopicAdapter();
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        forumService.getTopicsInForum(forumId, 30).enqueue(new Callback<TopicXmlList>() {
            @Override
            public void onResponse(Call<TopicXmlList> call, Response<TopicXmlList> response) {
                adapter.setTopics(response.body().getTopics());
            }

            @Override
            public void onFailure(Call<TopicXmlList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, Topic topic) {
        Timber.i("Topic '%s' clicked", topic.getName());
        Intent in = TopicActivity.startWithTopicIntent(ForumActivity.this, topic);
        startActivity(in);
    }
}
