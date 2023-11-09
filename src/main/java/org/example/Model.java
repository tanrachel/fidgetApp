package org.example;
import java.io.*;

public class Model {
    Reddit redditClass;
    News newsClass;
    Weather weatherClass;

    public Model(Reddit reddit, News news, Weather weather){
        this.redditClass = reddit;
        this.newsClass = news;
        this.weatherClass = weather;
    }
    public WeatherObject getWeather(){
        return weatherClass.getWeather();
    }
}
