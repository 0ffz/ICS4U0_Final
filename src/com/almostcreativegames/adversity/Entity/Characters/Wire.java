package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import javafx.scene.image.Image;

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
public class Wire extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;

    private double health = 10;
    private ArrayList<Entity> sparks = new ArrayList<>();

    {
        setName("Sparking wire");
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 20, 20, 1));
        sprite.setCurrentAnimation("idle");
        return sprite;
    }

    @Override
    public void onInteract() {
        if(room.getGame().hasAttribute("Talked to tutorial")) {
            Battle battle = new Battle("Battle/Battle", this, room, room.getGame());
            room.getGame().getRenderer().loadRoom(battle);
        }
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                startDialog(new Dialog(
                        "The wire seems to have electrical\nsparks coming off of it",
                        "You probably shouldn't touch it\nbare handed") {
                    @Override
                    public void onEnd() {
                        battle.nextTurn();
                    }
                });
            }
        };

        Button cut = new Button("Cut wire") {
            @Override
            public void onInteract() {
                if (room.getGame().isEquipped("Electrical Gloves"))
                    startDialog(new Dialog(
                            "You managed to cut part of the wire!") {
                        @Override
                        public void onEnd() {
                            addHealth(-5);
                            battle.nextTurn();
                        }
                    });
                else
                    startDialog(new Dialog(
                            "You try cutting the wire but\nsparks fly off it",
                            "It felt pretty painful") {
                        @Override
                        public void onEnd() {
                            battle.getGame().getCurrentPlayer().addHealth(-5);
                            battle.nextTurn();
                        }
                    });
            }
        };

        return Arrays.asList(inspect, cut);
    }

    @Override
    public void onBattleEnd(Battle battle) {
        remove();
    }

    @Override
    public void onWin(Battle battle) {
        battle.getGame().addAttribute("Job done");
        startDialog(new Dialog(Arrays.asList(
                "The wire finally got cut!",
                "You should talk to the boss\nnow")));
    }

    /**
     * Creates a spark Entity that hurts the player
     *
     * @param battle the current battle
     */
    private void createSpark(Battle battle) {
        Entity spark = new Entity() {
            {
                setImage(new Image("Battle/Wire/Electric spark.png", Math.random() * 20 + 20, 0, true, true));
                setPosition(Math.random() * 1000, Math.random() * 300 + 100);
                setLayer(10);
                addVelocity(0, Math.random() * 400 + 200);
                friction = 1;
            }

            @Override
            public void onIntersect() {
                battle.getGame().getCurrentPlayer().addHealth(-1);
                remove();
                if (battle.checkDead())
                    timer.cancel();
            }
        };
        sparks.add(spark);
        battle.addEntity(spark);
    }

    /**
     * Starts this entity's turn
     *
     * @param battle the current battle
     */
    @Override
    public void startTurn(Battle battle) {
        timer = new Timer();

        //delayed and repeating tasks from http://mrbool.com/how-to-schedule-recurring-tasks-in-java-applications/28909
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                createSpark(battle);
            }
        }, 100, 100);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                battle.nextTurn();
                for (Entity spark : sparks)
                    spark.remove();
            }
        }, 10000);
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
        addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
    }
}
