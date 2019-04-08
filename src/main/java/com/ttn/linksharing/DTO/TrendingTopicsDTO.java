package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Topic;

import java.util.List;

public class TrendingTopicsDTO {
    List<Topic> trendingTopics;

    public List<Topic> getTrendingTopics() {
        return trendingTopics;
    }

    public void setTrendingTopics(List<Topic> trendingTopics) {
        this.trendingTopics = trendingTopics;
    }
}
