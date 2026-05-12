package net.max_rhino.gl2d_opengl.engine.elements.shape;

import net.max_rhino.gl2d_core.engine.DrawableDisposable;
import net.max_rhino.gl2d_core.engine.Transformable;
import net.max_rhino.gl2d_core.engine.elements.shape.Shape;
import org.joml.Vector2f;

import java.awt.*;

public abstract class ShapeImpl implements Shape {
    protected Vector2f position = new Vector2f();
    protected Vector2f scale = new Vector2f(1);
    protected Color color = Color.WHITE;

    public ShapeImpl(float x, float y) {
        position.set(x, y);
    }

    public Color getColor() {
        return color;
    }

    public ShapeImpl setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public ShapeImpl setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    @Override
    public Vector2f getScale() {
        return scale;
    }

    @Override
    public ShapeImpl setScale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public ShapeImpl setScale(float scale) {
        this.scale = new Vector2f(scale);
        return this;
    }

    @Override
    public void dispose() {

    }
}
