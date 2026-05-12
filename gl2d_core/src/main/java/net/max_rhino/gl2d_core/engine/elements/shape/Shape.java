package net.max_rhino.gl2d_core.engine.elements.shape;

import net.max_rhino.gl2d_core.engine.DrawableDisposable;
import net.max_rhino.gl2d_core.engine.Transformable;
import org.joml.Vector2f;

import java.awt.*;

public interface Shape extends DrawableDisposable, Transformable {

    Color getColor();

    Shape setColor(Color color);
}
