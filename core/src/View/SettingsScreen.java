package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dungeonadventure.game.DungeonAdventure;
import Controller.Settings;

public class SettingsScreen implements Screen {
    private final DungeonAdventure game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Settings settings;

    private final Texture SoundActive;
    private final Texture SoundInactive;
    private final Texture MuteActive;
    private final Texture MuteInactive;
    private final Texture PlusActive;
    private final Texture PlusInactive;
    private final Texture MinusActive;
    private final Texture MinusInactive;
    private final Texture BackActive;
    private final Texture BackInactive;
    private final Texture[] VolumeBars;

    private final Rectangle soundButton;
    private final Rectangle plusButton;
    private final Rectangle minusButton;
    private final Rectangle volumeBar;
    private final Rectangle backButton;

    private final int SOUND_BUTTON_WIDTH = 64;
    private final int SOUND_BUTTON_HEIGHT = 64;
    private final int PLUSMINUS_BUTTON_WIDTH = 64;
    private final int PLUSMINUS_BUTTON_HEIGHT = 64;
    private final int VOLUME_WIDTH = 512;
    private final int VOLUME_HEIGHT = 64;
    private final int BACK_BUTTON_WIDTH = 48;
    private final int BACK_BUTTON_HEIGHT = 48;

    public SettingsScreen(final DungeonAdventure game, final Settings settings) {
        this.game = game;
        this.settings = settings;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();

        SoundActive = new Texture("SoundActive.png");
        SoundInactive = new Texture("SoundInactive.png");
        MuteActive = new Texture("MuteActive.png");
        MuteInactive = new Texture("MuteInactive.png");
        PlusActive = new Texture("PlusActive.png");
        PlusInactive = new Texture("PlusInactive.png");
        MinusActive = new Texture("MinusActive.png");
        MinusInactive = new Texture("MinusInactive.png");
        BackActive = new Texture("BackActive.png");
        BackInactive = new Texture("BackInactive.png");

        VolumeBars = new Texture[11];
        for (int i = 0; i <= 10; i++) {
            VolumeBars[i] = new Texture(i + "VolumeBar.png");
        }

        backButton = new Rectangle(0, 436, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        soundButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - (2 * ((float) SOUND_BUTTON_WIDTH / 2)) + 32, 250, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
        minusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) - 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
        plusButton = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) PLUSMINUS_BUTTON_WIDTH / 2) + 160, 350, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
        volumeBar = new Rectangle((float) DungeonAdventure.WIDTH / 2 - ((float) VOLUME_WIDTH / 2) + 128, 350, VOLUME_WIDTH, VOLUME_HEIGHT);

        setupInputProcessor();
    }

    private void setupInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

                if (soundButton.contains(touchPos.x, touchPos.y)) {
                    settings.toggleSound();
                } else if (plusButton.contains(touchPos.x, touchPos.y)) {
                    settings.increaseVolume();
                } else if (minusButton.contains(touchPos.x, touchPos.y)) {
                    settings.decreaseVolume();
                } else if (backButton.contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new MainMenuScreen(game, settings));
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        drawBackButton();
        drawSound();
        drawPlus();
        drawMinus();
        drawVolume();

        batch.end();
    }

    private void drawSound() {
        batch.draw(settings.isSoundOn() ? SoundActive : MuteActive, soundButton.x, soundButton.y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
    }

    private void drawPlus() {
        batch.draw(PlusActive, plusButton.x, plusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    }

    private void drawMinus() {
        batch.draw(MinusActive, minusButton.x, minusButton.y, PLUSMINUS_BUTTON_WIDTH, PLUSMINUS_BUTTON_HEIGHT);
    }

    private void drawVolume() {
        batch.draw(VolumeBars[settings.getVolumeLevel()], volumeBar.x, volumeBar.y, VOLUME_WIDTH, VOLUME_HEIGHT);
    }
    private void drawBackButton() {
        batch.draw(BackActive, backButton.x, backButton.y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        SoundActive.dispose();
        SoundInactive.dispose();
        MuteActive.dispose();
        MuteInactive.dispose();
        PlusActive.dispose();
        PlusInactive.dispose();
        MinusActive.dispose();
        MinusInactive.dispose();
        for (Texture texture : VolumeBars) {
            texture.dispose();
        }
    }
}

