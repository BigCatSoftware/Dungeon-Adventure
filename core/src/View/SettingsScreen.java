package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

public class SettingsScreen extends ScreenAdapter {
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;

    private final OrthographicCamera myCamera;
    private final SpriteBatch myBatch;
    private final BitmapFont myFont;

    private final Texture mySoundActive;
    private final Texture mySoundInactive;
    private final Texture myMuteActive;
    private final Texture myMuteInactive;
    private final Texture myPlusActive;
    private final Texture myPlusInactive;
    private final Texture myMinusActive;
    private final Texture myMinusInactive;
    private final Texture myBackActive;
    //private final Texture myBackInactive;
    private final Texture[] myVolumeBars;

    private final Rectangle mySoundButton;
    private final Rectangle myPlusButton;
    private final Rectangle myMinusButton;
    private final Rectangle myVolumeBar;
    private final Rectangle myBackButton;

    private static final int SOUND_BUTTON_WIDTH = 64;
    private static final int SOUND_BUTTON_HEIGHT = 64;
    private static final int PLUSMINUS_BUTTON_WIDTH = 64;
    private static final int PLUSMINUS_BUTTON_HEIGHT = 64;
    private static final int VOLUME_WIDTH = 512;
    private static final int VOLUME_HEIGHT = 64;
    private static final int BACK_BUTTON_WIDTH = 48;
    private static final int BACK_BUTTON_HEIGHT = 48;

    public SettingsScreen(final DungeonAdventure theGame, final Screen previousScreen) {
        myGame = theGame;
        myPreviousScreen = previousScreen;

        myCamera = new OrthographicCamera();
        myCamera.setToOrtho(false, 800, 480);
        myBatch = new SpriteBatch();
        myFont = new BitmapFont();

        mySoundActive = new Texture("SoundActive.png");
        mySoundInactive = new Texture("SoundInactive.png");
        myMuteActive = new Texture("MuteActive.png");
        myMuteInactive = new Texture("MuteInactive.png");
        myPlusActive = new Texture("PlusActive.png");
        myPlusInactive = new Texture("PlusInactive.png");
        myMinusActive = new Texture("MinusActive.png");
        myMinusInactive = new Texture("MinusInactive.png");
        myBackActive = new Texture("BackActive.png");
        //myBackInactive = new Texture("BackInactive.png");

        myVolumeBars = new Texture[11];
        for (int i = 0; i <= 10; i++) {
            myVolumeBars[i] = new Texture(i + "VolumeBar.png");
        }

        myBackButton = new Rectangle(0, 436, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        mySoundButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - (2 * ((float) SOUND_BUTTON_WIDTH / 2)) + 32, 250, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
        myMinusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) - 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
        myPlusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) + 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
        myVolumeBar = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) VOLUME_WIDTH / 2) + 128, 350, VOLUME_WIDTH, VOLUME_HEIGHT);

        setupInputProcessor();
    }

    private void setupInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
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
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myCamera.update();
        myBatch.setProjectionMatrix(myCamera.combined);

        myBatch.begin();

        drawBackButton();
        drawSound();
        drawPlus();
        drawMinus();
        drawVolume();

        myBatch.end();
    }

    private void drawSound() {
        myBatch.draw(mySETTINGS.isSoundOn() ? mySoundActive : myMuteActive, mySoundButton.x, mySoundButton.y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
    }

    private void drawPlus() {
        myBatch.draw(myPlusActive, myPlusButton.x, myPlusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    }

    private void drawMinus() {
        myBatch.draw(myMinusActive, myMinusButton.x, myMinusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    }

    private void drawVolume() {
        myBatch.draw(myVolumeBars[mySETTINGS.getVolumeLevel()], myVolumeBar.x, myVolumeBar.y, VOLUME_WIDTH, VOLUME_HEIGHT);
    }

    private void drawBackButton() {
        myBatch.draw(myBackActive, myBackButton.x, myBackButton.y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
    }

    @Override
    public void dispose() {
        myBatch.dispose();
        myFont.dispose();
        mySoundActive.dispose();
        mySoundInactive.dispose();
        myMuteActive.dispose();
        myMuteInactive.dispose();
        myPlusActive.dispose();
        myPlusInactive.dispose();
        myMinusActive.dispose();
        myMinusInactive.dispose();
        for (Texture texture : myVolumeBars) {
            texture.dispose();
        }
    }
}
