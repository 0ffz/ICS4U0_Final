package com.almostcreativegames.adversity.Entity.Button;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Button extends Entity {
    protected String text = "";
    private List<Entity> subOptions = new ArrayList<>();
    private Battle battle;
    private Color fillColor = Color.WHITE;
    private Color strokeColor = Color.BLACK;
    private Font font = Fonts.battleMenuItem;
    private Dialog dialog;

    public Button(String text) {
        this.text = text;
        setImage(new Image("Menu/LongButton.png", 0, 100, true, true));
    }

    public Button setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public Button setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public Button setFont(Font font) {
        this.font = font;
        return this;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);

        gc.setFont(font);
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillText(text, getX() + font.getSize() / 2, getY() + font.getSize() * 1.2);
        gc.strokeText(text, getX() + font.getSize() / 2, getY() + font.getSize() * 1.2);
    }
}