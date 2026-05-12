package net.max_rhino.gl2d_core.engine;

public interface Texture extends Disposable {
    void bind();
    void unbind();

    int id();
    int width();
    int height();
}
