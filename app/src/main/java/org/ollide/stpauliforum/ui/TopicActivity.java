package org.ollide.stpauliforum.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.ollide.stpauliforum.ForumApp;
import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.api.TopicService;
import org.ollide.stpauliforum.model.Topic;
import org.ollide.stpauliforum.model.html.PostList;
import org.ollide.stpauliforum.ui.adapter.DividerItemDecoration;
import org.ollide.stpauliforum.ui.adapter.PostAdapter;
import org.ollide.stpauliforum.ui.widget.PagingView;
import org.ollide.stpauliforum.util.ForumUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TopicActivity extends AppCompatActivity implements PagingView.PagingListener {

    public static final String EXTRA_TOPIC_ID = "topicId";
    public static final String EXTRA_LAST_POST_ID = "lastPostId";
    public static final String EXTRA_TOPIC_NAME = "topicName";

    @Inject
    TopicService topicService;

    @BindView(R.id.postRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.pagingView)
    PagingView pagingView;

    private int topicId;
    private int lastPostId = 0;
    private String topicName = "";

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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(EXTRA_TOPIC_NAME)) {
            topicName = extras.getString(EXTRA_TOPIC_NAME);
            topicId = extras.getInt(EXTRA_TOPIC_ID, 0);
            lastPostId = extras.getInt(EXTRA_LAST_POST_ID, 0);
        } else if (intent.getData() != null) {
            Uri data = intent.getData();
            topicId = Integer.parseInt(data.getQueryParameter("t"));
            topicName = "Thema";
        } else {
            Timber.w("what am I doing here?!");
            finish();
            return;
        }
        actionBar.setTitle(topicName);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        if (topicId > 0) {
            topicService.getPostsInTopicPage(topicId, 30).enqueue(postListCallback);
        } else if (lastPostId > 0) {
            topicService.getPostsByPostId(lastPostId).enqueue(postListCallback);
        }

        pagingView.setListener(this);
    }

    Callback<PostList> postListCallback = new Callback<PostList>() {
        @Override
        public void onResponse(Call<PostList> call, Response<PostList> response) {
            Timber.d("got a response");
            PostList postList = response.body();
            if (postList != null) {
                postAdapter.setPosts(postList.getPosts());
                pagingView.setPageInfo(postList.getCurrentPage(), postList.getLastPage());
            }
        }

        @Override
        public void onFailure(Call<PostList> call, Throwable t) {
            Timber.w(t, "onFailure");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_open_in_browser:
                Intent in = new Intent(TopicActivity.this, WebViewActivity.class);
                in.putExtra("url", ForumUtils.urlShowThreadLatest(topicId));
                startActivity(in);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageRequested(int page) {
        // TODO
    }
}
