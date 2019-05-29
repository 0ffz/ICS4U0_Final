package com.almostcreativegames.adversity.Input;

import javafx.scene.Scene;

import java.util.ArrayList;

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

    public static boolean isKeyPressed(String key){
        return input.contains(key);
    }
}
