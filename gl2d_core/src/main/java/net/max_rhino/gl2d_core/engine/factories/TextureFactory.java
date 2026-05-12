package net.max_rhino.gl2d_core.engine.factories;

import net.max_rhino.gl2d_core.engine.Texture;
import net.max_rhino.gl2d_core.engine.providers.TextureProvider;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class TextureFactory {

    private static final TextureProvider provider =
            ServiceLoader.load(TextureProvider.class)
                    .findFirst()
                    .orElseThrow();

    public static Texture create(int id, int width, int height) {
        return provider.create(id, width, height);
    }

    public static Texture create(String path) {
        return provider.create(path);
    }

    public static Texture create(Path path) {
        return provider.create(path);
    }
}
