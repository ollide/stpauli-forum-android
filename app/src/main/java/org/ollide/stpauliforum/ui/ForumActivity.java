package org.ollide.stpauliforum.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.ollide.stpauliforum.ForumApp;
import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.api.ForumService;
import org.ollide.stpauliforum.model.xml.TopicXmlList;
import org.ollide.stpauliforum.ui.adapter.DividerItemDecoration;
import org.ollide.stpauliforum.ui.adapter.TopicAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumActivity extends AppCompatActivity {

    public static final String EXTRA_FORUM_ID = "forumId";

    @Inject
    ForumService forumService;

    @Bind(R.id.forumRecyclerView)
    RecyclerView recyclerView;

//    @Bind(R.id.forumSwipeRefresh)
//    SwipeRefreshLayout swipeRefresh;

    private int forumId;

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
        actionBar.setTitle("St. Pauli Fanaktionen");

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_FORUM_ID)) {
            forumId = extras.getInt(EXTRA_FORUM_ID);
        } else {
            forumId = 4;
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final TopicAdapter adapter = new TopicAdapter();
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
}
