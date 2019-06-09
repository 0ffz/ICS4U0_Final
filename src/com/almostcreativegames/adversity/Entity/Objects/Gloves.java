package com.almostcreativegames.adversity.Entity.Objects;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Equippable;
import javafx.scene.image.Image;

/**
 * A class for the Gloves entity that you have to find and collect
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Gloves moved from RoomManager to it's own class</p>
 */
public class Gloves extends Entity {

    private String type;

    /**
     * Gloves Constructor
     * @param type the type of gloves so that they can be differentiated
     */
    public Gloves(String type) {
        this.type = type;
        setName(type + " Gloves");
        if (type.equals("Chemical")) {
            setImage(new Image("Entities/Chemical Gloves.png", 50, 50, true, true));
            setPosition(200, 715);
        } else {
            setImage(new Image("Entities/Electrical Gloves.png", 50, 50, true, true));
            setPosition(200, 890);
        }
    }

    @Override
    public void onInteract() {
        hideInderactIndicator();
        Equippable gloves = new Equippable(type + " Gloves");
        getGame().addEquipment(gloves);
        startDialog(new Dialog("You picked up some " + type + "\ngloves",
                "Be sure to equip them when \nneeded while working!"));
        remove();
    }

    @Override
    public void onRoomLoad() {
        System.out.println("On room load called for " + type + " Gloves");
        if (getGame().hasEquipment(type + " Gloves")) {
            System.out.println("Removing " + type + " Gloves");
            remove();
        }
    }
}
