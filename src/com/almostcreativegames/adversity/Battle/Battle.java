package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.Rooms.Room;

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
    private Entity fighting;
    private Player soul;
    private Player previousPlayer = Player.getCurrentPlayer();
    private Entity act;
    private boolean playerTurn = true;

    public Battle(String imageURL, Entity fighting, Room fromRoom) {
        super(imageURL);

        this.fighting = fighting;
        this.fromRoom = fromRoom;

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
        act = new Entity() {
            @Override
            public void onInteract() {
                System.out.println("I have been pressed!");
            }
        };
        act.setImage("DialogBox.png");
        act.setPosition(100, 600);
        addEntity(act);
    }

    public void endBattle(Renderer renderer) {
        renderer.loadRoom(fromRoom);

        //return previous player to be playable again
        previousPlayer.setCanMove(true);
        previousPlayer.show();
        Player.setCurrentPlayer(previousPlayer);
    }
}
