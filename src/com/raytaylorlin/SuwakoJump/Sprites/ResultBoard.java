package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

import java.util.Date;

public class ResultBoard extends JSprite {
    private StarLevel starLevel;
    private Bitmap bmpStarLevel;
    private boolean canDrawText = false;
    private int score, boardNum;
    private long intervalTime;

    public ResultBoard(Bitmap bmpBoard, Bitmap bmpStarLevel,
                       Point drawPosition, int score, long intervalTime, int boardNum) {
        super(bmpBoard, drawPosition);
        this.bmpStarLevel = bmpStarLevel;
        this.score = score;
        this.intervalTime = intervalTime;
        this.boardNum = boardNum;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        paint.setARGB(255, 129, 129, 0);
        if (this.canDrawText) {
            canvas.drawText(String.valueOf(this.score),
                    (int) (this.drawPosition.x + this.imageSize.x * 0.5583),
                    (int) (this.drawPosition.y + this.imageSize.y * 0.3557), paint);
            String strTime = String.valueOf(
                    new Date(this.intervalTime).getTime() / 100 / 10.0);
            strTime += "s";
            canvas.drawText(String.valueOf(strTime),
                    (int) (this.drawPosition.x + this.imageSize.x * 0.5583),
                    (int) (this.drawPosition.y + this.imageSize.y * 0.5), paint);
            this.starLevel.draw(canvas, paint);
        }
    }

    @Override
    public void update() {
        if (this.drawPosition.y <
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.2412)) {
            this.drawPosition.y += this.imageSize.y / 10;
        } else {
            this.starLevel = new StarLevel(this.bmpStarLevel,
                    new Point((int) (this.drawPosition.x + this.imageSize.x * 0.5111),
                            (int) (this.drawPosition.y + this.imageSize.y * 0.5651)),
                    this.calStarLevel(), true);
            this.canDrawText = true;
        }
    }

    public Rect getRestartRect() {
        Point pos = this.drawPosition;
        Point size = this.imageSize;
        return new Rect(
                (int) (pos.x + size.x * 0.0972),
                (int) (pos.y + size.y * 0.8077),
                (int) (pos.x + size.x * 0.4722),
                (int) (pos.y + size.y * 0.9319));
    }

    public Rect getNextStageRect() {
        Point pos = this.drawPosition;
        Point size = this.imageSize;
        return new Rect(
                (int) (pos.x + size.x * 0.5472),
                (int) (pos.y + size.y * 0.8077),
                (int) (pos.x + size.x * 0.9222),
                (int) (pos.y + size.y * 0.9319));
    }

    private int calStarLevel() {
        float BASE_TIME = 0.3f;
        float interval = new Date(this.intervalTime).getTime() / 100 / 10.0f;
        int starLevel = 1;
        float[] standardTime = {
                BASE_TIME * 3 * this.boardNum,
                BASE_TIME * 2 * this.boardNum,
                BASE_TIME * this.boardNum
        };
        if (interval < standardTime[2]) {
            starLevel = 3;
        } else if (interval < standardTime[1]) {
            starLevel = 2;
        } else {
            starLevel = 1;
        }
        return starLevel;
    }
}
