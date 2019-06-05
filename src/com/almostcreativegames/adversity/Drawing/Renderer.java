package com.almostcreativegames.adversity.Drawing;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class for easing rendering of objects. Allows placing sprites int layers.
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic map with layer functionality</p>
 * <p>0.1.2 - Added background and changed to TreeMap to preserve layer order</p>
 * <p>1.2.3 - No longer checks if the image is null, since some entities render only text or shapes</p>
 */
public class Renderer {
    private GraphicsContext gc;
    private Room currentRoom;
    //TODO Instead of containing a list of Sprites, have a layer object which would render in order
    // based on an object's y coordinate.
    private Map<Integer, CopyOnWriteArrayList<Entity>> layers = new TreeMap<>(); //Concurrent version of ArrayList solves concurrency problems https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk

    /**
     * An object for rendering Entities onto a GraphicsContext
     *
     * @param gc used to draw the entities
     */
    public Renderer(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Returns the room that is currently rendering
     *
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Register sprites to be rendered when render() is called
     *
     * @param entity the entity to be registered
     */
    public void register(Entity entity) {
        int layer = entity.getLayer();
        if (layers.get(layer) == null)
            layers.put(layer, new CopyOnWriteArrayList<Entity>() {{
                add(entity);
            }});
        else
            layers.get(layer).add(entity);
    }

    /**
     * Clears the registered entities in the TreeMap
     */
    public void unregisterAll() {
        layers = new TreeMap<>();
    }

    /**
     * Render all registered entities
     *
     * @param time the amount of time passed between frames (used by EntityAnimated)
     */
    public void render(double time) {
        gc.drawImage(currentRoom.getBackground(), 0, 0); //draw the background first, then every other entity in order by layer

        for (List<Entity> layer : layers.values()) {
            for (Entity entity : layer) {
                if (entity.isRemoved()) { //if the entity got removed, unregister it
                    layer.remove(entity);
                    continue;
                }
                if (entity.isVisible()) //if the entity is hidden, don't render it
                    entity.render(gc, time);
            }
        }
    }

    /**
     * Loads a room to render it and its held entities
     *
     * @param room the room to be loaded
     */
    public void loadRoom(Room room) {
        currentRoom = room;
        unregisterAll();
        if (room != null) {
            for (Entity entity : room.getEntities())
                register(entity);
            for (Entity e : room.getEntities()) {
                System.out.print(e.getName() + ", ");
            }
            System.out.println();
        } else
            System.out.println("Room is null");
    }
}
