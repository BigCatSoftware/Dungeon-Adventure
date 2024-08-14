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

/**
 * This screen will use static implementations to show a message to user through a UI.
 * @author Tiger Schueler
 * @version 13AUG24
 */
public class StatisticsScreen extends InputAdapter {
    private static final int BUTTON_WIDTH = 354;
    private static final int BUTTON_HEIGHT = 54;
    private static final int TABLE_WIDTH = 800;
    private static final int TABLE_HEIGHT = 800;
    private static final int SCROLL_PANE_OFFSET = 20;
    private static final Table myTable = createTable();
    private static final Label myTextLabel = createLabel();
    private static final ScrollPane myScrollPane = createScrollPane();
    private static final TextButton myEXITButton = createExitButton();
    private static Table createTable(){
        final Table table = new Table();
        table.setBounds(0, 0, TABLE_WIDTH, TABLE_HEIGHT);
        return table;
    }
    public static Label createLabel(){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"),
                Gdx.files.internal("fonts/alagard.png"), false);
        return new Label("", style);
    }
    public static TextButton createExitButton(){

        final TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture("CombatButton.Up.png"));
        final TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture("CombatButton.Down.png"));
        final TextureRegionDrawable buttonDisabled = new TextureRegionDrawable(new Texture("CombatButton.Disabled.png"));
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/alagard.fnt"),
                Gdx.files.internal("fonts/alagard.png"), false);
        style.up = buttonUp;
        style.down = buttonDown;
        style.disabled = buttonDisabled;
        final TextButton button = new TextButton("EXIT", style);
        button.setBounds(myTable.getWidth()/2 - BUTTON_WIDTH/2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        return button;
    }
    private static ScrollPane createScrollPane(){
        myTextLabel.setAlignment(Align.left);
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
        myTable.addActor(myEXITButton);
        myTable.setBackground(new TextureRegionDrawable(new Texture("GameOver1.png")));
        return myTable;
    }
    public static void addListener(final ChangeListener theListener){
        myEXITButton.addListener(theListener);
    }
}
