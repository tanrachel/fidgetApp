package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class Reddit implements ContentObject {
    List<Content> redditPostList = new ArrayList<>();
    private String url;
    public Reddit(){
        this.url = "https://www.reddit.com/r/funny/top.json?t=day";
    }
    public String getAPIUrl(){
        return this.url;
    }
    public Content getContent(){
        if (redditPostList.size() > 1){
            Content currentPost = redditPostList.get(0);
            redditPostList.remove(0);
            return currentPost;
        }else{
            return redditPostList.get(0);
        }
    }
    @Override
    public List<Content> unmarshallJson(String rawJson) {
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
                            if (fallbackUrl != null) {
                                Content redditObject = new Content("video",title, fallbackUrl, redditVideo.getHeight() ,redditVideo.getWidth(), postData.getIsVideo());
                                redditPostList.add(redditObject);
                            }


                        }
                    }
                }else{
                    String imageURL = postData.getDestUrl();
                    if (imageURL != null){
                        Content redditObject = new Content("image",title, imageURL,0,0, false);
                        redditPostList.add(redditObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redditPostList;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Content item : redditPostList) {
            sb.append("title: "+item.toString()+"\n");
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
    @JsonProperty("height")
    private float height;
    @JsonProperty("width")
    private float width;

    public String getFallbackUrl() {
        return fallbackUrl;
    }
    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }
}
