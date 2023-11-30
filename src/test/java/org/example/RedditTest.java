package org.example;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import java.nio.file.Files;
import java.nio.file.Paths;
public class RedditTest extends TestCase {

    public void testUnmarshallJson() {
        Reddit testReddit = new Reddit();
        String jsonFilePath = "src/test/java/org/example/resources/redditAPIResponse.json";
        String jsonFile = "";

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));
            jsonFile = new String(bytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testReddit.unmarshallJson(jsonFile);
        String expectedResult = "title: typeOfContent: video Content:  title: The mic was in picture! url: https://v.redd.it/itdbrijgkzyb1/DASH_720.mp4?source=fallback\n" +
                "title: typeOfContent: image Content:  title: Guy in my city thinks he's got the new traffic cameras cracked url: https://i.redd.it/p80ta4hwkzyb1.jpg\n";
        assertEquals(expectedResult,testReddit.toString());

    }
}