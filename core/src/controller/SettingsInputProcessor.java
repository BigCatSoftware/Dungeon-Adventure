package controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

/**
 * Handles user input for the settings screen of the game.
 * Processes touch events for sound settings, volume control, and navigation.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class SettingsInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final Rectangle mySoundButton;
    private final Rectangle myPlusButton;
    private final Rectangle myMinusButton;
    private final Rectangle myBackButton;
    private final OrthographicCamera myCamera;

    /**
     * Constructs a new SettingsInputProcessor.
     *
     * @param theGame          the instance of the game
     * @param thePreviousScreen the previous screen to return to when back button is pressed
     * @param theCamera        the camera for the current screen
     * @param theSoundButton   the rectangle representing the sound button area
     * @param thePlusButton    the rectangle representing the plus button area
     * @param theMinusButton   the rectangle representing the minus button area
     * @param theBackButton    the rectangle representing the back button area
     */
    public SettingsInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen, final OrthographicCamera theCamera,
                                  final Rectangle theSoundButton, final Rectangle thePlusButton,
                                  final Rectangle theMinusButton, final Rectangle theBackButton) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        mySoundButton = theSoundButton;
        myBackButton = theBackButton;
        myMinusButton = theMinusButton;
        myPlusButton = thePlusButton;
        myCamera = theCamera;
    }

    /**
     * Processes touch down events.
     *
     * @param screenX the x coordinate of the touch event
     * @param screenY the y coordinate of the touch event
     * @param pointer  the pointer index of the touch event
     * @param button   the button index of the touch event
     * @return true if the event was handled, false otherwise
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        myCamera.unproject(touchPos);

        // Check if the touch position intersects with any buttons
        if (mySoundButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.toggleSound();
        } else if (myPlusButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.increaseVolume();
        } else if (myMinusButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.decreaseVolume();
        } else if (myBackButton.contains(touchPos.x, touchPos.y)) {
            myGame.setScreen(myPreviousScreen);
        } else {
            System.out.println("Touch event did not match any button.");
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }
}