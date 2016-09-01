package org.ollide.stpauliforum.model;

public class PostMessage implements Message {

    private String message;

    public PostMessage() {
        // default constructor
    }

    public PostMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message == null ? "" : message;
    }
}
