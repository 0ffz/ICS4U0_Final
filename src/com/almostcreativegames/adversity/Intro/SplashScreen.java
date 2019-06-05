package com.almostcreativegames.adversity.Intro;


import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.*;

public class SplashScreen {

    /**
     * {@inheritDoc}
     */
    public void introScene() {
        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();

        body.getChildren().add(imageView);

        KeyFrame frame1 = new KeyFrame(Duration.seconds(0), new KeyValue(imageView.imageProperty(), new Image("Entities/Boss.png")), new KeyValue(imageView.opacityProperty(), 0.0));
        KeyFrame fadein1 = new KeyFrame(Duration.seconds(1), new KeyValue(imageView.opacityProperty(), 1.0));
        KeyFrame fadeout1 = new KeyFrame(Duration.seconds(1.5), new KeyValue(imageView.opacityProperty(), 0.0));
        KeyFrame frame2 = new KeyFrame(Duration.seconds(1.5), new KeyValue(imageView.imageProperty(), new Image("Entities/Mom.png")));
        KeyFrame fadein2 = new KeyFrame(Duration.seconds(2.5), new KeyValue(imageView.opacityProperty(), 1.0));
        KeyFrame fadeout2 = new KeyFrame(Duration.seconds(3), new KeyValue(imageView.opacityProperty(), 0.0));
        Timeline timeline = new Timeline(frame1, fadein1, fadeout1, frame2, fadein2, fadeout2);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        timeline.play();
    }
}
