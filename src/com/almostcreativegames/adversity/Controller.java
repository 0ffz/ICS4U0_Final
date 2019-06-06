package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Saves.Save;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class listens to button presses. It checks for saved game when initialized. When start game button pressed,
 * closes menu and opens game window. Closes window when exit button pressed.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Checks for saved game when initialized. When start game button pressed, closes menu and opens game
 * window. Closes window when exit button pressed</p>
 * <p>0.1.2 - Removed print message</p>
 */

public class Controller {
    public Button newGameButton;
    public Button gameButton;
    public Button exit;

    public void initialize(){
        if(!Save.saveExists())
            gameButton.setText("New Game");

//        Save.saveGame(1, 10);
    }

    /**
     *
     * @param e
     */
    public void startGame(ActionEvent e){
        Stage stage = new Stage();
        GameRunner game = new GameRunner(true);
        game.start(stage);
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }

    public void newGame(ActionEvent e){
        Stage stage = new Stage();
        GameRunner game = new GameRunner(false);
        game.start(stage);
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }

    /**
     *
     * @param e
     */
    public void exit(ActionEvent e){
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }
}
