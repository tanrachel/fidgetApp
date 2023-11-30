package org.example;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WeatherTest extends TestCase {
    public void testUnmarshallJson() {
        Weather testWeather = new Weather("test-API");
        String jsonFilePath = "src/test/java/org/example/resources/weatherAPIResponse.json";
        String jsonFile = "";

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));
            jsonFile = new String(bytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testWeather.unmarshallJson(jsonFile);
        String expectedResult = "City: Seattle\n" +
                "Temp: 8.39\n" +
                "Feels Like: 6.5\n" +
                "Weather Icon: http://openweathermap.org/img/w/03d.png\n";
        String actualResult = "City: " +testWeather.getContent().getCityName()+"\n"+
                "Temp: "+ testWeather.getContent().getTemp() +"\n"+
                "Feels Like: " + testWeather.getContent().getFeelsLike()+"\n"+
                "Weather Icon: "+ testWeather.getContent().getIcon()+"\n";
        assertEquals(expectedResult,actualResult);
    }
}