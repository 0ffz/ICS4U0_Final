package com.almostcreativegames.adversity.Entity.Objects;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Equippable;
import javafx.scene.image.Image;

public class Gloves extends Entity {

    private String type;

    public Gloves(String type) {
        this.type = type;
        setName(type + " Gloves");
        if (type.equals("Chemical")) {
            setImage(new Image("Entities/Chemical Gloves.png", 50, 50, true, true));
            setPosition(200, 715);
        } else {
            setImage(new Image("Entities/Electrical Gloves.png", 50, 50, true, true));
            setPosition(200, 815);
        }
    }

    @Override
    public void onInteract() {
        Equippable gloves = new Equippable(type + " Gloves");
        getGame().getEquipment().add(gloves);
        startDialog(new Dialog("You picked up some " + type + "\ngloves",
                "Be sure to equip them when \nneeded while working!"));
        remove();
    }

    @Override
    public void onRoomLoad() {
        if (getGame().hasEquipment(type + " Gloves"))
            remove();
    }
}
