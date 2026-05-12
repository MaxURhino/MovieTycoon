package net.max_rhino.gl2d_core.engine.math;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public record Rect2i(Vector2i pos, Vector2i size) {
    public Rect2i(int x, int y, int w, int h) {
        this(new Vector2i(x, y), new Vector2i(w, h));
    }

    @NotNull
    @Override
    public String toString() {
        return "[Pos=" + pos + ",Size=" + size + "]";
    }
}
