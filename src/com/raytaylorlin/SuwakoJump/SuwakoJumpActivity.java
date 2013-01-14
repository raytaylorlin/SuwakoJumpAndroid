package com.raytaylorlin.SuwakoJump;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.*;
import com.raytaylorlin.SuwakoJump.View.*;

public class SuwakoJumpActivity extends Activity {
    //    CommonView gameView;//GameView的引用

    //    FailView failView;//游戏失败界面的引用
//    HelpView helpView;//HelpView的引用
//    WinView winView;//欢迎界面的引用
    private CommonView currentView;
    private CommonView loadingView, welcomeView, gameView;
    //进度条界面的引用
//    private LoadingView loadingView;
//    private WelcomeView welcomeView;
//    private GameView gameView;

    boolean isSound = true;//是否播放声音
    public static final int MSG_REFRESH = 0x000001;
    public static final int MSG_CHANGE_TO_GAMEVIEW = 0x000002;

    public static int DISPLAY_WIDTH, DISPLAY_HEIGHT;
    public static double X_SCALE_FACTOR, Y_SCALE_FACTOR;

    /*
    * 处理消息
    */
    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            SuwakoJumpActivity _this = SuwakoJumpActivity.this;
            switch (msg.what) {
                case SuwakoJumpActivity.MSG_REFRESH:
                    _this.currentView.update();
                    break;
                case SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW:
                    gameView = new GameView(_this);
                    _this.changeView(gameView);
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            gameView = new GameView(SuwakoJumpActivity.this);
                            Looper.loop();
                        }
                    }.start();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DISPLAY_WIDTH = dm.widthPixels;
        DISPLAY_HEIGHT = dm.heightPixels;
        X_SCALE_FACTOR = DISPLAY_WIDTH / 480.0;
        Y_SCALE_FACTOR = DISPLAY_HEIGHT / 800.0;

        this.loadingView = new LoadingView(this, 1);
        this.welcomeView = new WelcomeView(this);
        this.setContentView(this.welcomeView);//设置加载界面
        this.currentView = this.welcomeView;
//        this.setContentView(this.loadingView);//设置加载界面
        new Thread() {//线程
            public void run() {
//                Looper.prepare();
//                welcomeView = new WelcomeView(PlaneActivity.this);//初始化WelcomeView
            }
        }.start();//启动线程
    }


    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return true;
    }

    /*
     * 切换界面
     * @param targetView 切换的目标界面
     */
    public void changeView(CommonView targetView) {
        this.setContentView(targetView);
        this.currentView = targetView;
    }


    class GameThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = new Message();
                message.what = SuwakoJumpActivity.MSG_REFRESH;
                SuwakoJumpActivity.this.myHandler.sendMessage(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
