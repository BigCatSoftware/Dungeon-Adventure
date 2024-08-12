package com.dungeonadventure.database;

import model.Enemy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:game_data.db"; // Database file path

    public DatabaseManager() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS game_data ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " player_x INTEGER,"
                + " player_y INTEGER,"
                + " inventory TEXT,"
                + " enemies TEXT" // Storing enemies as a JSON string or similar
                + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveGame(int playerX, int playerY, List<Enemy> enemies) {
        String enemyData = convertEnemiesToJson(enemies); // Convert enemies to a JSON string or similar
        String sql = "INSERT INTO game_data (player_x, player_y, inventory, enemies) VALUES (?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerX);
            pstmt.setInt(2, playerY);
           // pstmt.setString(3, inventory);
            pstmt.setString(4, enemyData);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GameData loadGame() {
        String sql = "SELECT * FROM game_data ORDER BY id DESC LIMIT 1;"; // Load the last saved game
        GameData gameData = null;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int playerX = rs.getInt("player_x");
                int playerY = rs.getInt("player_y");
                String inventory = rs.getString("inventory");
                List<Enemy> enemies = convertJsonToEnemies(rs.getString("enemies")); // Convert JSON back to EnemyData list
                gameData = new GameData(playerX, playerY, inventory, enemies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameData;
    }

    private String convertEnemiesToJson(List<Enemy> enemies) {
        // Implement JSON conversion here (e.g., using Gson or Jackson)
        return ""; // Replace with actual implementation
    }

    private List<Enemy> convertJsonToEnemies(String json) {
        // Implement JSON parsing here (e.g., using Gson or Jackson)
        return new ArrayList<>(); // Replace with actual implementation
    }
}
