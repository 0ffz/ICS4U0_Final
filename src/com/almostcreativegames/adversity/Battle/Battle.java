package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.BattleButton;
import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.geometry.Rectangle2D;

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
    private boolean playerTurn = true;

    public Battle(String imageURL, Entity fighting, Room fromRoom, Renderer renderer) {
        super(imageURL);
        this.renderer = renderer;
        this.fromRoom = fromRoom;

        if (!(fighting instanceof Battleable)) { //if entity is not battleable, end the battle
            endBattle();
            return;
        }

        fightingSprite = ((Battleable) fighting).getBattleSprite();

        Rectangle2D boundary = fightingSprite.getBoundary();
        fightingSprite.setPosition(500 - boundary.getWidth() / 2, 200); //center the sprite
        addEntity(fightingSprite);

        //hide overworld player
        previousPlayer.setCanMove(false);
        previousPlayer.hide();

        //creating soul
        soul = new Player();
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        soul.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 1));
        soul.addAnimation("left", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        soul.addAnimation("right", new SpriteAnimation(playerSprite, 0, 30, 11, 15, 2, 2, 5, 5, 5));
        soul.addAnimation("up", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        soul.addAnimation("down", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 5));

        soul.setCurrentAnimation("idle");
        soul.setPosition(100, 100);
        addEntity(soul);
        Player.setCurrentPlayer(soul);

        //TODO create all the other buttons with textures
        //creating buttons with options
        //act button
        act = new BattleButton();
        act.setBattle(this);
        act.setImage("DialogBox.png");
        act.setPosition(100, 600);
        Entity option1 = new BattleButton();
        option1.setImage("DialogBox.png");
        act.addSubOption(option1);
        Entity option2 = new BattleButton();
        option2.setImage("DialogBox.png");
        act.addSubOption(option2);
        addEntity(act);
    }

    public void endBattle() {
        renderer.loadRoom(fromRoom);

        //return previous player to be playable again
        previousPlayer.setCanMove(true);
        previousPlayer.show();
        Player.setCurrentPlayer(previousPlayer);
    }
}
