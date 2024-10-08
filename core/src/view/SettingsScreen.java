package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.dungeonadventure.game.DungeonAdventure;
import controller.SettingsInputProcessor;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

public class SettingsScreen extends ScreenAdapter{
private final DungeonAdventure myGame;
private final Screen myPreviousScreen;

private final OrthographicCamera myCamera;
private final SpriteBatch myBatch;
private final BitmapFont myFont;

private final Texture mySoundActive;
private final Texture mySoundInactive;
private final Texture myMuteActive;
private final Texture myMuteInactive;
private final Texture myPlusActive;
private final Texture myPlusInactive;
private final Texture myMinusActive;
private final Texture myMinusInactive;
private final Texture myBackActive;
private final Texture myBackInactive;
private final Texture[] myVolumeBars;

private final Rectangle mySoundButton;
private final Rectangle myPlusButton;
private final Rectangle myMinusButton;
private final Rectangle myVolumeBar;
private final Rectangle myBackButton;

private static final int SOUND_BUTTON_WIDTH = 64;
private static final int SOUND_BUTTON_HEIGHT = 64;
private static final int PLUSMINUS_BUTTON_WIDTH = 64;
private static final int PLUSMINUS_BUTTON_HEIGHT = 64;
private static final int VOLUME_WIDTH = 512;
private static final int VOLUME_HEIGHT = 64;
private static final int BACK_BUTTON_WIDTH = 48;
private static final int BACK_BUTTON_HEIGHT = 48;

/**
 * Constructs a new SettingsScreen.
 *
 * @param theGame the main game instance
 * @param previousScreen the previous screen to return to after adjusting settings
 */
public SettingsScreen(final DungeonAdventure theGame, final Screen previousScreen) {
    myGame = theGame;
    myPreviousScreen = previousScreen;

    myCamera = new OrthographicCamera();
    myCamera.setToOrtho(false, 800, 480);
    myBatch = new SpriteBatch();
    myFont = new BitmapFont();

    mySoundActive = new Texture("SoundActive.png");
    mySoundInactive = new Texture("SoundInactive.png");
    myMuteActive = new Texture("MuteActive.png");
    myMuteInactive = new Texture("MuteInactive.png");
    myPlusActive = new Texture("PlusActive.png");
    myPlusInactive = new Texture("PlusInactive.png");
    myMinusActive = new Texture("MinusActive.png");
    myMinusInactive = new Texture("MinusInactive.png");
    myBackActive = new Texture("BackActive.png");
    myBackInactive = new Texture("BackInactive.png");

    myVolumeBars = new Texture[11];
    for (int i = 0; i <= 10; i++) {
        myVolumeBars[i] = new Texture(i + "VolumeBar.png");
    }

    myBackButton = new Rectangle(0, 436, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
    mySoundButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - (2 * ((float) SOUND_BUTTON_WIDTH / 2)) + 32, 250, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
    myMinusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) - 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    myPlusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) + 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    myVolumeBar = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) VOLUME_WIDTH / 2) + 128, 350, VOLUME_WIDTH, VOLUME_HEIGHT);
}
/**
 * Called when the screen is shown.
 * Sets the input processor to handle settings inputs.
 */
@Override
public void show() {
    Gdx.input.setInputProcessor(new SettingsInputProcessor(myGame, myPreviousScreen, myCamera,
            mySoundButton, myPlusButton, myMinusButton, myBackButton));
    mySETTINGS.updateMusic();
}

/**
 * Renders the screen.
 * Clears the screen, updates the camera, and draws the settings UI elements.
 *
 * @param delta the time in seconds since the last render
 */
@Override
public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    myCamera.update();
    myBatch.setProjectionMatrix(myCamera.combined);

    myBatch.begin();
    mySETTINGS.updateMusic();

    drawBackButton();
    drawSound();
    drawPlus();
    drawMinus();
    drawVolume();

    myBatch.end();
}

/**
 * Draws the sound button.
 * The button changes appearance based on the sound setting.
 */
private void drawSound() {
    Texture activeTexture = mySETTINGS.isSoundOn() ? mySoundActive : myMuteActive;
    Texture inactiveTexture = mySETTINGS.isSoundOn() ? mySoundInactive : myMuteInactive;
    myBatch.draw(isHovered(mySoundButton, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT) ? inactiveTexture : activeTexture, mySoundButton.x, mySoundButton.y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
}

/**
 * Draws the plus button for increasing the volume.
 */
private void drawPlus() {
    myBatch.draw(isHovered(myPlusButton, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT) ? myPlusInactive : myPlusActive, myPlusButton.x, myPlusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
}

/**
 * Draws the minus button for decreasing the volume.
 */
private void drawMinus() {
    myBatch.draw(myMinusActive, myMinusButton.x, myMinusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
}

/**
 * Draws the volume bar based on the current volume level.
 */
private void drawVolume() {
    myBatch.draw(myVolumeBars[mySETTINGS.getVolumeLevel()], myVolumeBar.x, myVolumeBar.y, VOLUME_WIDTH, VOLUME_HEIGHT);
}

/**
 * Draws the back button to return to the previous screen.
 */
private void drawBackButton() {
    myBatch.draw(myBackActive, myBackButton.x, myBackButton.y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
}

/**
 * Checks if the mouse is hovering over a specified button.
 *
 * @param theButton the rectangle representing the button
 * @return true if the mouse is hovering over the button, false otherwise
 */
private boolean isHovered(final Rectangle theButton, final int theButtonWidth, final int theButtonHeight) {
    return Gdx.input.getX() < theButton.x + theButtonWidth && Gdx.input.getX() > theButton.x &&
            DungeonAdventure.HEIGHT - Gdx.input.getY() < theButton.y + theButtonHeight &&
            DungeonAdventure.HEIGHT - Gdx.input.getY() > theButton.y;
}

/**
 * Called when the screen is disposed.
 * Releases all resources.
 */
@Override
public void dispose() {
    myBatch.dispose();
    myFont.dispose();
    mySoundActive.dispose();
    mySoundInactive.dispose();
    myMuteActive.dispose();
    myMuteInactive.dispose();
    myPlusActive.dispose();
    myPlusInactive.dispose();
    myMinusActive.dispose();
    myMinusInactive.dispose();
    for (Texture texture : myVolumeBars) {
        texture.dispose();
    }
}
}