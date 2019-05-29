package com.almostcreativegames.adversity;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.AnimatedEntity;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
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
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The main class for running the game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic game setup</p>
 * <p>0.1.2 - Added has proper room transition and an animated player. Moved many important objects to be instance
 * variables</p>
 */
public class GameRunner extends Application {
    private AnimatedEntity player = new AnimatedEntity(5);
    private Entity dialogBox = new Entity(6);
    private Canvas canvas = new Canvas(1000, 1000);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private RoomManager rooms = new RoomManager();
    private Renderer renderer = new Renderer(gc);


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
//        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press 'F11' to toggle fullscreen");

        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<String>(); //the keys that are currently pressed

        //track keys being pressed and released
        scene.setOnKeyPressed(e -> { //lambda used as per Intellij's suggestion
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Room currentRoom = rooms.getCurrentRoom();

        String playerSprite = "Entities/Player/Player-spritesheet.png";
        player.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 1));
        player.addAnimation("left", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("right", new SpriteAnimation(playerSprite, 0, 30, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("up", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        player.addAnimation("down", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 5));

        player.setCurrentAnimation("idle");
        player.setPosition(600, 600);
        currentRoom.addEntity(player);

        rooms.loadRoom(renderer, 0, 0); //load starting room

        //what actually runs during the game
        final long[] lastNanoTime = {System.nanoTime()};
        final int[] score = {0};
        new AnimationTimer() {
            long startTime = System.currentTimeMillis();

            public void handle(long currentNanoTime) {
                Room currentRoom = rooms.getCurrentRoom();
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                // game logic

                if (input.contains("LEFT") || input.contains("A")) player.addVelocity(-100, 0);
                if (input.contains("RIGHT") || input.contains("D")) player.addVelocity(100, 0);
                if (input.contains("UP") || input.contains("W")) player.addVelocity(0, -100);
                if (input.contains("DOWN") || input.contains("S")) player.addVelocity(0, 100);
                if (input.contains("F11") && System.currentTimeMillis() - startTime > 100) { //TODO eventually have buttonpress objects that can take in a delay/only be clicked once
                    stage.setFullScreen(!stage.isFullScreen());
                    startTime = System.currentTimeMillis();
                }
                if (input.contains("E")) {
                    if (!dialogBox.isRemoved()) {
                        dialogBox.setImage(new Image("DialogBox.png", 500, 0, true, true));
                        dialogBox.setPosition(250, 700);
                        currentRoom.addEntity(dialogBox);
                        renderer.register(dialogBox, dialogBox.getLayer());
                    }
                    else{
                        //TODO Remove the dialog box
                    }
                }

                if (currentRoom.isColliding(player, elapsedTime)) {
                    double tempVX = player.getVelocityX();
                    player.setVelocity(0, player.getVelocityY());
                    if (currentRoom.isColliding(player, elapsedTime)) {
                        player.setVelocity(tempVX, 0);
                        if (currentRoom.isColliding(player, elapsedTime))
                            player.setVelocity(0, 0);
                    }

                }
                player.update(elapsedTime, 1.3);

                player.setCurrentAnimation("idle");
                if (player.getVelocityY() < -1)
                    player.setCurrentAnimation("up");
                else if (player.getVelocityY() > 1)
                    player.setCurrentAnimation("down");
                else if (player.getVelocityX() < -1)
                    player.setCurrentAnimation("left");
                else if (player.getVelocityX() > 1)
                    player.setCurrentAnimation("right");

                // TODO collision detection example; Enfei use something like this for initiating dialogs
                for (Entity collider : currentRoom.getIntersects(player)) {
                    score[0]++;
//                    if(collider.getName().equals("Mom"))
//                        dialogBox.doSomething()
                }

                //render
                gc.clearRect(0, 0, 1000, 1000);
                renderer.render(elapsedTime);

                String pointsText = "Cash: $" + (100 * score[0]);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);

                wrapScreen(player);
            }
        }.start();

        scene.setFill(Color.BLACK); //makes letterbox black
        letterbox(scene);
        stage.show();

        stage.setOnCloseRequest((e) -> {
//            System.out.println("Stage is closing");

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

    public void wrapScreen(Entity entity) {
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
            rooms.getCurrentRoom().moveEntity(rooms.getRoomAtOffset(offsetX, offsetY), player);
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