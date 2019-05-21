package com.almostcreativegames.adversity.Scenes;


import javafx.scene.image.Image;

/**
 * TODO Eventually will be a map of scenes for out game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class Scene {
    private Image image = new Image("Rooms/Home.png", 1000, 1000, false, true);

    public Image getImage() {
        return image;
    }

    public Scene setImage(Image image) {
        this.image = image;
        return this;
    }
}
