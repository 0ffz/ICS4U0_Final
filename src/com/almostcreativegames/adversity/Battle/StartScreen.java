package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Menu.Text;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The room that gets loaded when the player begins a new game
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
public class StartScreen extends Room {
    /**
     * Creates a room with the title of the game and the Almost Creative Games logo
     *
     * @param game a reference to the game
     */
    public StartScreen(GameRunner game) {
        super("Battle/Empty.png");
        setGame(game);

        //freeze overworld player
        Player player = game.getCurrentPlayer();
        player.setCanMove(false);

        Timer timer = new Timer();

        //list out a new line of credits every 2000ms
        timer.schedule(new TimerTask() {
            int iteration = 0;

            @Override
            public void run() {
                switch (iteration) {
                    case 0:
                        Text title = new Text("Don't get hurt,\nstay at work");
                        title.setFillColor(Color.WHITE);
                        title.setFont(Fonts.TITLE);
                        title.setPosition(500 - title.getTextWidth() / 2, 300);
                        addEntity(title);
                        break;
                    case 1:
                        //create logo
                        Entity logo = new Entity();
                        logo.setImage(new Image("Logo.png", 300, 0, true, true));
                        logo.setPosition(500 - logo.getImage().getWidth() / 2, 650);
                        addEntity(logo);

                        Text company = new Text("Made by Almost Creative Games");
                        company.setFillColor(Color.WHITE);
                        company.setFont(Fonts.BATTLE_BUTTON);
                        company.setPosition(500 - company.getTextWidth() / 2, 600);
                        addEntity(company);
                        break;
                    case 3:
                        //delete the save and end the game
                        timer.cancel();
                        onEnd();
                        break;
                }
                iteration++;
            }
        }, 0, 2000);
    }

    public void onEnd() {

    }
}
