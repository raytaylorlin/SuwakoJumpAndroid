package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;
//import SuwakoJump.Frame.MainGame;
//import SuwakoJump.Frame.CanvasPanel;
//import SuwakoJump.Lib.SoundEffect;
//import SuwakoJump.Lib.SoundHelper;

public class Suwako extends JSprite {
    private final int STATUS_UP = 0;
    private final int STATUS_DOWN = 1;
    private final int STATUS_ATTACK = 2;

    private final int FIRST_V = 20;
    private final int MOVE_STEP_X = 10;
    private final int MOVE_STEP_Y = 4;
    private final int FRAME_Y_BUFFER = 2;

//    private SoundEffect seJump=new SoundEffect("sound/jump.wav");

    public boolean isOverLine;
    public int currentFrameX = 0;
    public boolean isDead;

    private int status = STATUS_UP;
    private int moveStepX = 0;
    private boolean isUp;
    private boolean isSuperUp = true;
    private int frameYBuffer = 0;
    private int fallCount = 1;

    public Suwako(Bitmap image, Point drawPosition, Point imageSize) {
        super(image, drawPosition, imageSize);
        this.sheetSize = new Point(20, 2);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect srcRect = new Rect(this.cutPosition.x + this.currentFrameX * this.imageSize.x,
                this.cutPosition.y, this.cutPosition.x + (this.currentFrameX + 1) * this.imageSize.x, this.cutPosition.y + this.imageSize.y);
        Rect dstRect = new Rect(this.drawPosition.x, this.drawPosition.y,
                this.drawPosition.x + this.imageSize.x, this.drawPosition.y + this.imageSize.y);
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
                            this.drawPosition.y -= FIRST_V * 5 - (this.currentFrameX % (this.sheetSize.x / 2));
                            this.isSuperUp = false;
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
            case STATUS_ATTACK:
                break;
        }
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

    public void setSensorX(float sensorX) {
        this.moveStepX = -((int) sensorX) * 6;
    }

    public boolean checkLanding(Board board) {
        //下降过程中做与板子的碰撞检测
        Point boardPos = board.getPosition();
        Point boardSize = board.getSize();
        if (!this.isUp
                && this.drawPosition.y + this.imageSize.y - 34 >= boardPos.y - boardSize.y
                && this.drawPosition.y + this.imageSize.y - 34 <= boardPos.y + boardSize.y * 1.5
                && this.drawPosition.x + this.imageSize.x / 2 + 15 >= boardPos.x
                && this.drawPosition.x + this.imageSize.x / 2 - 15 <= boardPos.x + boardSize.x) {
            //获取碰撞的板子类型
            String fullType = board.getClass().getName();

//            String boardType = (fullType.substring(fullType.lastIndexOf(".") + 1)).toString();
            if (fullType.contains("BrokenBoard")) {
                ((BrokenBoard) board).setBroken();
                return false;
            } else if (fullType.contains("VanishBoard")) {
                ((VanishBoard) board).vanish();
            }
            this.setStatus(STATUS_UP);
            this.drawPosition.y = boardPos.y - this.imageSize.y;
//            SoundHelper.play(this.seJump);
            return true;
        } else {
            return false;
        }
    }

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

    public int getFallingTime() {
        int f = (this.sheetSize.x / 2 - this.currentFrameX) * (FRAME_Y_BUFFER - 1) / 2;
        if (f == 0) {
            f = 1;
        }
        return f;
    }

    private void setStatus(int statusIndex) {
        this.status = statusIndex;
        switch (statusIndex){
            case STATUS_UP:
                this.currentFrameX = 0;
                this.fallCount = 1;
                this.isUp = true;
                break;
            case STATUS_DOWN:
                this.isUp = false;
                break;

        }
    }
}
