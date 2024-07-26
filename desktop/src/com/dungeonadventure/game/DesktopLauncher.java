package com.dungeonadventure.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * The entry point for the desktop version of the Dungeon Adventure game.
 * Configures and launches the LWJGL3 application.
 */
public class DesktopLauncher {
	/**
	 * The main method that starts the Dungeon Adventure game.
	 *
	 * @param arg the command line arguments
	 */
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); // Set the desired frames per second
		config.setResizable(false); // Prevent window resizing
		config.setWindowedMode(DungeonAdventure.WIDTH, DungeonAdventure.HEIGHT); // Set the window size
		config.setTitle("Dungeon Adventure"); // Set the window title
		new Lwjgl3Application(new DungeonAdventure(), config); // Launch the application
	}
}

