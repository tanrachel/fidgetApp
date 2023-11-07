package org.example;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Reddit red = new Reddit();
        try {
            // Make a request to the Reddit API and print the response

            String redditAPIResponse = red.makeAPIRequest("https://www.reddit.com/r/funny/top.json?t=day");
            red.unmarshallJson(redditAPIResponse);
            System.out.println(red);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}