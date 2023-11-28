package org.example;

public class Bored {
    private String title;

    
    public Bored(String title) {
        this.title = title;
    }

    

    @Override
    public String toString() {
        return "title: " + title;
    }
    public void unmarshallJson(String boredResponse) {
    }
    //public Bored(String boredApiKey) {
    //}

    public String getUrl() {
        return null;
    }

}
