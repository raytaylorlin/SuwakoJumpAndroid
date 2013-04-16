package com.raytaylorlin.SuwakoJump;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import com.raytaylorlin.SuwakoJump.Lib.SoundHelper;
import com.raytaylorlin.SuwakoJump.View.*;

import java.util.Timer;

public class SuwakoJumpActivity extends Activity {
    private CommonView selectStageView, welcomeView;
    private GameView gameView;
    private LoadingView loadingView;

    private static Bitmap StandardBitmap;
    //重力加速度感应管理器
    private SensorManager sensorManager;
    private float sensorX;

    public static final int MSG_REFRESH = 0x000001;
    public static final int MSG_CHANGE_TO_GAMEVIEW = 0x000002;
    public static final int MSG_CHANGE_TO_SELECTVIEW = 0x000003;
    public static final int MSG_CHANGE_TO_LOADINGVIEW = 0x000004;
    public static final int GAME_FRAME_RATE = 30;

    public static final int WELCOME_VIEW_INDEX = 0;
    public static final int GAME_VIEW_INDEX = 1;

    public static int DISPLAY_WIDTH, DISPLAY_HEIGHT;
    public static double X_SCALE_FACTOR, Y_SCALE_FACTOR;
    public int CURRENT_VIEW_INDEX = WELCOME_VIEW_INDEX;

    /*
    * 处理消息
    */
    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            SuwakoJumpActivity _this = SuwakoJumpActivity.this;
            switch (msg.arg1) {
                case SuwakoJumpActivity.MSG_CHANGE_TO_LOADINGVIEW:
//                    _this.loadingView = new LoadingView(_this);
                    _this.loadingView.setStage(msg.arg2);
                    _this.changeView(_this.loadingView);
                    break;
                case SuwakoJumpActivity.MSG_CHANGE_TO_SELECTVIEW:
//                    _this.selectStageView = new SelectStageView(_this);
                    _this.changeView(_this.selectStageView);
                    break;
                case SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW:
//                    _this.gameView = new GameView(_this, msg.arg2);
                    _this.gameView.initialize(msg.arg2);
                    _this.changeView(_this.gameView);
//                    _this.gameView.recycleBitmap();
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //设置实际宽高值和缩放比例
        StandardBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.welcome_view_background);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DISPLAY_WIDTH = dm.widthPixels;
        DISPLAY_HEIGHT = dm.heightPixels;
        X_SCALE_FACTOR = (float) DISPLAY_WIDTH / (StandardBitmap.getWidth());
        Y_SCALE_FACTOR = (float) DISPLAY_HEIGHT / (StandardBitmap.getHeight());

        //初始化重力感应器
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener listener = new SensorEventListener() {
            public void onSensorChanged(SensorEvent e) {
                float x = e.values[SensorManager.DATA_X];
                SuwakoJumpActivity.this.setSensorX(x);
            }

            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        //注册listener，第三个参数是检测的精确度
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

        //初始化游戏界面
        this.loadingView = new LoadingView(this);
        this.welcomeView = new WelcomeView(this);
        this.selectStageView = new SelectStageView(this);
        this.gameView = new GameView(this);
        this.setContentView(this.welcomeView);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出游戏");
            builder.setMessage("确定要退出游戏吗？");
            builder.setNegativeButton("否", null);
            builder.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            System.exit(0);
                            SoundHelper.stop();
                        }
                    });
            builder.show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

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
        this.CURRENT_VIEW_INDEX = targetView.getViewIndex();
    }

    /*
     * 设置重力感应器X轴数值
     * @param sensorX 设置的X轴数值
     */
    public void setSensorX(float sensorX) {
        this.sensorX = sensorX;
    }

    /*
     * 获取重力感应器X轴数值
     * @return 重力感应器X轴数值
     */
    public float getSensorX() {
        return this.sensorX;
    }
}
