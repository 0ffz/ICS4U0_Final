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
 * @version 1.2.2
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Image can be resized, on creation. The getFrame() method is assumed to be called every frame with the
 * amount of time that has passed</p>
 * <p>1.2.2 - Reduced number of parameters needed to be passed in constructor</p>
 */
public class SpriteAnimation {
    private ArrayList<Image> frames;
    private double fps;
    private double progress;

    public SpriteAnimation(String spriteSheetPath, int topX, int topY, int width, int height, int columns, int numFrames, double sizeX, double sizeY, double speed) {
        this(spriteSheetPath, topX, topY, width, height, columns, numFrames, sizeX, sizeY, speed, 0, 0);
    }

    public SpriteAnimation(String spriteSheetPath, int topX, int topY, int width, int height, int columns, int numFrames, double sizeX, double sizeY, double speed, int gapX, int gapY) {
        fps = speed;
        width *= sizeX;
        topX *= sizeX;
        height *= sizeY;
        topY *= sizeY;
        gapX *= sizeX;
        gapY *= sizeY;

        Image beforeResize = new Image(spriteSheetPath);
        Image spriteSheet = new Image(spriteSheetPath, beforeResize.getWidth() * sizeX, beforeResize.getHeight() * sizeY, false, false);
        frames = new ArrayList<Image>();

        PixelReader reader = spriteSheet.getPixelReader();
        int rows = ((numFrames - (numFrames % columns)) / columns) + 1; //TODO make sure this is correct
        System.out.println(numFrames + " " + (numFrames % columns));
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < columns; col++) {
                if (row * columns + col == numFrames) //if on last frame
                    return;
                WritableImage newImage = new WritableImage(reader, col * (width + gapX) + topX, row * (height + gapY) + topY, width, height);
                frames.add(newImage);
            }
    }

    /**
     * @param time the amount of time passed
     * @return the frame that should currently be played
     */
    public Image getFrame(double time) {
        progress += time;

        if ((int) (progress * fps) >= frames.size()) //if looped through all frames
            progress = 0; //start from the beginning
        return frames.get((int) (progress * fps));
    }
}