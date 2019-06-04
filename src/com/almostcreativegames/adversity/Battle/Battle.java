package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Menu.*;
import com.almostcreativegames.adversity.Entity.Player;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;
import com.almostcreativegames.adversity.GameRunner;
import com.almostcreativegames.adversity.Rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * A class for creating turn based battle sequences
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.1
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Created basis for class, almost identical to Room, with a fighting entity held inside and a method to end
 * the battle and return to the room from which the battle occurred. The room contains health bars, a player, fighting
 * entity sprite, an ACT and EQUIP button which both open submenus, with back buttons</p>
 */
public class Battle extends Room {
    private Room fromRoom; //the room from which the Battle Menu was opened (we use it to return to that room later)
    private Entity fightingSprite;
    private BattleBehaviour enemy;
    private Player soul;
    private Player previousPlayer = Player.getCurrentPlayer();
    private BattleButton act;
    private BattleButton item;
    private HealthDisplay playerHealth;
    private HealthDisplay enemyHealth;

    private boolean playerTurn = true;

    public Battle(String imageURL, Entity enemy, Room fromRoom, GameRunner game) {
        super(imageURL);

        //if the entity being fought does not have the right behaviours, end the battle
        if (!(enemy instanceof BattleBehaviour && enemy instanceof HealthBehaviour)) {
            endBattle();
            return;
        }

        setGame(game);
        this.fromRoom = fromRoom;
        this.enemy = ((BattleBehaviour) enemy);

        fightingSprite = ((BattleBehaviour) enemy).getBattleSprite();

        Rectangle2D boundary = fightingSprite.getBoundary();
        fightingSprite.setPosition(500 - boundary.getWidth() / 2, 200); //center the sprite
        addEntity(fightingSprite);

        //hide overworld player
        previousPlayer.setCanMove(false);
        previousPlayer.hide();

        //creating soul
        soul = new Player();
        soul.setLayer(5);

        String playerSprite = "Entities/Player/Soul-spritesheet.png";
        soul.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 11, 2, 1, 5, 5, 1));
        soul.addAnimation("left", new SpriteAnimation(playerSprite, 0, 12, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("right", new SpriteAnimation(playerSprite, 0, 24, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("down", new SpriteAnimation(playerSprite, 0, 36, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.addAnimation("up", new SpriteAnimation(playerSprite, 0, 48, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.setCurrentAnimation("idle");

        soul.setPosition(450, 800);
        addEntity(soul);
        Player.setCurrentPlayer(soul);

        //TODO create all the other buttons with textures
        //creating buttons with options
        //act button
        act = new BattleButton("ACT");
        act.setFont(Fonts.BATTLE_BUTTON);
        act.setBattle(this);
        act.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        act.setPosition(40, 875);
        addEntity(act);


        item = new BattleButton("ITEM");
        item.setFont(Fonts.BATTLE_BUTTON);
        item.setBattle(this);
        item.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        item.setPosition(250, 875);
        addEntity(item);


        //register all the menu options stored in the battleable entity
        for (Button button : ((BattleBehaviour) enemy).getActOptions(this))
            act.addSubOption(button);

        //Adding a back button to all menus
        BackButton actBack = new BackButton(this);
        BackButton itemBack = new BackButton(this);

        act.addSubOption(actBack);
        item.addSubOption(itemBack);

        //create player's health meter and label
        Text you = new Text("You:");
        you.setPosition(290 - you.getTextWidth(), 620);
        addEntity(you);

        playerHealth = new HealthDisplay(soul);
        playerHealth.setLayer(10);
        playerHealth.setPosition(300, 600);
        playerHealth.setDimensions(400, 25);
        addEntity(playerHealth);

        //create enemy's health meter and name label
        String enemyName = enemy.getName() + ":";
        Text name = new Text(enemyName);
        name.setPosition(290 - name.getTextWidth(), 41);
        addEntity(name);

        enemyHealth = new HealthDisplay(((HealthBehaviour) enemy));
        enemyHealth.setLayer(10);
        enemyHealth.setPosition(300, 20);
        enemyHealth.setDimensions(400, 25);
        addEntity(enemyHealth);
    }

    public void nextTurn() {
        if (playerHealth.isDead()) {
            endBattle();
            //TODO call game end method in GameRunner
        }
        if (enemyHealth.isDead()) {
            enemy.onBattleEnd(this);
            endBattle();
        }

        if (playerTurn)
            startEnemyTurn();
        else
            startPlayerTurn();

        playerTurn = !playerTurn;
    }

    //TODO implement the actual turn based system
    private void startEnemyTurn() {
        closeMenus();
        hideMenuButtons();
        enemy.startTurn(this);
    }

    private void startPlayerTurn() {
        closeMenus();
    }

    public void hideMenuButtons() {
        act.hide();
        item.hide();
    }

    public void closeMenus() {
        act.closeMenu();
        item.closeMenu();
    }

    public void endBattle() {
        renderer.loadRoom(fromRoom);

        //return previous player to be playable again
        previousPlayer.setCanMove(true);
        previousPlayer.show();
        Player.setCurrentPlayer(previousPlayer);
    }
}
