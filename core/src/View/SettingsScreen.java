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

public class SettingsScreen implements Screen {
    public DungeonAdventure game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final Texture SoundActive;
    private final Texture SoundInactive;
    private final Texture MuteActive;
    private final Texture MuteInactive;
    private final Texture PlusActive;
    private final Texture PlusInactive;
    private final Texture MinusActive;
    private final Texture MinusInactive;
    private final Texture VolumeBar0;
    private final Texture VolumeBar1;
    private final Texture VolumeBar2;
    private final Texture VolumeBar3;
    private final Texture VolumeBar4;
    private final Texture VolumeBar5;
    private final Texture VolumeBar6;
    private final Texture VolumeBar7;
    private final Texture VolumeBar8;
    private final Texture VolumeBar9;
    private final Texture VolumeBar10;

    private final Rectangle soundButton;
    private final Rectangle muteButton;
    private final Rectangle plusButton;
    private final Rectangle minusButton;
    private final Rectangle volumeBar;

    private int volumeLevel = 5; // Example volume level, from 0 to 10
    private boolean isSoundOn = true;
    private boolean isMuted = false;

    public SettingsScreen(final DungeonAdventure game) {
        this.game = game;
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
        VolumeBar0 = new Texture("0VolumeBar.png");
        VolumeBar1 = new Texture("1VolumeBar.png");
        VolumeBar2 = new Texture("2VolumeBar.png");
        VolumeBar3 = new Texture("3VolumeBar.png");
        VolumeBar4 = new Texture("4VolumeBar.png");
        VolumeBar5 = new Texture("5VolumeBar.png");
        VolumeBar6 = new Texture("6VolumeBar.png");
        VolumeBar7 = new Texture("7VolumeBar.png");
        VolumeBar8 = new Texture("8VolumeBar.png");
        VolumeBar9 = new Texture("9VolumeBar.png");
        VolumeBar10 = new Texture("10VolumeBar.png");

        soundButton = new Rectangle(100, 350, 64, 64);
        muteButton = new Rectangle(200, 350, 64, 64);
        plusButton = new Rectangle(300, 350, 64, 64);
        minusButton = new Rectangle(400, 350, 64, 64);
        volumeBar = new Rectangle(500, 350, 128, 64);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

                if (soundButton.contains(touchPos.x, touchPos.y)) {
                    isSoundOn = !isSoundOn;
                } else if (muteButton.contains(touchPos.x, touchPos.y)) {
                    isMuted = !isMuted;
                } else if (plusButton.contains(touchPos.x, touchPos.y)) {
                    if (volumeLevel < 10) volumeLevel++;
                } else if (minusButton.contains(touchPos.x, touchPos.y)) {
                    if (volumeLevel > 0) volumeLevel--;
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
        batch.draw(isSoundOn ? SoundActive : SoundInactive, soundButton.x, soundButton.y);
        batch.draw(isMuted ? MuteActive : MuteInactive, muteButton.x, muteButton.y);
        batch.draw(PlusActive, plusButton.x, plusButton.y); // Assuming the button is always active
        batch.draw(MinusActive, minusButton.x, minusButton.y); // Assuming the button is always active

        // Draw the appropriate volume bar based on the volume level
        switch (volumeLevel) {
            case 0: batch.draw(VolumeBar0, volumeBar.x, volumeBar.y); break;
            case 1: batch.draw(VolumeBar1, volumeBar.x, volumeBar.y); break;
            case 2: batch.draw(VolumeBar2, volumeBar.x, volumeBar.y); break;
            case 3: batch.draw(VolumeBar3, volumeBar.x, volumeBar.y); break;
            case 4: batch.draw(VolumeBar4, volumeBar.x, volumeBar.y); break;
            case 5: batch.draw(VolumeBar5, volumeBar.x, volumeBar.y); break;
            case 6: batch.draw(VolumeBar6, volumeBar.x, volumeBar.y); break;
            case 7: batch.draw(VolumeBar7, volumeBar.x, volumeBar.y); break;
            case 8: batch.draw(VolumeBar8, volumeBar.x, volumeBar.y); break;
            case 9: batch.draw(VolumeBar9, volumeBar.x, volumeBar.y); break;
            case 10: batch.draw(VolumeBar10, volumeBar.x, volumeBar.y); break;
        }
        batch.end();

        /**
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (soundButton.contains(touchPos.x, touchPos.y)) {
                isSoundOn = !isSoundOn;
            } else if (muteButton.contains(touchPos.x, touchPos.y)) {
                isMuted = !isMuted;
            } else if (plusButton.contains(touchPos.x, touchPos.y)) {
                if (volumeLevel < 10) volumeLevel++;
            } else if (minusButton.contains(touchPos.x, touchPos.y)) {
                if (volumeLevel > 0) volumeLevel--;
            }
        }
         */
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
        VolumeBar0.dispose();
        VolumeBar1.dispose();
        VolumeBar2.dispose();
        VolumeBar3.dispose();
        VolumeBar4.dispose();
        VolumeBar5.dispose();
        VolumeBar6.dispose();
        VolumeBar7.dispose();
        VolumeBar8.dispose();
        VolumeBar9.dispose();
        VolumeBar10.dispose();
    }
}
