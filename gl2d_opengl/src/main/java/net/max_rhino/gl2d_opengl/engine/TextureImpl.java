package net.max_rhino.gl2d_opengl.engine;

import net.max_rhino.gl2d_core.engine.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL12.*;

public record TextureImpl(int id, int width, int height) implements Texture {
    public TextureImpl() {
        this(0, 0, 0);
    }

    public TextureImpl(String path) {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

        ByteBuffer image = STBImage.stbi_load(
                path,
                widthBuffer,
                heightBuffer,
                channelsBuffer,
                4
        );

        if (image == null) {
            throw new IllegalStateException(
                    "Failed to load texture: " + STBImage.stbi_failure_reason()
            );
        }

        this(glGenTextures(), widthBuffer.get(0), heightBuffer.get(0));
        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA,
                width,
                height,
                0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                image
        );

        glBindTexture(GL_TEXTURE_2D, 0);

        STBImage.stbi_image_free(image);
    }

    public TextureImpl(Path path) {
        this(path.toString());
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void dispose() {
        glDeleteTextures(id);
    }
}
