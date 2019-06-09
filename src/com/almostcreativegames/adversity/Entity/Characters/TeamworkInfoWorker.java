package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

public class TeamworkInfoWorker extends Entity {

    {
        setName("Teamwork Info");
        setImage(new Image("Entities/Teamwork Info.png", 80, 0, true, true));
        setPosition(400, 400);
    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Teamwork"))
            startDialog(new Dialog("Hey you!", "You new here?", "Well, you should make sure \nto get to know everyone!", "Being on the same track as \neveryone makes the \nworkplace a safer place!") {
                @Override
                public void onEnd() {
                    getGame().addAttribute("Spoken to Teamwork");
                }
            });
        else
            startDialog(new Dialog("Hope you've gotten to know \neveryone!", "Puts everyone into a safer environment!"));
    }
}
