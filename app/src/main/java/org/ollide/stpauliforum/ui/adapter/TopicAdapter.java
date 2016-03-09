package org.ollide.stpauliforum.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.model.Topic;
import org.ollide.stpauliforum.model.xml.TopicXml;

import java.util.Collections;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<TopicXml> topics = Collections.emptyList();
    private OnItemClickListener clickListener;

    public TopicAdapter() {
    }

    public void setTopics(List<TopicXml> topics) {
        this.topics = topics;
        notifyDataSetChanged();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder viewHolder, int i) {
        Topic topic = Topic.fromXml(topics.get(i));

        viewHolder.title.setText(topic.getName());

        long lastPost = topic.getLastReplyDate().toDate().getTime();
        long now = System.currentTimeMillis();
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(lastPost, now, DateUtils.MINUTE_IN_MILLIS);
        String header = String.format("%s  -  %s", timeAgo, topic.getForumName());
        viewHolder.header.setText(header);

        String desc = topic.getSnippet();
        if (desc == null || desc.isEmpty()) {
            viewHolder.description.setVisibility(View.GONE);
        } else {
            viewHolder.description.setVisibility(View.VISIBLE);
        }
        viewHolder.description.setText(desc);
    }

    @Override
    public int getItemCount() {
        return topics == null ? 0 : topics.size();
    }

    class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView header;
        TextView title;
        TextView description;

        public TopicViewHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.header);
            title = (TextView) itemView.findViewById(R.id.textView);
            description = (TextView) itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                TopicXml topicXml = topics.get(getAdapterPosition());
                clickListener.onItemClick(v, Topic.fromXml(topicXml));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Topic topic);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
