package com.almostcreativegames.adversity.Entity.Objects;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;

import java.util.Arrays;
import java.util.List;

/**
 * A class for the Conveyor Belt entity that you have to talk to
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.3.1
 *
 * <h2>Changelog</h2>
 * <p>0.3.1 - Chemical Helper moved from RoomManager to it's own class</p>
 */
public class Mixer extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private double health = 20;

    {
        setName("Mixer");
        setPosition(650, 200);

        addAnimation("invisible", new SpriteAnimation("Empty.png", 0, 0, 1, 1, 1, 1, 300, 200, 1));
        setCurrentAnimation("invisible");
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("moving", new SpriteAnimation("Entities/Mixer/Mixer.png", 0, 0, 33, 22, 1, 2, 15, 15, 3));
        sprite.setCurrentAnimation("moving");
        return sprite;
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() > 3)
            remove();
    }

    @Override
    public void onInteract() {
        hideInderactIndicator();
        if (room.getGame().getDay() == 3) {
            if (getGame().hasAttribute("Talked to MixerHelper")) {
                Battle battle = new Battle(this, room, getGame());
                getGame().getRenderer().loadRoom(battle);
            } else
                startDialog(new Dialog("Maybe you should talk to\nthe mixer guy first."));
        } else
            startDialog(new Dialog("You probably shouldn't touch\nthat."));
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                if (getGame().hasAttribute("Attempted Mixer Off"))
                    startDialog(new Dialog(
                            "Although the guy tried,\nthe mixer seems to still be\nrunning, though turning\nat a slower rate.") {
                        @Override
                        public void onEnd() {
                            battle.closeMenus();
                        }
                    });
                else
                    startDialog(new Dialog(
                            "The mixer is currently\nmoving.",
                            "Although, it is only a\nslight rotating motion.",
                            "The mixer guy should know\nhow to turn it off.") {
                        @Override
                        public void onEnd() {
                            getGame().addAttribute("Inspected mixer");
                            battle.closeMenus();
                        }
                    });
            }
        };
        Button clean = new Button("Clean") {
            @Override
            public void onInteract() {
                getGame().setPlayerHealth(0);
                startDialog(new Dialog(
                        "You decide to clean\nthe mixer and get done\nwith your job",
                        "You enter and the mixer\nstarts speeding up") {
                    @Override
                    public void onEnd() {
                        getGame().gameOver();
                    }
                });
            }
        };

        return Arrays.asList(inspect, clean);
    }

    @Override
    public void onBattleStart(Battle battle) {
    }

    @Override
    public void onBattleEnd(Battle battle) {
    }

    @Override
    public void onWin(Battle battle) {
        battle.getGame().addAttribute("Job done");
        remove();
        startDialog(new Dialog(Arrays.asList(
                "The belt is now clean!",
                "You should talk to the boss\nnow.")));
        getGame().addAttribute("Conveyor cleaned");
    }

    @Override
    public void onPlayerTurn(Battle battle) {
        startDialog(new Dialog("Just a little more\ncleaning"));
    }

    @Override
    public void startTurn(Battle battle) {
    }

    @Override
    public void addHealth(double amount) {

    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public double getMaxHealth() {
        return 20;
    }
}
