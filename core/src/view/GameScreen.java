package view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import controller.DungeonInputProcessor;
import controller.PlayerInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
import model.GameMaster;
import model.Position;
import model.Priestess;
import model.Thief;
import model.Warrior;

import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_Y;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_HEIGHT;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_WIDTH;

/**
 * Represents the main game screen where the dungeon is rendered and the player interacts with the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class GameScreen implements Screen {
    private static final int TILE_SIZE = 16;

    private final DungeonAdventure myGame;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;
    private final Texture myPlayerTexture;
    private final DungeonRenderer myDungeonRenderer;

    /**
     * Constructs a new GameScreen.
     *
     * @param theGame the main game instance
     */
    public GameScreen(final DungeonAdventure theGame) {
        myGame = theGame;
        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");
        myPlayerTexture = initPlayerTexture();
        myDungeonRenderer = new DungeonRenderer();
    }
    private Texture initPlayerTexture(){
        Texture playerTexture = null;
        if(GameMaster.getInstance().getPlayer() instanceof Warrior){
            playerTexture = new Texture("Pixel Warrior.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Priestess){
            playerTexture = new Texture("Pixel Priestess.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Thief){
            playerTexture = new Texture("Pixel Thief.png");
        }
        else{
            throw new IllegalArgumentException("Can't determine hero class from game master to" +
                " draw its texture");
        }
        return playerTexture;
    }
    /**
     * Called when the screen is shown.
     * Sets the input processor to handle player inputs.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new PlayerInputProcessor(myGame,GameScreen.this));//new PlayerInputProcessor(myPlayer, myGame, GameScreen.this));
    }

    /**
     * Renders the screen.
     * Clears the screen, renders the dungeon, and draws the settings button.
     *
     * @param delta the time in seconds since the last render
     */
    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        myGame.batch.begin();

        // Render the dungeon
        myDungeonRenderer.render(myGame.batch);
        // Render character
        myGame.batch.draw(myPlayerTexture, getPlayerPos().getMyX()*TILE_SIZE,getPlayerPos().getMyY()*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        settingsButtonDraw();

        myGame.batch.end();
    }

    /**
     * Draws the settings button.
     * The button changes appearance based on whether it is hovered over or not.
     */
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
    private Position getPlayerPos(){
        return GameMaster.getInstance().getPlayer().getPosition();
    }
    /**
     * Called when the screen is resized.
     *
     * @param width the new width of the screen
     * @param height the new height of the screen
     */
    @Override
    public void resize(final int width, final int height) {
        // Handle resizing if needed
    }

    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {
        // Handle pause logic if needed
    }

    /**
     * Called when the screen is resumed.
     */
    @Override
    public void resume() {
        // Handle resume logic if needed
    }

    /**
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {
        // Handle hide logic if needed
    }

    /**
     * Called when the screen is disposed.
     * Releases all resources.
     */
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        mySettingsButtonActive.dispose();
        mySettingsButtonInactive.dispose();
        // Dispose of other resources if needed
    }
}
