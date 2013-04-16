package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class TipsBoard extends JSprite {
    public TipsBoard(Bitmap bitmap, Point drawPosition) {
        super(bitmap, drawPosition);
    }

    @Override
    public void update(){
        if (this.drawPosition.y <
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.15)) {
            this.drawPosition.y += this.imageSize.y / 10;
        }
    }

    public Rect getRect() {
        Point pos = this.drawPosition;
        Point size = this.imageSize;
        return new Rect(
                (int) (pos.x + size.x * 0.1625),
                (int) (pos.y + size.y * 0.4937),
                (int) (pos.x + size.x * 0.625),
                (int) (pos.y + size.y * 0.9625));
    }

}
