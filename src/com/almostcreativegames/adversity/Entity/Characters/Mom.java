package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


/**
 * A class for the Mom entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Mom entity moved from RoomManager to it's own class</p>
 */
public class Mom extends Entity {
    private boolean talkedTo = false;

    {
        setName("Mom");
        setImage(new Image("Entities/Mom.png", 80, 0, true, true));
        setPosition(300, 715);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 0 && !talkedTo)
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        hideInderactIndicator();
        if(getGame().hasAttribute("Job done"))
            startDialog(new Dialog("Welcome home honey.",
                    "You look pretty tired.\nWhy don't you go get some\nrest.",
                    "I'll see you tomorrow!"));
        else if (getGame().getDay() == 0) {
            talkedTo = true;
            startDialog(new Dialog("I'm so proud of you honey!", "You're so grown up now!", "Soon you'll be going to \nuniversity and now you even \nhave a job!", "Anyways you should be going \nto work now honey.", "Don't wanna be late on your \nfirst day!", "Oh and remember honey to \nalways stay safe!"));
        } else {
            startDialog(new Dialog("See you later honey.", "Remember not to get hurt!"));
        }
    }
}
