package controller;

import view.DungeonScreen;
import view.SettingsScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dungeonadventure.game.DungeonAdventure;

<<<<<<< HEAD:core/src/controller/MenuInputProcessor.java
import static com.dungeonadventure.game.DungeonAdventure.myBackgroundMusic;

=======
>>>>>>> nazarii_branch:core/src/Controller/MenuInputProcessor.java
/**
 * Handles user input on the main menu screen, allowing the player to start the game,
 * adjust settings, or exit the application.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class MenuInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final OrthographicCamera myCamera;

    private final int EXIT_BUTTON_X;
    private final int EXIT_BUTTON_Y;
    private final int EXIT_BUTTON_WIDTH;
    private final int EXIT_BUTTON_HEIGHT;

    private final int PLAY_BUTTON_X;
    private final int PLAY_BUTTON_Y;
    private final int PLAY_BUTTON_WIDTH;
    private final int PLAY_BUTTON_HEIGHT;

    private final int SETTINGS_BUTTON_X;
    private final int SETTINGS_BUTTON_Y;
    private final int SETTINGS_BUTTON_WIDTH;
    private final int SETTINGS_BUTTON_HEIGHT;

    /**
     * Constructs a new MenuInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     * @param camera the camera used to render the screen
     * @param EXIT_BUTTON_X the x-coordinate of the exit button
     * @param EXIT_BUTTON_Y the y-coordinate of the exit button
     * @param EXIT_BUTTON_WIDTH the width of the exit button
     * @param EXIT_BUTTON_HEIGHT the height of the exit button
     * @param PLAY_BUTTON_X the x-coordinate of the play button
     * @param PLAY_BUTTON_Y the y-coordinate of the play button
     * @param PLAY_BUTTON_WIDTH the width of the play button
     * @param PLAY_BUTTON_HEIGHT the height of the play button
     * @param SETTINGS_BUTTON_X the x-coordinate of the settings button
     * @param SETTINGS_BUTTON_Y the y-coordinate of the settings button
     * @param SETTINGS_BUTTON_WIDTH the width of the settings button
     * @param SETTINGS_BUTTON_HEIGHT the height of the settings button
     */
    public MenuInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen, final OrthographicCamera camera,
                              final int EXIT_BUTTON_X, final int EXIT_BUTTON_Y, final int EXIT_BUTTON_WIDTH, final int EXIT_BUTTON_HEIGHT,
                              final int PLAY_BUTTON_X, final int PLAY_BUTTON_Y, final int PLAY_BUTTON_WIDTH, final int PLAY_BUTTON_HEIGHT,
                              final int SETTINGS_BUTTON_X, final int SETTINGS_BUTTON_Y, final int SETTINGS_BUTTON_WIDTH, final int SETTINGS_BUTTON_HEIGHT) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        myCamera = camera;

        this.EXIT_BUTTON_X = EXIT_BUTTON_X;
        this.EXIT_BUTTON_Y = EXIT_BUTTON_Y;
        this.EXIT_BUTTON_WIDTH = EXIT_BUTTON_WIDTH;
        this.EXIT_BUTTON_HEIGHT = EXIT_BUTTON_HEIGHT;

        this.PLAY_BUTTON_X = PLAY_BUTTON_X;
        this.PLAY_BUTTON_Y = PLAY_BUTTON_Y;
        this.PLAY_BUTTON_WIDTH = PLAY_BUTTON_WIDTH;
        this.PLAY_BUTTON_HEIGHT = PLAY_BUTTON_HEIGHT;

        this.SETTINGS_BUTTON_X = SETTINGS_BUTTON_X;
        this.SETTINGS_BUTTON_Y = SETTINGS_BUTTON_Y;
        this.SETTINGS_BUTTON_WIDTH = SETTINGS_BUTTON_WIDTH;
        this.SETTINGS_BUTTON_HEIGHT = SETTINGS_BUTTON_HEIGHT;
    }

    /**
     * Handles touch down events on the screen.
     *
     * @param screenX the x-coordinate of the touch
     * @param screenY the y-coordinate of the touch
     * @param pointer the pointer for the event
     * @param button the button for the event
     * @return true if the event is handled, false otherwise
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        myCamera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

        System.out.println("Touch at: (" + x + ", " + y + ")");

        if (isInBounds(x, y, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT)) {
            Gdx.app.exit();
        } else if (isInBounds(x, y, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT)) {
            myBackgroundMusic.stop();
            myGame.setScreen(new DungeonScreen(myGame));
        } else if (isInBounds(x, y, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
        } else {
            System.out.println("Touch event did not match any button.");
        }

        return true; // Return true if the event is handled
    }

    /**
     * Checks if the touch is within the bounds of a button.
     *
     * @param x the x-coordinate of the touch
     * @param y the y-coordinate of the touch
     * @param buttonX the x-coordinate of the button
     * @param buttonY the y-coordinate of the button
     * @param buttonWidth the width of the button
     * @param buttonHeight the height of the button
     * @return true if the touch is within the bounds of the button, false otherwise
     */
    private boolean isInBounds(final float x, final float y, final float buttonX, final float buttonY, final float buttonWidth, final float buttonHeight) {
        return x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight;
    }
}