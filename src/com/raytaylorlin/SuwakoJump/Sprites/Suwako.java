package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.GameLogic;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Lib.SoundHelper;
import com.raytaylorlin.SuwakoJump.Sprites.Board.Board;
import com.raytaylorlin.SuwakoJump.Sprites.Board.BrokenBoard;
import com.raytaylorlin.SuwakoJump.Sprites.Board.VanishBoard;
import com.raytaylorlin.SuwakoJump.Sprites.Item.Item;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

import java.util.HashMap;

public class Suwako extends JSprite {
    private final int STATUS_UP = 0;
    private final int STATUS_DOWN = 1;
    private final int STATUS_ATTACK = 2;
    private final int STATUS_WIN = 3;

    private final int FIRST_V = (int) ((20 / 800.0) *
            SuwakoJumpActivity.DISPLAY_HEIGHT);
    private final int MOVE_STEP_Y = 4;
    private final int FRAME_Y_BUFFER = 2;

    private HashMap<String, Bitmap> bmpHashMap;
    private GameLogic gameLogic;

    public boolean isOverLine;
    public int currentFrameX = 0;
    public boolean isDead;

    private int status = STATUS_UP;
    private int moveStepX = 0;
    private boolean isUp;
    private boolean isSuperUp = false;
    private int frameYBuffer = 0;
    private int fallCount = 1;

    public Suwako(HashMap<String, Bitmap> bmpHashMap,
                  Point drawPosition, Point imageSize, GameLogic gameLogic) {
        super(bmpHashMap.get("suwako_jump"), drawPosition, imageSize);
        this.bmpHashMap = bmpHashMap;
        this.gameLogic = gameLogic;
        this.sheetSize = new Point(20, 2);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect, dstRect;
        if (this.status == STATUS_UP || this.status == STATUS_DOWN) {
            srcRect = new Rect(this.cutPosition.x + this.currentFrameX * this.imageSize.x,
                    this.cutPosition.y, this.cutPosition.x + (this.currentFrameX + 1) * this.imageSize.x, this.cutPosition.y + this.imageSize.y);
            dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                    this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y);
        } else {
            srcRect = new Rect(this.cutPosition.x, this.cutPosition.y,
                    this.cutPosition.x + this.imageSize.x, this.cutPosition.y + this.imageSize.y);
            dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                    this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y);
        }
        canvas.drawBitmap(this.drawImage, srcRect, dstRect, paint);
    }

    @Override
    public void update() {
        switch (this.status) {
            //上升状态的逻辑更新
            case STATUS_UP:
                if (this.currentFrameX < this.sheetSize.x / 2) {
                    this.isUp = true;
                    if (!this.isOverLine) {
                        if (this.isSuperUp) {
                            this.drawPosition.y -= FIRST_V * 6 - (this.currentFrameX % (this.sheetSize.x / 2));
                        } else {
                            this.drawPosition.y -= FIRST_V - (this.currentFrameX % (this.sheetSize.x / 2));
                        }
                        this.frameYBuffer++;
                        if (this.frameYBuffer == FRAME_Y_BUFFER) {
                            this.currentFrameX++;
                            this.frameYBuffer = 0;
                        }
                    } else {
                        this.currentFrameX += 2;
                    }
                } else {
                    this.setStatus(STATUS_DOWN);
                }
                break;
            case STATUS_DOWN:
                this.drawPosition.y += MOVE_STEP_Y * fallCount * 0.3;
                this.currentFrameX = this.sheetSize.x / 2 + 1;
                fallCount++;
                break;
            case STATUS_WIN:
                break;
            case STATUS_ATTACK:
                break;
        }
        if (this.status != STATUS_WIN) {
            //判断是否过刷新线
            int gameW = SuwakoJumpActivity.DISPLAY_WIDTH;
            int gameH = SuwakoJumpActivity.DISPLAY_HEIGHT;
            int gameSB = 0;
            this.isOverLine = this.drawPosition.y < gameH / 2 + gameSB;
            //判断是否过左右边界，可穿越到另一边
            if (this.drawPosition.x + this.imageSize.x / 2 >= gameW) {
                this.drawPosition.x = -this.imageSize.x / 2;
            } else if (this.drawPosition.x + this.imageSize.x / 2 < 0) {
                this.drawPosition.x = gameW - this.imageSize.x / 2;
            }
            //判断是否死亡
            if (this.drawPosition.y > gameH + gameSB) {
                this.isDead = true;
            }
            //判断左右倾斜（重力感应器）
            if (this.moveStepX <= 0) {
                this.cutPosition.y = this.imageSize.y;
            } else {
                this.cutPosition.y = 0;
            }
            this.drawPosition.x += this.moveStepX;
        }
    }

    /*
     * 根据重力感应器X轴的值设置水平位移量
     * @param sensorX 重力感应器X轴参量
     */
    public void setMoveStepX(float sensorX) {
        if (this.status != STATUS_WIN) {
            this.moveStepX = -((int) sensorX) * 6;
        }
    }

    /*
     * 设置超级跳跃（弹簧效果）
     */
    public void setSuperUp() {
        this.isSuperUp = true;
    }


    public void attack(Point point) {
        //TODO: 攻击
    }

    /*
     * 检测落地是否碰到板子
     * @param board 做碰撞检测的板子
     * @return 是否碰到板子
     */
    public boolean checkLanding(Board board) {
        //下降过程中做与板子的碰撞检测
        Point boardPos = board.getPosition();
        Point boardSize = board.getSize();
        int footLine = this.drawPosition.y + this.imageSize.y -
                (int) (this.imageSize.y * 0.22);
        if (!this.isUp
                && footLine >= boardPos.y - boardSize.y
                && footLine <= boardPos.y + boardSize.y
                && this.drawPosition.x + this.imageSize.x * 0.75 >= boardPos.x
                && this.drawPosition.x + this.imageSize.x * 0.25 <= boardPos.x + boardSize.x) {
            //获取碰撞的板子类型
            String boardType = board.getClass().getName();
            if (boardType.contains("BrokenBoard")) {
                ((BrokenBoard) board).setBroken();
                SoundHelper.play(SoundHelper.SoundMap.get("broken"), false);
                return false;
            } else if (boardType.contains("VanishBoard")) {
                ((VanishBoard) board).vanish();
                this.checkItem(board);
            } else if (boardType.contains("FlagBoard")) {
                this.gameLogic.notifyStageClear();
                this.setStatus(STATUS_WIN);
                this.drawPosition.y = boardPos.y - this.drawImage.getHeight();
                return true;
            }
            //检测板子上的道具
            this.checkItem(board);
            //重新设置主角状态
            this.setStatus(STATUS_UP);
            this.drawPosition.y = boardPos.y - this.imageSize.y;
            SoundHelper.play(SoundHelper.SoundMap.get("jump2"), false);
            return true;
        } else {
            return false;
        }
    }

    private void checkItem(Board board) {
        Item item = board.getItem();
        if (item == null) {
            return;
        }
        Rect suwakoRect = this.getRect();
        Rect itemRect = item.getRect();
        if (!this.isUp && suwakoRect.intersect(itemRect)) {
            item.gainEffect(this);
            SoundHelper.play(SoundHelper.SoundMap.get("jump"), false);
        }
    }

    /*
     * 获取总高度上升的距离
     */
    public int getDuration() {
        int gameH = SuwakoJumpActivity.DISPLAY_HEIGHT;
        int gameSB = 0;
        int d = 0;

        if (this.drawPosition.y < gameH / 2 + gameSB) {
            d += gameH / 2 + gameSB - this.drawPosition.y;
        }
        for (int i = this.sheetSize.x / 2 - 1; i >= this.currentFrameX; i--) {
            d += (FIRST_V - i) * FRAME_Y_BUFFER;
        }
        return d;
    }

    /*
     * 获取坠落时间
     */
    public int getFallingTime() {
        int f = (this.sheetSize.x / 2 - this.currentFrameX) * (FRAME_Y_BUFFER - 1) / 2;
        if (f == 0) {
            f = 1;
        }
        return f;
    }

    /*
     * 设置状态
     * @param statusIndex 状态编码（枚举值，参见字段定义）
     */
    private void setStatus(int statusIndex) {
        this.status = statusIndex;
        switch (statusIndex) {
            case STATUS_UP:
                this.currentFrameX = 0;
                this.fallCount = 1;
                this.isUp = true;
                break;
            case STATUS_DOWN:
                this.isUp = false;
                this.isSuperUp = false;
                break;
            case STATUS_WIN:
                this.drawImage = this.bmpHashMap.get("suwako_win");
                this.cutPosition = new Point();
                break;
        }
    }
}
