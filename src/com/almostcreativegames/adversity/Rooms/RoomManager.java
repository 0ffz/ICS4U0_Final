package com.almostcreativegames.adversity.Rooms;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.TutorialMan;
import com.almostcreativegames.adversity.Entity.Characters.Wire;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
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
        rooms[3][0].addEntity(wire);

        TutorialMan tutorialMan = new TutorialMan();
        tutorialMan.setPosition(100, 800);
        rooms[3][0].addEntity(tutorialMan);

        Entity bed = new Entity() {
            @Override
            public void onInteract() {
                if (GameRunner.isJobDone()) {
                    GameRunner.nextDay();
                    GameRunner.work();
                    //if (GameRunner.getDay() == 6)
                    //  endScene();
                }
            }
        };
        bed.setImage(new Image("Entities/Bed.png", 150, 0, true, true));
        bed.setPosition(100, 725);
        rooms[3][0].addEntity(bed);

        rooms[3][1] = new Room("Rooms/Living Room");

        Entity mom = new Entity() {
            @Override
            public void onInteract() {
                //TODO remove all the Arrays.asList()
                if (GameRunner.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("You should be going to work \nhoney.", "Don't wanna be late on your first \nday!", "Oh and remember honey to \nalways stay safe!")));
                else
                    startDialog(new Dialog(Arrays.asList("See you later honey.", "Remember not to get hurt!")));
            }
        };
        EntityAnimated eButton = new EntityAnimated();
        eButton.addAnimation("appear", new SpriteAnimation("Entities/E.png", 0, 0, 50, 50, 2, 2, 1, 1, 3));
        eButton.setCurrentAnimation("appear");
        eButton.setPosition(370, 700);

        mom.setImage(new Image("Entities/Mom.png", 80, 0, true, true));
        mom.setPosition(300, 715);

        rooms[3][1].addEntity(mom);
        rooms[3][1].addEntity(eButton);

        rooms[3][2] = new Room("Rooms/Road 1");
        rooms[3][3] = new Room("Rooms/Road 2");
        rooms[3][4] = new Room("Rooms/Outside Factory");
        rooms[2][2] = new Room("Rooms/Factory Floor 2");

        rooms[2][3] = new Room("Rooms/Factory Floor");

        Entity electricalEmployee = new Entity() {
            @Override
            public void onInteract() {
                if (GameRunner.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hey there you seem new.", "I remember my first day here.", "I was excited to start my job and \nget working.", "But then I had suddenly gotten an \ninjury from the electrical box.", "Oh shoot I'm starting to ramble.", "We should get back to work.", "Anyways nice meeting you always \nmake sure that you have the \nproper equipment!")));
                else
                    startDialog(new Dialog(Arrays.asList("Hey there again.", "I hope you remember to always \nuse the right equipment!")));
            }
        };

        electricalEmployee.setImage(new Image("Entities/Electrician.png", 80, 0, true, true));
        electricalEmployee.setPosition(300, 390);
        rooms[2][3].addEntity(electricalEmployee);

        rooms[2][4] = new Room("Rooms/Factory Entrance");

        Entity boss = new Entity() {
            @Override
            public void onInteract() {
                if (GameRunner.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hello!", "Welcome to your first day of your \njob.", "Today you can spend the day \nlooking around and learning \nabout the workplace.")));
                else if (GameRunner.getDay() == 1)
                    startDialog(new Dialog(Arrays.asList("Today is your first official day at \nwork!", "I'll start you off lightly by giving \nyou a simple task.", "Please proceed straight up to fix \nthe electrical panel, it seems to be \nmalfunctioning today")));
                else
                    startDialog(new Dialog(Arrays.asList("Welcome back to work!", "How are you doing?", "Stop right there, it doesn't matter.", "I need you to clean out the mixing \nbin!")));
            }
        };
        boss.setImage(new Image("Entities/Boss.png", 80, 0, true, true));
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
