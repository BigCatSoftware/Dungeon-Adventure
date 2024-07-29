package view;

import controller.MenuInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;
import static com.dungeonadventure.game.DungeonAdventure.myBackgroundMusic;


/**
 * Represents the main menu screen where players can start the game, adjust settings, or exit the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class MainMenuScreen implements Screen {
    private final DungeonAdventure myGame;
    private final OrthographicCamera myCamera;
    private final Texture myExitButtonActive;
    private final Texture myExitButtonInactive;
    private final Texture myPlayButtonActive;
    private final Texture myPlayButtonInactive;
    private final Texture myDungeonAdventureTitle;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;

    private final int DUNGEON_TITLE_WIDTH = 450;
    private final int DUNGEON_TITLE_HEIGHT = 450;
    private final int EXIT_BUTTON_WIDTH = 250;
    private final int EXIT_BUTTON_HEIGHT = 120;
    private final int PLAY_BUTTON_WIDTH = 300;
    private final int PLAY_BUTTON_HEIGHT = 120;
    private final int EXIT_BUTTON_Y = 250;
    private final int PLAY_BUTTON_Y = 375;
    private final int TITLE_Y = 300;
    private final int SETTINGS_BUTTON_WIDTH = 64;
    private final int SETTINGS_BUTTON_HEIGHT = 64;
    private final int SETTINGS_BUTTON_Y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_HEIGHT;

    private final int EXIT_BUTTON_X;
    private final int PLAY_BUTTON_X;
    private final int SETTINGS_BUTTON_X;

    /**
     * Constructs a new MainMenuScreen.
     *
     * @param theGame the main game instance
     */
    public MainMenuScreen(final DungeonAdventure theGame) {
        myGame = theGame;

        myCamera = new OrthographicCamera();
        myCamera.setToOrtho(false, DungeonAdventure.WIDTH, DungeonAdventure.HEIGHT);

        myExitButtonActive = new Texture("exit_button_active.png");
        myExitButtonInactive = new Texture("exit_button_inactive.png");
        myPlayButtonActive = new Texture("play_button_active.png");
        myPlayButtonInactive = new Texture("play_button_inactive.png");
        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");
        myDungeonAdventureTitle = new Texture("DungeonAdventureTitle.png");

        myBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("WindChimes.ogg"));


        EXIT_BUTTON_X = DungeonAdventure.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        PLAY_BUTTON_X = DungeonAdventure.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        SETTINGS_BUTTON_X = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
    }

    /**
     * Called when the screen is shown.
     * Sets the input processor to handle menu inputs.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MenuInputProcessor(myGame, MainMenuScreen.this, myCamera,
                EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT,
                PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT,
                SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT));

        mySETTINGS.updateMusic();
    }

    /**
     * Renders the screen.
     * Clears the screen, draws the title and buttons.
     *
     * @param delta the time in seconds since the last render
     */
    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        myGame.batch.setProjectionMatrix(myCamera.combined);
        myGame.batch.begin();

        int x = DungeonAdventure.WIDTH / 2 - DUNGEON_TITLE_WIDTH / 2;
        myGame.batch.draw(myDungeonAdventureTitle, x, TITLE_Y, DUNGEON_TITLE_WIDTH, DUNGEON_TITLE_HEIGHT);

        mySETTINGS.updateMusic();

        exitButtonDraw();
        playButtonDraw();
        settingsButtonDraw();

        myGame.batch.end();
    }

    /**
     * Draws the exit button.
     * The button changes appearance based on whether it is hovered over or not.
     */
    private void exitButtonDraw() {
        int x = EXIT_BUTTON_X;
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {
            myGame.batch.draw(myExitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myExitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
    }

    /**
     * Draws the play button.
     * The button changes appearance based on whether it is hovered over or not.
     */
    private void playButtonDraw() {
        int x = PLAY_BUTTON_X;
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {
            myGame.batch.draw(myPlayButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myPlayButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
    }

    /**
     * Draws the settings button.
     * The button changes appearance based on whether it is hovered over or not.
     */
    private void settingsButtonDraw() {
        int x = SETTINGS_BUTTON_X;
        if (Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y) {
            myGame.batch.draw(mySettingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(mySettingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }
    }

    /**
     * Called when the screen is resized.
     *
     * @param width the new width of the screen
     * @param height the new height of the screen
     */
    @Override
    public void resize(final int width, final int height) {
        // Implement if needed
    }

    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {
        // Implement if needed
    }

    /**
     * Called when the screen is resumed.
     */
    @Override
    public void resume() {
        // Implement if needed
    }

    /**
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {
        // Implement if needed
    }

    /**
     * Called when the screen is disposed.
     * Releases all resources.
     */
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        myBackgroundMusic.dispose();
        myExitButtonActive.dispose();
        myExitButtonInactive.dispose();
        myPlayButtonActive.dispose();
        myPlayButtonInactive.dispose();
        mySettingsButtonActive.dispose();
        mySettingsButtonInactive.dispose();
        myDungeonAdventureTitle.dispose();
        myBackgroundMusic.dispose();
    }
}

