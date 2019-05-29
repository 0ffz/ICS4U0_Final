package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A class for managing sprites. Stores position, image, velocity
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Simple sprite with position, size and movement capabilities</p>
 * <p>0.1.2 - Renamed to entity, now stores layer. Velocity now slows down with friction.
 * New method for seeing what position will be for collision detection.</p>
 * <p>0.2.3 - Now holds a room object to remove itself from it when remove() is called.
 * Now holds a name that can be used as an identifier.</p>
 */
public class Entity {

    private Image image;
    protected Room room;
    private String name;

    public Entity(int layer) {
        this.layer = layer;
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
    }

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

    public Room getRoom() {
        return room;
    }

    public Entity setRoom(Room room) {
        this.room = room;
        return this;
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
        room.removeEntity(this);
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

    public void update(double time, double friction) {
        x += velocityX * time;
        y += velocityY * time;
        velocityX /= friction;
        velocityY /= friction;
    }

    public double[] simulateUpdate(double time) {
        return new double[]{velocityX * time, velocityY * time};
    }

    public void render(GraphicsContext gc, double time) {
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