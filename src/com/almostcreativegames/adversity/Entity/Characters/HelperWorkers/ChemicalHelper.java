package com.almostcreativegames.adversity.Entity.Characters.HelperWorkers;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

/**
 * A class for the Chemical Helper entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Chemical Helper moved from RoomManager to it's own class</p>
 * <p>1.3.2 - Code Cleanup</p>
 */
public class ChemicalHelper extends Entity {

    {
        setName("Chemical Helper");
        setImage(new Image("Entities/Chemical Helper.png", 80, 0, true, true));
        setPosition(60, 640);
    }


    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 5)
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 5)
            startDialog(new Dialog("Hey you the new guy?", "Well this is the chemical \nbin you gotta clean!"));
        else
            startDialog(new Dialog("Hey don't bother me I'm \ndoing my job.", "You should go do your job"));
        hideInderactIndicator();

    }
}
