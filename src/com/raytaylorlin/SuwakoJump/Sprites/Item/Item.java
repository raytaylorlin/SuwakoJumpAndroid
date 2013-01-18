package com.raytaylorlin.SuwakoJump.Sprites.Item;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Sprites.Suwako;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public abstract class Item extends JSprite {
    protected static final int ITEM_TYPE_NORMAL = 0;
    //    protected static final int BOARD_TYPE_BROKEN = 1;
//    protected static final int BOARD_TYPE_MOVING = 2;
//    protected static final int BOARD_TYPE_VANISH = 3;
    protected static final int UP_MOVING_V = 40;

    public boolean isDownMoving, isUpMoving;
    protected int fallingCount, fallingDuration;
    protected int fallingTotalCount = 1;

    public Item(Bitmap image, Point drawPosition) {
        super(image, drawPosition);
    }

    /*
     * 绘制精灵
     */
    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(this.cutPosition.x, this.cutPosition.y,
                this.cutPosition.x + this.imageSize.x, this.cutPosition.y + this.imageSize.y);
        Rect dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y);
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

    /*
     * 更新逻辑
     */
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
        if (this.drawPosition.y > SuwakoJumpActivity.DISPLAY_HEIGHT) {
//                + MainGame.GAME_SCORE_BAR_HEIGHT) {
            this.toDisposed = true;
        }
    }

    /*
     * 设置快速下降效果，用于模拟主角上升
     * @param duration 快速下降距离
     * @param fallingTime 快速下降时间
     */
    public void setFallingDown(int duration, int fallingTime) {
        this.isDownMoving = true;
        this.fallingCount = 0;
        this.fallingDuration = duration;
        this.fallingTotalCount = fallingTime * 3;
    }

    /*
     * 获取道具效果（供子类覆写）
     */
    public void gainEffect(Suwako suwako) {
//        if (this.suwako == null) {
//            return;
//        }
    }
}
