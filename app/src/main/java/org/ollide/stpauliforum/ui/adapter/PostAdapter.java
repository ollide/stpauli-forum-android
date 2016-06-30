package org.ollide.stpauliforum.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.model.Message;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.Quote;
import org.ollide.stpauliforum.ui.widget.QuoteView;

import java.util.Collections;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = Collections.emptyList();

    private Context viewContext;

    public PostAdapter() {
        // default constructor
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder viewHolder, int i) {
        Post post = posts.get(i);

        // TODO: DI Picasso
        Picasso.with(viewHolder.avatar.getContext()).load(post.getAvatarUrl()).into(viewHolder.avatar);

        viewHolder.author.setText(post.getAuthor());
        viewHolder.date.setText(post.getPublishedAt());

        for (Message message : post.getMessages()) {
            if (message instanceof Quote) {
                QuoteView qv = new QuoteView(viewContext);
                qv.setQuote((Quote) message);
                viewHolder.content.addView(qv);
            } else {
                TextView tv = new TextView(viewContext);
                tv.setTextAppearance(viewContext, R.style.PostMessage);
                tv.setText(Html.fromHtml(message.getMessage()));
                viewHolder.content.addView(tv);
            }
        }
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView author;
        TextView date;
        LinearLayout content;

        public PostViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.authorAvatarIv);
            author = (TextView) itemView.findViewById(R.id.authorNameTv);
            date = (TextView) itemView.findViewById(R.id.publishDateTv);
            content = (LinearLayout) itemView.findViewById(R.id.postContentLayout);
        }

    }

}
