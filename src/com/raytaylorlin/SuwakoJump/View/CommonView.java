package com.raytaylorlin.SuwakoJump.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;
import com.raytaylorlin.SuwakoJump.Lib.TutorialThread;

import java.util.HashMap;
import java.util.Iterator;

public abstract class CommonView extends SurfaceView implements SurfaceHolder.Callback {
    //主activity的引用
    protected SuwakoJumpActivity mainActivity;
    //刷帧的线程
    protected TutorialThread tutorialThread;
    //主画笔
    protected Paint mainPaint;
    protected int viewIndex;
    protected HashMap<String, Bitmap> bmpHashMap = new HashMap<String, Bitmap>();

    public CommonView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.getHolder().addCallback(this);
        this.mainActivity = mainActivity;

        //加载画笔和字体资源
        this.mainPaint = new Paint();
        this.mainPaint.setAntiAlias(true);
        Typeface typeFace = Typeface.createFromAsset(mainActivity.getAssets(),
                "fonts/ErasBoldITC.ttf");
        this.mainPaint.setTypeface(typeFace);
        this.mainPaint.setTextSize((float)
                (27 * (SuwakoJumpActivity.DISPLAY_WIDTH / 480.0)));

        this.initBitmap();
        this.initSprite();
    }

    /*
     * 初始化并加载图片，需要在子类中覆写
     */
    protected abstract void initBitmap();

    /*
    * 初始化精灵，需要在子类中覆写
    */
    protected abstract void initSprite();

    /*
    * 绘制画布，需要在子类中覆写
    */
    public abstract void onDraw(Canvas canvas);

    /*
    * 更新逻辑，需要在子类中覆写
    */
    public abstract void update();

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /*
     * surface创建时被调用
     */
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化重绘线程
        if (this.tutorialThread == null) {
            this.tutorialThread = new TutorialThread(this.getHolder(), this);
        }
        //设置线程标志位
        this.tutorialThread.setRunning(true);
        //启动线程
        this.tutorialThread.start();
    }

    /*
     * surface销毁时被调用
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean isRetrying = true;
        this.tutorialThread.setRunning(false);
        while (isRetrying) {
            try {
                //等待线程结束
                tutorialThread.join();
                isRetrying = false;
            } catch (InterruptedException e) {
            }
        }
        this.tutorialThread = null;
    }

    public void recycleBitmap(){
        //释放bitmap占用的内存
        Iterator<Bitmap> iBmp = this.bmpHashMap.values().iterator();
        while (iBmp.hasNext()) {
            Bitmap bmp = iBmp.next();
            if(!bmp.isRecycled()){
                bmp.recycle();
            }
        }
    }

    public int getViewIndex() {
        return this.viewIndex;
    }
}
