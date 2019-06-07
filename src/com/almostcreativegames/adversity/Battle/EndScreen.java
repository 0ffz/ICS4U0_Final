package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Menu.Text;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import com.almostcreativegames.adversity.Saves.Save;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The room that gets loaded when the player completes the game
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
public class EndScreen extends Room {
    /**
     * Creates a room containing the player in a car, followed by credits
     *
     * @param imageURL the background of the battle
     * @param previous the previous room
     * @param game     a reference to the game
     */
    //TODO could probably remove this if we know for sure we're loading the same background each time
    public EndScreen(String imageURL, Room previous, GameRunner game) {
        super(imageURL);
        setGame(game);

        //hide overworld player
        Player player = game.getCurrentPlayer();
        player.setCanMove(false);

        //create the road background
        EntityAnimated driving = new EntityAnimated();
        driving.addAnimation("driving", new SpriteAnimation("Rooms/End Scene/End.png", 0, 0, 100, 100, 1, 1, 10, 10, 1));
        driving.setCurrentAnimation("driving");
        driving.setPosition(0, 0);
        addEntity(driving);

        //create car
        EntityAnimated car = new EntityAnimated();
        car.addAnimation("driving", new SpriteAnimation("Entities/End scene/Car.png", 0, 0, 76, 36, 1, 2, 7, 7, 2));
        car.setCurrentAnimation("driving");
        car.setPosition(500 - car.getImage().getWidth() / 2, 300);
        addEntity(car);

        game.startDialog(new Dialog("Well, you finally have\ntime to relax...") {
            @Override
            public void onEnd() {
                driving.remove();
                car.remove();

                Timer timer = new Timer();

                timer.schedule(new TimerTask() {
                    int iteration = 0;

                    @Override
                    public void run() {
                        switch (iteration) {
                            case 0:
                                Text theEnd = new Text("The end");
                                theEnd.setFillColor(Color.WHITE);
                                theEnd.setFont(Fonts.TITLE); //TODO convert Fonts
                                theEnd.setPosition(500 - theEnd.getTextWidth() / 2, 500);
                                addEntity(theEnd);
                                break;
                            case 1:
                                Text title = new Text("Don't get hurt, stay at work");
                                title.setFillColor(Color.WHITE);
                                title.setFont(Fonts.BATTLE_BUTTON);
                                title.setPosition(500 - title.getTextWidth() / 2, 600);
                                addEntity(title);
                                break;
                            case 2:
                                Text authors = new Text("By Daniel Voznyy and Enfei Zhang");
                                authors.setFillColor(Color.WHITE);
                                authors.setFont(Fonts.BATTLE_BUTTON);
                                authors.setPosition(500 - authors.getTextWidth() / 2, 650);
                                addEntity(authors);
                                break;
                            case 3:
                                Text thanks = new Text("Thanks for playing!");
                                thanks.setFillColor(Color.WHITE);
                                thanks.setFont(Fonts.BATTLE_BUTTON);
                                thanks.setPosition(500 - thanks.getTextWidth() / 2, 700);
                                addEntity(thanks);
                                break;
                            case 5:
                                Save.delete();
                                timer.cancel();
                                game.close();
                                break;
                        }
                        iteration++;
                    }
                }, 200, 2000);
            }
        }, this);
    }
}
