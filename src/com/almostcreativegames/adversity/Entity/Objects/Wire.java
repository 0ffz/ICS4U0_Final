package com.almostcreativegames.adversity.Entity.Objects;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
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
 * @version 1.2.2
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Wire class created with necessary animation a interactions implemented</p>
 * <p>1.2.2 - Battle completed</p>
 */
public class Wire extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;

    private double health = 10;
    private ArrayList<Entity> sparks = new ArrayList<>();

    {
        setName("Sparking wire");
        setPosition(100, 270);
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 20, 20, 1));
        sprite.setCurrentAnimation("idle");
        return sprite;
    }

    @Override
    public void onRoomLoad() {
        if (getGame().getDay() > 1)
            remove();
        else if(getGame().hasAttribute("Talked to WireHelper") && getGame().hasEquipment("Electrical Gloves"))
            showInteractIndicator();
    }

    @Override
    public void onInteract() {
        hideInderactIndicator();
        if (getGame().hasAttribute("Talked to tutorial") && getGame().getDay() == 1) {
            if (getGame().hasAttribute("Talked to WireHelper")) {
                Battle battle = new Battle("Battle/Battle.png", this, room, getGame());
                getGame().getRenderer().loadRoom(battle);
            } else
                startDialog(new Dialog("You should talk to the wire\nhelper before doing this."));
        } else
            startDialog(new Dialog("You probably shouldn't touch\nthat."));
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                startDialog(new Dialog(
                        "The wire seems to have\nelectrical sparks coming off\nof it.",
                        "You probably shouldn't touch\nit bare handed.") {
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
                if (getGame().isEquipped("Electrical Gloves"))
                    startDialog(new Dialog(
                            "You managed to cut\npart of the wire!") {
                        @Override
                        public void onEnd() {
                            addHealth(-4);
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

    }

    @Override
    public void onWin(Battle battle) {
        battle.getGame().addAttribute("Job done");
        hideInderactIndicator();
        remove();
        startDialog(new Dialog(Arrays.asList(
                "The wire finally got cut!",
                "You should talk to the boss\nnow.")));
        getGame().addAttribute("Wire cut");
    }

    @Override
    public void onBattleStart(Battle battle) {

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

    @Override
    protected void registerAnimations() {
        addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
    }
}
