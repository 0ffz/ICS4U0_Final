package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Entity;
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
    private boolean playerTurn = true;

    public Battle(String imageURL, Entity fighting, Room fromRoom) {
        super(imageURL);
        this.fighting = fighting;
        this.fromRoom = fromRoom;
        addEntity(fighting);
    }

    public void endBattle(Renderer renderer) {
        renderer.loadRoom(fromRoom);
    }
}
