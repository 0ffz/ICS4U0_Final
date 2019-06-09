package com.almostcreativegames.adversity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The home screen of the game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic menu</p>
 * <p>1.3.2 - Changed menu's size</p>
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main menu.fxml"));
        primaryStage.setTitle("DGHSAW launcher");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }
}
