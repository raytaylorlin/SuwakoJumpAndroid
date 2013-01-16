package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.Bitmap;
import android.graphics.Point;

public class NormalBoard extends Board {
    public NormalBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_FRAME_SIZE, new Point(1, 1), new Point(0, 0));
    }

}

