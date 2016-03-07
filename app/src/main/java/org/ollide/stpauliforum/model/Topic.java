package org.ollide.stpauliforum.model;

import org.joda.time.LocalDateTime;

public class Topic {

    private int id;
    private String name;

    private int forumId;

    private int replies;
    private User author;
    private int views;

    private LocalDateTime lastReplyDate;
    private User lastReplyUser;
    private int lastPostId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public LocalDateTime getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(LocalDateTime lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public User getLastReplyUser() {
        return lastReplyUser;
    }

    public void setLastReplyUser(User lastReplyUser) {
        this.lastReplyUser = lastReplyUser;
    }

    public int getLastPostId() {
        return lastPostId;
    }

    public void setLastPostId(int lastPostId) {
        this.lastPostId = lastPostId;
    }
}
