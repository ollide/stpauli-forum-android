package org.ollide.stpauliforum.model;

import android.support.annotation.Nullable;

import org.joda.time.LocalDateTime;

public class Quote implements QuotedMessage {

    private String author;

    private String publishedAt;
    private LocalDateTime publishDate;

    private String message;

    private int depth;

    private Quote nestedQuote;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setNestedQuote(Quote nestedQuote) {
        this.nestedQuote = nestedQuote;
    }

    @Override
    @Nullable
    public Quote getNestedQuote() {
        return nestedQuote;
    }

    public int getNestedQuoteCount() {
        int count = 0;
        Quote nested = getNestedQuote();
        while (nested != null) {
            nested = nested.getNestedQuote();
            count++;
        }
        return count;
    }
}
