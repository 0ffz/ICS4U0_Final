package com.almostcreativegames.adversity.Entity;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;


/**
 * A class for animated entities. Holds multiple animations in a hashmap and sets the current image to the frame from
 * the current animation
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.1.1
 *
 * <h2>Changelog</h2>
 * <p>1.1.1 - Holds multiple animations in a hashmap and sets the current image to the frame from the current animation
 * and renders it</p>
 */
public class EntityAnimated extends Entity {
    private Map<String, SpriteAnimation> animations = new HashMap<>();
    private SpriteAnimation currentAnimation;

    public EntityAnimated() {
        super();
        registerAnimations();
    }

    /**
     * Adds an animation with a name assigned to it
     *
     * @param name      the name assigned to the animation
     * @param animation the animation to be added
     */
    public void addAnimation(String name, SpriteAnimation animation) {
        animations.put(name, animation);
    }

    /**
     * Sets the current animation given its name
     *
     * @param name the name of the animation
     */
    public void setCurrentAnimation(String name) {
        currentAnimation = animations.get(name);
        setImage(currentAnimation.getFrame(0));
    }

    /**
     * Acts as a method for subclasses to override which allows them to auto register animations on instantiation
     */
    protected void registerAnimations() {
    }

    /**
     * Gets the current frame from the current animation and renders it
     *
     * @param gc   the GraphicsContext with which to render
     * @param time the amount of time that has passed between the last frame being drawn
     */
    @Override
    public void render(GraphicsContext gc, double time) {
        setImage(currentAnimation.getFrame(time));
        super.render(gc, time);
    }
}