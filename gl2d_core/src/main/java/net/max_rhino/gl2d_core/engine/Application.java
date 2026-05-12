package net.max_rhino.gl2d_core.engine;

import net.max_rhino.gl2d_core.engine.math.Rect2i;
import net.max_rhino.gl2d_core.engine.utils.WindowResizeType;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class Application implements Runnable {
    protected int width, height;
    public final int definedWidth, definedHeight;
    protected String title;
    protected long window;

    private WindowResizeType resizeType;

    private List<DrawableDisposable> drawables = new ArrayList<>();
    private Rect2i vp;
    private Color clearColor = Color.BLACK;

    public Application(int width, int height, String title) {
        this(width, height, title, WindowResizeType.STRETCH);
    }

    public Vector2d getCurrentWindowScale() {
        return new Vector2d((double)width / (double)definedWidth, (double)height / (double)definedHeight);
    }

    public Application(int width, int height, String title, WindowResizeType resizeType) {
        this.width  = width;  this.definedWidth  = width;
        this.height = height; this.definedHeight = height;
        this.title = title;
        this.resizeType = resizeType;
    }

    public Application setResizeType(WindowResizeType resizeType) {
        this.resizeType = resizeType;
        return this;
    }

    public WindowResizeType getResizeType() {
        return resizeType;
    }

    public Application addDrawable(DrawableDisposable drawable) {
        drawables.add(drawable);
        return this;
    }

    @Override
    public void run() {
        init();
        loop();

        for (DrawableDisposable drawable : drawables) {
            drawable.dispose();
        }

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void resizeWindow(boolean initial) {
        WindowResizeType operatedResizeType = initial ? WindowResizeType.STRETCH : resizeType;

        vp = new Rect2i(0, 0, width, height);

        if (operatedResizeType == WindowResizeType.ASPECT) {
            float targetAspect = (float) (this.definedWidth) / (float) (this.definedHeight);
            float windowAspect = (float) width / height;

            if (windowAspect > targetAspect) {
                vp.size().x = (int) (height * targetAspect);
                vp.pos().x = (width - vp.size().x) / 2;
            } else {
                vp.size().y = (int) (width / targetAspect);
                vp.pos().y = (height - vp.size().y) / 2;
            }
        }

        glViewport(vp.pos().x, vp.pos().y, vp.size().x, vp.size().y);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.definedWidth, this.definedHeight, 0, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void resizeWindow() {
        this.resizeWindow(false);
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.window = glfwCreateWindow(this.width, this.height, this.title, 0, 0);

        vp = new Rect2i(0, 0, this.width, this.height);

        if (this.window == 0) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);
        if (vidMode != null) {
            glfwSetWindowPos(this.window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }

        glfwSetFramebufferSizeCallback(this.window, (_, width, height) -> {
            this.width = width;
            this.height = height;

            resizeWindow();
        });

        glfwSetCursorPosCallback(this.window, (_, x, y) -> {
            Input.initMousePos(x, y);
        });

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);

        GL.createCapabilities();

        Input.init(() -> this);

        resizeWindow(true);

        onCreate();
    }

    public Rect2i getScaledScissorPos(Rect2i rect) {
        float widthScale = (float)this.vp.size().x / this.definedWidth;
        float heightScale = (float)this.vp.size().y / this.definedHeight;
        return new Rect2i(this.vp.pos().x + rect.pos().x, this.vp.pos().y + rect.pos().y, (int)(widthScale * rect.size().x), (int)(heightScale * rect.size().y));
    }

    private void loop() {
        while (!glfwWindowShouldClose(this.window)) {
            glfwPollEvents();

            this.render();

            glfwSwapBuffers(this.window);
        }
    }

    protected void setClearColor(Color color) {
        this.clearColor = color;
        glClearColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    protected void clear() {
        Color temp = this.clearColor;

        setClearColor(resizeType.getBorderColor());

        glClear(GL_COLOR_BUFFER_BIT);

        setClearColor(temp);

        glEnable(GL_SCISSOR_TEST);
        glScissor(vp.pos().x, vp.pos().y, vp.size().x, vp.size().y);

        glClear(GL_COLOR_BUFFER_BIT);

        glDisable(GL_SCISSOR_TEST);
    }

    protected abstract void onCreate();

    protected void render() {
        for (DrawableDisposable drawable : drawables) {
            drawable.render(0);
        }
    }
}
