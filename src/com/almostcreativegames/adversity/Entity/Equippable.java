package com.almostcreativegames.adversity.Entity;

public class Equippable implements Comparable<Equippable>{
    private String name;

    public String getName() {
        return name;
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

    @Override
    public int compareTo(Equippable o) {
        return name.compareTo(o.getName());
    }
}
