package net.max_rhino.movietycoon.game;

import net.max_rhino.movietycoon.PathsUtil;
import net.max_rhino.gl2d_core.engine.Application;
import net.max_rhino.gl2d_core.engine.elements.font.GLFont;
import net.max_rhino.gl2d_core.engine.utils.WindowResizeType;
import net.max_rhino.movietycoon.game.elements.UI;
import org.lwjgl.opengl.GL11;

public class MainApp extends Application {
    public static GLFont RUBIK_BOLD;

    public static GLFont RUBIK_MEDIUM;

    public MainApp() {
        super(1152, 648, "Movie Tycoon", WindowResizeType.ASPECT);
    }

    @Override
    public void onCreate() {
        //this.setClearColor(Color.RED);
        UI ui = new UI();
        this.addDrawable(ui);

        RUBIK_BOLD = new GLFont(
                PathsUtil.font("Rubik-Bold").toString(),
                32
        );

        RUBIK_MEDIUM = new GLFont(
                PathsUtil.font("Rubik-Medium").toString(),
                32
        );
    }

    @Override
    protected void render(double dt) {
        this.clear();

        super.render(dt);

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
}
