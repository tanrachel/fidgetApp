package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;

public class News implements ContentObject {
    private String url;
    public News(String newsApiKey){
        this.url = "https://newsapi.org/v2/top-headlines?country=us&apiKey="+newsApiKey;
    }
    private List<Content> listOfNewsPost = new ArrayList<>();
    public String getAPIUrl(){
        return this.url;
    }
    public Content getContent(){
        if (listOfNewsPost.size() > 1){
            Content currentPost = listOfNewsPost.get(0);
            listOfNewsPost.remove(0);
            return currentPost;
        }else{
            return listOfNewsPost.get(0);
        }
    }
    @Override
    public List<Content> unmarshallJson(String rawJson) {
        NewsJson newsJson = new NewsJson();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            newsJson = objectMapper.readValue(rawJson, NewsJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(NewsPost post: newsJson.getListOfNewsPosts()){
            Content newsContent = new Content("text", post.getDescription(), post.getTitle(), post.getUrl());
            listOfNewsPost.add(newsContent);
        }
        return listOfNewsPost;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Content post: listOfNewsPost){
            sb.append("title: " + post.toString() + " ");
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
