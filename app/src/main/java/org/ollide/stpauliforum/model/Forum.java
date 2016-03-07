package org.ollide.stpauliforum.model;

import java.util.List;

public class Forum implements ForumArea {

    public Forum() {
        // default constructor
    }

    public Forum(int id, String name, String description, List<Topic> topics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topics = topics;
    }

    private int id;
    private String name;
    private String description;
    private List<Topic> topics;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
