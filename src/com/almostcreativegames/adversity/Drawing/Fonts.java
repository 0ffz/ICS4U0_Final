package com.almostcreativegames.adversity.Drawing;

import com.almostcreativegames.adversity.Main;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * A class for the different Fonts used in the game
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 - Fonts class created and 2 initial Fonts added</p>
 * <p>1.3.2 - Added more fonts</p>
 */

public class Fonts {
    private static final String mainFont = Main.class.getResource("uni0553.ttf").toExternalForm();
    public static final Font TITLE = Font.loadFont(mainFont, 100);
    public static final Font BATTLE_BUTTON = Font.loadFont(mainFont, 40);
    public static final Font NORMAL = Font.loadFont(mainFont, 30);
}
