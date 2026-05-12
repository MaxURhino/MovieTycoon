package net.max_rhino.gl2d_core.engine.elements.sprite;

import net.max_rhino.gl2d_core.engine.DrawableDisposable;

import java.util.function.Supplier;

public class ScrollingSprite implements DrawableDisposable {
    private final Supplier<Sprite> sprite;
    private final int startX, startY;
    private int xDelay, yDelay, oldX, oldY, xCopies, yCopies, frame;

    public ScrollingSprite(Supplier<Sprite> sprite, int startX, int startY) {
        this.sprite = sprite;
        this.startX = startX;
        this.startY = startY;

        this.oldX = startX;
        this.oldY = startY;
    }

    @Override
    public void dispose() {
        sprite.get().dispose();
    }

    public ScrollingSprite setxDelay(int xDelay) {
        this.xDelay = xDelay;
        return this;
    }

    public ScrollingSprite setyDelay(int yDelay) {
        this.yDelay = yDelay;
        return this;
    }

    public int getxDelay() {
        return xDelay;
    }

    public int getyDelay() {
        return yDelay;
    }

    public int getFrame() {
        return frame;
    }

    public ScrollingSprite setFrame(int frame) {
        this.frame = frame;
        return this;
    }

    public ScrollingSprite setxCopies(int xCopies) {
        this.xCopies = xCopies;
        return this;
    }

    public ScrollingSprite setyCopies(int yCopies) {
        this.yCopies = yCopies;
        return this;
    }

    public int getxCopies() {
        return xCopies;
    }

    public int getyCopies() {
        return yCopies;
    }

    @Override
    public ScrollingSprite render(double dt) {
        if (dt == 0) return this;

        int calculatedDT = (dt != 0) ? (int)(0.01 / dt) : 1;
        if (calculatedDT == 0) calculatedDT = 1;

        this.sprite.get().setPosition(this.startX, this.startY);

        int xStep = xDelay * calculatedDT;
        int yStep = yDelay * calculatedDT;

        if (xStep > 0 && this.frame % xStep == 0) {
            this.oldX = -((int)((float) frame / xStep) % this.sprite.get().getTexture().width());
        }

        if (yStep > 0 && this.frame % yStep == 0) {
            this.oldY = -((int)((float) frame / yStep) % this.sprite.get().getTexture().height());
        }

        this.sprite.get().setX(this.oldX);

        for (int i = 0; i < xCopies + 1; i++) {
            this.sprite.get().setY(this.oldY);

            for (int j = 0; j < yCopies + 1; j++) {
                this.sprite.get().render(dt);
                this.sprite.get().addY(this.sprite.get().getTexture().height());
            }

            this.sprite.get().addX(this.sprite.get().getTexture().width());
        }

        return this;
    }
}
