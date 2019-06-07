package com.almostcreativegames.adversity.Saves;

import com.almostcreativegames.adversity.Entity.Equippable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * The class that will create saves of our game
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 *
 * <h2>Changelog</h2>
 * <p>0.0.1 - Save class created with the premise of saving implemented.</p>
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
    public static void saveGame(int day, List<String> attributes, Set<Equippable> equipment) {
        System.out.println("creating save");
//        System.out.println("Save exists: " + saveExists());
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
        System.out.println(saveExists());
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
//                System.out.println(Paths.get(saveDir + fileName));
                System.out.println(Files.deleteIfExists(Paths.get(saveDir + fileName)));
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
