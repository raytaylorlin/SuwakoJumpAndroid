package com.raytaylorlin.SuwakoJump.Lib;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class JSprite {
    protected Bitmap drawImage;
    protected Point drawPosition, cutPosition;
    protected Point sheetSize, imageSize;
    protected boolean toDisposed;

    public JSprite(Bitmap image, Point drawPosition) {
        this(image, drawPosition, new Point(image.getWidth(), image.getHeight()),
                new Point(1, 1), new Point(0, 0));
    }

    public JSprite(Bitmap image, Point drawPosition, Point imageSize) {
        this(image, drawPosition, imageSize, new Point(1, 1), new Point(0, 0));
    }

    public JSprite(Bitmap image, Point drawPosition, Point imageSize, Point sheetSize, Point cutPosition) {
        this.drawImage = image;
        this.drawPosition = drawPosition;
        this.imageSize = imageSize;
        this.sheetSize = sheetSize;
        this.cutPosition = cutPosition;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.drawImage,
                this.drawPosition.x, this.drawPosition.y, paint);
    }

    public void update() {
    }

    public Rect getRect() {
        return new Rect(this.drawPosition.x, this.drawPosition.y,
                this.drawPosition.x + this.imageSize.x,
                this.drawPosition.y + this.imageSize.y);
    }


    public Point getPosition() {
        return this.drawPosition;
    }

    public Point getSize() {
        return this.imageSize;
    }

    public boolean isToDisposed() {
        return this.toDisposed;
    }
}
