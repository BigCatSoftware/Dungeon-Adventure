package controller;

import view.SettingsScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.*;
import static controller.DungeonInputProcessor.GAME_MASTER;

/**
 * Handles user input for player actions and settings navigation in the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class PlayerInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;

    /**
     * Constructs a new PlayerInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public PlayerInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
    }

    /**
     * Handles touch down events on the screen, specifically for navigating to the settings screen.
     *
     * @param screenX the x-coordinate of the touch
     * @param screenY the y-coordinate of the touch
     * @param pointer the pointer for the event
     * @param button the button for the event
     * @return true if the touch event is handled, false otherwise
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to match the game's coordinate system
        int x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
        int y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_Y - SETTINGS_BUTTON_HEIGHT;

        if (screenX >= x && screenX <= x + SETTINGS_BUTTON_WIDTH &&
                screenY >= y && screenY <= y + SETTINGS_BUTTON_HEIGHT) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
            return true; // Indicates that the touch event was handled
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    /**
     * Handles key down events for player movement and actions.
     *
     * @param keycode the key code of the pressed key
     * @return true if the key event is handled, false otherwise
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                //check if cell at up is walkable
                if(GAME_MASTER.getMap()[GAME_MASTER.getPlayerX()][GAME_MASTER.getPlayerY()+1].isWalkable()){
                    GAME_MASTER.getPlayer().moveCharacterUp();
                }
                break;
            case Input.Keys.DOWN:
                if(GAME_MASTER.getMap()[GAME_MASTER.getPlayerX()][GAME_MASTER.getPlayerY()-1].isWalkable()){
                    GAME_MASTER.getPlayer().moveCharacterDown();
                }
                break;
            case Input.Keys.LEFT:
                if(GAME_MASTER.getMap()[GAME_MASTER.getPlayerX()-1][GAME_MASTER.getPlayerY()].isWalkable()){
                    GAME_MASTER.getPlayer().moveCharacterLeft();
                }
                break;
            case Input.Keys.RIGHT:
                if(GAME_MASTER.getMap()[GAME_MASTER.getPlayerX()+1][GAME_MASTER.getPlayerY()].isWalkable()){
                    GAME_MASTER.getPlayer().moveCharacterRight();
                }
                break;
            case Input.Keys.SPACE:
                GAME_MASTER.getPlayer().attack();
                break;
            default:
                return false; // Indicates that the key event was not handled
        }
        return true; // Indicates that the key event was handled
    }
}
