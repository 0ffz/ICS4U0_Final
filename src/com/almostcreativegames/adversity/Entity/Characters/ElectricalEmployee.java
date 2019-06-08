package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


/**
 * A class for the Electrical Employee entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Electrical Employee Entity moved from RoomManager to it's own class</p>
 */
public class ElectricalEmployee extends Entity {

    {
        setName("Electrical Employee");
        setImage(new Image("Entities/Electrician.png", 80, 0, true, true));
        setPosition(300, 390);
    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Equipment"))
            startDialog(new Dialog("Hey there you seem new.", "I remember my first day here.", "I was excited to start my job \nand get working.", "But then I had suddenly \ngotten an injury from the \nelectrical box.", "Oh shoot I'm starting to \nramble.", "We should get back to work.", "Anyways nice meeting you \nalways make sure that you \nhave the proper equipment!"){
                @Override
                public void onEnd() {
                    getGame().addAttribute("Spoken to Equipment");
                }
            });
        else
            startDialog(new Dialog("Hey there again.", "I hope you remember to \nalways use the right \nequipment!"));
    }
}
