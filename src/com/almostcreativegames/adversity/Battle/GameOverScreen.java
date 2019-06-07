package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Equippable;
import com.almostcreativegames.adversity.Entity.Menu.*;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
public class GameOverScreen extends Room {
    protected Player soul;
    protected Button retry;
    protected Button quit;

    /**
     * Defines a new battle object, in which a new room is created and entered.
     *
     * @param imageURL the background of the battle
     */
    //TODO could probably remove this if we know for sure we're loading the same background each time
    public GameOverScreen(String imageURL, GameRunner game) {
        super(imageURL);
        setGame(game);

        //creating soul
        soul = new Player();
        soul.setLayer(5);
        soul.setName("Soul");

        String playerSprite = "Entities/Player/Soul-spritesheet.png";
        soul.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 11, 2, 1, 5, 5, 1));
        soul.addAnimation("left", new SpriteAnimation(playerSprite, 0, 12, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("right", new SpriteAnimation(playerSprite, 0, 24, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("down", new SpriteAnimation(playerSprite, 0, 36, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.addAnimation("up", new SpriteAnimation(playerSprite, 0, 48, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.setCurrentAnimation("idle");

        soul.setPosition(450, 800);
        addEntity(soul);
        game.setCurrentPlayer(soul);

        //creating buttons with options
        //retry button
        retry = new Button("RETRY"){
            @Override
            public void onInteract() {
                game.close();
                remove();
                Stage stage = new Stage();
                GameRunner game = new GameRunner(true);
                game.start(stage);
            }
        };
        retry.setFont(Fonts.BATTLE_BUTTON);
        retry.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        retry.setPosition(500 - retry.getImage().getWidth(), 875);
        addEntity(retry);

        //quit button
        quit = new Button("QUIT"){
            @Override
            public void onInteract() {
                try {
                    remove();
                    game.close();
                    game.saveGame();
                    game.openMainMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        quit.setFont(Fonts.BATTLE_BUTTON);
        quit.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        quit.setPosition(500, 875);
        addEntity(quit);
    }
}
