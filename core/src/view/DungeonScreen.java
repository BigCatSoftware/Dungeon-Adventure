package view;

import controller.DungeonInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeonadventure.game.DungeonAdventure;

public class DungeonScreen implements Screen {
    private final DungeonAdventure myGame;

    private final OrthographicCamera myCamera;

    private final Texture myBackButtonActive;
    private final Texture myBackButtonInactive;
    private final Texture myWarriorButtonActive;
    private final Texture myWarriorButtonInactive;
    private final Texture myThiefButtonActive;
    private final Texture myThiefButtonInactive;
    private final Texture myPriestessButtonActive;
    private final Texture myPriestessButtonInactive;
    private final Texture myLoadButtonInactive;
    private final Texture myLoadButtonActive;
    private final Texture myDungeonAdventureTitle;
    private final Texture mySettingsButtonActive;
    private final Texture mySettingsButtonInactive;
    private final Texture myChooseCharacterTitle;
    private final Texture myOrTitle;


    private final int DUNGEON_TITLE_WIDTH = 450;
    private final int DUNGEON_TITLE_HEIGHT = 450;

    private final int BACK_BUTTON_WIDTH = 64;
    private final int BACK_BUTTON_HEIGHT = 64;

    private final int CHARACTER_BUTTON_WIDTH = 250;
    private final int CHARACTER_BUTTON_HEIGHT = 84;

    private final int OR_TITLE_WIDTH = 75;
    private final int OR_TITLE_HEIGHT = 75;

    private final int HERO_BUTTON_WIDTH = 175;
    private final int HERO_BUTTON_HEIGHT = 42;

    private final int LOAD_BUTTON_WIDTH = 300;
    private final int LOAD_BUTTON_HEIGHT = 120;

    private final int SETTINGS_BUTTON_WIDTH = 64;
    private final int SETTINGS_BUTTON_HEIGHT = 64;

    private final int BACK_BUTTON_Y = DungeonAdventure.HEIGHT - BACK_BUTTON_HEIGHT;
    private final int HERO_BUTTON_Y = 375;
    private final int LOAD_BUTTON_Y = 100;
    private final int CHARACTER_TITLE_Y = HERO_BUTTON_Y + 2 * HERO_BUTTON_HEIGHT;
    private final int OR_TITLE_Y = HERO_BUTTON_Y - 2 * HERO_BUTTON_HEIGHT - 30;
    private final int TITLE_Y = 300;
    private final int SETTINGS_BUTTON_Y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_HEIGHT;

    private final int BACK_BUTTON_X;
    private final int WARRIOR_BUTTON_X;
    private final int THIEF_BUTTON_X;
    private final int PRIESTESS_BUTTON_X;
    private final int LOAD_BUTTON_X;
    private final int SETTINGS_BUTTON_X;

    public DungeonScreen(final DungeonAdventure theGame) {
        myGame = theGame;

        myCamera = new OrthographicCamera();
        myCamera.setToOrtho(false, DungeonAdventure.WIDTH, DungeonAdventure.HEIGHT);

        myOrTitle = new Texture("Or.png");

        myChooseCharacterTitle = new Texture("ChooseCharacter.png");

        myLoadButtonActive = new Texture("LoadActive.png");
        myLoadButtonInactive = new Texture("LoadInactive.png");

        myBackButtonActive = new Texture("BackActive.png");
        myBackButtonInactive = new Texture("BackInactive.png");

        myWarriorButtonActive = new Texture("WarriorActive.png");
        myWarriorButtonInactive = new Texture("WarriorInactive.png");

        myThiefButtonActive = new Texture("ThiefActive.png");
        myThiefButtonInactive = new Texture("ThiefInactive.png");

        myPriestessButtonActive = new Texture("PriestessActive.png");
        myPriestessButtonInactive = new Texture("PriestessInactive.png");

        mySettingsButtonActive = new Texture("SettingsActive.png");
        mySettingsButtonInactive = new Texture("SettingsInactive.png");

        myDungeonAdventureTitle = new Texture("DungeonAdventureTitle.png");

        BACK_BUTTON_X = 0;
        WARRIOR_BUTTON_X = DungeonAdventure.WIDTH / 2 - HERO_BUTTON_WIDTH / 2 - HERO_BUTTON_WIDTH;
        THIEF_BUTTON_X = DungeonAdventure.WIDTH / 2 - HERO_BUTTON_WIDTH / 2 + HERO_BUTTON_WIDTH;
        PRIESTESS_BUTTON_X = DungeonAdventure.WIDTH / 2 - HERO_BUTTON_WIDTH / 2;
        LOAD_BUTTON_X = DungeonAdventure.WIDTH / 2 - LOAD_BUTTON_WIDTH / 2;
        SETTINGS_BUTTON_X = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new DungeonInputProcessor(myGame, DungeonScreen.this, myCamera,
                BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT,
                WARRIOR_BUTTON_X,  HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT,
                THIEF_BUTTON_X, PRIESTESS_BUTTON_X, LOAD_BUTTON_X, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT,
                SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)); // Set input processor
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        myGame.batch.setProjectionMatrix(myCamera.combined);
        myGame.batch.begin();

        int x = DungeonAdventure.WIDTH / 2 - DUNGEON_TITLE_WIDTH / 2;
        myGame.batch.draw(myDungeonAdventureTitle, x, TITLE_Y, DUNGEON_TITLE_WIDTH, DUNGEON_TITLE_HEIGHT);

        x = DungeonAdventure.WIDTH / 2 - CHARACTER_BUTTON_WIDTH / 2;
        myGame.batch.draw(myChooseCharacterTitle, x, CHARACTER_TITLE_Y, CHARACTER_BUTTON_WIDTH,CHARACTER_BUTTON_HEIGHT);

        x = DungeonAdventure.WIDTH / 2 - OR_TITLE_WIDTH / 2;
        myGame.batch.draw(myOrTitle, x, OR_TITLE_Y, OR_TITLE_WIDTH, OR_TITLE_HEIGHT);

        settingsButtonDraw();
        warriorButtonDraw();
        thiefButtonDraw();
        priestessButtonDraw();
        loadButtonDraw();
        backButtonDraw();
        myGame.batch.end();
    }

    private void backButtonDraw() {
        int x = BACK_BUTTON_X;
        if (isButtonHovered(x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT)) {
            myGame.batch.draw(myBackButtonActive, x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myBackButtonInactive, x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }
    }

    private void loadButtonDraw() {
        int x = LOAD_BUTTON_X;
        if (isButtonHovered(x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT)) {
            myGame.batch.draw(myLoadButtonActive, x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myLoadButtonInactive, x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
        }
    }

    private void warriorButtonDraw() {
        int x = WARRIOR_BUTTON_X;
        if (isButtonHovered(x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            myGame.batch.draw(myWarriorButtonActive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myWarriorButtonInactive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        }
    }

    private void thiefButtonDraw() {
        int x = THIEF_BUTTON_X;
        if (isButtonHovered(x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            myGame.batch.draw(myThiefButtonActive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myThiefButtonInactive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        }
    }

    private void priestessButtonDraw() {
        int x = PRIESTESS_BUTTON_X;
        if (isButtonHovered(x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT)) {
            myGame.batch.draw(myPriestessButtonActive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(myPriestessButtonInactive, x, HERO_BUTTON_Y, HERO_BUTTON_WIDTH, HERO_BUTTON_HEIGHT);
        }
    }

    private void settingsButtonDraw() {
        int x = SETTINGS_BUTTON_X;
        if (isButtonHovered(x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT)) {
            myGame.batch.draw(mySettingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        } else {
            myGame.batch.draw(mySettingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }
    }

    private boolean isButtonHovered(final int buttonX, final int buttonY,
                                    final int buttonWidth, final int buttonHeight) {
        int touchX = Gdx.input.getX();
        int touchY = DungeonAdventure.HEIGHT - Gdx.input.getY();
        return touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                touchY >= buttonY && touchY <= buttonY + buttonHeight;
    }

    @Override
    public void resize(final int width, final int height) {
        // Implement if needed
    }

    @Override
    public void pause() {
        // Implement if needed
    }

    @Override
    public void resume() {
        // Implement if needed
    }

    @Override
    public void hide() {
        // Implement if needed
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        mySettingsButtonActive.dispose();
        mySettingsButtonInactive.dispose();
        myDungeonAdventureTitle.dispose();
        myBackButtonActive.dispose();
        myBackButtonInactive.dispose();
        myWarriorButtonActive.dispose();
        myWarriorButtonInactive.dispose();
        myThiefButtonActive.dispose();
        myThiefButtonInactive.dispose();
        myPriestessButtonActive.dispose();
        myPriestessButtonInactive.dispose();
        myLoadButtonActive.dispose();
        myLoadButtonInactive.dispose();
    }
}
