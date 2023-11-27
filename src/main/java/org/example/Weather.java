package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class Weather implements JSONObjects{
    private WeatherObject weather = new WeatherObject();
    private String apiKey = "";
    private String url;
    private String location = "Seattle";
    public Weather(String apiKey){
        this.apiKey = apiKey;
        this.url = "http://api.openweathermap.org/data/2.5/weather?";
//                "q=Seattle"+ "&appid="+apiKey+ "&units=metric";

    }
    public String getUrl(){
        return this.url+"q="+location+"&appid="+apiKey+"&units=metric";
    }
    public void setLocation(String location){
        this.location = location;
    }
    public WeatherObject getWeather(){
        return this.weather;
    }
    public String getWeatherImageUrl(){
        return weather.getIcon();
    }
    public String getWeatherTemp(){
        return String.valueOf(weather.getTemp());
    }
    public String getWeatherFeelsLike(){
        return String.valueOf(weather.getFeels_like());
    }
    @Override
    public void unmarshallJson(String rawJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherJson weatherJson = objectMapper.readValue(rawJson, WeatherJson.class);
//            prep our WeatherObject for ease of use later on
            weather.setCityName(weatherJson.getCityName());
            weather.setTemp(weatherJson.getTemperature().getTemp());
            weather.setFeels_like(weatherJson.getTemperature().getFeels_like());
            weather.setIcon(weatherJson.getWeatherIcon().getIcon());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("City: " + this.weather.getCityName() + "\n");
        sb.append("Temp: " + this.weather.getTemp() + "\n");
        sb.append("Feels Like: " + this.weather.getFeels_like() + "\n");
        sb.append("Weather Icon: "+ this.weather.getIcon()+"\n");
        return sb.toString();
    }


}
class WeatherObject{
    private String cityName;
    private float temp;
    private float feels_like;
    private String icon;

    public void setCityName(String name){
        this.cityName = name;
    }
    public void setTemp(float temp){
        this.temp = temp;
    }
    public void setFeels_like(float feels_like){
        this.feels_like = feels_like;
    }
    public void setIcon(String icon){
        this.icon = "http://openweathermap.org/img/w/" + icon + ".png";

    }

    public String getIcon() {
        return icon;
    }

    public String getCityName(){
        return this.cityName;
    }
    public float getTemp(){
        return this.temp;
    }
    public float getFeels_like(){
        return this.feels_like;
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