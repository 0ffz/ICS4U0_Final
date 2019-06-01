package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> responses = new ArrayList<>();
    private int index = 0;

    public List<String> getResponses() {
        return responses;
    }

    public Dialog setResponses(List<String> responses) {
        this.responses = responses;
        return this;
    }

    public void nextMessage() {
        index++;
    }

    public void onExit() {

    }

    public String getMessage(Room room, Entity player) {
        for (Entity collider : room.getEntities()) {
            if (collider.intersects(player))
                break;
            else {
                if (index < responses.size() - 1)
                    this.nextMessage();
                else
                    break;
            }
        }
        return responses.get(index);
    }
}
