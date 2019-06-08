package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

/**
 * A class for the Conveyor Helper entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Conveyor Helper moved from RoomManager to it's own class</p>
 */
public class ConveyorHelper extends Entity {

    {
        setName("Conveyor Helper");
        setImage(new Image("Entities/Conveyor Helper.png", 80, 0, true, true));
        setPosition(650, 800);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 2 && getGame().hasAttribute("Day 3 talked to boss") || getGame().hasAttribute("Spoken to Conveyor Helper") || getGame().hasAttribute("Conveyor half finished"))
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 2) {
            if (getGame().hasAttribute("Spoken to Conveyor Helper"))
                startDialog(new Dialog("Oh what's wrong?", "You need me to turn off the \nconveyor belt?", "No problem buddy I'll do it \nright now!") {
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Conveyor Belt Off");
                    }
                });
            else if (getGame().hasAttribute("Conveyor half finished"))
                startDialog(new Dialog("You need the next section?", "Alright turning it on", "3", "2", "1", "and off"){
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Conveyor Belt Moved");
                    }
                });
            else {
                startDialog(new Dialog("Hey you the new guy?", "Well this is the conveyor \nbelt you gotta fix!") {
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Spoken to Conveyor Helper");
                    }
                });
            }
        } else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
        hideInderactIndicator();
    }

}
