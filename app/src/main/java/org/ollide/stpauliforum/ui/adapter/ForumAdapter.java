package org.ollide.stpauliforum.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.data.Categories;
import org.ollide.stpauliforum.model.Category;
import org.ollide.stpauliforum.model.Forum;
import org.ollide.stpauliforum.model.ForumArea;

import java.util.ArrayList;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.AreaViewHolder> {

    private static final int VIEW_TYPE_CATEGEORY = 0;
    private static final int VIEW_TYPE_FORUM = 1;

    private final List<ForumArea> areas = new ArrayList<>();
    private OnItemClickListener clickListener;

    public ForumAdapter() {
        for (Category category : Categories.ALL) {
            areas.add(category);
            areas.addAll(category.getForums());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return areas.get(position) instanceof Category ? VIEW_TYPE_CATEGEORY : VIEW_TYPE_FORUM;
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layout = (viewType == VIEW_TYPE_CATEGEORY) ? R.layout.main_item_category : R.layout.main_item_forum;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new AreaViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(AreaViewHolder viewHolder, int i) {
        ForumArea area = areas.get(i);

        viewHolder.name.setText(area.getName());

        if (area instanceof Forum) {
            Forum forum = (Forum) area;
            String desc = forum.getDescription();
            if (desc == null || desc.isEmpty()) {
                viewHolder.description.setVisibility(View.GONE);
            } else {
                viewHolder.description.setVisibility(View.VISIBLE);
            }
            viewHolder.description.setText(desc);
        }
    }

    @Override
    public int getItemCount() {
        return areas == null ? 0 : areas.size();
    }

    class AreaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        TextView description;

        public AreaViewHolder(View itemView, int viewType) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.areaName);

            if (viewType == VIEW_TYPE_FORUM) {
                description = (TextView) itemView.findViewById(R.id.forumDescription);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(v, (Forum) areas.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Forum forum);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
