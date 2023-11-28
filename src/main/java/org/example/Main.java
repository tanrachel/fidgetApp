package org.example;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("W_API_KEY");
        String newsApiKey = dotenv.get("NEWS_API_KEY");
        String boredApiKey = dotenv.get("BORED_API_KEY");

        HttpClient httpClient = new HttpClient();
        Reddit reddit = new Reddit();
        Weather weather = new Weather(apiKey);
        News news = new News(newsApiKey);
        Bored bored = new Bored(boredApiKey);

        try {
            // Make a request to the Reddit API and print the response

            String redditAPIResponse = httpClient.makeAPIRequest(reddit.getUrl());
            reddit.unmarshallJson(redditAPIResponse);
            String weatherResponse = httpClient.makeAPIRequest(weather.getUrl());
            weather.unmarshallJson(weatherResponse);
            String newsResponse = httpClient.makeAPIRequest(news.getUrl());
            news.unmarshallJson(newsResponse);
            String boredResponse = httpClient.makeAPIRequest(bored.getUrl());
            bored.unmarshallJson(boredResponse);
//            System.out.println(reddit);
//            System.out.println("----------------------------");
//            System.out.println(weather);
//            System.out.println("-----------------------------");
//            System.out.println(news);
//            System.out.println("-----------------------------");
//            System.out.println(bored);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Model m = new Model(reddit,news,weather, bored);
        View v = new View();
        Controller c = new Controller(m,v);
        v.registerController(c);
    }


}