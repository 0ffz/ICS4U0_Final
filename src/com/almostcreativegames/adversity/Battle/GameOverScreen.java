package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Menu.Text;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The room that gets loaded after the player dies
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
public class GameOverScreen extends Room {

    /**
     * Creates a room for the game over screen, containing a soul and options to retry or quit the game.
     *
     * @param game a reference to the game
     */
    public GameOverScreen(GameRunner game) {
        super("Battle/Empty.png");
        setGame(game);

        //create game over text
        Text gameOver = new Text("Game Over");
        gameOver.setFillColor(Color.WHITE);
        gameOver.setFont(Fonts.TITLE);
        gameOver.setPosition(500 - gameOver.getTextWidth() / 2, 400);
        addEntity(gameOver);

        //creating soul
        Player soul = new Player();
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
        Button retry = new Button("RETRY") {
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
        Button quit = new Button("QUIT") {
            @Override
            public void onInteract() {
                try {
                    remove();
                    game.close();
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
