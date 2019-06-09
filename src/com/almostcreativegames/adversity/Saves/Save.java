package com.almostcreativegames.adversity.Saves;

import com.almostcreativegames.adversity.Entity.Equippable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The class that will create saves of our game
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 1.3.2
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Save class created with the premise of saving implemented.</p>
 * <p>1.3.2 - Now saves game attributes and equipment.
 * Added methods for reading and deleting the save file.</p>
 */
public class Save {
    private static String saveDir = System.getProperty("user.home") + File.separator + "almostcreative" + File.separator + "dghsaw";
    private static String fileName = File.separator + "save.dat" + File.separator;

    /**
     * Saves the game
     *
     * @param day        the current day
     * @param attributes general game attributes
     * @param equipment  the player's current equipment
     */
    public static void saveGame(int day, List<String> attributes, List<Equippable> equipment) {
        try {
            File file = new File(saveDir);
            file.mkdirs();

            PrintWriter output = new PrintWriter((new FileWriter(saveDir + fileName))); //creates a variable to access the file
            output.println(day);

            //save attributes to file
            for (String attribute : attributes)
                output.println(attribute);

            //print equipment to file
            output.println("Equipment");

            for (Equippable equippable : equipment) {
                output.println(equippable.getName());
                output.println(equippable.isEquipped());
            }

            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the save file if it exists
     *
     * @return a BufferedReader opened to the save file
     */
    public static BufferedReader getSave() {
        try {
            if (!saveExists())
                return null;
            return new BufferedReader((new FileReader(saveDir + fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes the save file
     */
    public static void delete() {
        if (saveExists()) {
            try {
                Files.deleteIfExists(Paths.get(saveDir + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if a save exists
     *
     * @return whether it exists
     */
    public static boolean saveExists() {
        return new File(saveDir + fileName).exists();
    }
}
