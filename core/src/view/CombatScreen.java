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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
import model.DungeonCharacter;
import model.Enemy;
import model.GameMaster;
import model.Gremlin;
import model.Hero;
import model.Ogre;
import model.Priestess;
import model.Skeleton;
import model.Thief;
import model.Warrior;

import java.util.Random;

/**
 * This is a combat screen that will be brought up once an enemy is in the same tile as hero.
 * The battle will utilize many mechanics in model like attack, block, heal and update the view
 * like buttons and labels.
 * @author Nazarii Revitskyi
 * @version July 31, 2024.
 */
public final class CombatScreen extends ScreenAdapter {
    private final static int HERO_ICON_X = 40;
    private final static int HERO_ICON_Y = 460;
    private final static int ENEMY_ICON_X = 460;
    private final static int ENEMY_ICON_Y = 460;
    private final static int BUTTON_WIDTH = 154;
    private final static int BUTTON_HEIGHT = 54;
    private final static int BUTTON_NUM = 5;
    private final static int HERO_LABEL_X = 40;
    private final static int NAME_Y = 760;
    private final static int ENEMY_LABEL_X = 460;
    private final static int HEALTH_Y = 420;
    private final static int LABEL_WIDTH = 300;
    private final static int LABEL_HEIGHT = 40;
    private final static int LOG_X = 15;
    private final static int LOG_Y = 80;
    private final static int LOG_WIDTH = 770;
    private final static int LOG_HEIGHT = 290;
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final Image myCombatBack;
    private final Stage myStage;
    private final Table myTable;
    private final BitmapFont myFont;
    private final Label myHeroHP;
    private final Label myEnemyHP;
    private final ScrollPane myCombatLog;
    /**
     * Initialize the class.
     * @param theGame this is the game instance that will change the screen to a previous screen.
     * @param thePreviousScreen this is the screen that the game will change to.
     */
    public CombatScreen(final DungeonAdventure theGame, final Screen thePreviousScreen){
        //init
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
        myCombatBack = new Image(new Texture("CombatScreenBack.png"));
        myStage = new Stage();
        myTable = new Table();
        myFont = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"), Gdx.files.internal("fonts/alagard.png"), false);

        myTable.setFillParent(true);
        myStage.addActor(myTable);

        //add widgets to table here
        myTable.addActor(myCombatBack);
        myTable.addActor(initPlayerIcon());
        myTable.addActor(initEnemyIcon());
        TextButton[] buttons = initCombatButtons(myFont);
        for(TextButton b : buttons){
            myTable.addActor(b);
        }
        //add names
        myTable.addActor(initLabel(GameMaster.getInstance().getPlayer().getMyName(), Color.GOLD, myFont, HERO_LABEL_X, NAME_Y));
        myTable.addActor(initLabel(GameMaster.getInstance().getEnemy().getMyName(), Color.GOLDENROD, myFont, ENEMY_LABEL_X, NAME_Y));
        //add healths
        myHeroHP = initLabel(heroHealth(),updateHPColor(GameMaster.getInstance().getPlayer()), myFont,HERO_LABEL_X, HEALTH_Y);
        myEnemyHP = initLabel(enemyHealth(),updateHPColor(GameMaster.getInstance().getEnemy()), myFont, ENEMY_LABEL_X, HEALTH_Y);
        myTable.addActor(myHeroHP);
        myTable.addActor(myEnemyHP);
        //add combat log
        myCombatLog = initCombatLog(myFont);
        myTable.addActor(myCombatLog);
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
//        myGame.batch.begin();
//        myGame.batch.draw(myCombatScreenBack, 0, 0);
//        myGame.batch.draw(myPlayerCombatIcon, HERO_ICON_X, HERO_ICON_Y);
//        myGame.batch.draw(myEnemyCombatIcon, ENEMY_ICON_X, ENEMY_ICON_Y); //random enemy icon.
//        myCombatLog.draw(myGame.batch,1);
//        myCombatLog.act(delta);
//        myGame.batch.end();
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
     * This method compares health to max health and returns color. Green - good, Yellow - ok,
     * Red - bad.
     * @param theEntity entity to check.
     * @return color level of health.
     */
    private Color updateHPColor(final DungeonCharacter theEntity){

        final int currentHealth;
        final double maxHealth;
        if(theEntity instanceof Hero){
            currentHealth = GameMaster.getInstance().getPlayer().getCurrentHealth();
            maxHealth = GameMaster.getInstance().getPlayer().getMaxHealth();
        }
        else if(theEntity instanceof Enemy){
            currentHealth = GameMaster.getInstance().getEnemy().getCurrentHealth();
            maxHealth = GameMaster.getInstance().getEnemy().getMaxHealth();
        }
        else{
            throw new IllegalArgumentException("Unknown type passed to combat screen when " +
                "trying to update the hp of hero or enemy while passing: " + theEntity);
        }
        Color color = Color.GREEN;
        if(currentHealth < (maxHealth*((double)1/3))){
            color = Color.RED;
        }
        else if(currentHealth < (maxHealth*((double) 2 /3))){
            color = Color.YELLOW;
        }
        return color;
    }

    /**
     * When called gives string representation of enemy health that can be used in UI
     * @return String of enemy health.
     */
    private String enemyHealth(){
        return GameMaster.getInstance().getEnemy().getCurrentHealth() + "/" +
            GameMaster.getInstance().getEnemy().getMaxHealth();
    }

    /**
     * Checks if the log is too big, if it is - removes few earlier messages.
     * Takes previous message and concatenates new message to the log.
     * @param thePreviousText string previous text to update
     * @param theNewText string new text to add to previous text
     * @return String updated log with new message.
     */
    private String updateLog(final String thePreviousText, final String theNewText){
        if(thePreviousText.length() > Short.MAX_VALUE/7){
            return thePreviousText.substring(200) + System.lineSeparator() + theNewText;
        }
        return thePreviousText + System.lineSeparator() + theNewText;
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
     * Helper method that will decide enemy icon based on enemy instance.
     * @return image enemy image on the combat screen.
     */
    private Image initEnemyIcon(){
        final Texture texture;
        final Image image;
        if(GameMaster.getInstance().getEnemy() instanceof Gremlin){
            texture = new Texture("GremlinCombatIcon.png");
        }
        else if(GameMaster.getInstance().getEnemy() instanceof Skeleton){
            texture = new Texture("SkeletonCombatIcon.png");
        }
        else if(GameMaster.getInstance().getEnemy() instanceof Ogre){
            texture = new Texture("OgreCombatIcon.png");
        }
        else{
            throw new IllegalStateException("Failed to draw enemy combat icon because of" +
                " fail of state in CombatManager instance.");
        }
        image = new Image(texture);
        image.setPosition(ENEMY_ICON_X, ENEMY_ICON_Y);
        return image;
    }

    /**
     * Helper method that creates a list of buttons that will be used for user to combat the
     * enemy.
     * @param theFont font for the text on the buttons
     * @return textbutton array.
     */
    private TextButton[] initCombatButtons(final BitmapFont theFont){
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
        attackButtonListener(buttons);
        specialActionButtonListener(buttons);
        fleeButtonListener(buttons);
        inventoryButtonListener(buttons);
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
     * This method specifies what the attack button will do on press.
     * @param theButtons takes the array of buttons to change the attack button listener.
     */
    private void attackButtonListener(final TextButton[] theButtons){
        if(theButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Trying to initialize attackButton for wrong " +
                "count of buttons (has to be updated)");
        }
        theButtons[0].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //update log
                final Label logLabel = (Label) myCombatLog.getActor();
                logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance().getPlayer().attack(GameMaster.getInstance().getEnemy())));
                //update enemy hp
                myEnemyHP.setText(enemyHealth());
                myEnemyHP.setColor(updateHPColor(GameMaster.getInstance().getEnemy()));
                //enemy attack log
                if(!GameMaster.getInstance().getEnemy().getIsDead()){
                    logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance().getEnemy().attack(GameMaster.getInstance()
                        .getPlayer())));
                    myCombatLog.setScrollPercentY(1f);
                    //update player hp
                    myHeroHP.setText(heroHealth());
                    myHeroHP.setColor(updateHPColor(GameMaster.getInstance().getPlayer()));
                }
                //if player or enemy is dead we disable input on first four buttons
                if(GameMaster.getInstance().getPlayer().getIsDead() || GameMaster.getInstance().getEnemy().getIsDead()){
                    theButtons[0].setDisabled(true);
                    theButtons[1].setDisabled(true);
                    theButtons[2].setDisabled(true);
                    theButtons[3].setDisabled(true);
                }
            }
        });
    }

    /**
     * This method specifies what the special action button will do on press.
     * @param theButtons takes the array of buttons to change the special action button listener.
     */
    private void specialActionButtonListener(final TextButton[] theButtons){
        if(theButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Trying to initialize specialActionButton for wrong count of buttons (has to be updated)");
        }
        theButtons[1].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Label logLabel = (Label) myCombatLog.getActor();
                //warrior and thief apply damage to enemy while priestess heals self.
                if(GameMaster.getInstance().getPlayer() instanceof Warrior){
                    logLabel.setText(updateLog(String.valueOf(logLabel.getText()), ((Warrior) GameMaster.getInstance().getPlayer()).specialAction(GameMaster.getInstance().getEnemy())));
                }
                else if(GameMaster.getInstance().getPlayer() instanceof Priestess){
                    logLabel.setText(updateLog(String.valueOf(logLabel.getText()), ((Priestess) GameMaster.getInstance().getPlayer()).specialAction()));
                }
                else if(GameMaster.getInstance().getPlayer() instanceof Thief){
                    logLabel.setText(updateLog(String.valueOf(logLabel.getText()), ((Thief) GameMaster.getInstance().getPlayer()).specialAction(GameMaster.getInstance().getEnemy())));
                }
                else{
                    throw new IllegalStateException("Combat Manager player is not an instance " +
                        "of concrete implementation of Hero abstract class."
                        + GameMaster.getInstance().getPlayer().getClass().getSimpleName());
                }
                //update enemy hp
                myEnemyHP.setText(enemyHealth());
                myEnemyHP.setColor(updateHPColor(GameMaster.getInstance().getEnemy()));
                //enemy attack log
                if(!GameMaster.getInstance().getEnemy().getIsDead()){
                    logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance().getEnemy().attack(GameMaster.getInstance()
                        .getPlayer())));
                    myCombatLog.setScrollPercentY(1f);
                    //update player hp
                    myHeroHP.setText(heroHealth());
                    myHeroHP.setColor(updateHPColor(GameMaster.getInstance().getPlayer()));
                }
                if(GameMaster.getInstance().getPlayer().getIsDead() || GameMaster.getInstance().getEnemy().getIsDead()){
                    theButtons[0].setDisabled(true);
                    theButtons[1].setDisabled(true);
                    theButtons[2].setDisabled(true);
                    theButtons[3].setDisabled(true);
                }
            }
        });
    }

    /**
     * This method specifies what the flee button will do on press.
     * @param theButtons takes the array of buttons to change the flee button listener.
     */
    private void fleeButtonListener(final TextButton[] theButtons){
        if(theButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Trying to initialize skipTurnButton for wrong count of buttons (has to be updated)");
        }
        theButtons[2].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Label logLabel = (Label) myCombatLog.getActor();
                final Random rand = new Random();
                if(rand.nextBoolean()){
                    myGame.setScreen(myPreviousScreen);
                    dispose();
                }
                else{
                    logLabel.setText(updateLog(
                        String.valueOf(logLabel.getText()), "Failed to flee."));
                }
                myCombatLog.setScrollPercentY(1f);
            }
        });
    }
    /**
     * This method specifies what the inventory button will do on press.
     * @param theButtons takes the array of buttons to change the inventory button listener.
     */
    private void inventoryButtonListener(final TextButton[] theButtons){
        if(theButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Trying to initialize inventoryButton for wrong count of buttons (has to be updated)");
        }
        theButtons[3].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Label logLabel = (Label) myCombatLog.getActor();
                logLabel.setText(updateLog(
                    String.valueOf(logLabel.getText()), "<Opened inventory and used health potion!>"));

                myCombatLog.setScrollPercentY(1f);
            }
        });
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
                if(GameMaster.getInstance().getEnemy().getIsDead()){
                    myGame.setScreen(myPreviousScreen);
                }
                else{
                    Gdx.app.exit();
                }
            }
        });
    }

    /**
     * Helper method to set up text labels
     * @param theText string text
     * @param theColor color to use for the font
     * @param theFont bitmap font to use on the label
     * @param theX int x pos
     * @param theY int y pos
     * @return label to put on screen.
     */
    private Label initLabel(final String theText, final Color theColor, final BitmapFont theFont, final int theX, final int theY){
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = theFont;
        final Label label = new Label(theText, style);
        label.setFontScale(0.65f);
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
    private ScrollPane initCombatLog(final BitmapFont theFont){
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
