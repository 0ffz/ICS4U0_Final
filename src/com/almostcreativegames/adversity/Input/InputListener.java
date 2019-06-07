package com.almostcreativegames.adversity.Input;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for listening to user input
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.2.1
 *
 * <h2>Changelog</h2>
 * <p>1.2.1 - Created class which holds a static ArrayList for inputs and needs to be registered to listen to input by
 * a runner class. Added delay functionality directly into the isKeyPressed method</p>
 */
public class InputListener {
    private ArrayList<String> input = new ArrayList<>(); //the keys that are currently pressed
    private HashMap<String, Long> delays = new HashMap<>(); //how long ago a key was pressed

    /**
     * Registers the InputListener to listen to input from a specific scene. Can be set to work with multiple scenes.
     *
     * @param scene the scene to listen to key inputs from
     */
    public InputListener(Scene scene) {
        //track keys being pressed and released
        scene.setOnKeyPressed(e -> { //lambda used as per Intellij's suggestion
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
            delays.remove(code);
        });
    }

    /**
     * Checks if a key has been pressed
     *
     * @param key the name of the key pressed
     * @return whether it has been pressed
     */
    public boolean isKeyPressed(String key) {
        return input.contains(key);
    }

    /**
     * Checks if a key has been pressed, but not within delay milliseconds
     *
     * @param key   the name of the key pressed
     * @param delay the number of milliseconds
     * @return whether the key meets these criteria
     */
    public boolean isKeyPressed(String key, double delay) {
        if (!isKeyPressed(key))
            return false;
        if (!delays.containsKey(key))
            delays.put(key, System.currentTimeMillis() - (long) delay);
        if (System.currentTimeMillis() - delays.get(key) > delay) {
            delays.put(key, System.currentTimeMillis());
            return true;
        }
        return false;
    }
}
