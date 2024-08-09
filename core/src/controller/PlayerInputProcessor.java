package controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import model.GameMaster;
import view.CombatScreen;
import view.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.dungeonadventure.game.DungeonAdventure;
import view.SettingsScreen;

/**
 * Handles user input for player actions and settings navigation in the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class PlayerInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final GameScreen myPreviousScreen;
    private final int SETTINGS_BUTTON_X;
    private final int SETTINGS_BUTTON_Y;
    private final int SETTINGS_BUTTON_WIDTH;
    private final int SETTINGS_BUTTON_HEIGHT;
    private final Camera myCamera;

    /**
     * Constructs a new PlayerInputProcessor.
     *
     * @param theGame                the main game instance
     * @param thePreviousScreen      the previous screen to return to
     * @param SETTINGS_BUTTON_X
     * @param SETTINGS_BUTTON_Y
     * @param SETTINGS_BUTTON_WIDTH
     * @param SETTINGS_BUTTON_HEIGHT
     */
    public PlayerInputProcessor(final DungeonAdventure theGame, final GameScreen thePreviousScreen,
                                final int SETTINGS_BUTTON_X, final int SETTINGS_BUTTON_Y,
                                final int SETTINGS_BUTTON_WIDTH, final int SETTINGS_BUTTON_HEIGHT, final Camera theCamera) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        this.SETTINGS_BUTTON_X = SETTINGS_BUTTON_X;
        this.SETTINGS_BUTTON_Y = SETTINGS_BUTTON_Y;
        this.SETTINGS_BUTTON_WIDTH = SETTINGS_BUTTON_WIDTH;
        this.SETTINGS_BUTTON_HEIGHT = SETTINGS_BUTTON_HEIGHT;
        myCamera = theCamera;

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
                if(GameMaster.getInstance().isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                break;
            case Input.Keys.DOWN:
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()-1].isWalkable()){
                    gm.getPlayer().moveCharacterDown();
                }
                if(GameMaster.getInstance().isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                break;
            case Input.Keys.LEFT:
                if(gm.getMap()[gm.getPlayerX()-1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterLeft();
                }
                if(GameMaster.getInstance().isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                break;
            case Input.Keys.RIGHT:
                if(gm.getMap()[gm.getPlayerX()+1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterRight();
                }
                if(GameMaster.getInstance().isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                break;
            case Input.Keys.ESCAPE:
                myPreviousScreen.showMenu();
            default:
                return false; // Indicates that the key event was not handled
        }
        myPreviousScreen.setPlayerImagePosition();
        return true; // Indicates that the key event was handled
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
        if (isInBounds(x, y, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
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