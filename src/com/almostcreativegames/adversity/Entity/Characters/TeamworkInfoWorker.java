package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;

public class TeamworkInfoWorker extends Entity {

    {

    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Teamwork"))
        startDialog(new Dialog("Hey you!", "You new here?", "Well, you should make sure \nto get to know everyone!", "Being on the same track as \neveryone makes the \nworkplace a safer place!"){
            @Override
            public void onEnd() {
                getGame().addAttribute("Spoken to Teamwork");
            }
        });
        else
            startDialog(new Dialog("Hope you've gotten to know \neveryone!", "Puts everyone into a safer environment!"));
    }
}
