package net.max_rhino.gl2d_core.engine.elements.shape.factories;

import net.max_rhino.gl2d_core.engine.elements.shape.Rectangle;
import net.max_rhino.gl2d_core.engine.elements.shape.providers.RectangleProvider;

import java.util.ServiceLoader;

public class RectangleFactory {

    private static final RectangleProvider provider =
            ServiceLoader.load(RectangleProvider.class)
                    .findFirst()
                    .orElseThrow();

    public static Rectangle create(float x, float y, float width, float height) {
        return provider.create(x, y, width, height);
    }
}
