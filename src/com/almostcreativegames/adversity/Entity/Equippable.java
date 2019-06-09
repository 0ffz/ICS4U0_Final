package com.almostcreativegames.adversity.Entity;

import java.util.Objects;

/**
 * A class for the Equippable entities
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - Equippable class created with necessary implementation</p>
 */
public class Equippable implements Comparable<Equippable>{
    private String name;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        if (isEquipped())
            return name + "[x]";
        else
            return name + "[ ]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    private boolean isEquipped;

    public Equippable(String name) {
        this.name = name;
    }

    /**
     * Overriding equals so we can check whether an equippable is contained just by its name in the List of equipment
     * @param o object to be compared to
     * @return whether they are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equippable that = (Equippable) o;

        return Objects.equals(name, that.name);
    }

    /**
     * Overriding compareTo
     * @param o object to be compared to
     * @return a comparison between both Equippables' names
     */
    @Override
    public int compareTo(Equippable o) {
        return name.compareTo(o.getName());
    }
}
