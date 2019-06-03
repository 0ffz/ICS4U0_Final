package com.almostcreativegames.adversity.Entity.Menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Button extends Text {
    public Button(String text) {
        super(text);
        setImage(new Image("Menu/LongButton.png", 0, 100, true, true));
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        gc.drawImage(image, x, y);

        //draw text with offset based on font size so it always fits in the box
        gc.setFont(font);
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillText(text, x + font.getSize() / 2, y + font.getSize() * 1.2);
        gc.strokeText(text, x + font.getSize() / 2, y + font.getSize() * 1.2);
    }
}