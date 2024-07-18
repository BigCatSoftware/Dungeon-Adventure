package com.dungeonadventure.game;

import Controller.Settings;
import View.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DungeonAdventure extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this, new Settings()));
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
