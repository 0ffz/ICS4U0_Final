package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A class for displaying a game over screen with options to retry or close the game
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - </p>
 */
public class SleepScreen extends Room {

    /**
     * Defines a new battle object, in which a new room is created and entered.
     *
     * @param imageURL the background of the battle
     */
    //TODO could probably remove this if we know for sure we're loading the same background each time
    public SleepScreen(String imageURL, Room previous, GameRunner game) {
        super(imageURL);
        setGame(game);

        //hide overworld player
        Player player = game.getCurrentPlayer();
        player.setCanMove(false);

        //create sleeping sprite
        EntityAnimated sleepingPlayer = new EntityAnimated();
        sleepingPlayer.addAnimation("idle", new SpriteAnimation("Entities/Player/Sleeping.png", 0, 0, 22, 13, 1, 1, 25, 25, 1));
        sleepingPlayer.setCurrentAnimation("idle");
        sleepingPlayer.setPosition(200, 600);
        addEntity(sleepingPlayer);

        //create ZZZ above sleeping player
        EntityAnimated sleepZZZ = new EntityAnimated();
        sleepZZZ.addAnimation("idle", new SpriteAnimation("Menu/Sleeping/zzz.png", 0, 0, 15, 12, 4, 4, 30, 30, 3, 1, 1));
        sleepZZZ.setCurrentAnimation("idle");
        sleepZZZ.setPosition(500 - sleepZZZ.getImage().getWidth() / 2, 500 - sleepZZZ.getImage().getHeight() / 2);
        addEntity(sleepZZZ);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.getRenderer().loadRoom(previous);
                player.setCanMove(true);
                timer.cancel();
            }
        }, 5000);
    }
}
