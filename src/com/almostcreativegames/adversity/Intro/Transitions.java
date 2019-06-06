package com.almostcreativegames.adversity.Intro;


import com.almostcreativegames.adversity.GameRunner;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.*;

public class SplashScreen {

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

    public static void sleepScene(Stage idk){
        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();

        body.getChildren().add(imageView);

        KeyFrame sleepScene = new KeyFrame(Duration.seconds(3), new KeyValue(imageView.imageProperty(), new Image("Rooms/Game Room.png")));
        Timeline timeline = new Timeline(sleepScene);


        timeline.play();



        BorderPane root = new BorderPane();
        root.setCenter(body);
        idk.setScene(new Scene(root));
    }
}
