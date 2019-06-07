package com.almostcreativegames.adversity.Rooms;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.TutorialMan;
import com.almostcreativegames.adversity.Entity.Characters.Wire;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.Equippable;
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
    private String[] stats = {"904 workers died from \nworkplace related injuries \nin 2016 alone.", "13% of the total young \nworkforce is composed of \nyoung workers.", "In 2015 403 young workers \ndied in the workplace."};

    public RoomManager(GameRunner game) {
        currentX = 0;
        currentY = 3;
        rooms[3][0] = new Room("Rooms/Home/Home.png");
        Wire wire = new Wire();
        wire.setPosition(600, 600);

        rooms[3][0].addEntity(wire);

        TutorialMan tutorialMan = new TutorialMan();
        tutorialMan.setPosition(300, 600);
        rooms[3][0].addEntity(tutorialMan);

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

        rooms[3][1] = new Room("Rooms/Home/Living Room.png");

        EntityAnimated eButton = new EntityAnimated() {
            @Override
            public void onRoomLoad() {
                if (room.getGame().getDay() > 0)
                    remove();
            }
        };

        eButton.addAnimation("appear", new SpriteAnimation("Entities/E.png", 0, 0, 50, 50, 2, 2, 1, 1, 2));
        eButton.setCurrentAnimation("appear");
        eButton.setPosition(370, 700);

        Entity mom = new Entity() {
            @Override
            public void onInteract() {
                //TODO remove all the Arrays.asList()
                if (game.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("You should be going to work \nhoney.", "Don't wanna be late on your \nfirst day!", "Oh and remember honey to \nalways stay safe!")) {
                        @Override
                        public void onEnd() {
                            eButton.remove();
                        }
                    });
                else
                    startDialog(new Dialog(Arrays.asList("See you later honey.", "Remember not to get hurt!")));
            }
        };


        mom.setImage(new Image("Entities/Mom.png", 80, 0, true, true));
        mom.setPosition(300, 715);

        rooms[3][1].addEntity(mom);
        if (game.getDay() == 0)
            rooms[3][1].addEntity(eButton);

        rooms[3][2] = new Room("Rooms/Outside/Road 1.png");
        rooms[3][3] = new Room("Rooms/Outside/Road 2.png");
        rooms[3][4] = new Room("Rooms/Outside/Outside Factory.png");
        rooms[2][2] = new Room("Rooms/Factory/Factory Floor 2.png");

        Entity electricalGloves = new Entity() {
            @Override
            public void onInteract() {
                Equippable gloves = new Equippable("Electrical Gloves");
                game.getEquipment().add(gloves);
                startDialog(new Dialog("You picked up some Electrical \ngloves",
                        "Be sure to equip them when \nneeded while working!"));
                remove();
            }

            @Override
            public void onRoomLoad() {
                if (room.getGame().hasEquipment("Electrical Gloves"))
                    remove();
            }
        };
        electricalGloves.setImage(new Image("Entities/Electrical Gloves.png", 50, 50, true, true));
        electricalGloves.setPosition(200, 715);

        rooms[2][2].addEntity(electricalGloves);

        rooms[2][3] = new Room("Rooms/Factory/Factory Floor.png");

        Entity electricalEmployee = new Entity() {
            @Override
            public void onInteract() {
                if (game.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hey there you seem new.", "I remember my first day here.", "I was excited to start my job \nand get working.", "But then I had suddenly \ngotten an injury from the \nelectrical box.", "Oh shoot I'm starting to \nramble.", "We should get back to work.", "Anyways nice meeting you \nalways make sure that you \nhave the proper equipment!")));
                else
                    startDialog(new Dialog(Arrays.asList("Hey there again.", "I hope you remember to \nalways use the right \nequipment!")));
            }
        };

        electricalEmployee.setImage(new Image("Entities/Electrician.png", 80, 0, true, true));
        electricalEmployee.setPosition(300, 390);
        rooms[2][3].addEntity(electricalEmployee);

        rooms[2][4] = new Room("Rooms/Factory/Factory Entrance.png");

        Entity boss = new Entity() {
            @Override
            public void onInteract() {
                if (game.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hello!", "Welcome to your first day of \nyour job.", "Today you can spend the day \nlooking around and learning \nabout the workplace.")));
                else if (game.getDay() == 1)
                    startDialog(new Dialog(Arrays.asList("Today is your first official day \nat work!", "I'll start you off lightly by \ngiving you a simple task.", "Please proceed straight up to \nfix the electrical panel, it \nseems to be malfunctioning \ntoday.")));
                else
                    startDialog(new Dialog(Arrays.asList("Welcome back to work!", "How are you doing?", "Stop right there, it doesn't matter.", "I need you to clean out the mixing \nbin!")));
            }
        };

        boss.setImage(new Image("Entities/Boss.png", 80, 0, true, true));
        boss.setPosition(590, 715);
        rooms[2][4].addEntity(boss);

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

        Entity statisticsMan = new Entity() {
            @Override
            public void onInteract() {
                if (game.getDay() == 0)
                    startDialog(new Dialog(Arrays.asList("Hello there I'm just getting \nsome research done for \nworkplace safety statistics!", "Oh, you're new?", "Well let me tell you that in \n2015 110.5 per 10000 full time \nworkers between the ages of \n16-19 suffered a non fatal \ninjury.", "Take care of yourself in there!")));
                else
                    startDialog(new Dialog(Arrays.asList("Hey there again.", stats[(int)(Math.random() * 3)])));
            }
        };

        statisticsMan.setImage(new Image("Entities/Statistics Man.png", 80, 0, true, true));
        statisticsMan.setPosition(50, 100);

        rooms[1][4].addEntity(statisticsMan);

        rooms[1][5] = new Room("Rooms/Factory/Factory Floor 4.png");
        rooms[1][6] = new Room("Rooms/Factory/Factory Floor 6.png");
        rooms[0][4] = new Room("Rooms/Factory/Game Room.png");
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
