package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class News implements JSONObjects{
    private String url;
    private List<NewsPost> listOfNewsPost;
    public News(String newsApiKey){
        this.url = "https://newsapi.org/v2/everything?q=keyword&apiKey="+newsApiKey;
    }
    public String getUrl(){
        return this.url;
    }
    @Override
    public void unmarshallJson(String rawJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NewsJson newsJson = objectMapper.readValue(rawJson, NewsJson.class);
            listOfNewsPost = newsJson.getListOfNewsPosts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (NewsPost post: listOfNewsPost){
            sb.append("title: " + post.getTitle() + " ");
            sb.append("content: "+ post.getDescription()+ " ");
            sb.append("url: "+ post.getUrl() +"\n");
        }
        return sb.toString();
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class NewsJson{
   @JsonProperty("articles")
   private List<NewsPost> listOfNewsPosts;

   public List<NewsPost> getListOfNewsPosts() {
        return listOfNewsPosts;
   }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class NewsPost{
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    @JsonProperty("urlToImage")
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
