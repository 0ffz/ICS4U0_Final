package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class ElectricalEmployee extends Entity {

    {
        setName("Electrical Employee");
        setImage(new Image("Entities/Electrician.png", 80, 0, true, true));
        setPosition(300, 390);
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 0)
            startDialog(new Dialog("Hey there you seem new.", "I remember my first day here.", "I was excited to start my job \nand get working.", "But then I had suddenly \ngotten an injury from the \nelectrical box.", "Oh shoot I'm starting to \nramble.", "We should get back to work.", "Anyways nice meeting you \nalways make sure that you \nhave the proper equipment!"));
        else
            startDialog(new Dialog("Hey there again.", "I hope you remember to \nalways use the right \nequipment!"));
    }
}
