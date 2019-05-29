package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.image.Image;

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

    public void endBattle(Renderer renderer){
        renderer.loadRoom(fromRoom);
    }
}
