package org.example;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Bored implements ContentObject{
    private String title;
    private String url;
    private HashMap<String, String> headers;
    private List<Content> listOfBoredPost = new ArrayList<>();

    @Override
    public String toString() {
        return "title: " + title;
    }
    public List<Content> unmarshallJson(String boredResponse) {
        System.out.println(boredResponse);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BoredPost[] boredJson = objectMapper.readValue(boredResponse, BoredPost[].class);
            for (int i = 0; i < boredJson.length; i++) {
                Content boredContent = new Content("string", boredJson[i].getFact(), "", "");
                this.listOfBoredPost.add(boredContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfBoredPost;
    }
    public Bored(String boredApiKey) {
        this.url = "https://api.api-ninjas.com/v1/facts?limit=25";
        this.headers = new HashMap<String, String>();
        this.headers.put("X-Api-Key", boredApiKey);
        this.headers.put("accept", "application/json");
    }

    public String getAPIUrl() {
        return this.url;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public Content getContent() {
        if (listOfBoredPost.size() > 1){
            Content currentPost = listOfBoredPost.get(0);
            listOfBoredPost.remove(0);
            return currentPost;
        }else{
            return listOfBoredPost.get(0);
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BoredPost{
    @JsonProperty("fact")
    private String fact;

    public String getFact() {
        return this.fact;
    }
}