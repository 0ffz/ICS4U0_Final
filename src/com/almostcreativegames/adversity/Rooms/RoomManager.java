package com.almostcreativegames.adversity.Rooms;

import com.almostcreativegames.adversity.Drawing.Renderer;

/**
 * Manages the different rooms in our game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Map containing two Rooms</p>
 * <p>0.1.2 - Now contains proper Rooms for the game. Added methods for getting and moving between rooms</p>
 */

public class RoomManager {
    private Room[][] rooms = new Room[4][7];
    private int currentX;
    private int currentY;

    public RoomManager(){
        currentX = 0;
        currentY = 3;
        rooms[3][0] = new Room("Rooms/Home");
        rooms[3][1] = new Room("Rooms/Living Room");
        rooms[3][2] = new Room ("Rooms/Road 1");
        rooms[3][3] = new Room ("Rooms/Road 2");
        rooms[3][4] = new Room ("Rooms/Outside Factory");
        rooms[2][4] = new Room ("Rooms/Factory Entrance");
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