package org.example;
import javafx.scene.Scene;
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
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;


public class View extends JFrame {
    private JFrame mainFrame;
    private JComboBox choiceBox;
    private JComboBox weatherChoiceBox;
    private JPanel contentPanel;
    private JPanel weatherPanel;
    private JButton newsRefreshButton;
    private JButton redditRefreshButton;
    private JButton boredRefreshButton;
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

    public String getWeatherLocation(){
        String selectedOption = (String) this.weatherChoiceBox.getSelectedItem();
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
    private JPanel weatherChoicePanel() {
        // Spinner to determine how many input panels to generate
        JPanel weatherChoicePanel = new JPanel();
        String[] weatherLocation = {"Seattle","Los Angeles", "New York"};
        this.weatherChoiceBox = new JComboBox<>(weatherLocation);
        JLabel comboBoxTitle = new JLabel("LOCATION: ");
//        JLabel comboBoxChoice = new JLabel(comboBox.getActionCommand());
        weatherChoicePanel.add(comboBoxTitle);
        weatherChoicePanel.add(this.weatherChoiceBox);

        weatherChoicePanel.setPreferredSize(new Dimension(180,50));
//        choicePanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        return weatherChoicePanel;
    }
    public void displayWeather(ContentObject weather) {
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }

        Content weatherContent = weather.getContent();
        JPanel weatherChoicePanel = weatherChoicePanel();
        this.contentPanel.add(weatherChoicePanel);

        JPanel weatherTempPanel = new JPanel();
        this.weatherPanel = weatherTempPanel;

//        weatherTempPanel.add(Box.createVerticalGlue());
        weatherTempPanel.setLayout(new BoxLayout(weatherTempPanel, BoxLayout.Y_AXIS));

        try {
            // Replace "your_image_url_here" with the actual image URL
            URL imageUrl = new URL(weatherContent.getIcon());
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            weatherTempPanel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Temperature Panel
        JPanel tempPanel = new JPanel(new GridLayout(2,4,10,10));

        tempPanel.add(new JPanel());
        JLabel tempLabel = new JLabel("Temperature: ");
        tempLabel.setHorizontalAlignment(JLabel.RIGHT);
        tempPanel.add(tempLabel);
        tempPanel.add(new JTextField(weatherContent.getTemp()));
        tempPanel.add(new JPanel());

        tempPanel.add(new JPanel());
        JLabel feelsLikeLabel = new JLabel("Feels Like: ");
        feelsLikeLabel.setHorizontalAlignment(JLabel.RIGHT);
        tempPanel.add(feelsLikeLabel);
        tempPanel.add(new JTextField(weatherContent.getFeelsLike()));
        tempPanel.add(new JPanel());

        weatherTempPanel.add(tempPanel);
        weatherTempPanel.add(Box.createVerticalGlue());

        this.contentPanel.add(weatherPanel);
        refreshPage();
    }

    public void refreshWeatherPage(Weather weather){

        this.weatherPanel.removeAll();
        try {
            URL imageUrl = new URL(weather.getWeatherImageUrl());
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            this.weatherPanel.add(imageLabel);
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

        this.weatherPanel.add(tempPanel);
        this.weatherPanel.add(Box.createVerticalGlue());
        this.weatherPanel.revalidate();
        this.weatherPanel.repaint();
        refreshPage();
    }

    private void refreshPage(){
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }
    public void displayNews(ContentObject news) {
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }

        JPanel newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        Content currentNews = news.getContent();

        JTextArea textArea = new JTextArea(currentNews.getTitle()+"\n\n");
        textArea.append(currentNews.getContentDescription()+"\n\n");
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

    private void initFX(Content video) {
        Media media = new Media(video.getUrl());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        if(jfxPanel.getScene()!= null){
            redditVideoViewer.getMediaPlayer().stop();
        }

        redditVideoViewer.setMediaPlayer(mediaPlayer);

        double widthscale = (500 / video.getMediaWidth());
        double heightscale = (500 / video.getMediaHeight());
        redditVideoViewer.setFitWidth(video.getMediaWidth()*widthscale);
        redditVideoViewer.setFitHeight(video.getMediaHeight()*heightscale);

        StackPane root = new StackPane(redditVideoViewer);
        Scene scene = new Scene(root, 500, 500);

        jfxPanel.setScene(scene);
        mediaPlayer.play();
    }

    private JPanel displayRedditVideo(Content currentPost){

        JPanel redditPanel = new JPanel();
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
        return redditPanel;
    }

    private JPanel displayRedditImage(Content currentPost){
        JPanel redditPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        redditPanel.setLayout(new BoxLayout(redditPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(currentPost.getTitle());
        redditPanel.add(title);
        JLabel label = new JLabel();

        try {
            URL imageUrl = new URL(currentPost.getUrl());
            Image image = ImageIO.read(imageUrl);

            image = image.getScaledInstance(400,400 , Image.SCALE_SMOOTH);

            label.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        redditPanel.add(label);

        return redditPanel;
    }

    public void displayReddit(ContentObject reddit) {
        JPanel redditPanel;
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }
        Content currentPost = reddit.getContent();
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
    public void registerWeatherDynamicController(Controller controller) {
        this.weatherChoiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformedForWeatherComboBox(e);
            }
        });
    }

    public void displayBored(Bored bored) {
        if (this.contentPanel != null) {
            this.contentPanel.removeAll();
        }

        JPanel boredPannel = new JPanel();
        boredPannel.setLayout(new BoxLayout(boredPannel, BoxLayout.Y_AXIS));
        Content currentBored = bored.getContent();

        JTextArea textArea = new JTextArea(currentBored.getContentDescription());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500,400));
        boredPannel.add(scrollPane);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        this.boredRefreshButton = new JButton("Next");
        buttonPanel.add(this.boredRefreshButton);

        // Add the button panel to the main newsPanel
        boredPannel.add(buttonPanel);

        this.contentPanel.add(boredPannel);
        refreshPage();
    }

    public void registerBoredDynamicController(Controller controller) {
        this.boredRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformedForBoredRefresh(e);
            }
        });
    }
}

