package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Reddit implements JSONObjects {
//    RedditListing redditListing;
    List<RedditObject> redditPostList = new ArrayList<>();
    public List<RedditObject> getRedditListing(){
        return this.redditPostList;
    }
    /**
     * Makes a GET request to the Reddit API and returns the response as a String.
     *
     * @param url The URL of the Reddit API endpoint to make the request to.
     * @return Returns the response from the Reddit API as a String.
     * @throws IOException if there is an error making the request or reading the response.
     */

    public static String makeAPIRequest(String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create a URL object from the provided URL string
            URL apiURL = new URL(url);

            // Open a connection to the Reddit API
            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the Reddit API
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // Close the connection and reader resources
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }

        return response.toString();
    }

    @Override
    public void unmarshallJson(String rawJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RedditListing redditListing = objectMapper.readValue(rawJson, RedditListing.class);
            List<RedditPost> redditPosts = redditListing.getData().getChildren();

            for (RedditPost post : redditPosts) {
                RedditPostData postData = post.getData();
                String title = postData.getTitle();
                boolean isVideo = postData.getIsVideo();
                if (isVideo){
                    SecureMedia secureMedia = postData.getSecureMedia();
                    if (secureMedia != null) {
                        RedditVideo redditVideo = secureMedia.getRedditVideo();
                        if (redditVideo != null) {
                            String fallbackUrl = redditVideo.getFallbackUrl();
                            RedditObject redditObject = new RedditObject(title, fallbackUrl);
                            redditPostList.add(redditObject);

                        }
                    }
                }else{
                    String imageURL = postData.getDestUrl();
                    RedditObject redditObject = new RedditObject(title, imageURL);
                    redditPostList.add(redditObject);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (RedditObject item : redditPostList) {
            sb.append("title: "+item.getRedditPostTitle() + " url: "+ item.getRedditPostURL()+"\n");
        }
        return sb.toString();
    }
}


@JsonIgnoreProperties(ignoreUnknown = true)
class RedditListing {
    @JsonProperty("data")
    private RedditData data;

    public RedditData getData() {
        return data;
    }

    public void setData(RedditData data) {
        this.data = data;
    }
}
class RedditObject{
    private String title;
    private String media_url;

    public String getRedditPostTitle(){
        return this.title;
    }
    public String getRedditPostURL(){
        return this.media_url;
    }
    public RedditObject(String title, String url){
        this.title = title;
        this.media_url = url;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RedditData {
    @JsonProperty("children")
    private List<RedditPost> children;

    public List<RedditPost> getChildren() {
        return children;
    }

    public void setChildren(List<RedditPost> children) {
        this.children = children;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RedditPost {
    @JsonProperty("data")
    private RedditPostData data;

    public RedditPostData getData() {
        return data;
    }

    public void setData(RedditPostData data) {
        this.data = data;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RedditPostData {
    @JsonProperty("title")
    private String title;
    @JsonProperty("is_video")
    private boolean isVideo;
    @JsonProperty("url_overridden_by_dest")
    private String dest_url;
    @JsonProperty("secure_media")
    private SecureMedia secureMedia;

    public String getTitle() {
        return title;
    }
    public String getDestUrl() {
        return dest_url;
    }
    public boolean getIsVideo() {
        return isVideo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SecureMedia getSecureMedia() {
        return secureMedia;
    }

    public void setSecureMedia(SecureMedia secureMedia) {
        this.secureMedia = secureMedia;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SecureMedia {
    @JsonProperty("reddit_video")
    private RedditVideo redditVideo;

    public RedditVideo getRedditVideo() {
        return redditVideo;
    }

    public void setRedditVideo(RedditVideo redditVideo) {
        this.redditVideo = redditVideo;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RedditVideo {
    @JsonProperty("fallback_url")
    private String fallbackUrl;

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }
}
