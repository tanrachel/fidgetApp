package org.example;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;


public class View extends JFrame {
    private JFrame mainFrame;
    private JComboBox choiceBox;
    private JPanel contentPanel;
    private JButton newsRefreshButton;
    private JButton redditRefreshButton;
    private MediaView redditVideoViewer = new MediaView();
    private JFXPanel jfxPanel = new JFXPanel();

    View(){
        buildGUI();
    }
    private JPanel createChoicePanel() {
        // Spinner to determine how many input panels to generate
        JPanel choicePanel = new JPanel();
        String[] supportedFeatures = {"-","news", "weather", "reddit","i'm bored"};
        this.choiceBox = new JComboBox(supportedFeatures);
        JLabel comboBoxTitle = new JLabel("Select your fun: ");
//        JLabel comboBoxChoice = new JLabel(comboBox.getActionCommand());
        choicePanel.add(comboBoxTitle);
        choicePanel.add(this.choiceBox);

        choicePanel.setPreferredSize(new Dimension(180,50));
//        choicePanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        return choicePanel;
    }
    public void removeInitialChoiceComboBox(){
        this.choiceBox.removeItem("-");
    }
    public String getChoice(){
        String selectedOption = (String) this.choiceBox.getSelectedItem();
        return selectedOption;
    }


    private void buildGUI(){
        mainFrame = new JFrame("Fidgety");
        mainFrame.setPreferredSize(new Dimension(800,700));
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel choicePanel = createChoicePanel();
        mainFrame.add(choicePanel);
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.add(Box.createVerticalGlue());

        JLabel welcomeLabel = new JLabel("Welcome to Fidgety");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.contentPanel.add(welcomeLabel);
        this.contentPanel.setVisible(true);
        mainFrame.add(this.contentPanel);
        contentPanel.add(Box.createVerticalGlue());

        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayWeather(Weather weather) {
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }

        JPanel weatherPanel = new JPanel();
        weatherPanel.add(Box.createVerticalGlue());

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

        // Temperature Panel
        JPanel tempPanel = new JPanel(new GridLayout(2,4,10,10));

        tempPanel.add(new JPanel());
        JLabel tempLabel = new JLabel("Temperature: ");
        tempLabel.setHorizontalAlignment(JLabel.RIGHT);
        tempPanel.add(tempLabel);
        tempPanel.add(new JTextField(weather.getWeatherTemp()));
        tempPanel.add(new JPanel());

        tempPanel.add(new JPanel());
        JLabel feelsLikeLabel = new JLabel("Feels Like: ");
        feelsLikeLabel.setHorizontalAlignment(JLabel.RIGHT);
        tempPanel.add(feelsLikeLabel);
        tempPanel.add(new JTextField(weather.getWeatherFeelsLike()));
        tempPanel.add(new JPanel());

        weatherPanel.add(tempPanel);
        weatherPanel.add(Box.createVerticalGlue());
        this.contentPanel.add(weatherPanel);
        refreshPage();
    }

//    public void displayWeather(Weather weather) {
//        if (this.contentPanel != null) {
//            this.contentPanel.removeAll();
//        }
//
//        JPanel weatherPanel = new JPanel();
//        weatherPanel.add(Box.createVerticalGlue());
//
//        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));
//
//        try {
//            // Replace "your_image_url_here" with the actual image URL
//            URL imageUrl = new URL(weather.getWeatherImageUrl());
//            BufferedImage image = ImageIO.read(imageUrl);
//            JLabel imageLabel = new JLabel(new ImageIcon(image));
//            weatherPanel.add(imageLabel);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Temperature Panel
//        JPanel tempPanel = new JPanel();
//        tempPanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
//        tempPanel.setSize(new Dimension(80,30));
//        JLabel tempLabel = new JLabel("Temperature: ");
//        tempPanel.add(tempLabel);
//        tempPanel.add(new JTextField(weather.getWeatherTemp()));
//        weatherPanel.add(tempPanel);
//
//        // Feels Like Panel
//        JPanel feelsLikePanel = new JPanel();
//
//        feelsLikePanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
//        JLabel feelsLikeLabel = new JLabel("Feels Like: ");
//        feelsLikeLabel.setSize(new Dimension(80,30));
//
//        feelsLikePanel.add(feelsLikeLabel);
//        feelsLikePanel.add(new JTextField(weather.getWeatherFeelsLike()));
//        weatherPanel.add(feelsLikePanel);
//        weatherPanel.add(Box.createVerticalGlue());
//        this.contentPanel.add(weatherPanel);
//        refreshPage();
//    }
    private void refreshPage(){
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }
    public void displayNews(News news) {
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }

        JPanel newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        NewsPost currentNews = news.popOutNewsFromList();

        JTextArea textArea = new JTextArea(currentNews.getTitle()+"\n\n");
        textArea.append(currentNews.getDescription()+"\n\n");
        textArea.append(currentNews.getUrl());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500,400));
        newsPanel.add(scrollPane);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        this.newsRefreshButton = new JButton("Next");
        buttonPanel.add(this.newsRefreshButton);

        // Add the button panel to the main newsPanel
        newsPanel.add(buttonPanel);

        this.contentPanel.add(newsPanel);
        refreshPage();
    }
    private void resizeVideo(double width, double height) {
        Scene scene = jfxPanel.getScene();
        if (scene != null) {
            scene.getRoot().setStyle(String.format("-fx-background-color: black; -fx-background-size: %f %f;", width, height));
        }
    }

    private void initFX(RedditObject video) {
        Media media = new Media(video.getRedditPostURL());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        if(jfxPanel.getScene()!= null){
            redditVideoViewer.getMediaPlayer().stop();
        }

        redditVideoViewer.setMediaPlayer(mediaPlayer);

        double widthscale = (500 / video.getMedia_width());
        double heightscale = (500 / video.getMedia_height());
        redditVideoViewer.setFitWidth(video.getMedia_width()*widthscale);
        redditVideoViewer.setFitHeight(video.getMedia_height()*heightscale);

        StackPane root = new StackPane(redditVideoViewer);
        Scene scene = new Scene(root, 500, 500);

        jfxPanel.setScene(scene);
        mediaPlayer.play();
    }

    private JPanel displayRedditVideo(RedditObject currentPost){

        JPanel redditPanel = new JPanel();
//        redditPanel.setPreferredSize(new Dimension((int)currentPost.getMedia_width(), (int) currentPost.getMedia_height()));
        redditPanel.setLayout(new BoxLayout(redditPanel, BoxLayout.Y_AXIS));

        redditPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeVideo(redditPanel.getWidth(), redditPanel.getHeight());
            }
        });

        // Ensure initFX is called only once
        Platform.runLater(() -> {
            initFX(currentPost);});

        redditPanel.add(jfxPanel);
//        redditPanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        return redditPanel;
    }

    private JPanel displayRedditImage(RedditObject currentPost){
        JPanel redditPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        redditPanel.setLayout(new BoxLayout(redditPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(currentPost.getRedditPostTitle());
        redditPanel.add(title);
        JLabel label = new JLabel();

        try {
            URL imageUrl = new URL(currentPost.getRedditPostURL());
            Image image = ImageIO.read(imageUrl);

            image = image.getScaledInstance(400,400 , Image.SCALE_SMOOTH);

            label.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        redditPanel.add(label);

        return redditPanel;
    }

    public void displayReddit(Reddit reddit) {
        JPanel redditPanel;
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }
        RedditObject currentPost = reddit.popOutPostFromList();
        boolean isVideo = currentPost.isVideo();
        if (isVideo){
            redditPanel = displayRedditVideo(currentPost);
            System.out.println(currentPost.toString());
        }else{
            redditPanel = displayRedditImage(currentPost);
            System.out.println(currentPost.toString());

        }

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        this.redditRefreshButton = new JButton("Next");
        buttonPanel.add(redditRefreshButton);
        redditPanel.add(buttonPanel);

        this.contentPanel.add(redditPanel);
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
    public void registerRedditDynamicController(Controller controller) {
        redditRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformedForRedditRefresh(e);
            }
        });
    }
    public void registerNewsDynamicController(Controller controller) {
        newsRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformedForNewsRefresh(e);
            }
        });
    }
}


