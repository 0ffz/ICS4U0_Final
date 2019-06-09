package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Text extends Entity {
    protected String text;
    protected Color fillColor = Color.WHITE;
    protected Color strokeColor = null;
    protected Font font = Fonts.NORMAL;
    private List<Entity> subOptions = new ArrayList<>();
    private Battle battle;
    private Dialog dialog;

    public Text(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public Text setFont(Font font) {
        this.font = font;
        return this;
    }

    public Text setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public Text setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        super.render(gc, time);

        gc.setFont(font);
        gc.setFill(fillColor);
        gc.fillText(text, x, y);
        if(strokeColor != null) {
            gc.setStroke(strokeColor);
            gc.strokeText(text, x, y);
        }
    }

    /**
     * Calculates the width of the text with the current font
     *
     * @return a value for the width in pixels
     */
    public double getTextWidth() {
        javafx.scene.text.Text textWidth = new javafx.scene.text.Text(text);
        textWidth.setFont(font);
        return textWidth.getBoundsInLocal().getWidth();
    }
}