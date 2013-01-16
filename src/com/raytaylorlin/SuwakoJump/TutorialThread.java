package com.raytaylorlin.SuwakoJump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.View.CommonView;

public class TutorialThread extends Thread {
    //睡眠的毫秒数
    private int sleepSpan = 1000/SuwakoJumpActivity.GAME_FRAME_RATE;
    //
    protected SurfaceHolder surfaceHolder;
    //调用界面的引用
    protected CommonView calledView;
    //线程是否正在运行
    private boolean isRunning = false;

    public TutorialThread(SurfaceHolder surfaceHolder, CommonView calledView) {//构造器
        this.surfaceHolder = surfaceHolder;//SurfaceHolder的引用
        this.calledView = calledView;//欢迎界面的引用
    }

    public void setRunning(boolean value) {
        this.isRunning = value;
    }

    public void run() {
        Canvas canvas;
        while (this.isRunning) {
            canvas = null;
            try {
                // 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {//同步
                    this.calledView.onDraw(canvas);//调用绘制方法
                }
            } finally {
                if (canvas != null) {
                    //更新屏幕显示内容
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            this.threadHandle();
            try {
                //睡眠指定毫秒数
                Thread.sleep(sleepSpan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 线程其它处理操作，供覆写
     */
    protected void threadHandle(){
        this.calledView.update();
    }

}
