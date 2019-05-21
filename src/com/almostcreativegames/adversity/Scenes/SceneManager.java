package com.almostcreativegames.adversity.Scenes;

/**
 * Manages the different scenes in our game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class SceneManager {
    private Scene[][] scenes = new Scene[10][10];

    public SceneManager(){
        scenes[0][0] = new Scene();
        scenes[0][1] = new Scene();
    }

    public Scene getScene(int row, int col){
        try {
            return scenes[row][col];
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
