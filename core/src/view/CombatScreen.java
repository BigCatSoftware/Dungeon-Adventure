package view;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
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
import model.Hero;
import model.Priestess;
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
    private final static int INVENTORY_WIDTH = 800;
    private final static int INVENTORY_HEIGHT = 198;
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;
    private final Image myCombatBack;
    private final Stage myStage;
    private final Table myTable;
    private final BitmapFont myFont;
    private final Label myHeroHP;
    private final Label myEnemyHP;
    private final TextButton[] myButtons;
    private final ScrollPane myCombatLog;
    private final Table myInventory;
    private final Label myHealthPotionLabel;
    private final Label myKeyLabel;
    private final Label myBombLabel;
    private int myTurnsCounter;
    private int myTurnsCurrentCounter;
    private final Label myTurnsLabel;
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
        myInventory = new Table();
        myInventory.setVisible(false);
        myInventory.setBackground(new TextureRegionDrawable(new Texture("InventoryBack.png")));
        myInventory.setBounds(0,0, INVENTORY_WIDTH, INVENTORY_HEIGHT);
        myTable.setFillParent(true);
        myStage.addActor(myTable);
        myHealthPotionLabel = initLabel("0", Color.WHITE, myFont,0.65f, 0, 0);
        myKeyLabel = initLabel("0", Color.WHITE, myFont,0.65f, 0, 0);
        myBombLabel = initLabel("0", Color.WHITE, myFont,0.65f, 0, 0);
        myTurnsCounter = GameMaster.getInstance().compareSpeed(GameMaster.getInstance()
            .getPlayer(), GameMaster.getInstance().getEnemy());
        myTurnsCurrentCounter = myTurnsCounter;
        myTurnsLabel = initLabel(String.valueOf(myTurnsCurrentCounter), Color.GOLD, myFont,1.5f, INVENTORY_WIDTH/2, HEALTH_Y);
        myTurnsLabel.setAlignment(Align.left);
        //add widgets to table here
        myTable.addActor(myCombatBack);
        myTable.addActor(initPlayerIcon());
        myTable.addActor(initEnemyIcon());
        myButtons = initCombatButtons(myFont);
        for(TextButton b : myButtons){
            myTable.addActor(b);
        }
        //add names
        myTable.addActor(initLabel(GameMaster.getInstance().getPlayer().getMyName(), Color.GOLD, myFont,0.65f, HERO_LABEL_X, NAME_Y));
        myTable.addActor(initLabel(GameMaster.getInstance().getEnemy().getMyName(), Color.GOLDENROD, myFont,0.65f, ENEMY_LABEL_X, NAME_Y));
        //add healths
        myHeroHP = initLabel(heroHealth(),updateHPColor(GameMaster.getInstance().getPlayer()), myFont,0.65f,HERO_LABEL_X, HEALTH_Y);
        myEnemyHP = initLabel(enemyHealth(),updateHPColor(GameMaster.getInstance().getEnemy()), myFont,0.65f, ENEMY_LABEL_X, HEALTH_Y);
        myTable.addActor(myHeroHP);
        myTable.addActor(myEnemyHP);
        //add combat log
        myCombatLog = initCombatLog(myFont);
        myTable.addActor(myCombatLog);
        myTable.addActor(myTurnsLabel);
        //add inventory
        myInventory.addActor(initHealthIcon());
        myInventory.addActor(initKeyIcon());
        myInventory.addActor(initBombIcon());
        updateInventory();
        initInventButtons();
        myInventory.addActor(myHealthPotionLabel);
        myInventory.addActor(myKeyLabel);
        myInventory.addActor(myBombLabel);
        myTable.addActor(myInventory);
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
        final InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(myStage);
        mux.addProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode){
                switch(keyCode){
                    case Input.Keys.A:
                        if(!myButtons[0].isDisabled()) {
                            myButtons[0].toggle();
                        }
                        break;
                    case Input.Keys.S:
                        if(!myButtons[1].isDisabled()) {
                            myButtons[1].toggle();
                        }
                        break;
                    case Input.Keys.F:
                        if(!myButtons[2].isDisabled()) {
                            myButtons[2].toggle();
                        }
                        break;
                    case Input.Keys.E:
                        if(!myButtons[3].isDisabled()) {
                            myButtons[3].toggle();
                        }
                        break;
                    case Input.Keys.X:
                        myButtons[4].toggle();
                        break;
                    case Input.Keys.NUM_1:
                        if(!myButtons[3].isDisabled()) {
                            myInventory.findActor("1").fire(new ChangeListener.ChangeEvent());
                        }
                        break;
                    case Input.Keys.NUM_2:
                        if(!myButtons[3].isDisabled()) {
                            myInventory.findActor("2").fire(new ChangeListener.ChangeEvent());
                        }
                        break;
                    case Input.Keys.B:
                        if(!myButtons[3].isDisabled()) {
                            myInventory.findActor("3").fire(new ChangeListener.ChangeEvent());
                        }
                        break;
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(mux);
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
        if(GameMaster.getInstance().getEnemyType(GameMaster.getInstance().getEnemy()) == Enemy.Type.Gremlin){
            texture = new Texture("GremlinCombatIcon.png");
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/GremlinApproaching.ogg")));
        }
        else if(GameMaster.getInstance().getEnemyType(GameMaster.getInstance().getEnemy()) == Enemy.Type.Skeleton){
            texture = new Texture("SkeletonCombatIcon.png");
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/SkeletonApproaching.ogg")));
        }
        else if(GameMaster.getInstance().getEnemyType(GameMaster.getInstance().getEnemy()) == Enemy.Type.Ogre){
            texture = new Texture("OgreCombatIcon.png");
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/OgreApproaching.ogg")));
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
        String[] buttonText = {"[A]TTACK", "[S]PECIAL ACTION", "[F]LEE", "INV[E]NTORY", "E[X]IT/FORFEIT"};
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
    private void playerTurnAction(){
        if(myTurnsCurrentCounter == 1){
            final Label logLabel = (Label) myCombatLog.getActor();
            if(!GameMaster.getInstance().getEnemy().getIsDead()){
                logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance().enemyPerformAttack()));
                myCombatLog.setScrollPercentY(1f);
                //update player hp
                myHeroHP.setText(heroHealth());
                myHeroHP.setColor(updateHPColor(GameMaster.getInstance().getPlayer()));
                myTurnsCurrentCounter = myTurnsCounter;
            }
        }
        else{
            myTurnsCurrentCounter--;
        }
        //if player or enemy is dead we disable input on first four buttons
        myTurnsLabel.setText(String.valueOf(myTurnsCurrentCounter));
        disableWidgetsIfDeath();
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
        button.setProgrammaticChangeEvents(true);
        return button;
    }
    private void disableWidgetsIfDeath(){
        if(myButtons.length != BUTTON_NUM){
            throw new IllegalStateException("Initializing different number of buttons than" +
                " expecting to. Expected: " + BUTTON_NUM);
        }
        if(GameMaster.getInstance().getPlayer().getIsDead() ||
            GameMaster.getInstance().getEnemy().getIsDead()){
            myButtons[0].setDisabled(true);
            myButtons[1].setDisabled(true);
            myButtons[2].setDisabled(true);
            myButtons[3].setDisabled(true);
            myTurnsLabel.setVisible(false);
        }
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
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                //update log
                final Label logLabel = (Label) myCombatLog.getActor();
                logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance().playerPerformAttack()));
                //update enemy hp
                myEnemyHP.setText(enemyHealth());
                myEnemyHP.setColor(updateHPColor(GameMaster.getInstance().getEnemy()));

                playerTurnAction();
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
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                final Label logLabel = (Label) myCombatLog.getActor();
                //warrior and thief apply damage to enemy while priestess heals self.
                logLabel.setText(updateLog(String.valueOf(logLabel.getText()), GameMaster.getInstance()
                    .specialActionPerform()));
                //update enemy hp
                myEnemyHP.setText(enemyHealth());
                myEnemyHP.setColor(updateHPColor(GameMaster.getInstance().getEnemy()));
                playerTurnAction();
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
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                final Label logLabel = (Label) myCombatLog.getActor();
                final Random rand = new Random();
                if(rand.nextBoolean()){
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/MockingLaugh.ogg")));
                    dispose();
                    myGame.setScreen(myPreviousScreen);

                }
                else{
                    playerTurnAction();
                }
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
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
//                final Label logLabel = (Label) myCombatLog.getActor();
//                logLabel.setText(updateLog(
//                    String.valueOf(logLabel.getText()), "<Opened inventory and used health potion!>"));
                myInventory.setVisible(true);
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
  
    private Image initHealthIcon(){
        Image healthImage = new Image(new TextureRegionDrawable(new Texture("HealthPotionIcon.png")));
        healthImage.setPosition(INVENTORY_WIDTH/4 - healthImage.getWidth()/2, INVENTORY_HEIGHT/2);
        myHealthPotionLabel.setPosition(healthImage.getX(), healthImage.getY());
        myHealthPotionLabel.setAlignment(Align.bottomLeft);
        myInventory.addActor(healthImage);
        myInventory.addActor(myHealthPotionLabel);
        return healthImage;
    }
    private Image initKeyIcon(){
        Image keyImage = new Image(new TextureRegionDrawable(new Texture("KeyIcon.png")));
        keyImage.setPosition((INVENTORY_WIDTH/4) * 3 - keyImage.getWidth()/2, INVENTORY_HEIGHT/2);
        myKeyLabel.setPosition(keyImage.getX(), keyImage.getY());
        myKeyLabel.setAlignment(Align.bottomLeft);
        myInventory.addActor(keyImage);
        myInventory.addActor(myKeyLabel);
        return keyImage;
    }
    private Image initBombIcon() {
        final Image bombImage = new Image(new TextureRegionDrawable(new Texture("BombIcon.png")));
        bombImage.setBounds((INVENTORY_WIDTH/4) * 2 - bombImage.getWidth()/2, INVENTORY_HEIGHT/2, bombImage.getWidth(), bombImage.getHeight());
        myBombLabel.setPosition(bombImage.getX(), bombImage.getY());
        myBombLabel.setAlignment(Align.bottomLeft);
        myInventory.addActor(bombImage);
        myInventory.addActor(myBombLabel);
        return bombImage;
    }
    private void initInventButtons(){
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = myFont;
        style.up = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        style.down = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        style.disabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        initInventUseHealthButton(style);
        initInventUseBombButton(style);
        initInventBackButton(style);
    }
    private void initInventUseHealthButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("USE[1]", theStyle);
        button.setBounds(INVENTORY_WIDTH/4 - button.getWidth()/2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                final Label label = (Label)myCombatLog.getActor();
                label.setText(updateLog(String.valueOf(label.getText()), GameMaster.getInstance().getPlayer().useHealthPotion()));

                myHeroHP.setText(heroHealth());
                myHeroHP.setColor(updateHPColor(GameMaster.getInstance().getPlayer()));
                myInventory.setVisible(false);
                //TODO: use functionality to remove the health potion and add health.
                playerTurnAction();
                updateInventory();
                GameMaster.getInstance().getPlayer().setMyPotionsUsed();
            }
        });
        button.setProgrammaticChangeEvents(true);
        button.setName("1");
        myInventory.addActor(button);
    }

    private void initInventUseBombButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("USE[2]", theStyle);
        button.setBounds((INVENTORY_WIDTH/4) * 2 - button.getWidth()/2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                myInventory.setVisible(false);

                final Label label = (Label)myCombatLog.getActor();
                label.setText(updateLog(String.valueOf(label.getText()),
                        GameMaster.getInstance().heroUsesBomb()));
                updateInventory();
                //update enemy hp
                myEnemyHP.setText(enemyHealth());
                myEnemyHP.setColor(updateHPColor(GameMaster.getInstance().getEnemy()));
                playerTurnAction();
                GameMaster.getInstance().getPlayer().setMyBombsUsed();
            }
        });
        button.setProgrammaticChangeEvents(true);
        button.setName("2");
        myInventory.addActor(button);
    }

    private void initInventBackButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("[B]ACK", theStyle);
        button.setBounds(DungeonAdventure.WIDTH-button.getWidth(), 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")));
                myInventory.setVisible(false);
                playerTurnAction();
            }
        });
        button.setProgrammaticChangeEvents(true);
        button.setName("3");
        myInventory.addActor(button);
    }
    private void updateInventory(){
        myHealthPotionLabel.setText(GameMaster.getInstance().getHeroHealthPotions());
        myBombLabel.setText(GameMaster.getInstance().getHeroBombs());
        myKeyLabel.setText(GameMaster.getInstance().getHeroKeys());
    }
}