package org.example;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BoredTest extends TestCase {
    public void testUnmarshallJson() {
        Bored testBored = new Bored("test-API");
        String jsonFilePath = "src/test/java/org/example/resources/boredAPIResponse.json";
        String jsonFile = "";
    
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));
            jsonFile = new String(bytes);
    
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testBored.unmarshallJson(jsonFile);
    
        // Update the expected result based on the expected structure of your Bored class
        String expectedResult = "After the Eiffel Tower was built, one person was killed during the installation of the lifts. No one was killed during the actual construction of the tower";
        assertEquals(expectedResult, testBored.getContent().getContentDescription());
    }

    
}
