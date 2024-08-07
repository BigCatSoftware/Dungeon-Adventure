package view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import controller.PlayerInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;
import model.Enemy;
import model.GameMaster;
import model.Gremlin;
import model.Ogre;
import model.Priestess;
import model.Skeleton;
import model.Thief;
import model.Tile;
import model.Warrior;
import static com.dungeonadventure.game.DungeonAdventure.myBackgroundMusic;

import java.util.ArrayList;

/**
 * Represents the main game screen where the dungeon is rendered and the player interacts with the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class GameScreen implements Screen {
    private static final int TILE_SIZE = 16;
    private static final int BUTTON_WIDTH = 154;
    private static final int BUTTON_HEIGHT = 54;
    private static final int BUTTON_Y_OFFSET = 10;
    private static final int BUTTON_X_OFFSET = 77;
    private final Viewport myViewPort;
    private final Stage myStage;
    private final Table myGameTable;
    private final Table myGameMenuTable;
    private final BitmapFont myFont;
    private final DungeonAdventure myGame;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;
    private final Image myPlayerImage;
    private final Texture myWallTexture;
    private final Texture myDoorTexture;
    private final Texture myFloorTexture;
    private final Texture myGremlinTexture;
    private final Texture mySkeletonTexture;
    private final Texture myOgreTexture;
    private boolean myMenuShown = false;
    //private final DungeonRenderer myDungeonRenderer;

    /**
     * Constructs a new GameScreen.
     *
     * @param theGame the main game instance
     */
    public GameScreen(final DungeonAdventure theGame) {
        myGame = theGame;
        myBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("NightAmbianceLoop.ogg"));
        myBackgroundMusic.play();
        myBackgroundMusic.setLooping(true);
        myViewPort = new ScreenViewport();
        myStage = new Stage(myViewPort, myGame.batch);
        myGameTable = new Table();
        myGameMenuTable = new Table();
        myFont = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"), Gdx.files.internal("fonts/alagard.png"), false);
        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");
        myWallTexture = new Texture("wall.png");
        myDoorTexture = new Texture("door.png");
        myFloorTexture = new Texture("floor.png");
        myGremlinTexture = new Texture("Pixel Gremlin.png");
        mySkeletonTexture = new Texture("Pixel Skeleton.png");
        myOgreTexture = new Texture("Pixel Ogre.png");
        myGameTable.setFillParent(true);
        myStage.addActor(myGameTable);
        //add to table
        myPlayerImage = initPlayerTexture();
        myPlayerImage.setPosition(GameMaster.getInstance().getPlayerX()*TILE_SIZE, GameMaster.getInstance()
            .getPlayerY()*TILE_SIZE);
        myGameMenuTable.setBounds(DungeonAdventure.WIDTH / 4,(DungeonAdventure.HEIGHT / 4),DungeonAdventure.WIDTH / 2, DungeonAdventure.HEIGHT / 2);
        myGameMenuTable.setBackground(new TextureRegionDrawable(new Texture("GameMenuBackground.png")));
        myGameMenuTable.setVisible(false);
        initGameMenuButtons();
        myGameTable.addActor(myGameMenuTable);
        myGameTable.addActor(myPlayerImage);
        //myDungeonRenderer = new DungeonRenderer();
    }
    private Image initPlayerTexture(){
        Texture playerTexture = null;
        if(GameMaster.getInstance().getPlayer() instanceof Warrior){
            playerTexture = new Texture("Pixel Warrior.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Priestess){
            playerTexture = new Texture("Pixel Priestess.png");
        }
        else if(GameMaster.getInstance().getPlayer() instanceof Thief){
            playerTexture = new Texture("Pixel Thief.png");
        }
        else{
            throw new IllegalArgumentException("Can't determine hero class from game master to" +
                " draw its texture");
        }
        return new Image(playerTexture);
    }
    /**
     * Called when the screen is shown.
     * Sets the input processor to handle player inputs.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new PlayerInputProcessor(myGame,GameScreen.this));//new PlayerInputProcessor(myPlayer, myGame, GameScreen.this));
    }

    /**
     * Renders the screen.
     * Clears the screen, renders the dungeon, and draws the settings button.
     *
     * @param delta the time in seconds since the last render
     */
    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        //myGame.batch.begin();

        // Render the dungeon
        myGame.batch.begin();
        initMap();
        initEntities();
        myGame.batch.end();
        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
        //myGame.batch.end();
    }
    private void initMap(){
        Tile[][] map = GameMaster.getInstance().getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Texture texture = null;
                if (map[i][j] == Tile.WALL) {
                    texture = myWallTexture;
                } else if (map[i][j] == Tile.FLOOR) {
                    texture = myFloorTexture;
                } else if (map[i][j] == Tile.DOOR) {
                    texture = myDoorTexture;
                }
                if (texture != null) {
                    myGame.batch.draw(texture, i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                else{
                    throw new IllegalStateException("map image is set to null!");
                }
            }
        }
    }
    private void initEntities(){
        final ArrayList<Enemy> list = GameMaster.getInstance().getAllEnemies();
        for(Enemy e : list){
            if(e instanceof Gremlin){
                myGame.batch.draw(myGremlinTexture, e.getPosition().getMyX()*TILE_SIZE, e.getPosition().getMyY()*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
            else if(e instanceof Skeleton){
                myGame.batch.draw(mySkeletonTexture, e.getPosition().getMyX()*TILE_SIZE, e.getPosition().getMyY()*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
            else if(e instanceof Ogre){
                myGame.batch.draw(myOgreTexture, e.getPosition().getMyX()*TILE_SIZE, e.getPosition().getMyY()*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
            else{
                throw new IllegalStateException("Unknown enemy type when drawing on map. Entity name: " + e.getClass().getSimpleName());
            }
        }
    }
    public void setPlayerImagePosition(){
        myPlayerImage.setPosition(GameMaster.getInstance().getPlayerX()*TILE_SIZE, GameMaster.getInstance().getPlayerY()*TILE_SIZE);
    }
    private void initGameMenuButtons(){
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = myFont;
        style.up = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        style.down = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        style.disabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        initResumeButton(style);
        initSettingsButton(style);
        initMenuButton(style);
    }
    private void initResumeButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("RESUME", theStyle);
        button.setBounds(myGameMenuTable.getX() - BUTTON_X_OFFSET, myGameMenuTable.getY()*2 - (2 * BUTTON_HEIGHT)-(BUTTON_Y_OFFSET), BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")).play();
                showMenu();
            }
        });
        myGameMenuTable.addActor(button);
    }
    private void initSettingsButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("SETTINGS", theStyle);
        button.setBounds(myGameMenuTable.getX() - BUTTON_X_OFFSET, myGameMenuTable.getY()*2 - (3 * BUTTON_HEIGHT)-(2* BUTTON_Y_OFFSET), BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")).play();
                myGame.setScreen(new SettingsScreen(myGame, GameScreen.this));
                myGameMenuTable.setVisible(false);
                myMenuShown = false;
            }
        });
        myGameMenuTable.addActor(button);
    }
    private void initMenuButton(final TextButton.TextButtonStyle theStyle){
        final TextButton button = new TextButton("EXIT TO MENU", theStyle);
        button.setBounds(myGameMenuTable.getX() - BUTTON_X_OFFSET, myGameMenuTable.getY()*2 - (4 * BUTTON_HEIGHT) - (3 * BUTTON_Y_OFFSET), BUTTON_WIDTH, BUTTON_HEIGHT);
        button.getLabel().setFontScale(0.7f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg")).play();
                dispose();
                myGame.setScreen(new MainMenuScreen(myGame));
                //TODO: auto save and go to menu
                GameMaster.getInstance().restart();
            }
        });
        myGameMenuTable.addActor(button);
    }
    public void showMenu(){
        if(myMenuShown){
            myGameMenuTable.setVisible(false);
            Gdx.input.setInputProcessor(new PlayerInputProcessor(myGame, GameScreen.this));
            myMenuShown = false;
        }
        else{
            myGameMenuTable.setVisible(true);
            Gdx.input.setInputProcessor(myStage);
            myMenuShown = true;
        }
    }
    /**
     * Called when the screen is resized.
     *
     * @param width the new width of the screen
     * @param height the new height of the screen
     */
    @Override
    public void resize(final int width, final int height) {
        // Handle resizing if needed
    }

    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {
        // Handle pause logic if needed
    }

    /**
     * Called when the screen is resumed.
     */
    @Override
    public void resume() {
        // Handle resume logic if needed
    }

    /**
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {
        // Handle hide logic if needed
    }

    /**
     * Called when the screen is disposed.
     * Releases all resources.
     */
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        myBackgroundMusic.dispose();
        mySettingsButtonActive.dispose();
        mySettingsButtonInactive.dispose();
        // Dispose of other resources if needed
    }
}
