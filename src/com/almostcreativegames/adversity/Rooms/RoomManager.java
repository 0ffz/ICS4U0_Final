package com.almostcreativegames.adversity.Rooms;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.Wire;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.GameRunner;
import javafx.scene.image.Image;

import java.util.Arrays;

/**
 * Manages the different rooms in our game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy & Enfei Zhang
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Map containing two Rooms</p>
 * <p>0.1.2 - Now contains proper Rooms for the game. Added methods for getting and moving between rooms</p>
 * <p>0.2.3 -
 * Daniel: Now contains a reference to the GameRunner, which can then be used to access something like
 * the renderer and creates the necessary entities per room and completed the factory. Added wire to starting room.
 * Enfei: Added Mom and Boss entities to be created in rooms</p>
 */

public class RoomManager {
    private Room[][] rooms = new Room[4][7];
    private int currentX;
    private int currentY;

    public RoomManager(GameRunner game) {
        currentX = 0;
        currentY = 3;
        rooms[3][0] = new Room("Rooms/Home");
        Wire wire = new Wire();
        wire.setPosition(600, 600);
        Entity bed = new Entity(){
            @Override
            public void onInteract(){
                if (GameRunner.isJobDone()){
                    GameRunner.nextDay();
                    GameRunner.work();
                }
            }
        };
        bed.setImage(new Image("Bed.png", 150, 0, true, true));
        bed.setPosition(100, 725);
        rooms[3][0].addEntity(wire);

        rooms[3][1] = new Room("Rooms/Living Room");

        Entity mom = new Entity() {
            @Override
            public void onInteract() {
                if (GameRunner.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("You should be going to work honey.", "Don't wanna be late on your first day!", "Oh and remember honey to always stay safe!")));
            }
        };
        mom.setImage(new Image("Mom.png", 80, 0, true, true));
        mom.setPosition(300, 715);
        rooms[3][0].addEntity(bed);

        rooms[3][1].addEntity(mom);

        rooms[3][2] = new Room("Rooms/Road 1");
        rooms[3][3] = new Room("Rooms/Road 2");
        rooms[3][4] = new Room("Rooms/Outside Factory");
        rooms[2][2] = new Room("Rooms/Factory Floor 2");
        rooms[2][3] = new Room("Rooms/Factory Floor");
        rooms[2][4] = new Room("Rooms/Factory Entrance");
        Entity boss = new Entity() {
            @Override
            public void onInteract() {
                if (GameRunner.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hello", "Welcome to your first day of your job.", "Today you can spend the day looking around and learning about the workplace.")));
                else if (GameRunner.getDay() == 1)
                    startDialog(new Dialog(Arrays.asList("Today is your first official day at work!", "I'll start you off lightly by giving you a simple task.", "Please proceed straight up to fix the electrical panel, it seems to be malfunctioning today")));
                else if (GameRunner.getDay() == 2)
                    startDialog(new Dialog(Arrays.asList("Welcome back to work!", "Today I'll need you to fix a light bulb in the same room as before!")));
                else if (GameRunner.getDay() == 3)
                    startDialog(new Dialog(Arrays.asList("You know the drill by now.", "I need you to clean some chemicals from one of our machines in the room")));
                else if (GameRunner.getDay() == 4)
                    startDialog(new Dialog(Arrays.asList("How are you doing?", "Stop right there, it doesn't matter", "I need you to fix one of our conveyor belts.")));
                else
                    startDialog(new Dialog(Arrays.asList("How you doing?", "Don't matter how you doing", "I need you to clean out the mixing bin!")));
            }
        };
        boss.setImage(new Image("Boss.png", 80, 0, true, true));
        boss.setPosition(600, 715);
        rooms[2][4].addEntity(boss);

        rooms[2][5] = new Room("Rooms/Factory Floor");
        rooms[2][6] = new Room("Rooms/Factory Floor 3");
        rooms[1][2] = new Room("Rooms/Factory Floor 5");
        rooms[1][3] = new Room("Rooms/Factory Floor 4");
        rooms[1][4] = new Room("Rooms/Outside Game Room");
        rooms[1][5] = new Room("Rooms/Factory Floor 4");
        rooms[1][6] = new Room("Rooms/Factory Floor 6");
        rooms[0][4] = new Room("Rooms/Game Room");
        for (Room[] row : rooms)
            for (Room room : row)
                if (room != null)
                    room.setGame(game);
    }

    public Room getRoom(int row, int col) {
        try {
            return rooms[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Room getCurrentRoom() {
        return rooms[currentY][currentX];
    }

    public String getCurrentPosition() {
        return currentX + ", " + currentY;
    }

    public Room getRoomAtOffset(int offsetX, int offsetY) {
        return rooms[currentY + offsetY][currentX + offsetX];
    }

    public void loadRoom(Renderer renderer, int offsetX, int offsetY) {
        currentX += offsetX;
        currentY += offsetY;
        renderer.loadRoom(rooms[currentY][currentX]);
    }
}
