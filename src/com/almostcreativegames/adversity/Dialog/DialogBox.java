package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * A class for the dialog box entities
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang & Daniel Voznyy
 * @version 0.2.1
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 -
 * Enfei: DialogBox class created and dialog system added
 * Daniel: nextMessage() added</p>
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

    public boolean hasDialog() {
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