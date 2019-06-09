package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;


/**
 * A class for the Boss entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang, Daniel Voznyy
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Boss entity moved from RoomManager to it's own class</p>
 * <p>1.3.2 - Updated dialogs and cleaned up the code</p>
 */
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
        hideInderactIndicator();
        if (getGame().getDay() == 0) {
            if (getGame().hasAttribute("Day 1 talked to boss"))
                startDialog(new Dialog("Go on, talk to Mr. Tutorial\nif you haven't already"));
            else {
                startDialog(new Dialog("Hello!", "Welcome to your first day of \nyour job.",
                        "Today you can spend the day \nlooking around and learning \nabout the workplace.",
                        "Go talk to Mr. Tutorial right\nthere, he'll show you around.",
                        "After that, go up and talk to\none of the engineers about\nwhat you should do next."){
                    @Override
                    public void onEnd() {
                        tutorialMan.showInteractIndicator();
                    }
                });
                getGame().addAttribute("Day 1 talked to boss");
            }
        } else if (getGame().getDay() == 1) {
            if (getGame().hasAttribute("Wire cut"))
                startDialog(new Dialog("Well, looks like you did\nyour job.",
                        "You may now go home. Keep\nthe gloves with you.",
                        "We don't really have much\nstorage space left anyways."));
            else
                startDialog(new Dialog("Today is your first official day \nat work!",
                        "I know it's your second day,\nbut I also know Mr. Tutorial\ndid your job for you\nyesterday...",
                        "I'll let it slide this time, but\nyou'll be in trouble after that.",
                        "*You overhear him whisper\nsomething about millenials*",
                        "Let's start you off lightly by \ngiving you a simple task.",
                        "Please proceed straight up to \nfix the electrical panel, it \nseems to be malfunctioning \ntoday."));
            getGame().addAttribute("Day 2 talked to boss");
        } else if (getGame().getDay() == 2) {
            if (getGame().hasAttribute("Conveyor cleaned"))
                startDialog(new Dialog("I see you can clean too!",
                        "Nicely done, now you may\ngo home and relax."));
            else
                startDialog(new Dialog("Hello again, worker.",
                        "Today I want you to clean \nthe conveyor belt.", "It seems to be getting \nreally dusty!", "It's straight up from here"));
            getGame().addAttribute("Day 3 talked to boss");
        } else if (getGame().getDay() == 3) {
            if (getGame().hasAttribute("Attempted Mixer Off")) {
                startDialog(new Dialog("What are you doing back \nhere?",
                        "Oh, you can't clean it \nbecause the machine won't \nturn off?",
                        "I'm sorry I'll try to get \nan engineer to go fix\nit.", "You can go home now"));
                getGame().addAttribute("Job done");
            } else {
                getGame().addAttribute("Day 4 talked to boss");
                startDialog(new Dialog("Welcome back to work!",
                        "How are you doing?",
                        "Stop right there, it doesn't\nmatter.",
                        "I need you to clean out the\nmixing bin!",
                        "It's in the upper room like\nwith the other stuff!"));
            }
        }
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() == 0 && !getGame().hasAttribute("Day 1 talked to boss")
                || getGame().getDay() == 1 && (!getGame().hasAttribute("Day 2 talked to boss") || getGame().hasAttribute("Wire cut"))
                || getGame().getDay() == 2 && (!getGame().hasAttribute("Day 3 talked to boss") || getGame().hasAttribute("Conveyor cleaned"))
                || getGame().getDay() == 3 && (!getGame().hasAttribute("Day 4 talked to boss") || getGame().hasAttribute("Attempted Mixer Off")))
            showInteractIndicator();
    }
}
