package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class WireHelper extends Entity {

    {
        setName("Wire Helper");
        setImage(new Image("Entities/Wire Helper.png", 80, 0, true, true));
        setPosition(50, 370);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 1)
        showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 1)
            startDialog(new Dialog("Hey you the new guy?", "Well this is the wire you \ngotta fix!"){
                @Override
                public void onEnd() {
                    hideInderactIndicator();
                }
            });
        else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }
}