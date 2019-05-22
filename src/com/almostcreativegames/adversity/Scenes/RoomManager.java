package com.almostcreativegames.adversity.Scenes;

import com.almostcreativegames.adversity.Drawing.Renderer;

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

    public RoomManager() {
        rooms[0][0] = new Room();
        rooms[0][1] = new Room();
    }

    public Room getRoom(int row, int col) {
        try {
            return rooms[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Room getCurrentRoom() {
        return rooms[currentX][currentY];
    }

    public void loadRoom(Renderer renderer, int offsetX, int offsetY) {
        currentX += offsetX;
        currentY += offsetY;
        renderer.loadRoom(rooms[currentX][currentY]);
    }
}
