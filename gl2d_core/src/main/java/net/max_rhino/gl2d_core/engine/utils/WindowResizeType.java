package net.max_rhino.gl2d_core.engine.utils;

import java.awt.*;

public enum WindowResizeType {
    STRETCH,
    ASPECT;

    private Color borderColor = Color.BLACK;
    private boolean fullscreen = false;

    public Color getBorderColor() {
        return borderColor;
    }

    public WindowResizeType setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public WindowResizeType setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }
}
