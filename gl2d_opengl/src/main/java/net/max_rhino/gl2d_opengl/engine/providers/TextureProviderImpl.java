package net.max_rhino.gl2d_opengl.engine.providers;

import net.max_rhino.gl2d_core.engine.Texture;
import net.max_rhino.gl2d_core.engine.providers.TextureProvider;
import net.max_rhino.gl2d_opengl.engine.TextureImpl;

import java.nio.file.Path;

public class TextureProviderImpl implements TextureProvider {
    @Override
    public Texture create(int id, int width, int height) {
        return new TextureImpl(id, width, height);
    }

    @Override
    public Texture create(String path) {
        return new TextureImpl(path);
    }

    @Override
    public Texture create(Path path) {
        return new TextureImpl(path);
    }
}
