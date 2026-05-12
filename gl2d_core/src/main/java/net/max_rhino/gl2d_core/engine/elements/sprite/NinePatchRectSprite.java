package net.max_rhino.gl2d_core.engine.elements.sprite;

import net.max_rhino.gl2d_core.engine.DrawableDisposable;
import net.max_rhino.gl2d_core.engine.Texture;
import net.max_rhino.gl2d_core.engine.math.Rect2i;
import org.joml.Vector2i;

public class NinePatchRectSprite implements DrawableDisposable {
    private Sprite sprite;
    private int width, height, x, y;
    private final int border;

    public NinePatchRectSprite setWidth(int width) {
        this.width = width;
        if (this.width < border * 2) {
            this.width = border * 2;
        }
        return this;
    }

    public NinePatchRectSprite setHeight(int height) {
        this.height = height;
        if (this.height < border * 2) {
            this.height = border * 2;
        }
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBorder() {
        return border;
    }

    public NinePatchRectSprite(Texture texture, int x, int y, int width, int height, int border) {
        this.x = x; this.y = y;
        this.sprite = new Sprite(texture, x, y);
        this.border = border;
        setWidth(width).setHeight(height);
    }

    public NinePatchRectSprite setAntiAlias(boolean antiAlias) {
        this.sprite.setAntiAlias(antiAlias);
        return this;
    }

    @Override
    public void dispose() {
        this.sprite.dispose();
    }

    @Override
    public NinePatchRectSprite render(double dt) {
        //region top left corner
        this.sprite.setPosition(this.x, this.y);
        this.sprite.setRegion(new Rect2i(0, 0, border, border));
        this.sprite.render(dt);
        //endregion

        //region top bar
        if (this.width > border * 2) {
            this.sprite.setX(this.x + border);
            this.sprite.setRegion(new Rect2i(border, 0, this.sprite.getTexture().width() - (border * 2), border));
            this.sprite.setSize(new Vector2i(this.width - (this.border * 2), border));
            this.sprite.render(dt);
            this.sprite.setScale(1);
        }
        //endregion

        //region top right corner
        this.sprite.setX(this.x + (this.width - border));
        this.sprite.setRegion(new Rect2i(this.sprite.getTexture().width() - border, 0, border, border));
        this.sprite.render(dt);
        //endregion

        //region left bar
        if (this.height > border * 2) {
            this.sprite.setPosition(this.x, this.y + border);
            this.sprite.setRegion(new Rect2i(0, border, border, this.sprite.getTexture().height() - (border * 2)));
            this.sprite.setSize(new Vector2i(border, this.height - (this.border * 2)));
            this.sprite.render(dt);
            this.sprite.setScale(1);
        }
        //endregion

        //region right bar
        if (this.height > border * 2) {
            this.sprite.setPosition(this.x + (this.width - this.border), this.y + border);
            this.sprite.setRegion(new Rect2i((this.sprite.getTexture().width() - this.border), border, border, this.sprite.getTexture().height() - (border * 2)));
            this.sprite.setSize(new Vector2i(border, this.height - (this.border * 2)));
            this.sprite.render(dt);
            this.sprite.setScale(1);
        }
        //endregion

        //region bottom left corner
        this.sprite.setPosition(this.x, this.y + (this.height - border));
        this.sprite.setRegion(new Rect2i(0, this.sprite.getTexture().height() - border, border, border));
        this.sprite.render(dt);
        //endregion

        //region bottom bar
        if (this.width > border * 2) {
            this.sprite.setPosition(this.x + border, this.y + (this.height - border));
            this.sprite.setRegion(new Rect2i(border, this.sprite.getTexture().height() - border, this.sprite.getTexture().width() - (border * 2), border));
            this.sprite.setSize(new Vector2i(this.width - (this.border * 2), border));
            this.sprite.render(dt);
            this.sprite.setScale(1);
        }
        // endregion

        //region bottom right corner
        this.sprite.setPosition(this.x + (this.width - this.border), this.y + (this.height - border));
        this.sprite.setRegion(new Rect2i(this.sprite.getTexture().width() - border, this.sprite.getTexture().height() - border, border, border));
        this.sprite.render(dt);
        //endregion

        //region fill
        if (this.width > border * 2 && this.height > border * 2) {
            this.sprite.setPosition(this.x + border, this.y + border);
            this.sprite.setRegion(new Rect2i(border, border, this.sprite.getTexture().width() - (border * 2), this.sprite.getTexture().height() - (border * 2)));
            this.sprite.setSize(new Vector2i(this.width - (this.border * 2), this.height - (this.border * 2)));
            this.sprite.render(dt);
            this.sprite.setScale(1);
        }
        //endregion

        return this;
    }
}
