package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class DialogBox extends Entity {
    private Dialog dialog;

    public DialogBox(int layer) {
        super(layer);
        dialog = new Dialog();
        dialog.setResponses(Arrays.asList("Hello", "Goodbye"));
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);
        gc.strokeText(dialog.getMessage(), getX() + 20, getY() + 20);
    }
}
