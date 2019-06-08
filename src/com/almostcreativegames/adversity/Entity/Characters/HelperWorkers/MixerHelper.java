package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class MixerHelper extends Entity {

    {
        setName("Mixer Helper");
        setImage(new Image("Entities/Mixer Helper.png", 80, 0, true, true));
        setPosition(550, 80);
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 4)
        showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 4) {
            if (getGame().hasAttribute("Spoken to mixerHelper"))
                startDialog(new Dialog("Oh what's wrong?", "You need me to turn off the \nconveyor belt?", "No problem buddy I'll do it \nright now!") {
                    @Override
                    public void onEnd() {
                        getGame().addAttribute("Spoken to MixerHelper");
                        hideInderactIndicator();
                    }
                });
            else
                startDialog(new Dialog("Hey you the new guy?", "Well this is the mixing bin \nyou gotta clean!"));
        } else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
    }

}
