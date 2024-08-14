package controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import model.GameMaster;
import model.Tile;
import view.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.dungeonadventure.game.DungeonAdventure;


import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

/**
 * Handles user input for player actions and settings navigation in the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class PlayerInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final GameScreen myPreviousScreen;

    /**
     * Constructs a new PlayerInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public PlayerInputProcessor(final DungeonAdventure theGame, final GameScreen thePreviousScreen) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
    }

    /**
     * Handles key down events for player movement and actions.
     *
     * @param keycode the key code of the pressed key
     * @return true if the key event is handled, false otherwise
     */
    @Override
    public boolean keyDown(int keycode) {
        GameMaster gm = GameMaster.getInstance();
        switch (keycode) {
            case Input.Keys.UP:
                //check if cell at up is walkable
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()+1].isWalkable()){
                    gm.getPlayer().moveCharacterUp();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                gm.enemyMove();
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                checkTileType(gm.getPlayerX(), gm.getPlayerY());
                break;
            case Input.Keys.DOWN:
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()-1].isWalkable()){
                    gm.getPlayer().moveCharacterDown();

                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                gm.enemyMove();
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                checkTileType(gm.getPlayerX(), gm.getPlayerY());
                break;
            case Input.Keys.LEFT:
                if(gm.getMap()[gm.getPlayerX()-1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterLeft();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                gm.enemyMove();
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                checkTileType(gm.getPlayerX(), gm.getPlayerY());
                break;
            case Input.Keys.RIGHT:
                if(gm.getMap()[gm.getPlayerX()+1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterRight();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                gm.enemyMove();
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                checkTileType(gm.getPlayerX(), gm.getPlayerY());
                break;
            case Input.Keys.ESCAPE:
                myPreviousScreen.showMenu();
                break;
            case Input.Keys.E:
                openDoors();
                break;
            case Input.Keys.F10:
                gm.toggleCheats();
                break;
            default:
                return false; // Indicates that the key event was not handled
        }
        gm.updateMapFOW();
        myPreviousScreen.setPlayerImagePosition();
        return true; // Indicates that the key event was handled
    }
    private void checkTileType(final int theX, final int theY){
        GameMaster gm = GameMaster.getInstance();
        switch(GameMaster.getInstance().getMap()[theX][theY]){
            case HEALTH_POTION:
                gm.heroPicksHealthPotion();
                mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Twinkle.ogg")));
                GameMaster.getInstance().getMap()[theX][theY] = Tile.FLOOR;
                break;
            case POISON_POTION:
                gm.getPlayer().setMyTrapsTriggered();
                myPreviousScreen.showTrapMessage("Hero affected by poison!!!\n" + gm.heroTrapDamage(Tile.POISON_POTION));
                GameMaster.getInstance().getMap()[theX][theY] = Tile.FLOOR;
                break;
            case KEY:
                gm.heroPicksKey();
                gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                break;
            case BOMB:
                gm.heroPicksBomb();
                gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                break;
            case PIT_TRAP:
                gm.getPlayer().setMyTrapsTriggered();
                myPreviousScreen.showTrapMessage("Hero fell in pit!!! \n" + gm.heroTrapDamage(Tile.PIT_TRAP));
                break;
            case EXIT:
                statScreen(GameMaster.getInstance().getPlayer().getMyName(),
                        GameMaster.getInstance().getPlayer().getCurrentHealth(),
                        GameMaster.getInstance().getPlayer().getMyEnemiesKilled(),
                        GameMaster.getInstance().getPlayer().getMyPotionsUsed(),
                        GameMaster.getInstance().getPlayer().getMyBombsUsed(),
                        GameMaster.getInstance().getPlayer().getMyTrapsTriggered());
            default:
                break;
        }
    }

    /**
     * This method generates the stat screen message.
     */
    private void statScreen(final String thePlayerName, final int thePlayerHealth,
                            final int theEnemiesKilled, final int thePotionsUsed,
                            final int theBombsUsed, final int theTrapsTriggered) {
        final StringBuilder statBuilder = new StringBuilder();
        statBuilder.append(thePlayerName);
        statBuilder.append("\n");
        statBuilder.append("Player Health: ");
        statBuilder.append(thePlayerHealth);
        statBuilder.append("\n");
        statBuilder.append("Enemies Killed: ");
        statBuilder.append(theEnemiesKilled);
        statBuilder.append("\n");
        statBuilder.append("Total Potions: ");
        statBuilder.append(thePotionsUsed +
                GameMaster.getInstance().getPlayer().getMyCurrentPotions());
        statBuilder.append("\n");
        statBuilder.append("Potions Used: ");
        statBuilder.append(thePotionsUsed);
        statBuilder.append("\n");
        statBuilder.append("Total Bombs Collected: ");
        statBuilder.append(theBombsUsed +
                GameMaster.getInstance().getPlayer().getMyCurrentBombs());
        statBuilder.append("\n");
        statBuilder.append("Bombs Used: ");
        statBuilder.append(theBombsUsed);
        statBuilder.append("\n");
        statBuilder.append("Traps Triggered: ");
        statBuilder.append(theTrapsTriggered);
        statBuilder.append("\n");

        myPreviousScreen.showStatisticsScreen(statBuilder.toString());
    }

    private void openDoors() {
        final GameMaster gm = GameMaster.getInstance();
        for (int i = gm.getPlayerX() - 1; i <= gm.getPlayerX() + 1; i++) {
            for (int j = gm.getPlayerY() - 1; j <= gm.getPlayerY() + 1; j++) {
                if (gm.getMap()[i][j] == Tile.DOOR) {
                    gm.getMap()[i][j] = Tile.OPEN_DOOR;
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Door Creak.ogg")));
                } else if (gm.getMap()[i][j] == Tile.OPEN_DOOR) {
                    gm.getMap()[i][j] = Tile.DOOR;
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Door Creak2.ogg")));
                }
            }
        }

    }
}