package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class Board extends JSprite {
    protected static final int BOARD_TYPE_NORMAL = 0;
    protected static final int BOARD_TYPE_BROKEN = 1;
    protected static final int BOARD_TYPE_MOVING = 2;
    protected static final int BOARD_TYPE_VANISH = 3;
    protected static final int UP_MOVING_V = 40;

    public boolean isDownMoving, isUpMoving;
    protected int fallingCount, fallingDuration;
    protected int fallingTotalCount = 1;

    public Board(Bitmap image, Point drawPosition, int boardType) {
        super(image, drawPosition, new Point(image.getWidth(), image.getWidth() / 4),
                new Point(1, 1), new Point(0, image.getWidth() / 4 * boardType));
    }

    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(this.cutPosition.x, this.cutPosition.y,
                this.cutPosition.x + this.imageSize.x, this.cutPosition.y + this.imageSize.y);
        Rect dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y);
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

    public void update() {
        if (this.isDownMoving) {
            this.drawPosition.y += this.fallingDuration / this.fallingTotalCount;
            this.fallingCount++;
            if (this.fallingCount == this.fallingTotalCount) {
                this.isDownMoving = false;
            }
        } else if (this.isUpMoving) {
            this.drawPosition.y -= UP_MOVING_V;
            if (this.drawPosition.y < -200) {
                this.isUpMoving = false;
                this.toDisposed = true;
            }
        }
        this.doAction();
        if (this.drawPosition.y > SuwakoJumpActivity.DISPLAY_HEIGHT) {
//                + MainGame.GAME_SCORE_BAR_HEIGHT) {
            this.toDisposed = true;
        }
    }

    public void doAction() {
    }

    public void setFallingDown(int duration, int fallingTime) {
        this.isDownMoving = true;
        this.fallingCount = 0;
        this.fallingDuration = duration;
        this.fallingTotalCount = fallingTime * 3;
    }
}
