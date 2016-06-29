package org.ollide.stpauliforum.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.model.Post;

import java.util.Collections;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = Collections.emptyList();
    private OnItemClickListener clickListener;

    public PostAdapter() {
        // default constructor
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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

        // TODO
//        viewHolder.content.setText(Html.fromHtml(post.getMessage()));
        viewHolder.content.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView avatar;
        TextView author;
        TextView date;
        TextView content;

        public PostViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.authorAvatarIv);
            author = (TextView) itemView.findViewById(R.id.authorNameTv);
            date = (TextView) itemView.findViewById(R.id.publishDateTv);
            content = (TextView) itemView.findViewById(R.id.postContentTv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(v, posts.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Post post);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
