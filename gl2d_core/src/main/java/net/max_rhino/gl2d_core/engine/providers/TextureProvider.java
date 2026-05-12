package net.max_rhino.gl2d_core.engine.providers;

import net.max_rhino.gl2d_core.engine.Texture;

import java.nio.file.Path;

public interface TextureProvider {
    Texture create(int id, int width, int height);
    Texture create(String path);
    Texture create(Path path);
}
