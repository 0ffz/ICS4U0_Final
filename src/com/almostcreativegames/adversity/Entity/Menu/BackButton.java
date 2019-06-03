package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Battle.Battle;

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
