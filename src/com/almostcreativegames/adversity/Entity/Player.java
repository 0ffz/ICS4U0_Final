package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Input.InputListener;

/**
 * A class for a playable character that can move around the canvas with user input and have walking animations
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.1
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Moved most of GameRunner's manipulation of the player into its own class</p>
 */
public class Player extends EntityAnimated implements HealthBehaviour {
    private boolean canMove = true;

    public Player() {
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    @Override
    public void update(double time, double friction) {
        InputListener input = room.getGame().getInputListener();
        if (canMove) {
            if (input.isKeyPressed("LEFT") || input.isKeyPressed("A")) addVelocity(-100, 0);
            if (input.isKeyPressed("RIGHT") || input.isKeyPressed("D")) addVelocity(100, 0);
            if (input.isKeyPressed("UP") || input.isKeyPressed("W")) addVelocity(0, -100);
            if (input.isKeyPressed("DOWN") || input.isKeyPressed("S")) addVelocity(0, 100);

        }

        collisionCheck(time);

        super.update(time, friction);

        animate();
    }

    private void collisionCheck(double time) {
        if (room.isColliding(this, time)) {
            double tempVX = getVelocityX();
            setVelocity(0, getVelocityY());
            if (room.isColliding(this, time)) {
                setVelocity(tempVX, 0);
                if (room.isColliding(this, time))
                    setVelocity(0, 0);
            }
        }
    }

    private void animate() {
        setCurrentAnimation("idle");
        if (getVelocityX() < -50)
            setCurrentAnimation("left");
        else if (getVelocityX() > 50)
            setCurrentAnimation("right");
        else if (getVelocityY() < -50)
            setCurrentAnimation("up");
        else if (getVelocityY() > 50)
            setCurrentAnimation("down");
    }

    @Override
    public void addHealth(double amount) {
        room.getGame().setPlayerHealth(room.getGame().getPlayerHealth() + amount);
    }

    @Override
    public double getHealth() {
        return room.getGame().getPlayerHealth();
    }

    @Override
    public double getMaxHealth() {
        return room.getGame().getMaxPlayerHealth();
    }
}
