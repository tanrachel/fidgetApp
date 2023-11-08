package org.example;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("W_API_KEY");

        HttpClient httpClient = new HttpClient();
        Reddit reddit = new Reddit();
        Weather weather = new Weather(apiKey);
        try {
            // Make a request to the Reddit API and print the response

            String redditAPIResponse = httpClient.makeAPIRequest(reddit.getUrl());
            reddit.unmarshallJson(redditAPIResponse);
            String weatherResponse = httpClient.makeAPIRequest(weather.getUrl());
            weather.unmarshallJson(weatherResponse);
            System.out.println(reddit);
            System.out.println("----------------------------");
            System.out.println(weather);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}