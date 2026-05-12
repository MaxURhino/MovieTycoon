package net.max_rhino.gl2d_core;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class GL2D {
    public static Vector2i vector2fToVector2i(Vector2f vector2f) {
        return new Vector2i((int) vector2f.x, (int) vector2f.y);
    }
}
