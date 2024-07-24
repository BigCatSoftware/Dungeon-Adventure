package Controller;

import Model.Priestess;
import Model.Thief;
import Model.Warrior;
import View.DungeonScreen;
import View.GameScreen;
import View.MainMenuScreen;
import View.SettingsScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dungeonadventure.game.DungeonAdventure;

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        myCamera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

        if (isInBounds(x, y, BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT)) {
            myGame.setScreen(new MainMenuScreen(myGame));
        } else if (isInBounds(x, y, WARRIOR_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            myGame.setScreen(new GameScreen(myGame, new Warrior("WARRIOR", 0, 0)));
        } else if (isInBounds(x, y, THIEF_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            //myGame.setScreen(new DungeonScreen(myGame, new Thief("THIEF", 0, 0)));
        } else if (isInBounds(x, y, PRIESTESS_BUTTON_X, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            //myGame.setScreen(new DungeonScreen(myGame, new Priestess("PRIESTESS", 0, 0)));
        } else if (isInBounds(x, y, LOAD_BUTTON_X, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT)) {
            // Load functionality
        } else if (isInBounds(x, y, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
        }

        return true; // Return true if the event is handled
    }

    private boolean isInBounds(final float x, final float y, final float buttonX, final float buttonY, final float buttonWidth, final float buttonHeight) {
        return x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight;
    }
}
