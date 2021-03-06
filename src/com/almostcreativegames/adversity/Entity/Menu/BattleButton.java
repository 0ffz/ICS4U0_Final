package com.almostcreativegames.adversity.Entity.Menu;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for the Battle Button on the battle menu
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.1
 *
 * <h2>Changelog</h2>
 * <p>1.3.1 - BattleButton class created with necessary implementation</p>
 */
public class BattleButton extends Button {
    private List<Button> subOptions = new ArrayList<>();
    private Battle battle;

    public BattleButton(String text) {
        super(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void onInteract() {
        battle.hideMenuButtons();
        for (Entity option : subOptions) {
            option.show();
        }
    }

    public void addSubOption(Button option) {
        subOptions.add(option);
        int offsetX = 30;
        int offsetY = 665;

        option.setPosition(offsetX + (subOptions.size() - 1) % 2 * 475, offsetY + (subOptions.size() - 1) / 2 * 105);
        option.hide();
        battle.addEntity(option);
    }

    public void closeMenu(){
        for (Entity option : subOptions)
            option.hide();
        show();
    }
}
