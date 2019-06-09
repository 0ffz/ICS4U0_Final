package com.almostcreativegames.adversity.Battle;

import com.almostcreativegames.adversity.Drawing.Fonts;
import com.almostcreativegames.adversity.Entity.Behaviours.BattleBehaviour;
import com.almostcreativegames.adversity.Entity.Behaviours.HealthBehaviour;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.Equippable;
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
 * @version 0.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Created basis for class, almost identical to Room, with a fighting entity held inside and a method to end
 * the battle and return to the room from which the battle occurred. The room contains health bars, a player, fighting
 * entity sprite, an ACT and EQUIP button which both open submenus, with back buttons</p>
 * <p>0.3.2 - Added turn based mechanics to the battle. The player gets to choose actions, and enemy plays their own
 * appropriate minigame. Now also does death checks. Removed imageURL since it's always the same.</p>
 */
public class Battle extends Room {
    protected Room fromRoom; //the room from which the Battle Menu was opened (we use it to return to that room later)
    protected Entity fightingSprite;
    protected BattleBehaviour enemy;
    protected Player soul;
    protected Player previousPlayer;
    protected BattleButton act;
    protected BattleButton item;
    protected Button leave;
    protected HealthDisplay playerHealth;
    protected HealthDisplay enemyHealth;
    protected boolean playerTurn = true;

    /**
     * Defines a new battle object, in which a new room is created and entered.
     *
     * @param enemy    the enemy Entity being fought
     * @param fromRoom the room to return to after the battle is over
     * @param game     a reference to the GameRunner
     */
    public Battle(Entity enemy, Room fromRoom, GameRunner game) {
        super("Battle/Battle.png");
        previousPlayer = game.getCurrentPlayer();

        //if the entity being fought does not have the right behaviours, end the battle
        if (!(enemy instanceof BattleBehaviour && enemy instanceof HealthBehaviour)) {
            endBattle();
            return;
        }

        setGame(game);
        this.fromRoom = fromRoom;
        enemy.setRoom(this);
        this.enemy = ((BattleBehaviour) enemy);

        fightingSprite = ((BattleBehaviour) enemy).getBattleSprite();

        Rectangle2D boundary = fightingSprite.getBoundary();
        fightingSprite.setPosition(500 - boundary.getWidth() / 2, 200); //center the sprite
        addEntity(fightingSprite);

        //hide overworld player
        previousPlayer.setCanMove(false);
        previousPlayer.hide(); //TODO might not need this

        //creating soul
        soul = new Player();
        soul.setLayer(5);
        soul.setName("Soul");

        String playerSprite = "Entities/Player/Soul-spritesheet.png";
        soul.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 11, 2, 1, 5, 5, 1));
        soul.addAnimation("left", new SpriteAnimation(playerSprite, 0, 12, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("right", new SpriteAnimation(playerSprite, 0, 24, 11, 11, 5, 5, 5, 5, 5, 1, 0));
        soul.addAnimation("down", new SpriteAnimation(playerSprite, 0, 36, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.addAnimation("up", new SpriteAnimation(playerSprite, 0, 48, 11, 11, 5, 4, 5, 5, 5, 1, 0));
        soul.setCurrentAnimation("idle");

        soul.setPosition(450, 800);
        addEntity(soul);
        game.setCurrentPlayer(soul);

        //TODO create all the other buttons with textures
        //creating buttons with options
        //act button
        act = new BattleButton("ACT");
        act.setFont(Fonts.BATTLE_BUTTON);
        act.setBattle(this);
        act.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        act.setPosition(40, 875);
        addEntity(act);

        //register all the menu options stored in the battleable entity
        for (Button button : ((BattleBehaviour) enemy).getActOptions(this))
            act.addSubOption(button);

        //item button
        item = new BattleButton("ITEM");
        item.setFont(Fonts.BATTLE_BUTTON);
        item.setBattle(this);
        item.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        item.setPosition(250, 875);
        addEntity(item);

        //register all the menu options stored in the battleable entity
        for (Equippable equippable : game.getEquipment()) {
            String name = equippable.getDisplayName();
            item.addSubOption(new Button(name) {
                @Override
                public void onInteract() {
                    game.toggleEquipped(equippable.getName());
                    setText(equippable.getDisplayName());
                }
            });
        }

        //leave button
        leave = new Button("LEAVE") {
            @Override
            public void onInteract() {
                endBattle();
            }
        };
        leave.setFont(Fonts.BATTLE_BUTTON);
        leave.setImage(new Image("Menu/Button.png", 200, 0, true, true));
        leave.setPosition(460, 875);
        addEntity(leave);


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
        ((BattleBehaviour) enemy).onBattleStart(this);
    }

    /**
     * Checks if the player or enemy have died
     *
     * @return whether either is dead and the battle is now over
     */
    public boolean checkDead() {
        if (playerHealth.isDead()) {
            endBattle();
            game.gameOver();
            //TODO call game end method in GameRunner
            return true;
        }
        if (enemyHealth.isDead()) {
            endBattle();
            enemy.onWin(this);
            return true;
        }
        return false;
    }

    /**
     * Begins the next turn, and does health checks
     */
    public void nextTurn() {
        if (checkDead())
            return;

        if (playerTurn)
            startEnemyTurn();
        else
            startPlayerTurn();

        playerTurn = !playerTurn;
    }

    /**
     * Begins the enemy turn by playing their associated minigame
     */
    private void startEnemyTurn() {
        closeMenus();
        hideMenuButtons();
        enemy.startTurn(this);
    }

    /**
     * Begins the player's turn, by showing them their menu options
     */
    private void startPlayerTurn() {
        closeMenus();
        enemy.onPlayerTurn(this);
    }

    /**
     * Hides all menu buttons (called by the menu buttons to open the submenus)
     */
    public void hideMenuButtons() {
        act.hide();
        item.hide();
        leave.hide();
    }

    /**
     * Closes all menus, which also shows their main buttons
     */
    public void closeMenus() {
        act.closeMenu();
        item.closeMenu();
        leave.show();
    }

    /**
     * Ends the battle and returns to the overworld
     */
    public void endBattle() {
        ((Entity) enemy).setRoom(fromRoom);
        renderer.loadRoom(fromRoom);
        game.stopDialog();

        //return previous player to be playable again
        previousPlayer.setCanMove(true);
        previousPlayer.show();
        game.setCurrentPlayer(previousPlayer);
        enemy.onBattleEnd(this);
    }
}
