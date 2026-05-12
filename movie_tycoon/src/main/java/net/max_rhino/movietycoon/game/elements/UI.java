package net.max_rhino.movietycoon.game.elements;

import net.max_rhino.gl2d_core.engine.factories.TextureFactory;
import net.max_rhino.movietycoon.Main;
import net.max_rhino.movietycoon.PathsUtil;
import net.max_rhino.gl2d_core.engine.DrawableDisposable;
import net.max_rhino.gl2d_core.engine.elements.sprite.NinePatchRectSprite;
import net.max_rhino.gl2d_core.engine.elements.sprite.ScrollingSprite;
import net.max_rhino.gl2d_core.engine.elements.sprite.Sprite;
import net.max_rhino.gl2d_core.engine.math.Rect2i;
import org.lwjgl.opengl.GL11;

public class UI implements DrawableDisposable {
    private final NinePatchRectSprite rounded_square = new NinePatchRectSprite(
            TextureFactory.create(PathsUtil.image("ui/rounded_square")),
            0, 0,
            256, 128,
            40
    );
    private final Sprite scrolling_thing_sprite = new Sprite(
            TextureFactory.create(PathsUtil.image("ui/scrolling_thing")),
            0, 0
    );
    private final ScrollingSprite scrolling_thing = new ScrollingSprite(
            () -> scrolling_thing_sprite,
            450 - this.scrolling_thing_sprite.getTexture().width(), 0
    );
    private final Sprite shop_grid_sprite = new Sprite(
            TextureFactory.create(PathsUtil.image("ui/shop_grid")),
            0, 0
    );
    private final ScrollingSprite shop_grid = new ScrollingSprite(
            () -> shop_grid_sprite,
            0, 0
    );

    public UI() {
        this.rounded_square.setAntiAlias(true);
        this.scrolling_thing_sprite.setAntiAlias(true);
        this.shop_grid_sprite.setAntiAlias(true);

        this.scrolling_thing.setyDelay(2);
        this.scrolling_thing.setyCopies(7);

        this.shop_grid.setxDelay(5);
        this.shop_grid.setxCopies(12);
        this.shop_grid.setyDelay(5);
        this.shop_grid.setyCopies(12);
    }

    @Override
    public void dispose() {
        rounded_square.dispose();
    }

    int frame = 0;

    @Override
    public UI render(double dt) {
        frame += 1;
        this.shop_grid.setFrame(frame);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(-256, 0, 0);
        GL11.glRotatef(5, 0, 0, -1);
        Rect2i scissor = Main.mainApp.getScaledScissorPos(new Rect2i(0, 0, 450, Main.mainApp.definedHeight));
        GL11.glScissor(scissor.pos().x, scissor.pos().y, scissor.size().x, scissor.size().y);
        this.shop_grid.render(dt);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        this.scrolling_thing.setFrame(frame);
        this.scrolling_thing.render(dt);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        this.rounded_square.render(dt);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        return this;
    }
}
