package com.almostcreativegames.adversity.Entity;

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
public class Player extends EntityAnimated {
    private static Player currentPlayer;
    private boolean canMove = true;

    public Player() {
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    @Override
    public void update(double time, double friction) {
        if (canMove) {
            if (InputListener.isKeyPressed("LEFT") || InputListener.isKeyPressed("A")) addVelocity(-100, 0);
            if (InputListener.isKeyPressed("RIGHT") || InputListener.isKeyPressed("D")) addVelocity(100, 0);
            if (InputListener.isKeyPressed("UP") || InputListener.isKeyPressed("W")) addVelocity(0, -100);
            if (InputListener.isKeyPressed("DOWN") || InputListener.isKeyPressed("S")) addVelocity(0, 100);

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
}
