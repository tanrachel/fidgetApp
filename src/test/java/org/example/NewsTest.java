package org.example;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewsTest extends TestCase {
    public void testUnmarshallJson() {
        News testNews = new News("testApiKey");
        String jsonFilePath = "src/test/java/org/example/resources/newsAPIResponse.json";
        String jsonFile = "";

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));
            jsonFile = new String(bytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testNews.unmarshallJson(jsonFile);
        String expectedResult = "title: typeOfContent: text Content: Colorado’s Supreme Court this week had the opportunity to hand down a historic judgment on the constitutionality of “reverse keyword search warrants,” a powerful new surveillance technique that grants law enforcement the ability to identify potential criminal… title: 'Invasive' Google Keyword Search Warrants Get Court Greenlight, Here's Everything You Need to Know url: https://gizmodo.com/reverse-keyword-search-warrants-explainer-colorado-1850945867 ";
        assertEquals(expectedResult,testNews.toString());

    }

}