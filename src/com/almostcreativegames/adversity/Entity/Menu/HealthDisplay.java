package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
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
