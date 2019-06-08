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


    {
        setName("Mom");
        setImage(new Image("Entities/Mom.png", 80, 0, true, true));
        setPosition(300, 715);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 0)
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        //TODO remove all the Arrays.asList()
        if (getGame().getDay() == 0) {
            startDialog(new Dialog("You should be going to work \nhoney.", "Don't wanna be late on your \nfirst day!", "Oh and remember honey to \nalways stay safe!") {
                @Override
                public void onEnd() {
                    hideInderactIndicator();
                }
            });
        }
        else
            startDialog(new Dialog("See you later honey.", "Remember not to get hurt!"));
    }
}
