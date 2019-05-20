package com.almostcreativegames.adversity.Scenes;

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
