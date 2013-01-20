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
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.2412)) {
            this.drawPosition.y += this.imageSize.y / 10;
        }
    }

    public Rect getRect() {
        Point pos = this.drawPosition;
        Point size = this.imageSize;
        return new Rect(
                (int) (pos.x + size.x * 0.2583),
                (int) (pos.y + size.y * 0.7603),
                (int) (pos.x + size.x * 0.7583),
                (int) (pos.y + size.y * 0.926));
    }

}
