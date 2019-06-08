package com.almostcreativegames.adversity.Rooms;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.*;
import com.almostcreativegames.adversity.Entity.Characters.HelperWorkers.ChemicalHelper;
import com.almostcreativegames.adversity.Entity.Characters.HelperWorkers.ConveyorHelper;
import com.almostcreativegames.adversity.Entity.Characters.HelperWorkers.MixerHelper;
import com.almostcreativegames.adversity.Entity.Characters.HelperWorkers.WireHelper;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Objects.Gloves;
import com.almostcreativegames.adversity.Entity.Objects.Wire;
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
        rooms[3][0] = new Room("Rooms/Home/Home.png");

        Entity bed = new Entity() {
            @Override
            public void onInteract() {
                game.nextDayIfJobDone();
                //Transitions.sleepScene();
            }
        };
        bed.setImage(new Image("Entities/Bed.png", 150, 0, true, true));
        bed.setPosition(100, 725);
        rooms[3][0].addEntity(bed);

        Entity wasdButton = new Entity() {
            @Override
            public void onRoomLoad() {
                if (getGame().getDay() > 0)
                    remove();
            }

            @Override
            public void onIntersect() {
                remove();
            }
        };
        wasdButton.setImage("Menu/Tutorial/WASD.png");
        wasdButton.setPosition(200, 800);
        rooms[3][0].addEntity(wasdButton);

        EntityAnimated arrow = new EntityAnimated() {
            @Override
            public void onRoomLoad() {
                if (getGame().getDay() > 0)
                    remove();
            }

            @Override
            public void onIntersect() {
                remove();
            }
        };
        arrow.addAnimation("flash", new SpriteAnimation("Menu/Tutorial/Arrow.png", 0, 0, 36, 23, 1, 2, 5, 5, 1));
        arrow.setCurrentAnimation("flash");
        arrow.setPosition(800, 450);

        rooms[3][0].addEntity(arrow);

        rooms[3][1] = new Room("Rooms/Home/Living Room.png");

        Mom mom = new Mom();

        rooms[3][1].addEntity(mom);

        rooms[3][2] = new Room("Rooms/Outside/Road 1.png");
        rooms[3][3] = new Room("Rooms/Outside/Road 2.png");
        rooms[3][4] = new Room("Rooms/Outside/Outside Factory.png");
        rooms[2][2] = new Room("Rooms/Factory/Factory Floor 2.png");

        Gloves electricalGloves = new Gloves("Electrical");
        Gloves chemicalGloves = new Gloves("Chemical");

        rooms[2][2].addEntity(electricalGloves);
        rooms[2][2].addEntity(chemicalGloves);

        rooms[2][3] = new Room("Rooms/Factory/Factory Floor.png");

        ElectricalEmployee electricalEmployee = new ElectricalEmployee();

        rooms[2][3].addEntity(electricalEmployee);

        rooms[2][4] = new Room("Rooms/Factory/Factory Entrance.png");

        TutorialMan tutorialMan = new TutorialMan();

        Boss boss = new Boss(tutorialMan);

        rooms[2][4].addEntity(boss);
        boss.showInteractIndicator();
        rooms[2][4].addEntity(tutorialMan);


        rooms[2][5] = new Room("Rooms/Factory/Factory Floor.png");

        Entity workerTwo = new Entity() {
            @Override
            public void onInteract() {
                if (game.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Ow", "Oof", "Ow", "Oh hi there.", "Don't mind me, I'm just kinda \nhurt from my last job.", "My boss gave me a task that \nwas too dangerous, and I \ndidn't say no until it was too \nlate.", "You should know that you can \nrefuse work if it is deemed \ntoo dangerous", "Ow")));
                else
                    startDialog(new Dialog(Arrays.asList("Ow", "Remember you can refuse \nwork that is dangerous!", "Ow")));
            }
        };

        workerTwo.setImage(new Image("Entities/Worker 2.png", 80, 0, true, true));
        workerTwo.setPosition(790, 800);

        rooms[2][5].addEntity(workerTwo);

        rooms[2][6] = new Room("Rooms/Factory/Factory Floor 3.png");
        rooms[1][2] = new Room("Rooms/Factory/Factory Floor 5.png");
        rooms[1][3] = new Room("Rooms/Factory/Factory Floor 4.png");
        rooms[1][4] = new Room("Rooms/Outside/Outside Game Room.png");

        StatisticsMan statisticsMan = new StatisticsMan();

        rooms[1][4].addEntity(statisticsMan);

        rooms[1][5] = new Room("Rooms/Factory/Factory Floor 4.png");
        rooms[1][6] = new Room("Rooms/Factory/Factory Floor 6.png");
        rooms[0][4] = new Room("Rooms/Factory/Game Room.png");

        Wire wire = new Wire();

        WireHelper electricalHelper = new WireHelper();
        ChemicalHelper chemicalHelper = new ChemicalHelper();
        ConveyorHelper conveyorHelper = new ConveyorHelper();
        MixerHelper mixerHelper = new MixerHelper();

        rooms[0][4].addEntity(electricalHelper);
        rooms[0][4].addEntity(chemicalHelper);
        rooms[0][4].addEntity(mixerHelper);
        rooms[0][4].addEntity(conveyorHelper);
        rooms[0][4].addEntity(wire);

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
