package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


/**
 * A class for the Statistics Man entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Statistics Man moved from RoomManager to it's own class</p>
 */
public class StatisticsMan extends Entity {

    private String[] stats = {"904 workers died from \nworkplace related injuries \nin 2016 alone.", "13% of the total young \nworkforce is composed of \nyoung workers.", "In 2015 403 young workers \ndied in the workplace."};

    {
        setName("Statistics Man");
        setImage(new Image("Entities/Statistics Man.png", 80, 0, true, true));
        setPosition(50, 100);
    }

    @Override
    public void onInteract() {
        if (!getGame().hasAttribute("Spoken to Statistics"))
            startDialog(new Dialog("Hello there I'm just getting \nsome research done for \nworkplace safety statistics!", "Oh, you're new?", "Well let me tell you that in \n2015 110.5 per 10000 full time \nworkers between the ages of \n16-19 suffered a non fatal \ninjury.", "Take care of yourself in there!"){
                @Override
                public void onEnd() {
                    getGame().addAttribute("Spoken to Statistics");
                }
            });
        else
            startDialog(new Dialog("Hey there again.", stats[(int) (Math.random() * 2)]));
    }
}
