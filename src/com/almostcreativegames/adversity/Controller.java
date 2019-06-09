package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Saves.Save;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class listens to button presses. It checks for saved game when initialized. When start game button pressed,
 * closes menu and opens game window. Closes window when exit button pressed.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Checks for saved game when initialized. When start game button pressed, closes menu and opens game
 * window. Closes window when exit button pressed</p>
 * <p>0.1.2 - Removed print message</p>
 * <p>1.2.3 - Set fonts to buttons on screen.
 * Made continue game and new game buttons</p>
 */

public class Controller {
    public Button newGameButton;
    public Button continueGameButton;
    public Button exit;
    public Text title;

    /**
     * Disables the continue game button if there is no saved game
     */
    public void initialize() {
        if (!Save.saveExists())
            continueGameButton.setDisable(true);
        title.setFont(Fonts.HOME_SCREEN);
        title.setText("Don't get hurt,\nstay at work");
        continueGameButton.setFont(Fonts.NORMAL);
        newGameButton.setFont(Fonts.NORMAL);
        exit.setFont(Fonts.NORMAL);
    }

    /**
     * Gets called when the continue game button is pressed, plays the game and loads a save file
     *
     * @param e the button press event
     */
    public void startGame(ActionEvent e) {
        Stage stage = new Stage();
        GameRunner game = new GameRunner(true);
        game.start(stage);
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
    }

    /**
     * Gets called when the new game button is pressed, starts a new game, not reading the save file
     *
     * @param e the button press event
     */
    public void newGame(ActionEvent e) {
        Stage stage = new Stage();
        GameRunner game = new GameRunner(false);
        game.start(stage);
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
    }

    /**
     * The method that gets called when the exit button is pressed
     *
     * @param e the button press event
     */
    public void exit(ActionEvent e) {
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
    }
}
