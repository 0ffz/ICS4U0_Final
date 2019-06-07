package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Battle.TutorialBattle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;

import java.util.*;

/**
 * A class for the wire entity that can be battleable
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.1
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Wire class created with necessary animation a interactions implemented</p>
 */
public class TutorialMan extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;

    private double health = 10;
    private ArrayList<Entity> sparks = new ArrayList<>();

    {
        setName("Mr. Tutorial");
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("idle", new SpriteAnimation("Entities/Tutorial Man.png", 0, 0, 20, 50, 2, 1, 7, 7, 1));
        sprite.setCurrentAnimation("idle");
        return sprite;
    }

    @Override
    public void onInteract() {
        if (!room.getGame().hasAttribute("Talked to tutorial")) { //if not talked to him yet, begin dialog
            TutorialBattle battle = new TutorialBattle("Battle/Battle.png", this, room, room.getGame());
            room.getGame().getRenderer().loadRoom(battle);
        } else if (room.getGame().getDay() == 0)
            startDialog(new Dialog(
                    "Go on home, I'll\nhandle your stuff today"
            ));
        else
            startDialog(new Dialog(
                    "Hello again buddy",
                    "We're buddies, right?",
                    "Of course we are, I covered\nyour shift after all!",
                    "Well, good luck on your job!"
            ));
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                startDialog(new Dialog(
                        "The man is rather tall",
                        "He has a weird scent coming\noff him",
                        "He seems to be trying to say\nsomething",
                        "Well then, looks like you've\npressed the button already!",
                        "Many of the jobs you'll\nencounter will have an\n\"Inspect\" button",
                        "This button tells you a little\nbit about the task you're\nworking with!",
                        "Beware though, pressing it\nstill uses up your turn!",
                        "Normally, the task you're\ndoing would battle you\nback, but I'm nice so I\nwon't hit you or anything",
                        "Although, you do seem in\ndesperate need of training...",
                        "Alright, tell you what,\nI'll throw some fake punches\nat you",
                        "Get ready!") {
                    @Override
                    public void onEnd() {
                        battle.nextTurn();
                    }
                });
            }
        };
        return Arrays.asList(inspect);
    }

    @Override
    public void onBattleEnd(Battle battle) {
    }

    @Override
    public void onWin(Battle battle) {
        battle.getGame().addAttribute("Job done");
        startDialog(new Dialog(
                "The weird guy says:",
                "Congrats, you won your\nfirst battle!",
                "Get ready to battle many\nmore challenging tasks...",
                "I think you'll be good\nat this job",
                "Oh, seems like the day\n is over now!",
                "Go home and rest a bit,\n alright?"));
    }

    /**
     * Starts this entity's turn
     *
     * @param battle the current battle
     */
    @Override
    public void startTurn(Battle battle) {
        timer = new Timer();

        EntityAnimated leftHand = new EntityAnimated() {
            boolean hit = false;

            {
                addAnimation("left", new SpriteAnimation("Battle/Hands.png", 0, 0, 46, 30, 1, 1, 15, 20, 1));
                setCurrentAnimation("left");
                setPosition(-image.getWidth(), 530);
                setLayer(10);
                addVelocity(150, 0);
                friction = 1;
            }

            @Override
            public void onIntersect() {
                if (!hit)
                    battle.getGame().getCurrentPlayer().addHealth(-1);
                hit = true;
            }
        };
        battle.addEntity(leftHand);

        EntityAnimated rightHand = new EntityAnimated() {
            boolean hit = false;

            {
                addAnimation("right", new SpriteAnimation("Battle/Hands.png", 54, 0, 46, 30, 1, 1, 15, 20, 1));
                setCurrentAnimation("right");
                setPosition(1000, 530);
                setLayer(10);
                addVelocity(-150, 0);
                friction = 1;
            }

            @Override
            public void onIntersect() {
                if (!hit)
                    battle.getGame().getCurrentPlayer().addHealth(-1);
                hit = true;
            }
        };
        battle.addEntity(rightHand);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                leftHand.remove();
                rightHand.remove();
                timer.cancel();
                battle.nextTurn();
                startDialog(new Dialog(
                        "Oh gosh, sorry I didn't mean\nto hit you that bad...",
                        "You know what, you can take some\ntime off today...",
                        "I'll handle your shift for you",
                        "Don't get used to it though,\nI'm just feeling extra nice\ntoday") {
                    @Override
                    public void onEnd() {
                        battle.endBattle();
                        room.getGame().addAttribute("Talked to tutorial");
                        room.getGame().addAttribute("Job done");
                        startDialog(new Dialog(
                                "Well then, I'll see you\ntomorrow",
                                "Have fun at home!"
                        ));
                    }
                });
            }
        }, 5000);
    }

    @Override
    public void addHealth(double amount) {
        health += amount;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public double getMaxHealth() {
        return 10;
    }

    @Override
    protected void registerAnimations() {
        addAnimation("idle", new SpriteAnimation("Entities/Tutorial Man.png", 0, 0, 20, 50, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
    }
}
