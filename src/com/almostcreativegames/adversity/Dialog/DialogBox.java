package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class DialogBox extends Entity {

    private Dialog dialog;
    private Player player;

    public DialogBox(int layer) {
        super(layer);
        dialog = new Dialog();
        dialog.setResponses(Arrays.asList("Hello", "Goodbye", "I should do something else"));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);
        gc.strokeText(dialog.getMessage(getRoom(), player), getX() + 20, getY() + 40);
    }
}
