package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Player;
import javafx.scene.canvas.GraphicsContext;

public class DialogBox extends Entity {
    private Dialog dialog;
    private String message;

    public DialogBox(int layer) {
        super(layer);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        nextMessage();
    }

    public boolean hasDialog(){
        return dialog != null;
    }

    public void nextMessage() {
        message = dialog.nextMessage();
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);
        if (message == null) {
            hide();
            Player.getCurrentPlayer().setCanMove(true);
            dialog = null;
            return;
        }
        gc.strokeText(message, getX() + 20, getY() + 40);
    }
}