package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public class DialogBox extends Entity {

    Dialog dialog = new Dialog(this, this.getRoom());

    public DialogBox(int layer, boolean removed){
        super(layer, removed);
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);
        gc.strokeText(dialog.getMessage(), getX() + 20, getY() + 20);
    }

}
