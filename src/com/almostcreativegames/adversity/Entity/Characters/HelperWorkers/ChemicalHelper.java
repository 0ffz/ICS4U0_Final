package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class ChemicalHelper extends Entity {

    {
        setName("Chemical Helper");
        setImage(new Image("Entities/Chemical Helper.png", 80, 0, true, true));
        setPosition(60, 640);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 2)
        showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 2)
            startDialog(new Dialog("Hey you the new guy?", "Well this is the chemical \nbin you gotta clean!"){
                @Override
                public void onEnd() {
                    hideInderactIndicator();
                }
            });
        else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }
}
