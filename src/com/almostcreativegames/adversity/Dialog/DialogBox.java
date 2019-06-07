package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class for the dialog box entities
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang & Daniel Voznyy
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 -
 * Enfei: DialogBox class created and dialog system added
 * Daniel: nextMessage() added</p>
 * <p>1.3.2 -
 * Daniel: Fixed error with dialog calling next message one time after it had already run out of messages</p>
 */
public class DialogBox extends Button {
    private Dialog dialog;

    /**
     * Creates a dialog box with black text using the NORMAL font, on layer 10
     */
    public DialogBox() {
        super("");
        setLayer(10);
        setFont(Fonts.NORMAL);
        fillColor = Color.BLACK;
    }

    /**
     * @param dialog the dialog to be displayed in the box
     */
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        nextMessage();
    }

    /**
     * @return whether the box has a dialog to display
     */
    public boolean hasDialog() {
        return dialog != null;
    }

    /**
     * Plays the next message from the current dialog. If the dialog doesn't have a message, sets it to null
     */
    public void nextMessage() {
        Dialog previousDialog = dialog;
        text = dialog.nextMessage();
        if (text == null) {
            //if the dialogs were changed while we were getting the next message (i.e. a dialog interaction has launched another dialog)
            if (!previousDialog.equals(dialog))
                text = dialog.getCurrentMessage(); //switch over text to that dialog so it's not null
            else
                dialog = null;
        }
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        if (text == null) {
            hide();
            room.getGame().getCurrentPlayer().setCanMove(true);
            dialog = null;
            return;
        }

        super.render(gc, time);
    }
}