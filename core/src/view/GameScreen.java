package view;

<<<<<<< HEAD
import controller.PlayerInputProcessor;
import model.Hero;
import model.Dungeon;
=======
import com.badlogic.gdx.graphics.g2d.Sprite;
import controller.DungeonInputProcessor;
import controller.PlayerInputProcessor;
>>>>>>> nazarii_branch
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
<<<<<<< HEAD

import static com.dungeonadventure.game.DungeonAdventure.*;
=======
import model.GameMaster;
import model.Position;
import model.Priestess;
import model.Thief;
import model.Warrior;

import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_Y;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_HEIGHT;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_WIDTH;
>>>>>>> nazarii_branch

/**
 * Represents the main game screen where the dungeon is rendered and the player interacts with the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class GameScreen implements Screen {
<<<<<<< HEAD
=======
    private static final int TILE_SIZE = 16;
>>>>>>> nazarii_branch

    private final DungeonAdventure myGame;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;
<<<<<<< HEAD
    private final DungeonRenderer myDungeonRenderer;
    private final Hero myPlayer;
    private final int SETTINGS_BUTTON_X;

=======
    private final Texture myPlayerTexture;
    private final DungeonRenderer myDungeonRenderer;
>>>>>>> nazarii_branch

    /**
     * Constructs a new GameScreen.
     *
     * @param theGame the main game instance
<<<<<<< HEAD
     * @param thePlayer the player's hero character
     */
    public GameScreen(final DungeonAdventure theGame, final Hero thePlayer) {
        myGame = theGame;
        myPlayer = thePlayer;
        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");

        Dungeon myDungeon = new Dungeon();
        myDungeonRenderer = new DungeonRenderer(myDungeon);

        SETTINGS_BUTTON_X = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
    }

=======
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
>>>>>>> nazarii_branch
    /**
     * Called when the screen is shown.
     * Sets the input processor to handle player inputs.
     */
    @Override
    public void show() {
<<<<<<< HEAD
        Gdx.input.setInputProcessor(new PlayerInputProcessor(myPlayer, myGame, GameScreen.this));
        mySETTINGS.updateMusic();
=======
        Gdx.input.setInputProcessor(new PlayerInputProcessor(myGame,GameScreen.this));//new PlayerInputProcessor(myPlayer, myGame, GameScreen.this));
>>>>>>> nazarii_branch
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

<<<<<<< HEAD
        mySETTINGS.updateMusic();

        // Render the dungeon
        myDungeonRenderer.render(myGame.batch);

=======
        // Render the dungeon
        myDungeonRenderer.render(myGame.batch);
        // Render character
        myGame.batch.draw(myPlayerTexture, getPlayerPos().getMyX()*TILE_SIZE,getPlayerPos().getMyY()*TILE_SIZE,TILE_SIZE,TILE_SIZE);
>>>>>>> nazarii_branch
        settingsButtonDraw();

        myGame.batch.end();
    }

    /**
     * Draws the settings button.
     * The button changes appearance based on whether it is hovered over or not.
     */
    private void settingsButtonDraw() {
<<<<<<< HEAD
        int x = SETTINGS_BUTTON_X;
        if (isHovered(x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
=======
        int x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
        if (Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y) {
>>>>>>> nazarii_branch
            myGame.batch.draw(mySettingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(mySettingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }
    }
<<<<<<< HEAD
    /**
     * Checks if the mouse is hovering over a specified button.
     *
     * @param buttonX the x-coordinate of the button
     * @param buttonY the y-coordinate of the button
     * @param buttonWidth the width of the button
     * @param buttonHeight the height of the button
     * @return true if the mouse is hovering over the button, false otherwise
     */
    private boolean isHovered(int buttonX, int buttonY, int buttonWidth, int buttonHeight) {
        return Gdx.input.getX() < buttonX + buttonWidth && Gdx.input.getX() > buttonX &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() < buttonY + buttonHeight &&
                DungeonAdventure.HEIGHT - Gdx.input.getY() > buttonY;
    }

=======
    private Position getPlayerPos(){
        return GameMaster.getInstance().getPlayer().getPosition();
    }
>>>>>>> nazarii_branch
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
