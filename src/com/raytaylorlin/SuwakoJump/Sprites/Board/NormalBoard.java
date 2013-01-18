package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.Bitmap;
import android.graphics.Point;

public class NormalBoard extends Board {
    public NormalBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_TYPE_NORMAL);
    }
}

