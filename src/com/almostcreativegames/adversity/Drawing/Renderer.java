package com.almostcreativegames.adversity.Drawing;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
 * @version 0.1.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Basic map with layer functionality</p>
 * <p>0.1.2 - Added background and changed to TreeMap to preserve layer order</p>
 */
public class Renderer {
    private GraphicsContext gc;
    private Image background;

    //TODO Instead of containing a list of Sprites, have a layer object which would render in order
    // based on an object's y coordinate.
    private Map<Integer, CopyOnWriteArrayList<Entity>> layers; //Concurrent version of ArrayList solves concurrency problems https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
        layers = new TreeMap<Integer, CopyOnWriteArrayList<Entity>>();
    }

    /**
     * Register sprites to be rendered when render() is called
     *
     * @param entity
     * @param layer
     */
    public void register(Entity entity, int layer) {
        if (layers.get(layer) == null)
            layers.put(layer, new CopyOnWriteArrayList<Entity>() {{
                add(entity);
            }});
        else
            layers.get(layer).add(entity);
    }

    public void unregisterAll() {
        layers = new TreeMap<Integer, CopyOnWriteArrayList<Entity>>();
    }

    public void render(double time) {
        gc.drawImage(background, 0, 0); //draw the background first, then every other entity in order by layer

        for (List<Entity> layer : layers.values()) {
            for (Entity entity : layer) {
                if (entity.isRemoved()) {
                    layer.remove(entity);
                    continue;
                }
                entity.render(gc, time);
            }
        }
    }

    public void loadRoom(Room room) {
        unregisterAll();
        if (room != null) {
            background = room.getBackground();
            for (Entity e : room.getEntities())
                if (e.getImage() != null) //if the entity has something to render
                    register(e, e.getLayer());
            System.out.println(room.getEntities());
        }
        else
            System.out.println("Room is null");
    }
}
