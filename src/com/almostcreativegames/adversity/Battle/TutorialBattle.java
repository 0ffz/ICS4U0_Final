package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;

/**
 * A room that acts as a tutorial for the battle system
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - Created room</p>
 */
public class TutorialBattle extends Battle {
    /**
     * Creates a room with a dialog when it loads
     *
     * @param imageURL the background of the battle
     * @param enemy    the enemy Entity being fought
     * @param fromRoom the room to return to after the battle is over
     * @param game     a reference to the GameRunner
     */
    public TutorialBattle(String imageURL, Entity enemy, Room fromRoom, GameRunner game) {
        super(imageURL, enemy, fromRoom, game);
        //TODO if we don't have anything special in this room, this dialog can go straight into TutorialMan's onInteract
        game.startDialog(new Dialog(
                "Welcome to a tutorial on\nbattles. Press \"E\" to\ncontinue!",
                "This is what a battle looks\nlike!",
                "You'll find these when doing\ntasks here, so I thought\nI'd help you out...",
                "So you don't end up like me\non my first day",
                "Hehe, anyways...",
                "At the top, you'll see the\nname of the task you're\ndoing as well as its health",
                "On the bottom you'll see your\nown health!",
                "When the task's health\nreaches 0, you complete the\ntask and get to go home!",
                "When your health reaches 0\nWell, let's just say it's to the\nhospital for you",
                "When doing a task, you\ncontrol your soul, which\nlooks like a heart",
                "You can walk around and\npress any of the buttons\nbelow with \"E\"",
                "The first is \"ACT\", which\ncontains different actions\nyou can do by pressing\n\"E\" while standing over them",
                "The next is \"ITEM\", which\ncontains items you can equip",
                "You can equip or unequip\nthem by pressing \"E\" while\nstanding over them",
                "An equipment piece is\nequipped when there is\na little [x] next to it",
                "I won't keep you here much\nlonger, so here's the last\nthing",
                "After you choose an option,\nmy turn will start",
                "Don't worry, I don't bite,\nbut some of the stuff in the\nwarehouse does, so just be\ncareful",
                "Now, go out and battle me!"), this);
    }
}
