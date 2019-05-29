package com.almostcreativegames.adversity.Input;

import javafx.scene.Scene;

import java.util.ArrayList;

/**
 * A class for listening to user input
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.2.3 - Created class which holds a static ArrayList for inputs and needs to be registered to listen to input by
 * a runner class.</p>
 */
public class InputListener {
    private static ArrayList<String> input = new ArrayList<>(); //the keys that are currently pressed

    public static void registerScene(Scene scene) {
        //track keys being pressed and released
        scene.setOnKeyPressed(e -> { //lambda used as per Intellij's suggestion
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }

    public static boolean isKeyPressed(String key) {
        return input.contains(key);
    }
    //TODO store input in HashMap along with the time at which the key was pressed, then make method to check if the desired delay for a key has been achieved
    public static boolean isKeyPressed(String key, double delay){
        return input.contains(key);
    }
}
