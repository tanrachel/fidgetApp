package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;

public class Weather implements ContentObject {
    private List<Content> weather = new ArrayList<>();
    private String apiKey = "";
    private String url;
    private String location = "Seattle";
    public Weather(String apiKey){
        this.apiKey = apiKey;
        this.url = "http://api.openweathermap.org/data/2.5/weather?";
//                "q=Seattle"+ "&appid="+apiKey+ "&units=metric";

    }
    public String getAPIUrl(){
        return this.url+"q="+location+"&appid="+apiKey+"&units=metric";
    }
    public void setLocation(String location){
        this.location = location;
    }
    public Content getContent(){
        return weather.get(0);
    }
    public String getWeatherImageUrl(){
        Content weatherContent = weather.get(0);
        return weatherContent.Icon;
    }
    public String getWeatherTemp(){
        Content weatherContent = weather.get(0);
        return String.valueOf(weatherContent.Temp);
    }
    public String getWeatherFeelsLike(){
        Content weatherContent = weather.get(0);
        return String.valueOf(weatherContent.Feels_like);
    }
    @Override
    public List<Content> unmarshallJson(String rawJson) {
        WeatherJson weatherJson = new WeatherJson();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            weatherJson = objectMapper.readValue(rawJson, WeatherJson.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Content weatherContent = new Content("weather",
                weatherJson.getCityName(),
                weatherJson.getTemperature().getTemp(),
                weatherJson.getTemperature().getFeels_like(),
                "http://openweathermap.org/img/w/" + weatherJson.getWeatherIcon().getIcon() + ".png");
//        refresh weather list so that when making a new call, it empties the list
        weather = new ArrayList<>();
        weather.add(weatherContent);
        return weather;
    }


}

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherJson{
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("main")
    private WeatherTemperature temperature;
    @JsonProperty("weather")
    WeatherIcon[] weatherIcon;

    public WeatherIcon getWeatherIcon(){
        if (weatherIcon.length != 0){
            return weatherIcon[0];
        }
        return null;
    }
    public String getCityName() {
        return cityName;
    }

    public WeatherTemperature getTemperature() {
        return temperature;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherIcon{
    @JsonProperty("icon")
    String icon;

    public String getIcon() {
        return icon;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherTemperature{
    @JsonProperty("temp")
    private float temp;
    @JsonProperty("feels_like")
    private float feels_like;

    public float getFeels_like() {
        return feels_like;
    }
    public float getTemp(){
        return temp;
    }
}