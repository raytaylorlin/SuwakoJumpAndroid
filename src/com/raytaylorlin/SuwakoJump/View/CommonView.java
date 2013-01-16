package com.raytaylorlin.SuwakoJump.View;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;
import com.raytaylorlin.SuwakoJump.TutorialThread;

public abstract class CommonView extends SurfaceView implements SurfaceHolder.Callback {
    //主activity的引用
    protected SuwakoJumpActivity mainActivity;
    //刷帧的线程
    protected TutorialThread tutorialThread;
    //主画笔
    protected Paint mainPaint;
    protected int viewIndex;

    public CommonView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.getHolder().addCallback(this);
        this.mainActivity = mainActivity;
        //初始化重绘线程
        this.tutorialThread = new TutorialThread(this.getHolder(), this);
        this.mainPaint = new Paint();
        //当前加载的是那么界面
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
        //设置线程标志位
        this.tutorialThread.setRunning(true);
        //启动线程
        this.tutorialThread.start();
    }

    /*
     * surface销毁时被调用
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;//循环标志位
        tutorialThread.setRunning(false);//设置循环标志位
        while (retry) {
            try {
                tutorialThread.join();//等待线程结束
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public int getViewIndex(){
        return this.viewIndex;
    }
}
