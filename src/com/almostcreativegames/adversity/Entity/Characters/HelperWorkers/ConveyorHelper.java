package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Objects.ConveyorBelt;
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
    private ConveyorBelt conveyorBelt;

    {
        setName("Conveyor Helper");
        setImage(new Image("Entities/Conveyor Helper.png", 80, 0, true, true));
        setPosition(650, 800);
    }

    public ConveyorHelper(ConveyorBelt conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 2 && (getGame().hasAttribute("Day 3 talked to boss") && !(getGame().hasAttribute("Spoken to conveyor helper"))))
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 2 && getGame().hasAttribute("Day 3 talked to boss")) {
            if (getGame().hasAttribute("Conveyor cleaned")) {
                startDialog(new Dialog("Nice cleaning there!"));
            } else if (getGame().hasAttribute("Conveyor Belt Off")) {
                startDialog(new Dialog("Go on back to the conveyor\nbelt, it's off now."));
            } else if (getGame().hasAttribute("Inspected conveyor belt"))
                startDialog(new Dialog("Oh what's wrong?", "You need me to turn off the \nconveyor belt?",
                        "No problem buddy I'll do it \nright now!", "3", "2", "1",
                        "Aaaand it's off!") {
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Conveyor Belt Off");
                    }
                });
            else {
                startDialog(new Dialog("Hey you the new guy?",
                        "Well this is the conveyor \nbelt you gotta clean!",
                        "Just go on one of those\nchairs!"){
                    @Override
                    public void onEnd() {
                        conveyorBelt.showInteractIndicator();
                    }
                });
                getGame().addAttribute("Spoken to conveyor helper");
            }
        } else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
        hideInderactIndicator();
    }

}
