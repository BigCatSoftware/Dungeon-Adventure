package com.dungeonadventure.database;

import java.io.*;

/**
 * The GameSaverLoader class provides methods to save and load game data
 * using serialization. It allows the game state to be persisted to a file
 * and retrieved later.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class GameSaverLoader {

    /**
     * Saves the given game data to a specified file path.
     *
     * @param filePath The path of the file where the game data will be saved.
     * @param gameData The GameData object containing the state of the game to be saved.
     */
    public static void saveGame(String filePath, GameData gameData) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameData);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads game data from a specified file path.
     *
     * @param filePath The path of the file from which the game data will be loaded.
     * @return The GameData object containing the loaded game state, or null if loading failed.
     */
    public static GameData loadGame(String filePath) {
        GameData gameData = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            gameData = (GameData) objectInputStream.readObject();
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameData;
    }
}