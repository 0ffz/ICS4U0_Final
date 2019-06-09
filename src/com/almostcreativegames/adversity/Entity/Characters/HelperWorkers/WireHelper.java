package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

/**
 * A class for the Wire Helper entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Wire Helper moved from RoomManager to it's own class</p>
 */
public class WireHelper extends Entity {
    private boolean talkedTo;

    {
        setName("Wire Helper");
        setImage(new Image("Entities/Wire Helper.png", 80, 0, true, true));
        setPosition(50, 370);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 1 && !talkedTo && getGame().hasAttribute("Day 2 talked to boss")) {
            talkedTo = true;
            showInteractIndicator();
        }
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 1)
            startDialog(new Dialog("Hey you the new guy?",
                    "Well this is the wire you \ngotta fix!",
                    "There's some gloves in the\nstorage room down and to\nthe left.",
                    "You should go grab them.",
                    "Come back here after that\nand fix the wire") {
                @Override
                public void onEnd() {
                    hideInderactIndicator();
                    getGame().addAttribute("Talked to WireHelper");
                }
            });
        else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }
}