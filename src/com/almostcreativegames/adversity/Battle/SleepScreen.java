package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Text;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The room that gets loaded when the player goes to sleep
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
public class SleepScreen extends Room {
    /**
     * Creates a room containing the player sleeping in the bed. After some time, returns to the previos room
     *
     * @param imageURL the background of the battle
     * @param previous the previous room
     * @param game     a reference to the game
     */
    //TODO could probably remove this if we know for sure we're loading the same background each time
    public SleepScreen(String imageURL, Room previous, GameRunner game) {
        super(imageURL);
        setGame(game);

        //level/chapter indicator
        Text chapter = new Text("");
        int day = getGame().getDay();
        if (day == 0)
            chapter.setText("Chapter 1");
        else if (day > 0 && day < 4)
            chapter.setText("Chapter 2");
        else
            chapter.setText("Chapter 3");

        chapter.setFont(Fonts.HOME_SCREEN);
        chapter.setPosition(500 - chapter.getTextWidth() / 2, 200);
        addEntity(chapter);

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
                timer.cancel();
            }
        }, 5000);
    }
}
