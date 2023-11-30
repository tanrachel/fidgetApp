package org.example;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Bored {
    private String title;
    private String url;
    private HashMap<String, String> headers;
    private ArrayList<BoredPost> listOfBoredPost; 

    @Override
    public String toString() {
        return "title: " + title;
    }
    public void unmarshallJson(String boredResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BoredPost[] boredJson = objectMapper.readValue(boredResponse, BoredPost[].class);
            this.listOfBoredPost = new ArrayList<BoredPost>();
            for (int i = 0; i < boredJson.length; i++) {
                this.listOfBoredPost.add(boredJson[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Bored(String boredApiKey) {
        this.url = "https://api.api-ninjas.com/v1/facts?limit=25";
        this.headers = new HashMap<String, String>();
        this.headers.put("X-Api-Key", boredApiKey);
        this.headers.put("accept", "application/json");
    }

    public String getUrl() {
        return this.url;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public BoredPost popOutBoredFromList() {
        BoredPost post =  this.listOfBoredPost.get(0);
        this.listOfBoredPost.remove(0);
        return post;
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