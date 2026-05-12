package net.max_rhino.gl2d_opengl.engine.elements.shape.providers;

import net.max_rhino.gl2d_core.engine.elements.shape.Rectangle;
import net.max_rhino.gl2d_core.engine.elements.shape.providers.RectangleProvider;
import net.max_rhino.gl2d_opengl.engine.elements.shape.RectangleImpl;

public class RectangleProviderImpl implements RectangleProvider {
    @Override
    public Rectangle create(float x, float y, float width, float height) {
        return new RectangleImpl(x, y, width, height);
    }
}
