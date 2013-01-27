package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.Bitmap;
import android.graphics.Point;

//import SuwakoJump.Lib.RandomHelper;
//import SuwakoJump.Frame.MainGame;
import com.raytaylorlin.SuwakoJump.Lib.RandomHelper;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class MovingBoard extends Board {
    private static int X_MOVE_RANGE;
    private static int Y_MOVE_RANGE;
    private boolean isOneSideMoving = false;
    private boolean isVerticalMoving = false;
    private int currentMovingLocation;
    private int velocity = RandomHelper.getRandom(4,8);

    public MovingBoard(Bitmap image, Point drawPosition, boolean isVerticalMoving) {
        super(image, drawPosition, BOARD_TYPE_MOVING);
        X_MOVE_RANGE = SuwakoJumpActivity.DISPLAY_WIDTH - this.imageSize.x;
        Y_MOVE_RANGE = SuwakoJumpActivity.DISPLAY_HEIGHT;
        this.isVerticalMoving = isVerticalMoving;
        if (isVerticalMoving) {
            this.currentMovingLocation = RandomHelper.getRandom(Y_MOVE_RANGE);
        } else {
            this.currentMovingLocation = this.drawPosition.x;
        }
        this.isOneSideMoving = RandomHelper.getRandom() < 0.5;
    }

    @Override
    public void doAction() {
        if (this.currentMovingLocation <= 0 ||
                (this.isVerticalMoving && this.currentMovingLocation >= Y_MOVE_RANGE) ||
                (!this.isVerticalMoving && this.currentMovingLocation >= X_MOVE_RANGE)) {
            this.isOneSideMoving = !this.isOneSideMoving;
        }
        int delta = velocity * (this.isOneSideMoving ? 1 : -1);
        this.currentMovingLocation += delta;
        //垂直移动
        if (this.isVerticalMoving) {
            this.drawPosition.y += delta;
        }
        //水平移动
        else {
            this.drawPosition.x += delta;
        }
    }
}
