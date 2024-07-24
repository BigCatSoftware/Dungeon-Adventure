package com.dungeonadventure.database;

import controller.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:game_data.db"; // Path to your database file
    private Connection connection;

    public DatabaseHelper() {
        try {
            connection = DriverManager.getConnection(URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String createSettingsTable = "CREATE TABLE IF NOT EXISTS settings (" +
                    "id INTEGER PRIMARY KEY," +
                    "volume_level INTEGER," +
                    "is_sound_on BOOLEAN," +
                    "is_muted BOOLEAN" +
                    ");";
            stmt.execute(createSettingsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings(int volumeLevel, boolean isSoundOn, boolean isMuted) {
        String sql = "INSERT INTO settings(volume_level, is_sound_on, is_muted) VALUES(?, ?, ?);";
        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, volumeLevel);
            pstmt.setBoolean(2, isSoundOn);
            pstmt.setBoolean(3, isMuted);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings(Settings settings) {
        String sql = "SELECT volume_level, is_sound_on, is_muted FROM settings ORDER BY id DESC LIMIT 1;";
        try (Statement stmt = connection.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                settings.setVolumeLevel(rs.getInt("volume_level"));
                settings.setSoundOn(rs.getBoolean("is_sound_on"));
                settings.setMuted(rs.getBoolean("is_muted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

