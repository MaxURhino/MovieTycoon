package net.max_rhino.gl2d_opengl.engine.elements.shape;

import net.max_rhino.gl2d_core.engine.elements.shape.Rectangle;
import net.max_rhino.gl2d_core.engine.elements.shape.Shape;
import org.lwjgl.opengl.GL11;

public class RectangleImpl extends ShapeImpl implements Rectangle {
    private final float width;
    private final float height;

    public RectangleImpl(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public RectangleImpl render(double dt) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor4f(
                this.color.getRed()    / 255f,
                this.color.getGreen() / 255f,
                this.color.getBlue()  / 255f,
                this.color.getAlpha() / 255f
        );
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + (width * scale.x), position.y);
        GL11.glVertex2f(position.x + (width * scale.x), position.y + (height * scale.y));
        GL11.glVertex2f(position.x, position.y + (height * scale.y));

        GL11.glEnd();

        GL11.glColor3f(1, 1, 1);

        return this;
    }
}
