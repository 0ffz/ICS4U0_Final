package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.Arrays;

public class Boss extends Entity {
    private Entity tutorialMan;

    {
        setName("Boss");
        setImage(new Image("Entities/Boss.png", 80, 0, true, true));
        setPosition(590, 715);
    }

    public Boss(TutorialMan tutorialMan) { //we pass the entities we'll be directly affecting when creating some entities
        this.tutorialMan = tutorialMan;
    }

    @Override
    public void onInteract() {
        if (getGame().getDay() == 0) {
            if (getGame().hasAttribute("Day 1 talked to boss"))
                startDialog(new Dialog("Go on, talk to Mr. Tutorial\nif you haven't already"));
            else
                startDialog(new Dialog("Hello!", "Welcome to your first day of \nyour job.",
                        "Today you can spend the day \nlooking around and learning \nabout the workplace.",
                        "Go talk to Mr. Tutorial right\nthere, he'll show you around.") {
                    @Override
                    public void onEnd() {
                        tutorialMan.showInteractIndicator();
                        getGame().addAttribute("Day 1 talked to boss");
                    }
                });
        } else if (getGame().getDay() == 1)
            startDialog(new Dialog(Arrays.asList("Today is your first official day \nat work!", "I'll start you off lightly by \ngiving you a simple task.", "Please proceed straight up to \nfix the electrical panel, it \nseems to be malfunctioning \ntoday.")));
        else
            startDialog(new Dialog(Arrays.asList("Welcome back to work!", "How are you doing?", "Stop right there, it doesn't matter.", "I need you to clean out the mixing \nbin!")));
        hideInderactIndicator();
    }
}
