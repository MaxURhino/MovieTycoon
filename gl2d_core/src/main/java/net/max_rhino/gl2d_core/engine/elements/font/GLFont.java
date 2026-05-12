package net.max_rhino.gl2d_core.engine.elements.font;

import net.max_rhino.gl2d_core.engine.Disposable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.freetype.FT_Bitmap;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FT_GlyphSlot;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.util.freetype.FreeType.*;
import static org.lwjgl.opengl.GL12.*;

public class GLFont implements Disposable {
    private final Map<Character, Glyph> glyphs = new HashMap<>();

    private long library;
    private FT_Face face;

    public final int size;

    private ByteBuffer fontBuffer;

    private final String path;

    public GLFont(String path, int size) {
        this.path = path;
        this.size = size;
        load(path, size);
    }

    private void load(String path, int size) {
        try {
            Path path1 = Path.of(path);
            fontBuffer = BufferUtils.createByteBuffer(
                    (int) Files.size(path1)
            );

            fontBuffer.put(Files.readAllBytes(path1));
            fontBuffer.flip();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {

            PointerBuffer libraryPtr = stack.mallocPointer(1);

            if (FT_Init_FreeType(libraryPtr) != 0) {
                throw new RuntimeException("Failed to initialize library");
            }

            library = libraryPtr.get(0);

            PointerBuffer facePtr = stack.mallocPointer(1);

            if (FT_New_Memory_Face(
                    library,
                    fontBuffer,
                    0,
                    facePtr
            ) != 0) {
                throw new RuntimeException("Failed to initialize face");
            }

            face = FT_Face.create(facePtr.get(0));

            FT_Set_Pixel_Sizes(face, 0, size);

            loadGlyphs();
        }
    }

    private void loadGlyphs() {

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        for (char c = 32; c < 127; c++) {

            if (FT_Load_Char(face, c, FT_LOAD_RENDER) != 0) {
                continue;
            }

            FT_GlyphSlot slot = face.glyph();
            FT_Bitmap bitmap = slot.bitmap();

            int texture = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, texture);

            glTexImage2D(
                    GL_TEXTURE_2D,
                    0,
                    GL_ALPHA,
                    bitmap.width(),
                    bitmap.rows(),
                    0,
                    GL_ALPHA,
                    GL_UNSIGNED_BYTE,
                    bitmap.buffer(bitmap.width() * bitmap.rows())
            );

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            Glyph glyph = new Glyph(
                    texture,
                    bitmap.width(),
                    bitmap.rows(),
                    slot.bitmap_left(),
                    slot.bitmap_top(),
                    slot.advance().x()
            );

            glyphs.put(c, glyph);
        }
    }

    public Glyph getGlyph(char c) {
        return glyphs.get(c);
    }

    public GLFont setSize(int size) {
        return new GLFont(path, size);
    }

    @Override
    public void dispose() {

        for (Glyph glyph : glyphs.values()) {
            glDeleteTextures(glyph.texture);
        }

        FT_Done_Face(face);
        FT_Done_FreeType(library);
    }
}
