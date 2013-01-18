package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.Bitmap;
import android.graphics.Point;

public class VanishBoard extends Board {
    public VanishBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_TYPE_VANISH);
    }

    @Override
    public void doAction() {

    }

    public void vanish() {
        this.drawPosition.x=-100;
        this.toDisposed = true;
    }
}
