package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class View extends JFrame {
    private JFrame mainFrame;
    private JComboBox choiceBox;
    private JPanel contentPanel;
    View(){
        buildGUI();
    }
    private JPanel createChoicePanel() {
        // Spinner to determine how many input panels to generate
        JPanel choicePanel = new JPanel();
        String[] supportedFeatures = {"news", "weather", "reddit"};
        this.choiceBox = new JComboBox(supportedFeatures);
        JLabel comboBoxTitle = new JLabel("Select your fun: ");
//        JLabel comboBoxChoice = new JLabel(comboBox.getActionCommand());
        choicePanel.add(comboBoxTitle);
        choicePanel.add(this.choiceBox);

        choicePanel.setPreferredSize(new Dimension(180,100));
        return choicePanel;
    }
    public String getChoice(){
        String selectedOption = (String) this.choiceBox.getSelectedItem();
        return selectedOption;
    }


    private void buildGUI(){
        mainFrame = new JFrame("WeightedGrades");
        mainFrame.setPreferredSize(new Dimension(600,600));
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel choicePanel = createChoicePanel();
        mainFrame.add(choicePanel);
        contentPanel = new JPanel();
        mainFrame.add(contentPanel);

        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void displayWeather(Weather weather){
        if(this.contentPanel != null){
            this.contentPanel.removeAll();
        }
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));

        try {
            // Replace "your_image_url_here" with the actual image URL
            URL imageUrl = new URL(weather.getWeatherImageUrl());
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            weatherPanel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelComponent = new JLabel("Temperature: ");
        tempPanel.add(labelComponent);
        tempPanel.add(new JTextField(weather.getWeatherTemp()));

        JPanel feelsLikePanel = new JPanel();
        feelsLikePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel feelsLikelabelComponent = new JLabel("Feels Like: ");
        feelsLikePanel.add(feelsLikelabelComponent);
        feelsLikePanel.add(new JTextField(weather.getWeatherFeelsLike()));

        weatherPanel.add(tempPanel);
        weatherPanel.add(feelsLikePanel);
        this.contentPanel.add(weatherPanel);
        refreshPage();
    }
    private void refreshPage(){
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }
    public void displayNews(){
        if(this.contentPanel != null){
            this.contentPanel.removeAll();
        }
        refreshPage();
    }
    public void registerController(Controller controller) {
        choiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformedForComboBox(e);
            }
        });

    }
}
