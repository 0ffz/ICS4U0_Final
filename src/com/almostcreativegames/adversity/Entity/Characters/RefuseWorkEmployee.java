package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


public class RefuseWorkEmployee extends Entity {

    {
        setImage(new Image("Entities/Worker 2.png", 80, 0, true, true));
         setPosition(790, 800);
    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Refuse"))
            startDialog(new Dialog("Ow", "Oof", "Ow", "Oh hi there.", "Don't mind me, I'm just kinda \nhurt from my last job.", "My boss gave me a task that \nwas too dangerous, and I \ndidn't say no until it was too \nlate.", "You should know that you can \nrefuse work if it is deemed \ntoo dangerous", "Ow"){
                @Override
                public void onEnd() {
                    getGame().addAttribute("Spoken to Refuse");
                }
            });
        else
            startDialog(new Dialog("Ow", "Remember you can refuse \nwork that is dangerous!", "Ow"));
    }
}
