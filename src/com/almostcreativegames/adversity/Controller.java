package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Saves.Save;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class listens to button presses.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class Controller {
    public Button gameButton;
    public Button exit;

    public void initialize(){
        if(!Save.saveExists())
            gameButton.setText("New Game");

//        Save.saveGame(1, 10);
    }

    public void startGame(ActionEvent e){
        System.out.println("Hello");
        Stage stage = new Stage();
        GameRunner game = new GameRunner();
        game.start(stage);
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }

    public void exit(ActionEvent e){
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }
}
