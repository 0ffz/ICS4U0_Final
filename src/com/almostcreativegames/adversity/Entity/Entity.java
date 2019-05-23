package com.almostcreativegames.adversity.Entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * A class for managing sprites
 * TODO Make an entity class that uses this class
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */
public class Entity {
    private Image image;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean isRemoved;
    private int layer;

    public Entity() {
        x = 0;
        y = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public Entity(int layer) {
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public Entity setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Entity setY(double y) {
        this.y = y;
        return this;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void remove() {
        isRemoved = true;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        x += velocityX * time;
        y += velocityY * time;
        velocityX /= 1.3;
        velocityY /= 1.3;
    }

    public double[] simulateUpdate(double time) {
        return new double[]{velocityX * time, velocityY * time};
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean intersects(Entity s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + x + "," + y + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}