package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dungeonadventure.game.DungeonAdventure;

public class StatisticsScreen extends ScreenAdapter {
    private final static int HERO_ICON_X = 40;
    private final static int HERO_ICON_Y = 460;
    private final static int BUTTON_WIDTH = 154;
    private final static int BUTTON_HEIGHT = 54;
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final BitmapFont myFont;
    private final Label myHeroHP;
    private final TextButton[] myButtons;

    public StatisticsScreen(final DungeonAdventure theGame, final Screen thePreviousScreen,
                            final BitmapFont theFont, final Label theHeroHP,
                            final TextButton[] theButtons) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        myFont = theFont;
        myHeroHP = theHeroHP;
        myButtons = theButtons;
    }
}
