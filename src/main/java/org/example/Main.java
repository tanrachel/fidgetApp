package org.example;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("W_API_KEY");
        String newsApiKey = dotenv.get("NEWS_API_KEY");
        String boredApiKey = dotenv.get("BORED_API_KEY");

        HttpClient httpClient = new HttpClient();
        ContentObject reddit = new Reddit();
        ContentObject weather = new Weather(apiKey);
        ContentObject news = new News(newsApiKey);
        ContentObject bored = new Bored(boredApiKey);


        try {
            // test reddit json
            String jsonFilePath = "src/test/java/org/example/resources/redditTest.json";
            String jsonFile = "";

            try {
                byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));
                jsonFile = new String(bytes);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String redditAPIResponse = httpClient.makeAPIRequest(reddit.getAPIUrl());
            reddit.unmarshallJson(redditAPIResponse);
            String weatherResponse = httpClient.makeAPIRequest(weather.getAPIUrl());
            weather.unmarshallJson(weatherResponse);
            String newsResponse = httpClient.makeAPIRequest(news.getAPIUrl());
            news.unmarshallJson(newsResponse);
            String boredResponse = httpClient.makeAPIRequestWithHeaders(bored.getAPIUrl(), ((Bored)bored).getHeaders());
            bored.unmarshallJson(boredResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Model m = new Model(reddit, news, weather,bored);
        View v = new View();
        Controller c = new Controller(m,v,httpClient);
        v.registerController(c);
    }


}