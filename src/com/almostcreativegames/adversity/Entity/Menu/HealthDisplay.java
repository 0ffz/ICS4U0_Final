package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public class HealthDisplay extends Entity {
    private double health;
    private double maxHealth;

    public HealthDisplay(double health, double maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
        setDimensions(100, 20);
    }

    public void setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        gc.setFill(Color.ORANGERED);
        gc.fillRect(x, y, width, height);
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(x, y, width * (health / maxHealth), height);
    }
}
