package org.ollide.stpauliforum.model;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.ollide.stpauliforum.model.xml.TopicXml;

import timber.log.Timber;

public class Topic {

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");

    private int id;
    private String name;
    private String snippet;

    private int forumId;
    private String forumName;

    private int replies;
    private User author;
    private int views;

    private LocalDateTime lastReplyDate;
    private User lastReplyUser;
    private int lastPostId;

    public static Topic fromXml(TopicXml topicXml) {
        Topic t = new Topic();

        String lastPostLink = topicXml.getLink();
        String idTxt = lastPostLink.substring(
                lastPostLink.lastIndexOf("#") + 1, lastPostLink.length());
        try {
            t.setLastPostId(Integer.parseInt(idTxt));
        } catch (NumberFormatException e) {
            Timber.i("couldn't parse last post's id from link %s", lastPostLink);
        }

        String[] split = topicXml.getTitle().split(";");
        String date = split[0];
        String forumName = split[1];
        String topicName = split[2];

        t.setName(topicName);
        t.setForumName(forumName);

        String description = topicXml.getDescription();
        if (description != null) {
            t.setSnippet(description.trim().replace("\n", " ").replaceAll(" +", " "));
        }

        t.setLastReplyDate(FORMATTER.parseLocalDateTime(date));

        return t;
    }

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

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
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
