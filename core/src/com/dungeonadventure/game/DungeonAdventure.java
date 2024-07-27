package com.dungeonadventure.game;

import com.badlogic.gdx.audio.Music;
import controller.Settings;
import view.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import database.DatabaseHelper;

/**
 * The main class for the Dungeon Adventure game.
 * This class sets up the game environment, including screen dimensions,
 * settings, and database management. It extends the Game class from libGDX.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class DungeonAdventure extends Game {
	/**
	 * The SpriteBatch used for rendering 2D textures.
	 */
	public SpriteBatch batch;

	/**
	 * The Settings instance for managing game settings.
	 */
	public static Settings mySETTINGS;

	//private DatabaseHelper databaseHelper;

	/**
	 * The width of the game screen.
	 */
	public static final int WIDTH = 800;

	/**
	 * The height of the game screen.
	 */
	public static final int HEIGHT = 800;

	/**
	 * The width of the settings button.
	 */
	public static final int SETTINGS_BUTTON_WIDTH = 64;

	/**
	 * The height of the settings button.
	 */
	public static final int SETTINGS_BUTTON_HEIGHT = 64;

	/**
	 * The y-position of the settings button.
	 */
	public static final int SETTINGS_BUTTON_Y = HEIGHT - SETTINGS_BUTTON_HEIGHT;

	public static Music myBackgroundMusic;

	/**
	 * Creates and initializes the game, setting up the SpriteBatch,
	 * settings, and initial screen.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		//databaseHelper = new DatabaseHelper();

		mySETTINGS = new Settings();
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Renders the current screen by calling the superclass render method.
	 */
	@Override
	public void render() {
		super.render();
	}

	/**
	 * Disposes of resources when the game is closed, ensuring proper cleanup.
	 * Saves the current settings to the database.
	 */
	@Override
	public void dispose() {
		batch.dispose();
	}
}


