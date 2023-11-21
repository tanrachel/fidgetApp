package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View v;
    private Model m;
    public Controller(Model m, View v){
        this.m = m;
        this.v = v;
    }

    // gets values from user input and calculates weighted grades before returning it View
    @Override
    public void actionPerformed(ActionEvent e){
//        triggers reading from file and updates the GUI to reflect the content

    }


    public void actionPerformedForComboBox(ActionEvent e) {
        String choiceFromComboBox = v.getChoice();
        if(choiceFromComboBox.equals("weather")){
            v.displayWeather(m.weatherClass);
        } else if (choiceFromComboBox.equals("news")) {
            v.displayNews(m.newsClass);
            v.registerNewsDynamicController(this);

        } else if (choiceFromComboBox.equals("reddit")) {
            v.displayReddit(m.redditClass);
            v.registerRedditDynamicController(this);
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
}
