package com.almostcreativegames.adversity.Drawing;

import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Scenes.Room;
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
 * @version 0.0.1
 */
public class Renderer {
    private GraphicsContext gc;

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
        System.out.println(layers.get(layer));
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

    public void render() {
        for (List<Entity> layer : layers.values()) {
            for (Entity entity : layer) {
                if (entity.isRemoved()) {
                    layer.remove(entity);
                    continue;
                }
                entity.render(gc);
            }
        }
    }

    public void loadRoom(Room room) {
        unregisterAll();
        for (Entity e : room.getEntities())
            if (e.getImage() != null) //if the entity has something to render
                register(e, e.getLayer());
    }
}
