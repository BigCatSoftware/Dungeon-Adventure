package Controller;

import Model.Warrior;
import View.GameScreen;
import View.SettingsScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dungeonadventure.game.DungeonAdventure;

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        myCamera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

        if (isInBounds(x, y, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT)) {
            Gdx.app.exit();
        } else if (isInBounds(x, y, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT)) {
            myGame.setScreen(new GameScreen(myGame, new Warrior("theWarrior", 100, 100)));
        } else if (isInBounds(x, y, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
        }

        return true; // Return true if the event is handled
    }

    private boolean isInBounds(final float x, final float y, final float buttonX, final float buttonY, final float buttonWidth, final float buttonHeight) {
        return x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight;
    }
}

