package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class ConveyorHelper extends Entity {

    {
        setName("Conveyor Helper");
        setImage(new Image("Entities/Conveyor Helper.png", 80, 0, true, true));
        setPosition(650, 800);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 3)
        showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 3) {
            if (getGame().hasAttribute("Spoken to Conveyor Helper"))
                startDialog(new Dialog("Oh what's wrong?", "You need me to turn off the \nmixing bowl?", "Sorry bud, but it doesn't \nturn off!"){
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Spoken to Conveyor Helper");
                        hideInderactIndicator();
                    }
                });
            else {
                startDialog(new Dialog("Hey you the new guy?", "Well this is the conveyor \nbelt you gotta fix!"));
                getGame().addAttribute("Spoken to Conveyor Helper");
            }
        } else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }

}
