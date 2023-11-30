package org.example;

public class Model {
    ContentObject redditClass;
    ContentObject newsClass;
    ContentObject weatherClass;

    public Model(ContentObject reddit, ContentObject news, ContentObject weather){
        this.redditClass = reddit;
        this.newsClass = news;
        this.weatherClass = weather;
    }
}
