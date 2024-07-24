package controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;
import model.GameMaster;
import view.CombatScreen;

/**
 * This class handles input from the user during combat sequence. Will have a functionality
 * to support gameplay through mouse or keyboard.
 */
public final class CombatInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;

    /**
     * Constructs a new PlayerInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public CombatInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
    }
    @Override
    public boolean keyDown(int keycode){
        switch(keycode){
            case Input.Keys.C:
                myGame.setScreen(myPreviousScreen);
                if(myPreviousScreen instanceof CombatScreen){
                    myPreviousScreen.dispose();
                }
            break;
            case Input.Keys.A:
            default:
                return false; // Indicates that the key event was not handled
        }
        return true;  // Indicates that the key event was handled
    }
}
