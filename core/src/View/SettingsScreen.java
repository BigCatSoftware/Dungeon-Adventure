package View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeonadventure.game.DungeonAdventure;

public class SettingsScreen implements Screen {
   public DungeonAdventure game;

   Texture SoundActive;
   Texture SoundInactive;
   Texture MuteActive;
   Texture MuteInactive;
   Texture PlusActive;
   Texture PlusInactive;
   Texture MinusActive;
   Texture MinusInactive;


    public SettingsScreen (final DungeonAdventure game) {
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
