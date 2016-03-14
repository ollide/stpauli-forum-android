package org.ollide.stpauliforum.model.html;

import org.ollide.stpauliforum.model.Post;

import java.util.List;

public class PostList {

    private int topicId;
    private int topicName;

    private int currentPage;

    private List<Post> posts;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTopicName() {
        return topicName;
    }

    public void setTopicName(int topicName) {
        this.topicName = topicName;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
