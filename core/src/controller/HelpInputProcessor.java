package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;

/**
 * The HelpInputProcessor class handles input events for the Help screen.
 * It allows the player to navigate back to the previous screen by pressing a specific key.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class HelpInputProcessor extends InputAdapter {
    private final Screen myPreviousScreen;
    private final DungeonAdventure myGame;

    /**
     * Constructs a HelpInputProcessor instance.
     *
     * @param theGame The main game instance.
     * @param thePreviousScreen The previous screen to return to.
     */
    public HelpInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myPreviousScreen = thePreviousScreen;
        myGame = theGame;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.B:
                myGame.setScreen(myPreviousScreen);
                return true;
            default:
                return false;
        }
    }
}
