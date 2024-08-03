package controller;

<<<<<<< HEAD:core/src/controller/DungeonInputProcessor.java
=======
import model.GameMaster;
import model.NameGenerator;
import model.Priestess;
import model.Thief;
>>>>>>> nazarii_branch:core/src/Controller/DungeonInputProcessor.java
import model.Warrior;
import view.DungeonScreen;
import view.GameScreen;
import view.MainMenuScreen;
import view.SettingsScreen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.myBackgroundMusic;

/**
 * Handles user input on the dungeon screen, allowing the player to navigate and select various options.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class DungeonInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final OrthographicCamera myCamera;

    private final int BACK_BUTTON_X;
    private final int BACK_BUTTON_Y;
    private final int BACK_BUTTON_WIDTH;
    private final int BACK_BUTTON_HEIGHT;

    private final int WARRIOR_BUTTON_X;
    private final int THIEF_BUTTON_X;
    private final int PRIESTESS_BUTTON_X;

    private final int HERO_BUTTON_Y;
    private final int HERO_BUTTON_WIDTH;
    private final int HERO_BUTTON_HEIGHT;

    private final int LOAD_BUTTON_X;
    private final int LOAD_BUTTON_Y;
    private final int LOAD_BUTTON_WIDTH;
    private final int LOAD_BUTTON_HEIGHT;

    private final int SETTINGS_BUTTON_X;
    private final int SETTINGS_BUTTON_Y;
    private final int SETTINGS_BUTTON_WIDTH;
    private final int SETTINGS_BUTTON_HEIGHT;

    /**
     * Constructs a new DungeonInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     * @param theCamera the camera used to render the screen
     * @param BACK_BUTTON_X the x-coordinate of the back button
     * @param BACK_BUTTON_Y the y-coordinate of the back button
     * @param BACK_BUTTON_WIDTH the width of the back button
     * @param BACK_BUTTON_HEIGHT the height of the back button
     * @param WARRIOR_BUTTON_X the x-coordinate of the warrior button
     * @param HERO_BUTTON_Y the y-coordinate of the hero buttons
     * @param HERO_BUTTON_WIDTH the width of the hero buttons
     * @param HERO_BUTTON_HEIGHT the height of the hero buttons
     * @param THIEF_BUTTON_X the x-coordinate of the thief button
     * @param PRIESTESS_BUTTON_X the x-coordinate of the priestess button
     * @param LOAD_BUTTON_X the x-coordinate of the load button
     * @param LOAD_BUTTON_Y the y-coordinate of the load button
     * @param LOAD_BUTTON_WIDTH the width of the load button
     * @param LOAD_BUTTON_HEIGHT the height of the load button
     * @param SETTINGS_BUTTON_X the x-coordinate of the settings button
     * @param SETTINGS_BUTTON_Y the y-coordinate of the settings button
     * @param SETTINGS_BUTTON_WIDTH the width of the settings button
     * @param SETTINGS_BUTTON_HEIGHT the height of the settings button
     */
    public DungeonInputProcessor(final DungeonAdventure theGame, final DungeonScreen thePreviousScreen, final OrthographicCamera theCamera,
                                 final int BACK_BUTTON_X, final int BACK_BUTTON_Y, final int BACK_BUTTON_WIDTH, final int BACK_BUTTON_HEIGHT,
                                 final int WARRIOR_BUTTON_X, final int HERO_BUTTON_Y, final int HERO_BUTTON_WIDTH, final int HERO_BUTTON_HEIGHT,
                                 final int THIEF_BUTTON_X,
                                 final int PRIESTESS_BUTTON_X,
                                 final int LOAD_BUTTON_X, final int LOAD_BUTTON_Y, final int LOAD_BUTTON_WIDTH, final int LOAD_BUTTON_HEIGHT,
                                 final int SETTINGS_BUTTON_X, final int SETTINGS_BUTTON_Y, final int SETTINGS_BUTTON_WIDTH, final int SETTINGS_BUTTON_HEIGHT) {
        this.myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        this.myCamera = theCamera;

        this.BACK_BUTTON_X = BACK_BUTTON_X;
        this.BACK_BUTTON_Y = BACK_BUTTON_Y;
        this.BACK_BUTTON_WIDTH = BACK_BUTTON_WIDTH;
        this.BACK_BUTTON_HEIGHT = BACK_BUTTON_HEIGHT;

        this.WARRIOR_BUTTON_X = WARRIOR_BUTTON_X;
        this.THIEF_BUTTON_X = THIEF_BUTTON_X;
        this.PRIESTESS_BUTTON_X = PRIESTESS_BUTTON_X;

        this.HERO_BUTTON_Y = HERO_BUTTON_Y;
        this.HERO_BUTTON_WIDTH = HERO_BUTTON_WIDTH;
        this.HERO_BUTTON_HEIGHT = HERO_BUTTON_HEIGHT;

        this.LOAD_BUTTON_X = LOAD_BUTTON_X;
        this.LOAD_BUTTON_Y = LOAD_BUTTON_Y;
        this.LOAD_BUTTON_WIDTH = LOAD_BUTTON_WIDTH;
        this.LOAD_BUTTON_HEIGHT = LOAD_BUTTON_HEIGHT;

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

        if (isInBounds(x, y, BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT)) {
            myBackgroundMusic.stop();
            myGame.setScreen(new MainMenuScreen(myGame));
        } else if (isInBounds(x, y, WARRIOR_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            GameMaster.getInstance().setPlayer(new Warrior(NameGenerator.getWarriorName(), 1, 1));
            myGame.setScreen(new GameScreen(myGame));
        } else if (isInBounds(x, y, THIEF_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            GameMaster.getInstance().setPlayer(new Thief(NameGenerator.getThiefName(), 1, 1));
            myGame.setScreen(new DungeonScreen(myGame));
        } else if (isInBounds(x, y, PRIESTESS_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            GameMaster.getInstance().setPlayer(new Priestess(NameGenerator.getPriestessName(), 1,1));
            myGame.setScreen(new DungeonScreen(myGame));
        } else if (isInBounds(x, y, LOAD_BUTTON_X, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT)) {
            // Load functionality
        } else if (isInBounds(x, y, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
<<<<<<< HEAD:core/src/controller/DungeonInputProcessor.java
        } else {
            System.out.println("Touch event did not match any button.");
=======

>>>>>>> nazarii_branch:core/src/Controller/DungeonInputProcessor.java
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
