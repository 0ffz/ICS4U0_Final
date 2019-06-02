package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Battle.Battle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Button extends Entity {
    private List<Entity> subOptions = new ArrayList<>();
    private Battle battle;
    protected String text = "";
    private double offsetX = 10;
    private double offsetY = 20;

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.fillText(text, getX() + offsetX, getY() + offsetY);
        gc.strokeText(text, getX() + offsetX, getY() + offsetY);
    }
}