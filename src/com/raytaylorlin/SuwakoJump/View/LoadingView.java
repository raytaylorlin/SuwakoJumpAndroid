package com.raytaylorlin.SuwakoJump.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class LoadingView extends CommonView implements SurfaceHolder.Callback {
    private Paint paint;
    //背景图片
    private Bitmap loadingBackground;

    private int loadingProcess = 0;//0到100表示进度
    int type;

    public LoadingView(SuwakoJumpActivity mainActivity, int type) {
        super(mainActivity);
        this.type = type;
    }

    @Override
    protected void initBitmap() {
        this.paint = new Paint();//创建画笔
        this.paint.setTextSize(12);//设置字体大小
        this.loadingBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.loading_background);
        this.loadingBackground = Bitmap.createScaledBitmap(this.loadingBackground,
                SuwakoJumpActivity.DISPLAY_WIDTH, SuwakoJumpActivity.DISPLAY_HEIGHT, true);
//        this.processMan = BitmapFactory.decodeResource(getResources(), R.drawable.processman);
    }

    @Override
    protected void initSprite(){

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.loadingBackground, 0, 0, paint);//绘制背景图片
    }

    @Override
    public void update(){

    }
}