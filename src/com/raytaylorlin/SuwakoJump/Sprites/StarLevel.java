package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;

public class StarLevel extends JSprite {
    private int level = 0;
    private boolean isBig;

    public StarLevel(Bitmap image, Point drawPosition, int level, boolean isBig) {
        super(image, drawPosition);
        this.cutPosition = new Point(0, level * image.getHeight() / 4);
        this.isBig = isBig;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(this.cutPosition.x, this.cutPosition.y,
                this.cutPosition.x + this.imageSize.x, this.cutPosition.y + this.imageSize.y / 4);
        Rect dstRect;
        if (this.isBig) {
            dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                    this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y / 4);
        } else {
            dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                    this.drawPosition.x + (int) (this.imageSize.x * 0.7),
                    this.drawPosition.y + (int) (this.imageSize.y * 0.7) / 4);
        }
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

}
