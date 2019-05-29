package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;

import java.util.ArrayList;

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
    private Room currentRoom;
    private ArrayList<String> listOfResponses = new ArrayList<String>();

    public Dialog(Entity e, Room current) {
        player = e;
        currentRoom = current;
    }

    private void determineMessage() {
        message = "I shouldn't be wasting time on this!";
        for (Entity collider : currentRoom.getIntersects(player)) {
            if (collider.getName() != null) {
                if (collider.getName().equals("Mom"))
                    message = "You should be going to work now";
            }
        }

    }

    public String getMessage() {
        determineMessage();
        return message;
    }
}
