package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;

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

    public String getMessage(Room room, Entity player) {
        for (Entity entities : room.getEntities()) {
            if (entities.intersects(player) && entities.getDialog() != null)
                return entities.getDialog();
        }
        return "I should be doing something.";
    }
}
