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
public class ConveyorBelt extends EntityAnimated implements BattleBehaviour, HealthBehaviour {
    private Timer timer;
    private EntityAnimated conveyorBelt;
    private double health = 20;
    private ArrayList<Entity> dirtPieces = new ArrayList<>();
    private ArrayList<Entity> gears = new ArrayList<>();

    {
        setName("Conveyor Belt");
        conveyorBelt = new EntityAnimated();
        conveyorBelt.addAnimation("idle", new SpriteAnimation("Entities/Conveyor belt/Conveyor belt.png", 0, 0, 53, 12, 1, 1, 15, 15, 3));
        conveyorBelt.setCurrentAnimation("idle");
        conveyorBelt.setPosition(500 - conveyorBelt.getImage().getWidth() / 2, 700);
        conveyorBelt.hide();

        //TODO set position and image
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("moving", new SpriteAnimation("Entities/Conveyor belt/Conveyor belt.png", 0, 0, 53, 12, 1, 3, 15, 15, 3));
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
        if (getGame().hasAttribute("Spoken to Conveyor Helper 2") && room.getGame().getDay() == 3) {
            //Create Battle
        } else if (!getGame().hasAttribute("Spoken to Conveyor Helper 2") && room.getGame().getDay() == 3)
            startDialog(new Dialog("The conveyor belt seems to \nstill be moving!", "I probably shouldn't touch \nthat"));
        else
            startDialog(new Dialog("You probably shouldn't touch\nthat"));
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                if (getGame().hasAttribute("Conveyor belt off"))
                    startDialog(new Dialog(
                            "The conveyor belt is\nturned off.") {
                        @Override
                        public void onEnd() {
                            battle.closeMenus();
                        }
                    });
                else
                    startDialog(new Dialog(
                            "The conveyor belt is\ncurrently moving.") {
                        @Override
                        public void onEnd() {
                            battle.closeMenus();
                        }
                    });
            }
        };
        Button clean = new Button("Clean") {
            @Override
            public void onInteract() {
                //TODO remove !
                if (!getGame().hasAttribute("Conveyor belt off"))
                    startDialog(new Dialog(
                            "You begin to clean the\nbelt.") {
                        @Override
                        public void onEnd() {
                            battle.nextTurn();
                        }
                    });
                else {
                    getGame().setPlayerHealth(0);
                    startDialog(new Dialog(
                            "You begin to clean the\nbelt.",
                            "As you try cleaning it,\nyour finger gets caught in\none of the gears",
                            "You don't feel so good.") {
                        @Override
                        public void onEnd() {
                            getGame().gameOver();
                        }
                    });
                }
            }
        };

        return Arrays.asList(inspect, clean);
    }

    @Override
    public void onBattleStart(Battle battle) {
        battle.addEntity(conveyorBelt);
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
        getGame().addAttribute("Wire cut");
    }

    /**
     * Creates a spark Entity that hurts the player
     *
     * @param battle the current battle
     */
    private void createDirt(Battle battle) {
        Entity dirt = new Entity() {
            {
                setImage(new Image("Battle/Conveyor Belt/Dirt.png", Math.random() * 30 + 30, 0, true, true));
                setPosition(Math.random() * 700 + 150, Math.random() * 150 + 700);
                setLayer(10);
                friction = 1;
            }

            @Override
            public void onIntersect() {
                health -= 1;
                remove();
                dirtPieces.remove(this);
                if (dirtPieces.size() == 0) {
                    battle.nextTurn();
                }
                if (battle.checkDead())
                    timer.cancel();
            }
        };
        battle.addEntity(dirt);
        dirtPieces.add(dirt);
    }

    private void createGear(Battle battle) {
        Entity gear = new Entity() {
            {
                setImage(new Image("Battle/Conveyor Belt/Gear.png", Math.random() * 20 + 20, 0, true, true));
                setPosition(Math.random() * 800 + 100, Math.random() * 150 + 700);
                setLayer(10);
                addVelocity(Math.random() * 80 - 40, Math.random() * 80 - 40);
                friction = 1.001;
            }

            @Override
            public void onIntersect() {
                getGame().getCurrentPlayer().addHealth(-1);
                remove();
                if (battle.checkDead())
                    timer.cancel();
            }
        };
        battle.addEntity(gear);
        gears.add(gear);
    }

    @Override
    public void onPlayerTurn(Battle battle){
        for(Entity dirt: dirtPieces)
            dirt.remove();
        dirtPieces.clear();
        for(Entity gear: gears)
            gear.remove();
        gears.clear();
        conveyorBelt.hide();

        startDialog(new Dialog("Just a little more\ncleaning"));
    }
    /**
     * Starts this entity's turn
     *
     * @param battle the current battle
     */
    @Override
    public void startTurn(Battle battle) {
        conveyorBelt.show();

        timer = new Timer();

        for (int i = 0; i < 10; i++) {
            createDirt(battle);
        }

        for (int i = 0; i < 20; i++) {
            createGear(battle);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                battle.nextTurn();
            }
        }, 5000);
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

    @Override
    protected void registerAnimations() {
        super.registerAnimations();
    }
}
