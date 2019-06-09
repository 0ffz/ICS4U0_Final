package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;

import java.util.*;

/**
 * A class for the Tutorial Man entity that can be battleable
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.2.2
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - TutorialMan class created with necessary animation a interactions implemented</p>
 * <p>1.2.2 - TutorialMan proper battle animations implemented and Code Cleanup</p>
 */
public class TutorialMan extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;

    private double health = 10;

    {
        setName("Mr. Tutorial");
        setPosition(50, 700);

        addAnimation("idle", new SpriteAnimation("Entities/Tutorial Man.png", 0, 0, 20, 50, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
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
        hideInderactIndicator();

        if (!getGame().hasAttribute("Day 1 talked to boss")) {
            startDialog(new Dialog(
                    "You must be new, talk to\nthe boss right there, he'll\nget you set up."
            ));
        } else if (!getGame().hasAttribute("Talked to tutorial")) { //if not talked to him yet, begin dialog
            Battle battle = new Battle(this, room, room.getGame());
            getGame().getRenderer().loadRoom(battle);
        } else if (getGame().getDay() == 0)
            startDialog(new Dialog(
                    "Feel free to look around the\nfactory, I'll handle your stuff\ntoday."
            ));
        else
            startDialog(new Dialog(
                    "Hello again buddy.",
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
                        "The man is rather tall.",
                        "He has a weird scent coming\noff him.",
                        "He seems to be trying to say\nsomething.",
                        "Well then, looks like you've\npressed the button already!",
                        "Many of the jobs you'll\nencounter will have an\n\"Inspect\" button.",
                        "This button tells you a little\nbit about the task you're\nworking with!",
                        "Beware though, pressing it\nstill uses up your turn!",
                        "Normally, the task you're\ndoing would battle you\nback, but I'm nice so I\nwon't hit you or anything.",
                        "Although, you do seem in\ndesperate need of training...",
                        "Alright, tell you what,\nI'll throw some fake punches\nat you.",
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
    }

    @Override
    public void onBattleStart(Battle battle) {
        startDialog(new Dialog(
                "Welcome to a tutorial on\nbattles. Press \"E\" to\ncontinue!",
                "This is what a battle looks\nlike!",
                "You'll find these when doing\ntasks here, so I thought\nI'd help you out...",
                "So you don't end up like me\non my first day.",
                "Hehe, anyways...",
                "At the top, you'll see the\nname of the task you're\ndoing as well as its health.",
                "On the bottom you'll see your\nown health!",
                "When the task's health\nreaches 0, you complete the\ntask and get to go home!",
                "When your health reaches 0\nWell, let's just say it's to the\nhospital for you.",
                "When doing a task, you\ncontrol your soul, which\nlooks like a heart.",
                "You can walk around and\npress any of the buttons\nbelow with \"E\".",
                "The first is \"ACT\", which\ncontains different actions\nyou can do by pressing\n\"E\" while standing over them.",
                "The next is \"ITEM\", which\ncontains items you can equip.",
                "You can equip or unequip\nthem by pressing \"E\" while\nstanding over them.",
                "Equipment is equipped\nwhen there is a little [x]\nnext to it.",
                "\"LEAVE\" lets you leave the \nfight.",
                "One last thing,\nAfter you choose an option,\nmy turn will start.",
                "Don't worry, I don't bite,\nbut some of the stuff in the\nwarehouse does, so just be\ncareful.",
                "Now, go out and battle me!"));
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
                        "You look pretty bad.\nYou know what, take some \ntime off today...",
                        "I'll handle your shift for you.",
                        "Sorry about that, just\ndon't tell the boss",
                        "Don't get used to it though,\nI'm just feeling extra nice\ntoday") {
                    @Override
                    public void onEnd() {
                        battle.endBattle();
                        getGame().addAttribute("Talked to tutorial");
                        getGame().addAttribute("Job done");
                        startDialog(new Dialog(
                                "Well then, I'll see you\ntomorrow",
                                "Look around the factory,\nthen have fun at home!"
                        ));
                    }
                });
            }
        }, 3500);
    }

    @Override
    public void onPlayerTurn(Battle battle) {

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
}
