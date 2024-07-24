package View;

import Controller.PlayerInputProcessor;
import Model.Hero;
import Model.Dungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;


import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_Y;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_HEIGHT;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_WIDTH;

public class GameScreen implements Screen {

    private final DungeonAdventure myGame;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;
    private final DungeonRenderer myDungeonRenderer;
    private final Hero myPlayer;


    public GameScreen(final DungeonAdventure theGame, final Hero thePlayer) {
        myGame = theGame;
        myPlayer = thePlayer;
        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");

        Dungeon myDungeon = new Dungeon();
        myDungeonRenderer = new DungeonRenderer(myDungeon);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new PlayerInputProcessor(myPlayer, myGame, GameScreen.this));
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        myGame.batch.begin();

        // Render the dungeon
        myDungeonRenderer.render(myGame.batch);

        settingsButtonDraw();

        myGame.batch.end();
    }

    private void settingsButtonDraw() {
        int x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
        if (Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y) {
            myGame.batch.draw(mySettingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(mySettingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }
    }

    @Override
    public void resize(final int width, final int height) {
        // Handle resizing if needed
    }

    @Override
    public void pause() {
        // Handle pause logic if needed
    }

    @Override
    public void resume() {
        // Handle resume logic if needed
    }

    @Override
    public void hide() {
        // Handle hide logic if needed
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        mySettingsButtonActive.dispose();
        mySettingsButtonInactive.dispose();
        // Dispose of other resources if needed
    }
}


