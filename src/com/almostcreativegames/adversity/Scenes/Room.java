package com.almostcreativegames.adversity.Scenes;


import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Eventually will be a map of scenes for out game.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class Room {
    private Image background;
    private boolean[][] collision;
    private List<Entity> entities = new ArrayList<Entity>();

    public Room(String imageURL) {
        background = new Image(imageURL + ".png", 1000, 1000, false, true);
        Image backgroundCollision = new Image(imageURL + " col.png");
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

    public boolean isColliding(Entity e, double time) {
//        return false;
        double[] change = e.simulateUpdate(time);
        double changeX = change[0];
        double changeY = change[1];

        int topX = toIntCoord(e.getX() + changeX), topY = toIntCoord(e.getY() + changeY);
        int botX = toIntCoord(e.getBoundary().getMaxX() + changeX), botY = toIntCoord(e.getBoundary().getMaxY() + changeY);

        if (topX >= 0 && topY >= 0 && topX < 100 && topY < 100 && collision[topX][topY])
            return true;
//        if (botX >= 0 && botY >= 0 && botX < 100 && botY < 100 && collision[botX][botY])
//            return true;
        return false;
    }

    private int toIntCoord(double coord) {
        return (int) (coord / 10);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public Image getBackground() {
        return background;
    }

    public Room setBackground(Image background) {
        this.background = background;
        return this;
    }

    public void moveEntity(Room newRoom, Entity entity) {
        entities.remove(entity);
        newRoom.addEntity(entity);
    }
}
