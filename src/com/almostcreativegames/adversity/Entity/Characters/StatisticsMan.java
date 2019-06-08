package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class StatisticsMan extends Entity {

    private String[] stats = {"904 workers died from \nworkplace related injuries \nin 2016 alone.", "13% of the total young \nworkforce is composed of \nyoung workers.", "In 2015 403 young workers \ndied in the workplace."};

    {
        setName("Statistics Man");
        setImage(new Image("Entities/Statistics Man.png", 80, 0, true, true));
        setPosition(50, 100);
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 0)
            startDialog(new Dialog("Hello there I'm just getting \nsome research done for \nworkplace safety statistics!", "Oh, you're new?", "Well let me tell you that in \n2015 110.5 per 10000 full time \nworkers between the ages of \n16-19 suffered a non fatal \ninjury.", "Take care of yourself in there!"));
        else
            startDialog(new Dialog("Hey there again.", stats[(int) (Math.random() * 2)]));
    }
}
