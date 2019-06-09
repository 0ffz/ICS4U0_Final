package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

/**
 * A class for the Refuse Work Info Employee entity that you can talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Refuse Work Info Employee Entity moved from RoomManager to it's own class</p>
 * <p>1.3.2 - Cleaned up te code</p>
 */
public class RefuseWorkEmployee extends Entity {

    {
        setName("Refuse Work Info");
        setImage(new Image("Entities/Worker 2.png", 80, 0, true, true));
        setPosition(790, 800);
    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Refuse")) {
            startDialog(new Dialog("Ow", "Oof", "Ow", "Oh hi there.", "Don't mind me, I'm just kinda \nhurt from my last job.", "My boss gave me a task that \nwas too dangerous, and I \ndidn't say no until it was too \nlate.", "You should know that you can \nrefuse work if it is deemed \ntoo dangerous", "Ow"));
            getGame().addAttribute("Spoken to Refuse");
        } else
            startDialog(new Dialog("Ow", "Remember you can refuse \nwork that is dangerous!", "Ow"));
    }
}
