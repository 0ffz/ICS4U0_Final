package com.almostcreativegames.adversity.Scenes;

import com.almostcreativegames.adversity.Drawing.Renderer;
import javafx.scene.canvas.GraphicsContext;

/**
 * Manages the different rooms in our game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class RoomManager {
    private Room[][] rooms = new Room[10][10];
    private int currentX;
    private int currentY;

    public RoomManager(){
        rooms[0][0] = new Room("Rooms/Home");
        rooms[0][1] = new Room("Rooms/Living Room");
    }

    public Room getRoom(int row, int col){
        try {
            return rooms[row][col];
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public Room getCurrentRoom(){
        return rooms[currentY][currentX];
    }

    public String getCurrentPosition(){
        return currentX + ", " + currentY;
    }

    public Room getRoomAtOffset(int offsetX, int offsetY){
        return rooms[currentY + offsetY][currentX + offsetX];
    }

    public void loadRoom(Renderer renderer, int offsetX, int offsetY){
        currentX += offsetX;
        currentY += offsetY;
        renderer.loadRoom(rooms[currentY][currentX]);
    }
}
