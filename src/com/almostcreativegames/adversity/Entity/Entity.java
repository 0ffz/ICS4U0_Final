package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A class for managing sprites. Stores position, image, velocity
 * The basis for this code was taken from the Sprite class here:
 * https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.4
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Simple sprite with position, size and movement capabilities</p>
 * <p>0.1.2 - Renamed to entity, now stores layer. Velocity now slows down with friction.
 * New method for seeing what position will be for collision detection.</p>
 * <p>1.2.3 - Now holds a room object to remove itself from it when remove() is called.
 * Now holds a name that can be used as an identifier.
 * Added functionality to hide and show the entity</p>
 * <p>1.3.4 - Added NPE fix in remove()</p>
 */
public class Entity {
    protected Room room;
    protected Image image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double friction = 1.3;
    private double lifetime;
    private String name;
    private double velocityX;
    private double velocityY;
    private boolean hidden;
    private int layer;
    private boolean removed = false;
    public Entity(int layer) {
        this.layer = layer;
    }

    public Entity() {
        x = 0;
        y = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public double getLifetime() {
        return lifetime;
    }

    public void startDialog(Dialog dialog) {
        room.getGame().startDialog(dialog, room);
    }

    /**
     * Called when the player overlaps and presses "E" on the entity
     */
    public void onInteract() {
    }

    /**
     * Called when the player intersects with the entity
     */
    public void onIntersect() {
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
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

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
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
        return removed;
    }

    public boolean isHidden() {
        return hidden;
    }

    /**
     * Hides the entity
     */
    public void hide() {
        hidden = true;
    }

    /**
     * Shows the entity
     */
    public void show() {
        hidden = false;
    }

    /**
     * Removes the entity. If it is being rendered, the entity will be removed from the render map
     */
    public void remove() {
        removed = true;
        if (room == null)
            return;
        room.removeEntity(this);
    }

    /**
     * Sets the position of the entity
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the entity velocity
     *
     * @param x the x velocity
     * @param y the y velocity
     */
    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    /**
     * Adds velocity to the existing values
     *
     * @param x the x velocity to be added
     * @param y the y velocity to be added
     */
    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    /**
     * Updates the position of the entity and its velocity
     *
     * @param time     the amount of time that has passed between the last frame being drawn
     * @param friction the friction with which the entity's velocity should be slowed by
     */
    public void update(double time, double friction) {
        x += velocityX * time;
        y += velocityY * time;
        velocityX /= friction;
        velocityY /= friction;
    }

    /**
     * Simulates an update in velocity but does not actually change the values. Used for checking whether the entity
     * will collide and prevent it from doing so.
     *
     * @param time the amount of time that has passed between the last frame being drawn
     * @return the future x and y velocities
     */
    public double[] simulateUpdate(double time) {
        return new double[]{velocityX * time, velocityY * time};
    }

    /**
     * Renders the entity. time may be used by subclasses which overrides this method, such as the AnimatedEntity class.
     *
     * @param gc   the GraphicsContext with which to render
     * @param time the amount of time that has passed between the last frame being drawn
     */
    public void render(GraphicsContext gc, double time) {
        gc.drawImage(image, x, y);
        update(time, friction);
        lifetime += time;
    }

    /**
     * Gets the boundaries (used as a collider) of the entity
     *
     * @return returns a Rectangle2D object composed of the boundaries
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Checks whether this Entity's boundaries intersect with another entity
     *
     * @param other the Entity whose boundaries we are comparing with
     * @return whether they intersect
     */
    public boolean intersects(Entity other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + x + "," + y + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}