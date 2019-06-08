package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

public class Mom extends Entity {
    boolean talkedTo = false;

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
        //TODO remove all the Arrays.asList()
        if (getGame().getDay() == 0) {
            hideInderactIndicator();
            talkedTo = true;
            startDialog(new Dialog("You should be going to work \nhoney.", "Don't wanna be late on your \nfirst day!", "Oh and remember honey to \nalways stay safe!") {
                @Override
                public void onEnd() {
                }
            });
        } else
            startDialog(new Dialog("See you later honey.", "Remember not to get hurt!"));
    }
}
