package org.example;
import java.util.List;

//ContentObject will enforce rule for all feature objects (ie. Reddit, News, Weather, Bored) to implement mandatory methods
//Content Type will be the object that all feature objects will store their information in after unmarshalling from API endpoint
interface ContentObject {
    public String getAPIUrl();
    public List<Content> unmarshallJson(String rawJson);
    public Content getContent();
}

class Content {
    public String typeOfContent = "";
    public String contentDescription = "";
    public String title = "";
    public String url = "";
    public String CityName;
    public float Temp;
    public float Feels_like;
    public String Icon;
    public float mediaHeight;
    public float mediaWidth;
    public boolean isVideo;
    public Content(String typeOfContent, String Content, String title, String url) {
        this.typeOfContent = typeOfContent;
        this.contentDescription = Content;
        this.title = title;
        this.url = url;
    }
    public Content(String typeOfContent, String CityName, float Temp, float Feels_like, String Icon) {
        this.typeOfContent = typeOfContent;
        this.CityName = CityName;
        this.Temp = Temp;
        this.Feels_like = Feels_like;
        this.Icon = Icon;
    }
    public String toString() {
        return "typeOfContent: " + typeOfContent + " Content: " + contentDescription + " title: " + title + " url: " + url;
    }

    public String getUrl() {
        return url;
    }
    public String getTitle() {
        return title;
    }
    public String getContentDescription() {
        return contentDescription;
    }
    public String getTypeOfContent() {
        return typeOfContent;
    }
    public String getCityName() {
        return CityName;
    }
    public String getIcon() {
        return Icon;
    }

    public String getTemp() {
        return String.valueOf(Temp);
    }

    public String getFeelsLike() {
        return String.valueOf(Feels_like);
    }

    public boolean isVideo() {
        return isVideo;
    }

    public float getMediaHeight() {
        return mediaHeight;
    }

    public float getMediaWidth() {
        return mediaWidth;
    }
    public Content(String typeOfContent, String title, String url, float mediaHeight, float mediaWidth, boolean isVideo) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.url = url;
        this.mediaHeight = mediaHeight;
        this.mediaWidth = mediaWidth;
        this.isVideo = isVideo;
    }
}
