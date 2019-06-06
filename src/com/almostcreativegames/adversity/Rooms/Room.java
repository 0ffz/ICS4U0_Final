package com.almostcreativegames.adversity.Rooms;


import com.almostcreativegames.adversity.Drawing.Renderer;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.GameRunner;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Room system that holds entities, a background and collision map from black and white image.
 * Can check if an entity is going to collide with the collision map. Can move entities between rooms.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Beginning of scene system</p>
 * <p>0.1.2 - Renamed to Room, now holds entities, a background and collision map from black and white image.
 * Can check if an entity is going to collide with the collision map. Can move entities between rooms.</p>
 * <p>0.2.3 Added entity collision checker. Fixed bugs to do with concurrency. Now using direct reference to GameRunner object</p>
 */

public class Room {
    protected Renderer renderer;
    protected GameRunner game;
    private Image background;
    private boolean[][] collision;
    private List<Entity> entities = new CopyOnWriteArrayList<>(); //fix concurrency issues when going through entities

    public Room(String imageURL) {
        String fileExtension = imageURL.substring(imageURL.indexOf("."));
        background = new Image(imageURL, 1000, 1000, false, true);
        Image backgroundCollision = new Image(imageURL.substring(0, imageURL.indexOf(".")) + " col" + fileExtension);
        collision = convertToCollisionMap(backgroundCollision);
    }

    private static boolean[][] convertToCollisionMap(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        boolean[][] result = new boolean[width][height];

        PixelReader reader = image.getPixelReader();
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                result[row][col] = reader.getColor(row, col).equals(Color.BLACK);
            }
        }
        return result;
    }

    public GameRunner getGame() {
        return game;
    }

    public void setGame(GameRunner game) {
        this.game = game;
        renderer = game.getRenderer();
    }

    public boolean isColliding(Entity e, double time) {
//        return false;
        double[] change = e.simulateUpdate(time);
        double changeX = change[0];
        double changeY = change[1];

        int topX = toIntCoord(e.getX() + changeX);
        int topY = toIntCoord(e.getY() + changeY);
        int botX = toIntCoord(e.getBoundary().getMaxX() + changeX);
        int botY = toIntCoord(e.getBoundary().getMaxY() + changeY);

        //check if the boundaries of our future position overlap with a wall
        for (int row = topX; row <= botX; row++)
            for (int col = topY; col <= botY; col++) {
                //here we change the boundaries of the bounding box we got to stay in bounds
                if (row < 0)
                    row = 0;
                else if (row >= 100) {
                    row = 99;
                    botX = 99;
                }
                if (col < 0)
                    col = 0;
                else if (col >= 100) {
                    col = 99;
                    botY = 99;
                }
                //then we check if any of the positions in the box are going to be on a collider
                if (collision[row][col])
                    return true;
            }
        return false;
    }

    public ArrayList<Entity> getIntersects(Entity entity) {
        ArrayList<Entity> intersects = new ArrayList<>();
        for (Entity other : entities)
            if (!entity.equals(other) && entity.intersects(other))
                intersects.add(other);
        return intersects;
    }

    private int toIntCoord(double coord) {
        return (int) (coord / 10);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e) {
        e.setRoom(this);
        entities.add(e);
        if (renderer == null)
            return;
        Room renderedRoom = renderer.getCurrentRoom();
        if (renderedRoom != null && renderedRoom.equals(this))
            renderer.register(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public void moveEntity(Room newRoom, Entity entity) {
        if (newRoom == this) //prevent moving within the same room
            return;
        entities.remove(entity);
        newRoom.addEntity(entity);
    }
}
