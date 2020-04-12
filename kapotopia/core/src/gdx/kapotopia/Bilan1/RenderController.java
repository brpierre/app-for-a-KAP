package gdx.kapotopia.Bilan1;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.Iterator;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.BilanG1;

public class RenderController {

    /* Constants */

    private final String IMGBACK_PATH = "World1/Game1/EcranTotal.png";
    private final String BUBBLE_PATH = "ImagesGadgets/BulleExplicative.png";
    private final String MIR_PATH = "MireilleImages/MireilleInstruit.png";

    /* Basic Variables */

    private Kapotopia game;
    private BilanG1 bilan;

    private Viewport bilanViewport;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private boolean showStiSprites;
    private Queue<Sprite> stiSprites;

    // References to objects to be rendered
    private Texture background;
    private Texture bubble;
    private TextureRegion mireilleUni;

    public RenderController(Kapotopia game, BilanG1 bilan) {
        this.game = game;
        this.bilan = bilan;

        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.bilanViewport = new FitViewport(game.viewport.getWorldWidth(), game.viewport.getWorldHeight(), camera);

        this.batch = new SpriteBatch();

        final float wWidth = game.viewport.getWorldWidth();
        final float wHeight = game.viewport.getWorldHeight();

        // Defining visual elements

        background = AssetsManager.getInstance().getTextureByPath(IMGBACK_PATH);
        bubble = AssetsManager.getInstance().getTextureByPath(BUBBLE_PATH);

        final Texture mireille = AssetsManager.getInstance().getTextureByPath(MIR_PATH);
        this.mireilleUni = new TextureRegion(mireille, 85, 105, 595, mireille.getHeight() - 200); // We leave out the blanks
        showStiSprites = false;

        stiSprites = new Queue<Sprite>();
    }

    public void enqueueStiSprite(Sprite sprite) {
        stiSprites.addLast(sprite);
    }

    public void dequeueStiSprite() {
        stiSprites.removeFirst();
    }

    public void setShowStiSprites() {
        showStiSprites = true;
    }

    public void update() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background,0,0);
        batch.draw(bubble,0,0);
        batch.draw(mireilleUni, 0, 0, 0, 0, mireilleUni.getRegionWidth(), mireilleUni.getRegionHeight(), 0.45f, 0.45f, 0);

        if (showStiSprites) {
            Iterator iter = stiSprites.iterator();
            if (iter.hasNext()) {
                iter.next();
                while (iter.hasNext()) {
                    Sprite sti = (Sprite) iter.next();
                    sti.draw(batch);
                }
                Sprite first = stiSprites.first();
                //first.setScale(first.getScaleX() + 0.001f);
                first.draw(batch);
            }
        }

        batch.end();
    }

    public Viewport getViewport() {
        return bilanViewport;
    }
}
