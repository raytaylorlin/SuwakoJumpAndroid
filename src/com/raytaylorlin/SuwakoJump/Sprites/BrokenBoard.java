package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.Bitmap;
import android.graphics.Point;

public class BrokenBoard extends Board {
    private static final int actionTotalCount = 3;
    private boolean isBroken;
    private int actionCount;

    public BrokenBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_TYPE_BROKEN);
    }

    @Override
    public void doAction() {
        if (this.isBroken) {
            if (this.actionCount < actionTotalCount) {
                this.drawPosition.y += 2;
                this.actionCount++;
            } else {
                this.toDisposed = true;
            }
        }
    }

    public void setBroken() {
        this.actionCount = 0;
        this.isBroken = true;
    }
}
