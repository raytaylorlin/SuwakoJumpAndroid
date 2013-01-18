package com.raytaylorlin.SuwakoJump.Sprites.Item;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Sprites.Suwako;

public class Spring extends Item {
    private boolean isShort = false;
    private int shortCount = 0, SHORT_TOTAL_COUNT = 3;

    public Spring(Bitmap image, Point drawPosition) {
        super(image, drawPosition);
    }

    @Override
    public void update() {
        super.update();
        if (this.isShort) {
            if (this.shortCount < this.SHORT_TOTAL_COUNT) {
                this.shortCount++;
            } else {
                this.isShort = false;
                this.drawPosition.y -= this.imageSize.y;
                this.imageSize.y *= 2;
            }
        }
    }

    @Override
    public void gainEffect(Suwako suwako) {
        suwako.setSuperUp();
        this.isShort = true;
        this.drawPosition.y += this.imageSize.y;
        this.imageSize.y /= 2;
    }
}
