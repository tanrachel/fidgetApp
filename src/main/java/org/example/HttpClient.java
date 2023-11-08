package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    /**
     * Makes a GET request to the Reddit API and returns the response as a String.
     *
     * @param url The URL of the Reddit API endpoint to make the request to.
     * @return Returns the response from the Reddit API as a String.
     * @throws IOException if there is an error making the request or reading the response.
     */
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
}
