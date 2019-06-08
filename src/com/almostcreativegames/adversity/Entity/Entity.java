package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.GameRunner;
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
    private EntityAnimated interactIndicator;
    private double lifetime;
    private String name = "unnamed";
    private double velocityX;
    private double velocityY;
    private boolean hidden;
    private int layer;
    private boolean removed = false;

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
        getGame().startDialog(dialog, room);
    }

    /**
     * @return a reference to the GameRunner
     */
    public GameRunner getGame() {
        return room.getGame();
    }

    /**
     * Called when the room is loaded
     */
    public void onRoomLoad() {

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

    /**
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the entity to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the room the entity is in
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room for the entity to be put in
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the X velocity
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * @return the Y velocity
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * @return the entity's sprite
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the entity image and its width and height based on the sprite
     *
     * @param image the image the entity should be set to
     */
    public void setImage(Image image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * @param filename a path to the entity's sprite
     */
    public void setImage(String filename) {
        setImage(new Image(filename));
    }

    /**
     * @return the layer the entity is on
     */
    public int getLayer() {
        return layer;
    }

    /**
     * @param layer the layer for the entity to be placed on
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * @return the X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * @param x new X coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the Y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * @param y new Y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return whether the entity is removed
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * @return whether the entity is visible
     */
    public boolean isVisible() {
        return !hidden;
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

    @Override
    public String toString() {
        return " Position: [" + x + "," + y + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public void hideInderactIndicator() {
        if (interactIndicator != null) {
            interactIndicator.remove();
            interactIndicator = null;
        }
    }

    public void showInteractIndicator() {
        interactIndicator = new EntityAnimated();
        interactIndicator.addAnimation("flash", new SpriteAnimation("Menu/Tutorial/E to interact.png", 0, 0, 50, 50, 1, 2, 1, 1, 1));
        interactIndicator.setCurrentAnimation("flash");
        interactIndicator.setPosition(x + width / 2 - 25, y - 60);
        room.addEntity(interactIndicator);
    }
}