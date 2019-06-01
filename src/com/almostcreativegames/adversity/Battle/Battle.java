package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.*;
import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;
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
        act = new BattleButton();
        act.setText("ACT");
        act.setBattle(this);
        act.setImage("DialogBox.png");
        act.setPosition(100, 600);

        Button option1 = new BattleButton();
        option1.setText("1");
        option1.setImage("DialogBox.png");
        act.addSubOption(option1);

        Button option2 = new BattleButton();
        option2.setText("2");
        option2.setImage("DialogBox.png");
        act.addSubOption(option2);

        Button option3 = new BattleButton();
        option3.setText("3");
        option3.setImage("DialogBox.png");
        act.addSubOption(option3);

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
