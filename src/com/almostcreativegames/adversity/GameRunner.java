package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Battle.EndScreen;
import com.almostcreativegames.adversity.Battle.GameOverScreen;
import com.almostcreativegames.adversity.Battle.SleepScreen;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Dialog.DialogBox;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.Wire;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Equippable;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.Input.InputListener;
import com.almostcreativegames.adversity.Rooms.Room;
import com.almostcreativegames.adversity.Rooms.RoomManager;
import com.almostcreativegames.adversity.Saves.Save;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * The main class for running the game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy & Enfei Zhang
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic game setup</p>
 * <p>0.1.2 - Added has proper room transition and an animated player. Moved many important objects to be instance
 * variables</p>
 * <p>0.2.3 -
 * Daniel: Moved player collision and input detection into separate class. Added entity interaction system (pressing "E")
 * Enfei: Added dialogBox</p>
 */
public class GameRunner extends Application {
    private int day = 0;
    private Player player = new Player();
    private Canvas canvas = new Canvas(1000, 1000);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Renderer renderer = new Renderer(gc);
    private RoomManager rooms = new RoomManager(this);
    private DialogBox dialogBox;
    private Set<Equippable> equipment = new TreeSet<>();
    private double playerHealth = 10;
    private double maxPlayerHealth = 10;
    private List<String> gameAttributes = new ArrayList<>();
    private Stage stage;
    private Player currentPlayer;
    private InputListener inputListener;
    private boolean loadSave;
    private boolean close;

    public GameRunner() {
        this.loadSave = true;
    }

    public GameRunner(boolean loadSave) {
        this.loadSave = loadSave;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Resizes the scene to fit into new window dimensions
     *
     * @param scene     the scene to be resized
     * @param newWidth  the new width
     * @param newHeight the new height
     */
    private static void resize(Scene scene, double newWidth, double newHeight) {
        double scaleAmount = newWidth / 1000;
        if (newWidth > newHeight) {
            scaleAmount = newHeight / 1000;
            scene.getRoot().setTranslateX((newWidth - newHeight) / 2);
        } else
            scene.getRoot().setTranslateX(0);

        Scale scale = new Scale(scaleAmount, scaleAmount);
        scale.setPivotX(0);
        scale.setPivotY(0);
        scene.getRoot().getTransforms().setAll(scale);

    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public boolean hasAttribute(String attribute) {
        return gameAttributes.contains(attribute);
    }

    public boolean addAttribute(String attribute) {
        return gameAttributes.add(attribute);
    }

    public void removeAttribute(String attribute) {
        gameAttributes.remove(attribute);
    }

    public int getDay() {
        return day;
    }

    public void nextDayIfJobDone() {
        if (hasAttribute("Job done")) {
            day++;
            removeAttribute("Job done");
            Save.saveGame(day, gameAttributes, equipment);
            playSleepingScene();
            playMorningMessage();
        } else {
            startDialog(new Dialog("It is not time to sleep yet"), rooms.getCurrentRoom());
        }
    }

    private void playMorningMessage() {
        List<String> messages = new ArrayList<>();
        messages.add("Day " + (day + 1) + " of your job begins!");
        if (day == 1)
            messages.add("You seem to still be\nwearing the same clothes...\nMaybe you will change them\ntomorrow.");
        else if (day == 2)
            messages.add("After much consideration,\nyou have not decided to\nchange your clothes today.");
        else if (day == 3)
            messages.add("You wonder how often your\nboss changes his clothes.");
        else if (day == 4)
            messages.add("You tell yourself you will\ndefinitely change clothes\ntomorrow. After all,\nyou're going on vacation.");
        else if(day >= 5) {
            playEndScene();
            return; //don't play the morning dialog, go to the end scene instead
        }
        startDialog(new Dialog(messages), rooms.getCurrentRoom());
    }

    private void playEndScene() {
        EndScreen endScreen = new EndScreen("Battle/Empty.png", rooms.getCurrentRoom(), this);
        renderer.loadRoom(endScreen);
    }

    private void playSleepingScene() {
        SleepScreen sleepScreen = new SleepScreen("Battle/Empty.png", rooms.getCurrentRoom(), this);
        renderer.loadRoom(sleepScreen);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public double getMaxPlayerHealth() {
        return maxPlayerHealth;
    }

    public void setMaxPlayerHealth(double maxPlayerHealth) {
        this.maxPlayerHealth = maxPlayerHealth;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(double playerHealth) {
        this.playerHealth = playerHealth;
    }

    public Set<Equippable> getEquipment() {
        return equipment;
    }

    public void toggleEquipped(String name) {
        for (Equippable equippable : equipment)
            if (equippable.compareTo((new Equippable(name))) == 0) {
                equippable.setEquipped(!equippable.isEquipped());
                return;
            }
    }

    public boolean hasEquipment(String name) {
        return equipment.contains(new Equippable(name));
    }

    public boolean isEquipped(String name) {
        for (Equippable equippable : equipment)
            if (equippable.compareTo((new Equippable(name))) == 0)
                return equippable.isEquipped();
        return false;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Runs when launched. The basis of this code was taken from a money bag collecting game from
     * https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development
     * TODO create references page
     *
     * @param stage the stage that got created
     */
    @Override
    public void start(Stage stage) {
        //loading save file
        if (loadSave) {
            try {
                BufferedReader reader = Save.getSave();
                String line;

                day = Integer.parseInt(reader.readLine());
                //read the save file's lines until "Equipment"
                while ((line = reader.readLine()) != null && !(line.equals("Equipment")))
                    if (!hasAttribute(line)) //avoid repeating any attributes just in case
                        addAttribute(line);

                //adds equipment
                while ((line = reader.readLine()) != null) { //read the save file's lines
                    Equippable equippable = new Equippable(line);
                    equippable.setEquipped(Boolean.parseBoolean(reader.readLine()));
                    equipment.add(equippable);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) { //if no save exists, make one
                Save.saveGame(day, gameAttributes, equipment);
            }
        }
        //TODO remove all prints
        System.out.println(Arrays.toString(gameAttributes.toArray()));

        this.stage = stage;

        stage.setTitle("Don't get hurt, stay at work!");

        //create the scene
        Pane root = new Pane();
        Scene scene = new Scene(new Group(root), 1000, 1000);
        stage.setScene(scene);
        stage.setResizable(true);
        //TODO make game start fullscreen
//        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press 'F11' to toggle fullscreen");

        //Initialize the input listener
        inputListener = new InputListener(scene);

        //Initialize canvas
        root.getChildren().add(canvas);

        //setup player
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        player.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 1));
        player.addAnimation("left", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("right", new SpriteAnimation(playerSprite, 0, 30, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("down", new SpriteAnimation(playerSprite, 0, 45, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("up", new SpriteAnimation(playerSprite, 0, 60, 11, 15, 2, 2, 5, 5, 5));
        player.setCurrentAnimation("idle");
        player.setPosition(145, 800);
        setCurrentPlayer(player);
        rooms.getCurrentRoom().addEntity(player); //we are using rooms' getCurrentRoom because we haven't actually loaded any room yet, so the renderer would give a NPE

        //setup dialog box
        dialogBox = new DialogBox();
        dialogBox.setImage(new Image("Menu/DialogBox.png", 500, 0, true, true));
        dialogBox.setPosition(250, 700);
        renderer.register(dialogBox);
        rooms.getCurrentRoom().addEntity(dialogBox);
        dialogBox.hide();

        //load starting room
        rooms.loadRoom(renderer, 0, 0);

        //play intro if player started a new game
        if (!loadSave) {
            playSleepingScene();
            startDialog(new Dialog("You just woke up from sleep",
                    "It seems like today is an\nimportant day, but you can't\nseem to remember why",
                    "Surprisingly, you are already\ndressed in your clothes",
                    "You should probably go see\nmom to check what you're\ndoing today"), rooms.getCurrentRoom());
        } else {
            playMorningMessage();
        }
        //what actually runs during the game
        final long[] lastNanoTime = {System.nanoTime()}; //we turn these outside values into singular object arrays to be able to change them within the AnimationTimer

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if(close) {
                    close();
                    openMainMenu();
                    stop();
                    return;
                }
                Room currentRoom = renderer.getCurrentRoom();
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                //TODO remove these test keys
                if (inputListener.isKeyPressed("M", 100)) {
                    Wire wire = new Wire();
                    Battle battle = new Battle("Battle/Battle.png", wire, currentRoom, GameRunner.this);
                    renderer.loadRoom(battle);
                }

                if (inputListener.isKeyPressed("C", 100)) {
                    addAttribute("Job done");
                }

                if (inputListener.isKeyPressed("N", 100)) {
                    if (currentRoom instanceof Battle)
                        ((Battle) currentRoom).endBattle();
                }

                if (inputListener.isKeyPressed("F11", 200))
                    stage.setFullScreen(!stage.isFullScreen());

                if (inputListener.isKeyPressed("E", 200)) {
                    if (dialogBox.hasDialog()) {
                        dialogBox.nextMessage();
                    } else
                        for (Entity entity : currentRoom.getEntities()) {
                            if (entity.intersects(currentPlayer) && entity.isVisible()) {
                                entity.onInteract();
                            }
                        }
                }

                //check for entities intersecting with the player
                for (Entity entity : currentRoom.getEntities())
                    if (entity.intersects(currentPlayer) && entity.isVisible())
                        entity.onIntersect();

                //render
                gc.clearRect(0, 0, 1000, 1000);
                renderer.render(elapsedTime);
                wrapScreen(currentPlayer);
            }
        }.start();

        scene.setFill(Color.BLACK); //makes letterbox black
        letterbox(scene);
        stage.show();

        stage.setOnCloseRequest((e) -> {
//            openMainMenu();
        });

    }

    public void openMainMenu() {
        Stage mainStage = new Stage();
        Main main = new Main();
        try {
            main.start(mainStage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void saveGame() {
        Save.saveGame(day, gameAttributes, equipment);
    }

    public void gameOver() {
        GameOverScreen gameOverScreen = new GameOverScreen("Battle/Empty.png", this);
        renderer.loadRoom(gameOverScreen);
    }

    public void startDialog(Dialog dialog, Room room) {
        dialogBox.getRoom().moveEntity(room, dialogBox); //move dialog box to specified room
        dialogBox.setDialog(dialog);
        dialogBox.show();
        getCurrentPlayer().setCanMove(false);
    }

    public void close() {
        try {
            stage.close();
        }
        /*some parts of the program run in a timer which runs on a separate thread than the JavaFX application, and thus
        cannot close it. To bypass this, we store that we want to close the game and close it on the next frame within
        this class*/
        catch (IllegalStateException e){
            close = true;
        }
    }

    private void wrapScreen(Entity entity) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        Rectangle2D boundary = entity.getBoundary();
        int offsetX = 0;
        int offsetY = 0;

        if (boundary.getMinX() > w) {
            entity.setX(-boundary.getMaxX() + boundary.getMinX());
            offsetX++;
        } else if (entity.getBoundary().getMaxX() < 0) {
            entity.setX(w);
            offsetX--;
        }

        if (boundary.getMinY() > h) {
            entity.setY(-boundary.getMaxY() + boundary.getMinY());
            offsetY++;
        } else if (entity.getBoundary().getMaxY() < 0) {
            entity.setY(h);
            offsetY--;
        }
        if (offsetX != 0 || offsetY != 0) {
            renderer.getCurrentRoom().moveEntity(rooms.getRoomAtOffset(offsetX, offsetY), entity);
            rooms.loadRoom(renderer, offsetX, offsetY);
        }
    }

    /**
     * Code to resize and letterbox the scene adapted from https://gist.github.com/jewelsea/5603471
     *
     * @param scene the scene
     */
    private void letterbox(final Scene scene) {
        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;

        SceneSizeChangeListener(Scene scene) {
            this.scene = scene;
        }

        //TODO Adapt to work with non-square canvases if we ever need to
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();
            resize(scene, newWidth, newHeight);
        }
    }

}