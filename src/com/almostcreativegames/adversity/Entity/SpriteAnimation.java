package com.almostcreativegames.adversity.Entity;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;

/**
 * A class holding frames that can be used to animate an Entity
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.1
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Image can be resized, on creation. The getFrame() method is assumed to be called every frame with the
 * amount of time that has passed</p>
 */
public class SpriteAnimation {
    private ArrayList<Image> frames;
    private double fps;
    private double progress;

    public SpriteAnimation(String spriteSheetPath, int topX, int topY, int width, int height, int rows, int columns, int numFrames, double sizeX, double sizeY, double speed) {
        fps = speed;
        width *= sizeX;
        topX *= sizeX;
        height *= sizeY;
        topY *= sizeY;

        Image beforeResize = new Image(spriteSheetPath);
        Image spriteSheet = new Image(spriteSheetPath, beforeResize.getWidth() * sizeX, beforeResize.getHeight() * sizeY, false, false);
        frames = new ArrayList<Image>();

        PixelReader reader = spriteSheet.getPixelReader();

        for (int x = 0; x < rows; x++)
            for (int y = 0; y < columns; y++) {
                if (y * columns + x == numFrames) //if on last frame
                    return;
                WritableImage newImage = new WritableImage(reader, x * (width) + topX, y * height + topY, width, height);
                frames.add(newImage);
            }
    }

    /**
     *
     * @param time the amount of time passed
     * @return the frame that should currently be played
     */
    public Image getFrame(double time){
        progress += time;

        if ((int) (progress * fps) >= frames.size()) //if looped through all frames
            progress = time; //start from the beginning
        return frames.get((int) (progress * fps));
    }
}