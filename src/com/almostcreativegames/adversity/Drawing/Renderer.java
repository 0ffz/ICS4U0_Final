package com.almostcreativegames.adversity.Drawing;

import com.almostcreativegames.adversity.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<Integer, CopyOnWriteArrayList<Sprite>> layers; //Concurrent version of ArrayList solves concurrency problems https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
        layers = new HashMap<Integer, CopyOnWriteArrayList<Sprite>>();

    }

    /**
     * Register sprites to be rendered when render() is called
     *
     * @param sprite
     * @param layer
     */
    public void register(Sprite sprite, int layer) {
        System.out.println(layers.get(layer));
        if (layers.get(layer) == null)
            layers.put(layer, new CopyOnWriteArrayList<Sprite>() {{
                add(sprite);
            }});
        else
            layers.get(layer).add(sprite);
    }

    public void render() {
        for (List<Sprite> layer : layers.values()) {
            for (Sprite sprite : layer) {
                if (sprite.isRemoved()) {
                    layer.remove(sprite);
                    continue;
                }
                sprite.render(gc);
            }
        }
    }
}
