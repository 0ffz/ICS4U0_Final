package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class to display health on the battle menu
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - HealthDisplay class created with necessary implementation</p>
 */
public class HealthDisplay extends Entity {
    private HealthBehaviour parent;

    public HealthDisplay(HealthBehaviour parent) {
        this.parent = parent;
        setDimensions(100, 20);
    }

    public void setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public boolean isDead(){
        return parent.getHealth() <= 0;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        gc.setFill(Color.ORANGERED);
        gc.fillRect(x, y, width, height);
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(x, y, width * (parent.getHealth() / parent.getMaxHealth()), height);
    }
}
