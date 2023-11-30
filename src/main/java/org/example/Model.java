package org.example;
import java.io.*;

public class Model {
    Reddit redditClass;
    News newsClass;
    Weather weatherClass;
    Bored boredClass;

    public Model(Reddit reddit, News news, Weather weather, Bored bored){
        this.redditClass = reddit;
        this.newsClass = news;
        this.weatherClass = weather;
        this.boredClass = bored;
    }
    public WeatherObject getWeather(){
        return weatherClass.getWeather();
    }
}
