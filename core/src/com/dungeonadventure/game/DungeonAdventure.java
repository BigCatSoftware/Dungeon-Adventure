package com.dungeonadventure.game;

import Controller.Settings;
import View.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DungeonAdventure extends Game {
	public SpriteBatch batch;
	public static Settings mySETTINGS;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final int SETTINGS_BUTTON_WIDTH = 64;
	public static final int SETTINGS_BUTTON_HEIGHT = 64;
	public static final int SETTINGS_BUTTON_Y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_HEIGHT;

	@Override
	public void create () {
		batch = new SpriteBatch();
		mySETTINGS = new Settings();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
