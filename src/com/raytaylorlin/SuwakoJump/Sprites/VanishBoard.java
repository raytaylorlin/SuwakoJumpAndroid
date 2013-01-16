package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.Bitmap;
import android.graphics.Point;

public class VanishBoard extends Board {
    public VanishBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_FRAME_SIZE, new Point(1, 1), new Point(400, 45));
    }

    @Override
    public void doAction() {

    }

    public void vanish() {
        this.drawPosition.x=-100;
        this.toDisposed = true;
    }
}
