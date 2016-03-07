package org.ollide.stpauliforum.model;

import java.util.List;

public class Category implements ForumArea {

    public Category() {
        // default constructor
    }

    public Category(int id, String name, List<Forum> forums) {
        this.id = id;
        this.name = name;
        this.forums = forums;
    }

    private int id;
    private String name;
    private List<Forum> forums;

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

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }
}
