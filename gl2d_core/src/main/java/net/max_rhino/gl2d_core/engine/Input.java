package net.max_rhino.gl2d_core.engine;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class Input {
    private static Supplier<Application> app;
    private static double mouseX, mouseY;

    public static void init(Supplier<Application> application) {
        app = application;
    }

    public static void initMousePos(double x, double y) {
        Vector2d windowScale = app.get().getCurrentWindowScale();
        mouseX = x / windowScale.x;
        mouseY = y / windowScale.y;

        if (mouseX < 0) {
            mouseX = 0;
        }
        if (mouseX > app.get().width) {
            mouseX = app.get().width;
        }

        if (mouseY < 0) {
            mouseY = 0;
        }
        if (mouseY > app.get().height) {
            mouseY = app.get().height;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return GLFW.glfwGetKey(app.get().window, keyCode) == GLFW.GLFW_PRESS;
    }

    public static boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(app.get().window, button) == GLFW.GLFW_PRESS;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
