package com.almostcreativegames.adversity.Scenes;


import javafx.scene.image.Image;

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
