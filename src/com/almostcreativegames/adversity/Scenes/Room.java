package com.almostcreativegames.adversity.Scenes;


import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

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
    private Image background = new Image("Rooms/Home.png", 1000, 1000, false, true);
    private List<Entity> entities = new ArrayList<Entity>();

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
}
