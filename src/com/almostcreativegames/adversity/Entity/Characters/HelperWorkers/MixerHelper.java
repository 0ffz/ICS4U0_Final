package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


/**
 * A class for the Mixer Helper entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Mixer Helper moved from RoomManager to it's own class</p>
 */
public class MixerHelper extends Entity {

    private boolean talkedTo;

    {
        setName("Mixer Helper");
        setImage(new Image("Entities/Mixer Helper.png", 80, 0, true, true));
        setPosition(550, 80);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 3 && !talkedTo && getGame().hasAttribute("Day 4 talked to boss")) {
            talkedTo = true;
            showInteractIndicator();
        }
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 3) {
            if (getGame().hasAttribute("Spoken to mixerHelper"))
                startDialog(new Dialog("Oh what's wrong?", "You need me to turn off the \nmixing bowl?", "Sorry bud, but it doesn't \nturn off!", "You should probably go see \nthe boss!") {
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Spoken to MixerHelper");
                        hideInderactIndicator();
                    }
                });
            else
                startDialog(new Dialog("Hey you the new guy?", "Well this is the mixing bin \nyou gotta clean!"){
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Spoken to mixerHelper");
                    }
                });
        } else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }

}
