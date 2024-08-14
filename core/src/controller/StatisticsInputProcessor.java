package controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dungeonadventure.game.DungeonAdventure;
import view.MainMenuScreen;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

public class StatisticsInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
//    private final Screen myPreviousScreen;
    private final Rectangle myMainMenuButton;
    private final OrthographicCamera myCamera;

    public StatisticsInputProcessor(final DungeonAdventure theGame,
                                    final Screen thePreviousScreen,
                                    final Rectangle theMainMenuButton,
                                    final OrthographicCamera theCamera) {
        myGame = theGame;
//        myPreviousScreen = thePreviousScreen;
        myMainMenuButton = theMainMenuButton;
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
        if (myMainMenuButton.contains(touchPos.x, touchPos.y)) {
            myGame.setScreen(new MainMenuScreen(myGame));
        } else {
            System.out.println("Touch event did not match any button.");
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
