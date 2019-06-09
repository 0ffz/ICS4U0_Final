package com.almostcreativegames.adversity.Entity.Behaviours;

/**
 * An interface for the Health Behaviour on the battle menu
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - Health Behaviour Interface created with the necessary methods</p>
 */
public interface HealthBehaviour {
    void addHealth(double amount);

    double getHealth();

    double getMaxHealth();
}
