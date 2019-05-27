package com.almostcreativegames.adversity.Entity;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

import java.util.ArrayList;

public class SpriteAnimation {
    private ArrayList<Image> frames;
    private double fps = 1;
    private double progress;

    public SpriteAnimation(String spriteSheetPath, int topX, int topY, int width, int height, int rows, int columns, int numFrames) {
        Image spriteSheet = new Image(spriteSheetPath);
        frames = new ArrayList<Image>();
        ImageView view = new ImageView();
        view.setImage(spriteSheet);
        view.setViewport(new Rectangle2D(topX, topY, width, height));

        for (int x = 0; x < rows; x++)
            for (int y = 0; y < columns; y++) {
                if (y * columns + x == numFrames) //if on last frame
                    return;
                PixelReader reader = spriteSheet.getPixelReader();
                System.out.println(x * (width) + " " + y * height + " " + width + " " + height);
                WritableImage newImage = new WritableImage(reader, x * (width), y * height, width, height);
                frames.add(newImage);
            }
    }

    public Image getFrame(double time){
        progress += time;

        if ((int) (progress / fps) >= frames.size()) //if looped through all frames
            progress -= frames.size(); //start from the beginning

        return frames.get((int) (progress / fps));
    }
}