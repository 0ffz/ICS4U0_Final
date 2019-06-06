package com.almostcreativegames.adversity.Intro;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Transitions {

    public static void introScene(Stage idk) {
        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();

        body.getChildren().add(imageView);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.imageProperty(), new Image("Logo.png")), new KeyValue(imageView.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.imageProperty(), new Image("Entities/Mom.png"))),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView.opacityProperty(), 0.0)));

        timeline.play();
        idk.show();

        BorderPane root = new BorderPane();
        root.setCenter(body);
        idk.setScene(new Scene(root));
    }

    public static void sleepScene(Stage idk) {
        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();

        body.getChildren().add(imageView);

        KeyFrame sleepScene = new KeyFrame(Duration.seconds(3), new KeyValue(imageView.imageProperty(), new Image("Rooms/Factory/Game Room.png")));
        Timeline timeline = new Timeline(sleepScene);


        timeline.play();


        BorderPane root = new BorderPane();
        root.setCenter(body);
        idk.setScene(new Scene(root));
    }
}