package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.*;

/**
 * A class for determining the output of the dialog
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.1.1
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Added basic premise on how to return dialog</p>
 */

public class Dialog {
    private String message;
    private Entity player;

    public Dialog(Entity e){
        player = e;
    }
    private void determineMessage(){
        if (player.getX() == 10 && player.getY() == 20)
            message = "Oh no!";
        else
            message = "I wonder what to do!";
    }

    public String getMessage(){
        determineMessage();
        return message;
    }
}
