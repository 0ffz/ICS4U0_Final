package com.almostcreativegames.adversity;

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
public class Sprite {
    private Image image;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean isRemoved;
    public Sprite() {
        x = 0;
        y = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public double getX() {
        return x;
    }

    public Sprite setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Sprite setY(double y) {
        this.y = y;
        return this;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void remove() {
        isRemoved = true;
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
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
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + x + "," + y + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}