package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Testing extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Replace the video URL with your actual video URL
        String videoUrl = "https://v.redd.it/n7urf1w5x9zb1/DASH_1080.mp4";

        // Create a Media object from the video URL
        Media media = new Media(videoUrl);

        // Create a MediaPlayer from the Media object
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Create a MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a StackPane and add the MediaView to it
        StackPane root = new StackPane(mediaView);

        // Create a Scene with the StackPane as the root
        Scene scene = new Scene(root, 640, 480);

        // Set up the stage
        primaryStage.setTitle("Video Player");
        primaryStage.setScene(scene);

        // Set the MediaPlayer to play automatically
        mediaPlayer.setAutoPlay(true);

        // Show the stage
        primaryStage.show();
    }
}



//https://v.redd.it/n7urf1w5x9zb1/DASH_1080.mp4?source=fallback