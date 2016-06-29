package org.ollide.stpauliforum.model;

public class PostMessage implements Message {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message == null ? "" : message;
    }
}
