package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.*;

public class BrokenBoard extends Board {
    private static final int actionTotalCount = 3;
    private boolean isBroken;
    private int actionCount;

    public BrokenBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_TYPE_BROKEN);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(this.cutPosition.x, this.cutPosition.y,
                this.cutPosition.x + this.imageSize.x, this.cutPosition.y + this.imageSize.y / 4);
        Rect dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y / 4);
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

    @Override
    public void doAction() {
        if (this.isBroken) {
            if (this.actionCount < actionTotalCount) {
//                this.drawPosition.y += 2;
                this.cutPosition.y += this.imageSize.y / 4;
                this.actionCount++;
            } else {
                this.toDisposed = true;
            }
        }
    }

    @Override
    public Point getSize() {
        return new Point(this.imageSize.x, this.imageSize.y / 4);
    }

    public void setBroken() {
        this.actionCount = 0;
        this.isBroken = true;
    }
}
