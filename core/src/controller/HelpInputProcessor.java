package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;

public class HelpInputProcessor extends InputAdapter {
    private final Screen myPreviousScreen;
    private final DungeonAdventure myGame;

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
