package org.example;

public class Model {
    ContentObject redditClass;
    ContentObject newsClass;
    ContentObject weatherClass;
    ContentObject boredClass;

    public Model(ContentObject reddit, ContentObject news, ContentObject weather, ContentObject bored){
        this.redditClass = reddit;
        this.newsClass = news;
        this.weatherClass = weather;
        this.boredClass = bored;
    }
}
