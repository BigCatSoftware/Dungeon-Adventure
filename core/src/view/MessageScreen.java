package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.dungeonadventure.game.DungeonAdventure;

/**
 * This screen will use static implementations to show a message to user through a UI.
 * @author Nazarii Revitskyi
 * @version Aug. 9, 2024.
 */
public class MessageScreen extends InputAdapter {
    private static final int BUTTON_WIDTH = 154;
    private static final int BUTTON_HEIGHT = 54;
    private static final int TABLE_WIDTH = 600;
    private static final int TABLE_HEIGHT = 400;
    private static final int SCROLL_PANE_OFFSET = 20;
    private static final Table myTable = createTable();
    private static final Label myTextLabel = createLabel();
    private static final ScrollPane myScrollPane = createScrollPane();
    private static final TextButton myOKButton = createOkButton();
    private static Table createTable(){
        final Table table = new Table();
        table.setBounds(DungeonAdventure.WIDTH/2-TABLE_WIDTH/2, DungeonAdventure.HEIGHT/2-TABLE_HEIGHT/2, TABLE_WIDTH, TABLE_HEIGHT);
        return table;
    }
    public static Label createLabel(){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"),
            Gdx.files.internal("fonts/alagard.png"), false);
        return new Label("", style);
    }
    public static TextButton createOkButton(){

        final TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        final TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        final TextureRegionDrawable buttonDisabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"),
            Gdx.files.internal("fonts/alagard.png"), false);;
        style.up = buttonUp;
        style.down = buttonDown;
        style.disabled = buttonDisabled;
        final TextButton button = new TextButton("OK", style);
        button.setBounds(myTable.getWidth()/2 - BUTTON_WIDTH/2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        return button;
    }
    private static ScrollPane createScrollPane(){
        myTextLabel.setAlignment(Align.topLeft);
        myTextLabel.setFontScale(0.6f);
        myTextLabel.setWrap(true);
        final ScrollPane log = new ScrollPane(myTextLabel);
        log.setBounds(SCROLL_PANE_OFFSET, BUTTON_HEIGHT, myTable.getWidth() -2 * SCROLL_PANE_OFFSET, myTable.getHeight()-BUTTON_HEIGHT - SCROLL_PANE_OFFSET);
        log.setForceScroll(false, true);
        log.setFlickScroll(false);
        log.setOverscroll(false, true);
        return log;
    }
    public static void setLabelText(final String theText){
        myTextLabel.setText(theText);
    }
    public static Table getMessageTable(){
        myTable.addActor(myScrollPane);
        myTable.addActor(myOKButton);
        myTable.setBackground(new TextureRegionDrawable(new Texture("MessageBack.png")));
        return myTable;
    }
    public static void addListener(final ChangeListener theListener){
        myOKButton.addListener(theListener);
    }
    public static void setTablePosition(final float theX, final float theY){
        myTable.setPosition(theX, theY);
    }
}
