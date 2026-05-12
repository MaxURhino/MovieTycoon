package net.max_rhino.gl2d_core.engine.elements.font;

public class Glyph {
    public int texture;
    public int width;
    public int height;

    public int bearingX;
    public int bearingY;

    public long advance;

    public Glyph(
            int texture,
            int width,
            int height,
            int bearingX,
            int bearingY,
            long advance
    ) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.bearingX = bearingX;
        this.bearingY = bearingY;
        this.advance = advance;
    }
}
