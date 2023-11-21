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

        HttpClient httpClient = new HttpClient();
        Reddit reddit = new Reddit();
        Weather weather = new Weather(apiKey);
        News news = new News(newsApiKey);


        try {
            // Make a request to the Reddit API and print the response

            String redditAPIResponse = httpClient.makeAPIRequest(reddit.getUrl());
            reddit.unmarshallJson(redditAPIResponse);
            String weatherResponse = httpClient.makeAPIRequest(weather.getUrl());
            weather.unmarshallJson(weatherResponse);
            String newsResponse = httpClient.makeAPIRequest(news.getUrl());
            news.unmarshallJson(newsResponse);
//            System.out.println(reddit);
//            System.out.println("----------------------------");
//            System.out.println(weather);
//            System.out.println("-----------------------------");
//            System.out.println(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model m = new Model(reddit,news,weather);
        View v = new View();
        Controller c = new Controller(m,v);
        v.registerController(c);
    }


}