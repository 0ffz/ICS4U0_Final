package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class DialogBox extends Button {
    private Dialog dialog;

    public DialogBox() {
        super("");
        setLayer(10);
        setFont(Fonts.NORMAL);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        nextMessage();
    }

    public boolean hasDialog(){
        return dialog != null;
    }

    public void nextMessage() {
        text = dialog.nextMessage();
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        if (text == null) {
            hide();
            Player.getCurrentPlayer().setCanMove(true);
            dialog = null;
            return;
        }

        super.render(gc, time);
    }
}