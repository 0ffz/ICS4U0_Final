package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Battle.TutorialBattle;
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
public class TutorialMan extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;
    private boolean notFought = true;

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
        if(notFought) {
            TutorialBattle battle = new TutorialBattle("Battle/Battle", this, room, room.getGame());
            room.getGame().getRenderer().loadRoom(battle);
        } else
            startDialog(new Dialog(
                    "Go on home, I'll\nhandle your stuff today"
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
        battle.getGame().setJobDone(true);
        startDialog(new Dialog(
                "The weird guy says:",
                "Congrats, you won your\nfirst battle!",
                "Get ready to battle many\nmore challenging tasks...",
                "I think you'll be good\nat this job",
                "Oh, seems like the day\n is over now!",
                "Go home and rest a bit,\n alright?"));
    }

    /**
     * Creates a spark Entity that hurts the player
     *
     * @param battle the current battle
     */
    //TODO this guy's attack will be just two big entities shaped like fists flying from both sides
    // they'll guarantee to hit you, so that afterwards he can apologize for it.
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
                Player.getCurrentPlayer().addHealth(-1);
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
                startDialog(new Dialog(
                        "Oh gosh, sorry I didn't mean\nto hit you that bad...",
                        "You know what, you can take some\ntime off today...",
                        "I'll handle your shift for you",
                        "Don't get used to it though,\nI'm just feeling extra nice\ntoday"){
                    @Override
                    public void onEnd() {
                        battle.endBattle();
                        notFought = false;
                        startDialog(new Dialog(
                                "Well then, I'll see you\ntomorrow",
                                "Have fun at home!"
                        ));
                    }
                });
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
        addAnimation("idle", new SpriteAnimation("Entities/Tutorial Man.png", 0, 0, 20, 50, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
    }
}
