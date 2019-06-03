package com.almostcreativegames.adversity.Entity.Button;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class BattleButton extends Button {
    private List<Button> subOptions = new ArrayList<>();
    private Battle battle;

    public BattleButton(String text) {
        super(text);
    }

    //TODO some of the methods return the class itself, which is nice for setting lots of stuff for one object, but we
    // need to decide whether they should all return void or `this` for consistency.
    public BattleButton setText(String text) {
        this.text = text;
        return this;
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