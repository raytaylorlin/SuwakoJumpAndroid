package com.raytaylorlin.SuwakoJump.Sprites.Item;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Sprites.Suwako;

public class Spring extends Item {
    public Spring(Suwako suwako, Bitmap image, Point drawPosition) {
        super(suwako, image, drawPosition);
    }

    @Override
    public void gainEffect() {
        if (this.suwako != null) {
            this.suwako.setSuperUp();
        }
    }
}
