package com.raytaylorlin.SuwakoJump.Lib;

import android.graphics.Bitmap;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class ImageHelper {
    public static Bitmap adjustScaleImage(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * SuwakoJumpActivity.X_SCALE_FACTOR),
                (int) (bitmap.getHeight() * SuwakoJumpActivity.Y_SCALE_FACTOR), true);
    }
}