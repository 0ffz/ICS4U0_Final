package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Battle.Battle;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Button extends Entity {
    private List<Entity> subOptions = new ArrayList<>();
    private Battle battle;
    protected String text = "";

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);
        gc.fillText(text, getX(), getY());
        gc.strokeText(text, getX(), getY());
    }
}
