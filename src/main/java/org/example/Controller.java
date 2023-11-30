package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View v;
    private Model m;
    private HttpClient httpClient;
    public Controller(Model m, View v, HttpClient httpClient){
        this.m = m;
        this.v = v;
        this.httpClient = httpClient;
    }

    // gets values from user input and calculates weighted grades before returning it View
    @Override
    public void actionPerformed(ActionEvent e){
//        triggers reading from file and updates the GUI to reflect the content

    }

    public void actionPerformedForWeatherComboBox(ActionEvent e){
        String weatherLocation = v.getWeatherLocation();
        m.weatherClass.setLocation(weatherLocation);
        try {
            String weatherResponse = httpClient.makeAPIRequest(m.weatherClass.getUrl());
            m.weatherClass.unmarshallJson(weatherResponse);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        v.refreshWeatherPage(m.weatherClass);
    }
    public void actionPerformedForComboBox(ActionEvent e) {
        String choiceFromComboBox = v.getChoice();
        if(choiceFromComboBox.equals("weather")){
            v.displayWeather(m.weatherClass);
            v.registerWeatherDynamicController(this);
        } else if (choiceFromComboBox.equals("news")) {
            v.displayNews(m.newsClass);
            v.registerNewsDynamicController(this);

        } else if (choiceFromComboBox.equals("reddit")) {
            v.displayReddit(m.redditClass);
            v.registerRedditDynamicController(this);
        } else if (choiceFromComboBox.equals("i'm bored")) {
            v.displayBored(m.boredClass);
            v.registerBoredDynamicController(this);
        }
        v.removeInitialChoiceComboBox();
    }
    public void actionPerformedForRedditRefresh(ActionEvent e){
        v.displayReddit(m.redditClass);
        v.registerRedditDynamicController(this);


    }
    public void actionPerformedForNewsRefresh(ActionEvent e){
        v.displayNews(m.newsClass);
        v.registerNewsDynamicController(this);
    }

    public void actionPerformedForBoredRefresh(ActionEvent e){
        v.displayBored(m.boredClass);
        v.registerBoredDynamicController(this);
    }
}
