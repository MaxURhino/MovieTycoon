package net.max_rhino.gl2d_core.engine.elements.sprite;

import net.max_rhino.gl2d_core.GL2D;
import net.max_rhino.gl2d_core.engine.DrawableDisposable;
import net.max_rhino.gl2d_core.engine.Texture;
import net.max_rhino.gl2d_core.engine.Transformable;
import net.max_rhino.gl2d_core.engine.math.Placement;
import net.max_rhino.gl2d_core.engine.math.Rect2i;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class Sprite implements DrawableDisposable, Transformable {
    private Texture texture;
    private Vector2f position = new Vector2f();
    private Vector2f scale = new Vector2f(1);
    private Vector2f offset = new Vector2f();
    @Nullable
    private Rect2i region = null;
    private boolean flipX;
    private boolean flipY;
    private boolean antiAlias = false;
    private float angle;

    public Sprite setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
        applyFiltering();
        return this;
    }

    public float getAngle() {
        return angle;
    }

    public Sprite setAngle(float angle) {
        this.angle = angle;
        return this;
    }

    private void applyFiltering() {
        texture.bind();
        int filter = antiAlias ? GL_LINEAR : GL_NEAREST;
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
        texture.unbind();
    }

    public Sprite(Texture texture, float x, float y) {
        this.texture = texture;
        this.texture.bind();
        this.position.set(x, y);
    }

    public Sprite(Texture texture, Vector2f position) {
        this(texture, position.x, position.y);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.texture.bind();
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getOffset() {
        return offset;
    }

    public Sprite setRegion(@Nullable Rect2i region) {
        this.region = region;
        return this;
    }

    public Sprite setOffset(Vector2f offset) {
        this.offset = offset;
        return this;
    }

    @Nullable
    public Rect2i getRegion() {
        return region;
    }

    @Override
    public Sprite setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    @Override
    public Vector2f getScale() {
        return this.scale;
    }

    @Override
    public Sprite setScale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    public Sprite setSize(Vector2i size) {
        int width = this.texture.width();
        int height = this.texture.height();
        if (this.region != null) {
            width = this.region.size().x;
            height = this.region.size().y;
        }
        this.scale.x = size.x / (float)width;
        this.scale.y = size.y / (float)height;
        return this;
    }

    public Vector2i getSize() {
        return new Vector2i((int)(this.texture.width() * this.scale.x), (int)(this.texture.height() * this.scale.y));
    }

    @Override
    public Sprite setScale(float scale) {
        return setScale(new Vector2f(scale));
    }

    public boolean isFlipY() {
        return flipY;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    @Override
    public Sprite render(double dt) {
        glPushMatrix();

        float u0 = 0f;
        float v0 = 0f;
        float u1 = 1f;
        float v1 = 1f;

        int width = texture.width();
        int height = texture.height();

        if (region != null) {
            float texW = texture.width();
            float texH = texture.height();

            float x = region.pos().x;
            float y = region.pos().y;
            float w = region.size().x;
            float h = region.size().y;

            u0 = x / texW;
            v0 = y / texH;
            u1 = (x + w) / texW;
            v1 = (y + h) / texH;

            width = region.size().x;
            height = region.size().y;
        }

        float w = width * scale.x;
        float h = height * scale.y;

        if (isFlipX()) {
            float tempU0 = u0;

            u0 = u1;
            u1 = tempU0;
        }

        float x = position.x;
        float y = position.y;

        if (!offset.equals(0, 0)) {
            Placement placement = new Placement(GL2D.vector2fToVector2i(offset.mul(scale)), new Vector2i(width, height));
            x -= placement.getX(Placement.Placements.LEFT);
            y -= placement.getY(Placement.Placements.LEFT);
        }

        glTranslatef(x, y, 0);
        glTranslatef(w/2, h/2, 0);
        glRotatef(angle, 0, 0, 1);
        glTranslatef(-w/2, -h/2, 0);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        texture.bind();

        glBegin(GL_QUADS);

        glTexCoord2f(u0, v0);
        glVertex2f(0, 0);

        glTexCoord2f(u1, v0);
        glVertex2f(0 + w, 0);

        glTexCoord2f(u1, v1);
        glVertex2f(0 + w, 0 + h);

        glTexCoord2f(u0, v1);
        glVertex2f(0, 0 + h);

        glEnd();

        texture.unbind();
        glDisable(GL_TEXTURE_2D);

        glPopMatrix();

        return this;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
