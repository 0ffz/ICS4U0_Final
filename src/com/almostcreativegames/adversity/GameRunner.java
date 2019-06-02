package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.DialogBox;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Characters.Wire;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.Input.InputListener;
import com.almostcreativegames.adversity.Rooms.Room;
import com.almostcreativegames.adversity.Rooms.RoomManager;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * The main class for running the game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic game setup</p>
 * <p>0.1.2 - Added has proper room transition and an animated player. Moved many important objects to be instance
 * variables</p>
 * <p>0.2.3 - Moved player collision and input detection into separate class</p>
 */
public class GameRunner extends Application {
    private Player player = new Player();
    private Entity mom = new Entity();
    private Canvas canvas = new Canvas(1000, 1000);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Renderer renderer = new Renderer(gc);
    private RoomManager rooms = new RoomManager(renderer);
    private DialogBox dialogBox = new DialogBox(5);

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

    /**
     * Runs when launched. The basis of this code was taken from a money bag collecting game from
     * https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development
     *
     * @param stage the stage that got created
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Don't get hurt, stay at work!");

        //create the scene
        Pane root = new Pane();
        Scene scene = new Scene(new Group(root));
        stage.setScene(scene);
        stage.setResizable(true);
        InputListener.registerScene(scene);
//        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press 'F11' to toggle fullscreen");

        root.getChildren().add(canvas);

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        //setup player
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        player.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 1));
        player.addAnimation("left", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("right", new SpriteAnimation(playerSprite, 0, 30, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("up", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("down", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 5));
        player.setCurrentAnimation("idle");
        player.setPosition(600, 600);
        Player.setCurrentPlayer(player);
        rooms.getCurrentRoom().addEntity(player); //we are using rooms' getCurrentRoom because we haven't actually loaded any room yet, so the renderer would give a NPE

        //setup dialog box
        dialogBox.setImage(new Image("DialogBox.png", 500, 0, true, true));
        dialogBox.setPosition(250, 700);
        renderer.register(dialogBox);
        dialogBox.setPlayer(player);
        rooms.getCurrentRoom().addEntity(dialogBox);
        dialogBox.hide();

        //setup other entities
        mom.setImage(new Image("Mom.jpg", 90, 0, true, true));
        mom.setPosition(300, 715);
        mom.setDialog("You should be going to work honey.");
        renderer.register(mom);
        rooms.getCurrentRoom().addEntity(mom);
        mom.hide();

        //load starting room
        rooms.loadRoom(renderer, 0, 0);

        //what actually runs during the game
        final long[] lastNanoTime = {System.nanoTime()}; //we turn these outside values into singular object arrays to be able to change them within the AnimationTimer
        final int[] score = {0};
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                Room currentRoom = renderer.getCurrentRoom();
                Player currentPlayer = Player.getCurrentPlayer();
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                if (InputListener.isKeyPressed("M", 100)) {
                    Wire wire = new Wire();
                    //TODO create battle background and colliders
                    Battle battle = new Battle("Rooms/Factory Entrance", wire, currentRoom, renderer);
                    renderer.loadRoom(battle);
                }

                if (InputListener.isKeyPressed("N", 100)) {
                    if (currentRoom instanceof Battle)
                        ((Battle) currentRoom).endBattle();
                }

                if (InputListener.isKeyPressed("F11", 200)) {
                    stage.setFullScreen(!stage.isFullScreen());
                }

                if (InputListener.isKeyPressed("E", 200)) {
                    if (!dialogBox.isHidden()) {
                        dialogBox.hide();
                        currentPlayer.setCanMove(true);
                    } else {
                        dialogBox.show();
                        currentPlayer.setCanMove(false);
                    }
                }

                if (currentRoom.equals(rooms.getRoom(3, 1))) {
                    currentRoom.addEntity(mom);
                    mom.show();
                }
                else {
                    currentRoom.removeEntity(mom);
                    mom.hide();
                }

                currentPlayer.update(elapsedTime, 1.3);

                //render
                gc.clearRect(0, 0, 1000, 1000);
                renderer.render(elapsedTime);

                String pointsText = "Cash: $" + (100 * score[0]);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);
                wrapScreen(currentPlayer);
            }
        }.start();

        scene.setFill(Color.BLACK); //makes letterbox black
        letterbox(scene);
        stage.show();

        stage.setOnCloseRequest((e) -> {
            //TODO temporarily turned off while debugging
            /*Stage mainStage = new Stage();
            Main main = new Main();
            try {
                main.start(mainStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }*/
        });

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
            renderer.getCurrentRoom().moveEntity(rooms.getRoomAtOffset(offsetX, offsetY), dialogBox);
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

        resize(scene, scene.getWidth(), scene.getHeight());
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;

        public SceneSizeChangeListener(Scene scene) {
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