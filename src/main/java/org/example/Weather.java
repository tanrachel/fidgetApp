package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    public static String makeAPIRequest(String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create a URL object from the provided URL string
            URL apiURL = new URL(url);

            // Open a connection to the Reddit API
            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the Reddit API
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // Close the connection and reader resources
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }

        return response.toString();
    }
    public static void main(String[] args) {
        try {
            // Make a request to the Reddit API and print the response

            String APIResponse = makeAPIRequest("http://api.openweathermap.org/data/2.5/weather?" +
                    "q=\" + LOCATION + \"&appid=\" + API_KEY + "&units =imperial");
            System.out.println(APIResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
