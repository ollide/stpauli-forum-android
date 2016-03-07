package org.ollide.stpauliforum.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class TopicXmlList {

    @Element
    private Channel channel;

    @ElementList(inline = true, entry = "item")
    private List<TopicXml> topics;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<TopicXml> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicXml> topics) {
        this.topics = topics;
    }
}
