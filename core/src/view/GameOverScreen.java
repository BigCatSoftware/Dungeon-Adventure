package view;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
import model.GameMaster;

import java.util.Random;

/**
 * Represents the screen displayed when the game is over.
 * This class will manage the display of game over information and options
 * for the player to either restart or exit the game.
 * @author alvarovaldez-duran
 * @author Nazarii Revitskyi
 * @version 1.0
 */
public class GameOverScreen implements Screen {
    private final static int LABEL_WIDTH = 780;
    private final static int LABEL_HEIGHT = 100;
    private final static int BUTTON_WIDTH = 154;
    private final static int BUTTON_HEIGHT = 54;
    private final DungeonAdventure myGame;
    private final Stage myStage;
    private final Table myTable;
    private final Label myGameOverTitle;
    private String[] myGameOverTitleNames = initGameOverNames();
    private final Label myDeathLabel;
    private final String myDeathMessage;
    private final TextButton myMenuButton;
    private final BitmapFont myFont;
    /**
     * Constructor for the GameOverScreen.
     * Initialize any resources or settings necessary for the game over screen.
     */
    public GameOverScreen(final DungeonAdventure theGame) {
        mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/DeadPlayer.ogg")));
        myGame = theGame;
        myStage = new Stage();
        myTable = new Table();
        myFont = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"), Gdx.files.internal("fonts/alagard.png"), false);
        myGameOverTitle = initLabel(getRandomGameOverTitle(), Color.GOLDENROD, 2f, myFont, 0,DungeonAdventure.HEIGHT/2);
        myGameOverTitle.setWrap(true);
        myDeathMessage = getDeathLog();
        myDeathLabel = initLabel(myDeathMessage, Color.LIGHT_GRAY, 0.8f, myFont, 0, DungeonAdventure.HEIGHT/2-(int)myGameOverTitle.getHeight());
        myDeathLabel.setWrap(true);
        myMenuButton = initMainMenuButton();
        myTable.setFillParent(true);
        myStage.addActor(myTable);
        myTable.setBackground(new TextureRegionDrawable(new Texture("GameOver2.png")));
        //add widgets
        myTable.addActor(myGameOverTitle);
        myTable.addActor(myDeathLabel);
        myTable.addActor(myMenuButton);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
    }

    /**
     * Called when the screen is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
    }

    /**
     * Called when the screen is shown.
     * This is typically used to set up input processors or start animations.
     */
    public void show() {
        final InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(myStage);
        mux.addProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode){
                if (keyCode == Input.Keys.ENTER) {
                    myMenuButton.toggle();
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(mux);
    }

    /**
     * Called when the screen is hidden.
     * This is typically used to pause or stop any animations or inputs.
     */
    public void hide() {
    }

    /**
     * Called when the screen is paused.
     * This is typically used when the application is not active.
     */
    public void pause() {
    }

    /**
     * Called when the screen is resumed.
     * This is typically used when the application is reactivated.
     */
    public void resume() {
    }

    /**
     * Called when the screen should release all resources.
     */
    public void dispose() {
        myStage.dispose();
    }
    private Label initLabel(final String theText, final Color theColor, final float theScale, final BitmapFont theFont, final int theX, final int theY){
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = theFont;
        final Label label = new Label(theText, style);
        label.setFontScale(theScale);
        label.setAlignment(Align.center);
        label.setBounds(theX, theY, LABEL_WIDTH, LABEL_HEIGHT);
        label.setColor(theColor);
        return label;
    }
    private String getDeathLog(){
        return GameMaster.getInstance().getHeroDeathLog();
    }
    private TextButton initMainMenuButton(){
        final TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        final TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        final TextureRegionDrawable buttonDisabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"),
            Gdx.files.internal("fonts/alagard.png"), false);
        style.up = buttonUp;
        style.down = buttonDown;
        style.disabled = buttonDisabled;
        final TextButton button = new TextButton("MAIN MENU", style);
        button.getLabel().setFontScale(0.8f);
        button.setBounds(DungeonAdventure.WIDTH/2 - BUTTON_WIDTH/2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                myGame.setScreen(new MainMenuScreen(myGame));
                GameMaster.getInstance().restart();
            }
        });
        button.setProgrammaticChangeEvents(true);
        return button;
    }
    private String[] initGameOverNames(){
        final String[] titles = {"Something went wrong...", "Quit Gambling!", "Lost Again?",
            "What did you expect?", "Lost your own soul...", "There is no exit!", "They are coming!",
            "Adventure is over...", "You played 2 hours to die like this?", "Peace at last", "Life is hard",
            "What a misfortune...", "Did you try to find a shortcut?", "Skill issue", "You're still here?",
            "The princess is in the other dungeon", "Rest in Peace", "Game Over", "You died!", "Mortis"};
        return titles;
    }
    private String getRandomGameOverTitle(){
        Random rand = new Random();
        return myGameOverTitleNames[rand.nextInt(myGameOverTitleNames.length)];
    }
}

