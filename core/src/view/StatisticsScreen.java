package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
import controller.StatisticsInputProcessor;
import model.*;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

public class StatisticsScreen extends ScreenAdapter {
    private final static int HERO_ICON_X = 40;
    private final static int HERO_ICON_Y = 460;
    private final static int BUTTON_WIDTH = 154;
    private final static int BUTTON_HEIGHT = 54;
    private final static int BUTTON_NUM = 5;
    private final static int HERO_LABEL_X = 40;
    private final static int NAME_Y = 760;
    private final static int ENEMY_LABEL_X = 460;
    private final static int LABEL_WIDTH = 300;
    private final static int LABEL_HEIGHT = 40;
    private final static int LOG_X = 15;
    private final static int LOG_Y = 80;
    private final static int LOG_WIDTH = 770;
    private final static int LOG_HEIGHT = 290;

    private final static int MENU_BUTTON_HEIGHT = 50;
    private final static int MENU_BUTTON_WIDTH = 50;

    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final Image myLogBack;
    private final Stage myStage;
    private final Table myTable;
    private final BitmapFont myFont;
    private final TextButton[] myButtons;
    private final ScrollPane myCombatLog;

    /**
     * Initialize the class.
     * @param theGame this is the game instance that will change the screen to a previous screen.
     * @param thePreviousScreen this is the screen that the game will change to.
     */
    public StatisticsScreen(final DungeonAdventure theGame, final Screen thePreviousScreen){
        //init
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        myLogBack = new Image(new Texture("CombatScreenBack.png"));
        myStage = new Stage();
        myTable = new Table();
        myFont = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"), Gdx.files.internal("fonts/alagard.png"), false);
        myTable.setFillParent(true);
        myStage.addActor(myTable);

        //add widgets to table here
        myTable.addActor(myLogBack);
        myTable.addActor(initPlayerIcon());
//        myTable.addActor(initEnemyIcon());
        myButtons = initLogButtons(myFont);
        for(TextButton b : myButtons){
            myTable.addActor(b);
        }
        //add names
        myTable.addActor(initLabel(GameMaster.getInstance().getPlayer().getMyName(), Color.GOLD, myFont,0.65f, HERO_LABEL_X, NAME_Y));
        myTable.addActor(initLabel(GameMaster.getInstance().getEnemy().getMyName(), Color.GOLDENROD, myFont,0.65f, ENEMY_LABEL_X, NAME_Y));
        //add healths
//        myHeroHP = initLabel(heroHealth(),updateHPColor(GameMaster.getInstance().getPlayer()), myFont,0.65f,HERO_LABEL_X, HEALTH_Y);
//        myTable.addActor(myHeroHP);

        //add combat log
        myCombatLog = initStatLog(myFont);
        myTable.addActor(myCombatLog);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new StatisticsInputProcessor(myGame, myPreviousScreen, ));
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
    }
    @Override
    public void dispose(){
        myStage.dispose();
    }
    /**
     * Called when the screen is shown.
     * Sets the input processor to handle player inputs.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(myStage);
    }

    /**
     * When called gives string representation of hero health that can be used in UI
     * @return String of hero health.
     */
    private String heroHealth(){
        return GameMaster.getInstance().getPlayer().getCurrentHealth() + "/"
                + GameMaster.getInstance().getPlayer().getMaxHealth();
    }

    /**
     * Helper method that will decide player icon based on hero instance.
     * @return image hero image on the combat screen.
     */
    private Image initPlayerIcon(){
        final Texture texture;
        final Image image;
        if(GameMaster.getInstance().getPlayer() instanceof Warrior){
            texture = new Texture("WarriorCombatIcon.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Priestess){
            texture = new Texture("PriestessCombatIcon.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Thief){
            texture = new Texture("ThiefCombatIcon.png");
        }
        else{
            throw new IllegalStateException("Failed to draw hero combat icon because of" +
                    " fail of state in GameMaster instance.");
        }
        image = new Image(texture);
        image.setPosition(HERO_ICON_X, HERO_ICON_Y);
        return image;
    }

    /**
     * Helper method that creates a list of buttons that will be used for user to combat the
     * enemy.
     * @param theFont font for the text on the buttons
     * @return textbutton array.
     */
    private TextButton[] initLogButtons(final BitmapFont theFont){
        String[] buttonText = {"ATTACK", "SPECIAL ACTION", "FLEE", "INVENTORY", "EXIT/FORFEIT"};
        int xPos = 5;
        int xIncr = 5;
        int yPos = 5;
        final TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        final TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        final TextureRegionDrawable buttonDisabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        final TextButton.TextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = theFont;
        style.up = buttonUp;
        style.down = buttonDown;
        style.disabled = buttonDisabled;
        final TextButton[] buttons = new TextButton[5];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = buttonMaker(buttonText[i], style,(xPos + i*BUTTON_WIDTH),yPos,BUTTON_WIDTH,BUTTON_HEIGHT);
            xPos+=xIncr;
        }
//        attackButtonListener(buttons);
//        specialActionButtonListener(buttons);
//        fleeButtonListener(buttons);
//        inventoryButtonListener(buttons);
        exitForfeitButtonListener(buttons);
        return buttons;
    }

    /**
     * Helper method that will make a button using parameters.
     * @param theText string text on the button
     * @param theStyle textbuttonstyle of the button up, down, disabled, font
     * @param theX int x pos on screen
     * @param theY int y pos on screen
     * @param theWidth int button width
     * @param theHeight int button height
     * @return textbutton to add to the array.
     */
    private TextButton buttonMaker(final String theText,
                                   final TextButton.TextButtonStyle theStyle,
                                   final int theX, final int theY,
                                   final int theWidth, final int theHeight){

        final TextButton button = new TextButton(theText, theStyle);
        button.setBounds(theX,theY,theWidth,theHeight);
        button.getLabel().setWrap(true);
        button.getLabel().setFontScale(0.6f, 1.2f);
        return button;
    }

    /**
     * This method specifies what the exit button will do on press.
     * @param theButtons takes the array of buttons to change the exit button listener.
     */
    private void exitForfeitButtonListener(final TextButton[] theButtons){
        if(theButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Trying to initialize exitForfeitButton for wrong count of buttons (has to be updated)");
        }
        theButtons[4].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                if(GameMaster.getInstance().getEnemy().getIsDead()){
                    myGame.setScreen(myPreviousScreen);
                    GameMaster.getInstance().removeCurrentEnemyIfDead();
                }
                else{
                    dispose();
                    myPreviousScreen.dispose();
                    myGame.setScreen(new GameOverScreen(myGame));
                }
            }
        });
    }

    /**
     * Helper method to set up text labels
     * @param theText string text
     * @param theColor color to use for the font
     * @param theFont bitmap font to use on the label
     * @param theFontScale scale for the font for the label.
     * @param theX int x pos
     * @param theY int y pos
     * @return label to put on screen.
     */
    private Label initLabel(final String theText, final Color theColor, final BitmapFont theFont, final float theFontScale, final int theX, final int theY){
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = theFont;
        final Label label = new Label(theText, style);
        label.setFontScale(theFontScale);
        label.setAlignment(Align.center);
        label.setBounds(theX, theY, LABEL_WIDTH, LABEL_HEIGHT);
        label.setColor(theColor);
        return label;
    }

    /**
     * Helper method that will initialize the scrollpane that uses label to log combat activity.
     * @param theFont bitmap font to use in the log
     * @return scrollpane scrollable label of combat activity log.
     */
    private ScrollPane initStatLog(final BitmapFont theFont){
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = theFont;
        final Label label = new Label("", style);
        label.setAlignment(Align.topLeft);
        label.setFontScale(0.6f);
        label.setWrap(true);
        final ScrollPane log = new ScrollPane(label);
        log.setForceScroll(false, true);
        log.setFlickScroll(false);
        log.setOverscroll(false, true);
        log.setBounds(LOG_X, LOG_Y,LOG_WIDTH,LOG_HEIGHT);
        log.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                myStage.setScrollFocus(myCombatLog);
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                myStage.setScrollFocus(myCombatLog);
            }
        });
        return log;
    }
}
