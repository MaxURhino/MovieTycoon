package net.max_rhino.gl2d_core.engine.elements.font;

import static org.lwjgl.opengl.GL11.*;

public class TextRenderer {
    public static void drawText(
            GLFont font,
            String text,
            float x,
            float y
    ) {

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float currentX = x;

        for (char c : text.toCharArray()) {

            Glyph glyph = font.getGlyph(c);

            if (glyph == null) {
                continue;
            }

            float xpos = currentX + glyph.bearingX;
            float ypos = (y - glyph.bearingY) + font.size;

            float w = glyph.width;
            float h = glyph.height;

            glBindTexture(GL_TEXTURE_2D, glyph.texture);

            glBegin(GL_QUADS);

            glTexCoord2f(0f, 0f);
            glVertex2f(xpos, ypos);

            glTexCoord2f(1f, 0f);
            glVertex2f(xpos + w, ypos);

            glTexCoord2f(1f, 1f);
            glVertex2f(xpos + w, ypos + h);

            glTexCoord2f(0f, 1f);
            glVertex2f(xpos, ypos + h);

            glEnd();

            currentX += (glyph.advance >> 6);
        }

        glDisable(GL_BLEND);
    }

    public static float getTextWidth(
            GLFont font,
            String text
    ) {

        float width = 0;

        for (char c : text.toCharArray()) {

            Glyph glyph = font.getGlyph(c);

            if (glyph == null)
                continue;

            width += (glyph.advance >> 6);
        }

        return width;
    }
}
