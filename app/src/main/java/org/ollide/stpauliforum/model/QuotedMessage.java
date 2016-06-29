package org.ollide.stpauliforum.model;

import org.joda.time.LocalDateTime;

public interface QuotedMessage extends Message {

    Quote getNestedQuote();

    String getAuthor();
    String getPublishedAt();
    LocalDateTime getPublishDate();

}
