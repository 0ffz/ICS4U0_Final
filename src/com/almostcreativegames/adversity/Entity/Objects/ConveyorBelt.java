package com.almostcreativegames.adversity.Entity.Objects;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;

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

    private double health = 20;

    {
        setName("Conveyor Belt");
        //TODO set position and image
    }

    @Override
    public Entity getBattleSprite() {
        return null;
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
                startDialog(new Dialog(
                        "The conveyor belt has stopped moving!", "There seems to be something\n jammed into one of the \ngears.") {
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
        remove();
    }

    @Override
    public void onWin(Battle battle) {

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

    @Override
    protected void registerAnimations() {
        super.registerAnimations();
    }
}
