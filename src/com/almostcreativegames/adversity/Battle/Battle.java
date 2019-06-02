package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.*;
import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;
import com.almostcreativegames.adversity.Entity.Button.BackButton;
import com.almostcreativegames.adversity.Entity.Button.BattleButton;
import com.almostcreativegames.adversity.Entity.Button.Button;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * A class for creating turn based battle sequences
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.2.3 - Created basis for class, almost identical to Room, with a fighting entity held inside and a method to end
 * the battle and return to the room from which the battle occurred.</p>
 */
public class Battle extends Room {
    private Room fromRoom; //the room from which the Battle Menu was opened (we use it to return to that room later)
    private Entity fightingSprite;
    private Player soul;
    private Player previousPlayer = Player.getCurrentPlayer();
    private BattleButton act;
    private BattleButton item;
    private boolean playerTurn = true;

    public Battle(String imageURL, Battleable fighting, Room fromRoom, GameRunner game) {
        super(imageURL);
        setGame(game);
        this.fromRoom = fromRoom;

        fightingSprite = fighting.getBattleSprite();

        Rectangle2D boundary = fightingSprite.getBoundary();
        fightingSprite.setPosition(500 - boundary.getWidth() / 2, 200); //center the sprite
        addEntity(fightingSprite);

        //hide overworld player
        previousPlayer.setCanMove(false);
        previousPlayer.hide();

        //creating soul
        soul = new Player();
        soul.setLayer(5);
        String playerSprite = "Entities/Player/Soul-spritesheet.png";
        soul.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 11, 2, 1, 5, 5, 1));
        soul.addAnimation("left", new SpriteAnimation(playerSprite, 0, 12, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("right", new SpriteAnimation(playerSprite, 0, 24, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("down", new SpriteAnimation(playerSprite, 0, 36, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.addAnimation("up", new SpriteAnimation(playerSprite, 0, 48, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.setCurrentAnimation("idle");

        soul.setPosition(450, 800);
        addEntity(soul);
        Player.setCurrentPlayer(soul);

        //TODO create all the other buttons with textures
        //creating buttons with options
        //act button
        act = new BattleButton("ACT");
        act.setFont(Fonts.battleButton);
        act.setBattle(this);
        act.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        act.setPosition(40, 875);
        addEntity(act);


        item = new BattleButton("ITEM");
        item.setFont(Fonts.battleButton);
        item.setBattle(this);
        item.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        item.setPosition(250, 875);
        addEntity(item);


        //register all the menu options stored in the battleable entity
        for (Button button : fighting.getActOptions(this))
            act.addSubOption(button);

        //Adding a back button to all menus
        BackButton actBack = new BackButton(this);
        BackButton itemBack = new BackButton(this);

        act.addSubOption(actBack);
        item.addSubOption(itemBack);

    }

    public void hideMenuButtons() {
        act.hide();
        item.hide();
    }

    public void closeMenus() {
        act.closeMenu();
        item.closeMenu();
    }

    public void endBattle() {
        renderer.loadRoom(fromRoom);

        //return previous player to be playable again
        previousPlayer.setCanMove(true);
        previousPlayer.show();
        Player.setCurrentPlayer(previousPlayer);
    }
}
