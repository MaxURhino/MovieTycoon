package net.max_rhino.gl2d_core.engine.elements.shape.providers;

import net.max_rhino.gl2d_core.engine.elements.shape.Rectangle;

public interface RectangleProvider {
    Rectangle create(float x, float y, float width, float height);
}
