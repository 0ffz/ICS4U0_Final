package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Battle.Battle;

/**
 * A class for the Back Button on the battle menu
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - BackButton class created with necessary implementation</p>
 */
public class BackButton extends Button{
    private Battle battle;

    public BackButton(Battle battle) {
        super("Back");
        this.battle = battle;
    }

    @Override
    public void onInteract() {
        battle.closeMenus();
    }
}
