package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;

public class ScoreNumber extends JSprite {
    private static int NUMBER_WIDTH = 0;
    private static int NUMBER_HEIGHT = 0;

    private int showNumber, bitIndex;

    public ScoreNumber(Bitmap image, Point drawPosition, int bitIndex) {
        super(image, drawPosition, new Point(image.getWidth(), image.getHeight()),
                new Point(10, 1), new Point(0, 0));
        NUMBER_WIDTH = image.getWidth() / 10;
        NUMBER_HEIGHT = image.getHeight();
        this.bitIndex = bitIndex;
    }

    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(NUMBER_WIDTH * this.showNumber, this.cutPosition.y,
                NUMBER_WIDTH * (this.showNumber + 1), this.cutPosition.y + NUMBER_HEIGHT);
        Rect dstRect = new Rect(this.drawPosition.x + NUMBER_WIDTH * this.bitIndex, this.drawPosition.y,
                this.drawPosition.x + NUMBER_WIDTH * (this.bitIndex + 1), this.drawPosition.y + NUMBER_HEIGHT);
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

    public void update() {
//        if (this.showNumber != this.realNumber) {
//            this.cutPosition.x = this.showNumber * NUMBER_WIDTH;
//        }
    }

    public void setShowNumber(int showNumber) {
        this.showNumber = showNumber;
    }

    public void setBitIndex(int bitIndex) {
        this.bitIndex = bitIndex;
    }

}
