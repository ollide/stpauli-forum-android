package org.ollide.stpauliforum.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ollide.stpauliforum.ForumApp;
import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.api.TopicService;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.Topic;
import org.ollide.stpauliforum.model.html.PostList;
import org.ollide.stpauliforum.ui.adapter.DividerItemDecoration;
import org.ollide.stpauliforum.ui.adapter.PostAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TopicActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    public static final String EXTRA_TOPIC_ID = "topicId";
    public static final String EXTRA_LAST_POST_ID = "lastPostId";
    public static final String EXTRA_TOPIC_NAME = "topicName";

    @Inject
    TopicService topicService;

    @BindView(R.id.postRecyclerView)
    RecyclerView recyclerView;

    private int topicId;
    private int lastPostId;
    private String topicName;

    private PostAdapter postAdapter;

    public static Intent startWithTopicIntent(Context ctx, Topic topic) {
        Intent in = new Intent(ctx, TopicActivity.class);
        if (topic.getId() > 0) {
            in.putExtra(EXTRA_TOPIC_ID, topic.getId());
        }
        if (topic.getLastPostId() > 0) {
            in.putExtra(EXTRA_LAST_POST_ID, topic.getLastPostId());
        }
        in.putExtra(EXTRA_TOPIC_NAME, topic.getName());
        return in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

        ((ForumApp) getApplicationContext()).getComponent().inject(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_TOPIC_NAME)) {
            topicName = extras.getString(EXTRA_TOPIC_NAME);
            topicId = extras.getInt(EXTRA_TOPIC_ID, 0);
            lastPostId = extras.getInt(EXTRA_LAST_POST_ID, 0);
            actionBar.setTitle(topicName);
        } else {
            // throw exception
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter();
        postAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(postAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        if (topicId > 0) {
            topicService.getPostsInTopicPage(topicId, 30).enqueue(postListCallback);
        } else if (lastPostId > 0) {
            topicService.getPostsByPostId(lastPostId).enqueue(postListCallback);
        }
    }

    Callback<PostList> postListCallback = new Callback<PostList>() {
        @Override
        public void onResponse(Call<PostList> call, Response<PostList> response) {
            Timber.d("got a response");
            PostList postList = response.body();
            if (postList != null) {
                postAdapter.setPosts(postList.getPosts());
            }
        }

        @Override
        public void onFailure(Call<PostList> call, Throwable t) {
            Timber.d("onFailure");
        }
    };

    @Override
    public void onItemClick(View view, Post post) {
    }
}
