package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;

public class MainMenuScreen implements Screen {
    final DungeonAdventure game;

    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture DungeonAdventureTitle;
    Texture SettingsButtonActive;
    Texture SettingsButtonInactive;



    private static final int SETTINGS_BUTTON_WIDTH = 64;
    private static final int SETTINGS_BUTTON_HEIGHT = 64;
    private static final int DUNGEON_TITLE_WIDTH = 450;
    private static final int DUNGEON_TITLE_HEIGHT = 450;
    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 250;
    private static final int PLAY_BUTTON_Y = 330;
    private static final int TITLE_Y = 300;
    private static final int SETTINGS_BUTTON_Y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_HEIGHT;


    public MainMenuScreen(final DungeonAdventure game) {
        this.game = game;
        DungeonAdventureTitle = new Texture("DungeonAdventureTitle.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        SettingsButtonActive = new Texture("SettingsActive.png");
        SettingsButtonInactive = new Texture("SettingsInactive.png");

        final MainMenuScreen mainMenuScreen = this;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int x = DungeonAdventure.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
                if (screenX < x + EXIT_BUTTON_WIDTH && screenX > x &&
                        DungeonAdventure.HEIGHT - screenY < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                        DungeonAdventure.HEIGHT - screenY > EXIT_BUTTON_Y) {

                    mainMenuScreen.dispose();
                    Gdx.app.exit();
                }

                x = DungeonAdventure.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
                if (screenX < x + PLAY_BUTTON_WIDTH && screenX > x &&
                        DungeonAdventure.HEIGHT - screenY < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                        DungeonAdventure.HEIGHT - screenY > PLAY_BUTTON_Y) {

                    mainMenuScreen.dispose();
                   // game.setScreen(new GameScreen(game));
                }

                x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
                if (screenX < x + SETTINGS_BUTTON_WIDTH && screenX > x &&
                        DungeonAdventure.HEIGHT - screenY < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT &&
                        DungeonAdventure.HEIGHT - screenY > SETTINGS_BUTTON_Y) {
                    game.setScreen(new SettingsScreen(game));
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();

        int x = DungeonAdventure.WIDTH / 2 - DUNGEON_TITLE_WIDTH / 2;
        game.batch.draw(DungeonAdventureTitle, x, TITLE_Y, DUNGEON_TITLE_WIDTH, DUNGEON_TITLE_HEIGHT);
        x = DungeonAdventure.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        x = DungeonAdventure.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
        if (Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y) {

            game.batch.draw(SettingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        } else {
            game.batch.draw(SettingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        playButtonActive.dispose();
        playButtonInactive.dispose();
    }
}
