package controller;

<<<<<<< HEAD:core/src/controller/PlayerInputProcessor.java
import model.Hero;
=======
import model.GameMaster;
>>>>>>> nazarii_branch:core/src/Controller/PlayerInputProcessor.java
import view.SettingsScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.*;

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
<<<<<<< HEAD:core/src/controller/PlayerInputProcessor.java
     * @param thePlayer the player character
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public PlayerInputProcessor(final Hero thePlayer, final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myPlayer = thePlayer;
=======
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public PlayerInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen) {
>>>>>>> nazarii_branch:core/src/Controller/PlayerInputProcessor.java
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
        } else {
            System.out.println("Touch event did not match any button.");
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
        GameMaster gm = GameMaster.getInstance();
        switch (keycode) {
            case Input.Keys.UP:
                //check if cell at up is walkable
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()+1].isWalkable()){
                    gm.getPlayer().moveCharacterUp();
                }
                break;
            case Input.Keys.DOWN:
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()-1].isWalkable()){
                    gm.getPlayer().moveCharacterDown();
                }
                break;
            case Input.Keys.LEFT:
                if(gm.getMap()[gm.getPlayerX()-1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterLeft();
                }
                break;
            case Input.Keys.RIGHT:
                if(gm.getMap()[gm.getPlayerX()+1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterRight();
                }
                break;
            case Input.Keys.SPACE:
                gm.getPlayer().attack();
                //TODO: place combat screen to test combat.
                break;
            default:
                return false; // Indicates that the key event was not handled
        }
        return true; // Indicates that the key event was handled
    }
}
