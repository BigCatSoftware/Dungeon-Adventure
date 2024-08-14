package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.dungeonadventure.game.DungeonAdventure;
import controller.HelpInputProcessor;

public class HelpScreen implements Screen, Disposable {
    private final SpriteBatch myBatch;
    private final BitmapFont myFont;
    private final Screen myPreviousScreen;
    private final DungeonAdventure myGame;
    private final GlyphLayout myLayout;

    public HelpScreen(final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myPreviousScreen = thePreviousScreen;
        myGame = theGame;
        myBatch = new SpriteBatch();
        myFont = new BitmapFont();
        myLayout = new GlyphLayout();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new HelpInputProcessor(myGame, myPreviousScreen));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myBatch.begin();

        myLayout.setText(myFont, "Here are some tips:");
        myFont.draw(myBatch, "Here are some tips:", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 150);

        myLayout.setText(myFont, "- Tip 1: Always save your game!");
        myFont.draw(myBatch, "- Tip 1: Always save your game!", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 175);

        myLayout.setText(myFont, "- Tip 2: Explore every corner of the dungeon.");
        myFont.draw(myBatch, "- Tip 2: Explore every corner of the dungeon.", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 200);

        myLayout.setText(myFont, "- Tip 3: Watch out for traps!");
        myFont.draw(myBatch, "- Tip 3: Watch out for traps!", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 225);

        myLayout.setText(myFont, "- Tip 4: Collect all items to increase your chances of survival.");
        myFont.draw(myBatch, "- Tip 4: Collect all items to increase your chances of survival.", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 250);

        myLayout.setText(myFont, "Press 'B' to return to the previous screen.");
        myFont.draw(myBatch, "Press 'B' to return to the previous screen.", (DungeonAdventure.WIDTH - myLayout.width) / 2, DungeonAdventure.HEIGHT - 275);

        myBatch.end();
    }




    @Override
    public void resize(int width, int height) {
        // Handle screen resizing
    }

    @Override
    public void pause() {
        // Handle pause event
    }

    @Override
    public void resume() {
        // Handle resume event
    }

    @Override
    public void hide() {
        // Handle hiding the screen
    }

    @Override
    public void dispose() {
        myBatch.dispose();
        myFont.dispose();
    }
}
