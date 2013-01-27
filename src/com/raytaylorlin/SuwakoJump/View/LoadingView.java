package com.raytaylorlin.SuwakoJump.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class LoadingView extends CommonView implements SurfaceHolder.Callback {
    private Paint paint;
    //背景图片
    private Bitmap loadingBackground;
    private int stageNum = 1;
    private boolean isLoaded = false;
    private int count = 0, TOTAL_COUNT = 10;

    public LoadingView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    protected void initBitmap() {
        this.paint = new Paint();
        this.loadingBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.loading_background);
        this.loadingBackground = ImageHelper.adjustScaleImage(this.loadingBackground);
        this.bmpHashMap.put("loading_background", this.loadingBackground);
    }

    @Override
    protected void initSprite() {

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.loadingBackground, 0, 0, paint);//绘制背景图片
    }

    @Override
    public void update() {
        if (!this.isLoaded && this.count > this.TOTAL_COUNT) {
            Message msg = new Message();
            msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW;
            msg.arg2 = this.stageNum;
            this.mainActivity.myHandler.sendMessage(msg);
            this.isLoaded = true;
        } else {
            this.count++;
        }
    }

    public void setStage(int stageNum) {
        this.stageNum = stageNum;
        this.isLoaded = false;
        this.count = 0;
    }
}