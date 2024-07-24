package controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.*;

public class SettingsInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final Rectangle mySoundButton;
    private final Rectangle myPlusButton;
    private final Rectangle myMinusButton;
    private final Rectangle myBackButton;
    private final OrthographicCamera myCamera;

    public SettingsInputProcessor(final DungeonAdventure theGame, final Screen thePreviousScreen, final OrthographicCamera theCamera,
                                  final Rectangle theSoundButton, final Rectangle thePlusButton,
                                  final Rectangle theMinusButton, final Rectangle theBackButton) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        mySoundButton = theSoundButton;
        myBackButton = theBackButton;
        myMinusButton = theMinusButton;
        myPlusButton = thePlusButton;
        myCamera = theCamera;

    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        myCamera.unproject(touchPos);

        if (mySoundButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.toggleSound();
        } else if (myPlusButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.increaseVolume();
        } else if (myMinusButton.contains(touchPos.x, touchPos.y)) {
            mySETTINGS.decreaseVolume();
        } else if (myBackButton.contains(touchPos.x, touchPos.y)) {
            myGame.setScreen(myPreviousScreen);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }
}