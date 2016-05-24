package org.ollide.stpauliforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ollide.stpauliforum.model.Forum;
import org.ollide.stpauliforum.ui.ForumActivity;
import org.ollide.stpauliforum.ui.adapter.DividerItemDecoration;
import org.ollide.stpauliforum.ui.adapter.ForumAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ForumAdapter.OnItemClickListener {

    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((ForumApp) getApplicationContext()).getComponent().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        final ForumAdapter adapter = new ForumAdapter();
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onItemClick(View view, Forum forum) {
        Intent in = ForumActivity.startWithForumIntent(MainActivity.this, forum);
        startActivity(in);
    }
}
