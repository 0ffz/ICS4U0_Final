package com.almostcreativegames.adversity.Entity.Behaviours;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Entity;

import java.util.List;

/**A class to distinguish battleable entities
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.2.3
 *
 * <h2>Changelog</h2>
 * <p>0.2.3 - Battleable interface created</p>
 */

public interface Battleable {
    Entity getBattleSprite();

    List<Button> getActOptions(Battle battle);
}
