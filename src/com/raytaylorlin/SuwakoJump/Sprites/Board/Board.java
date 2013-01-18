package com.raytaylorlin.SuwakoJump.Sprites.Board;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Sprites.Item.Item;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public abstract class Board extends JSprite {
    //板子类别常量
    protected static final int BOARD_TYPE_NORMAL = 0;
    protected static final int BOARD_TYPE_BROKEN = 1;
    protected static final int BOARD_TYPE_MOVING = 2;
    protected static final int BOARD_TYPE_VANISH = 3;
    //上升速度
    protected static final int UP_MOVING_V = 40;

    //标记板子是否下降
    public boolean isDownMoving;
    //标记板子是否上升
    public boolean isUpMoving;
    //板子上的道具
    protected Item item;
    protected int itemOffsetX, itemOffsetY;
    protected int fallingCount, fallingDuration;
    protected int fallingTotalCount = 1;

    public Board(Bitmap image, Point drawPosition, int boardType) {
        super(image, drawPosition, new Point(image.getWidth(), image.getWidth() / 4),
                new Point(1, 1), new Point(0, image.getWidth() / 4 * boardType));
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
        if (this.item != null) {
            this.item.draw(canvas, paint);
        }
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
        this.doAction();
        //如果板子上有道具，则更新道具的逻辑和位置
        if (this.item != null) {
            this.item.update();
            this.item.setPosition(new Point(
                    this.drawPosition.x + this.itemOffsetX,
                    this.drawPosition.y + this.itemOffsetY));
        }
        if (this.drawPosition.y > SuwakoJumpActivity.DISPLAY_HEIGHT) {
//                + MainGame.GAME_SCORE_BAR_HEIGHT) {
            this.toDisposed = true;
        }
    }

    /*
     * 板子作用生效（供子类覆写）
     */
    public void doAction() {
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
        if (this.item != null) {
            this.item.setFallingDown(duration, fallingTime);
        }
    }

    public void setItem(Item item) {
        this.item = item;
        this.itemOffsetX = item.getPosition().x - this.drawPosition.x;
        this.itemOffsetY = item.getPosition().y - this.drawPosition.y;
    }

    public Item getItem() {
        return this.item;
    }
}
