package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.GameLogic;

public class FlagBoard extends Board {
    private Point boardSize;

    public FlagBoard(Bitmap image, Point drawPosition) {
        super(image, drawPosition, BOARD_TYPE_FLAG);
        this.boardSize = new Point(image.getWidth(), image.getWidth() / 4);
    }

    @Override
    public Point getPosition() {
        return new Point(this.drawPosition.x,
                this.drawPosition.y + (int) (this.imageSize.y * 0.777));
    }

    @Override
    public Point getSize() {
        return this.boardSize;
    }
}