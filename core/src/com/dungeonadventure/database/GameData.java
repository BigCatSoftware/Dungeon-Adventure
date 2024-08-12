package com.dungeonadventure.database;
import model.Enemy;

import java.io.Serializable;
import java.util.List;

public class GameData implements Serializable {
    private int playerX;
    private int playerY;
    private String inventory;
    private List<Enemy> enemies; // To hold enemy data

    public GameData(int playerX, int playerY, String inventory, List<Enemy> enemies) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.inventory = inventory;
        this.enemies = enemies;
    }

    // Getters
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public String getInventory() { return inventory; }
    public List<Enemy> getEnemies() { return enemies; }
}

