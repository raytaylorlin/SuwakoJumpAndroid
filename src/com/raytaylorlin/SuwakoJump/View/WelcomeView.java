package com.raytaylorlin.SuwakoJump.View;

import java.util.HashMap;

import android.content.Context;
import android.graphics.*;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.Sprites.StarLevel;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class WelcomeView extends CommonView implements SurfaceHolder.Callback {
    SoundPool soundPool;//声音
    HashMap<Integer, Integer> soundPoolMap;

    //背景图片
    private Bitmap bmpBackground;
    //开始游戏按钮图片
    private Bitmap bmpStartGameButton;

    Bitmap help;//帮助菜单
    Bitmap openSound;//打开声音菜单
    Bitmap closeSound;//关闭声音菜单
    Bitmap exit;//退出菜单

    int backgroundY = -200;//背景的坐标
    int background2Y = 40;


    public WelcomeView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.viewIndex = SuwakoJumpActivity.WELCOME_VIEW_INDEX;
//        this.welcomeThread = new WelcomeViewThread(this);
//        initSounds();//初始化声音

//        playSound(1);
    }

    @Override
    protected void initBitmap() {//初始化图片资源的方法
        this.bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_view_background);
        this.bmpBackground = ImageHelper.adjustScaleImage(this.bmpBackground);
        this.bmpStartGameButton = BitmapFactory.decodeResource(getResources(),
                R.drawable.start_game_button);
        this.bmpStartGameButton = ImageHelper.adjustScaleImage(this.bmpStartGameButton);
    }

    @Override
    protected void initSprite() {
        int btnX = (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5458);
        int btnStartGameY = (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.76875);
        int btnWidth = this.bmpStartGameButton.getWidth();
        int btnHeight = this.bmpStartGameButton.getHeight();

//        this.btnStartGame = new StarLevel(this.bmpStartGameButton,
//                new Point(btnX, btnStartGameY),
//                new Point(btnWidth, btnHeight)) {
//            public void onClick() {
//                System.out.println("start game");
//            }
//        };
    }

    public void initSounds() {//初始化声音的方法
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);//初始化SoundPool
        soundPoolMap = new HashMap<Integer, Integer>();//初始化   HashMap
//        soundPoolMap.put(1, soundPool.load(getContext(), R.raw.welcome1, 1));
    }

    public void playSound(int sound) {//播放声音的方法
        AudioManager mgr = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量
        float volume = streamVolumeCurrent / streamVolumeMax;   //设备的音量
        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);//播放
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.bmpBackground, 0, 0, mainPaint);
    }

    /*
     * 屏幕被触摸事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        //屏幕被按下
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect btnStartGameRect = new Rect(
                    (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5458),
                    (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.7687),
                    (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.9208),
                    (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.8387)
            );
            double x = event.getX();
            double y = event.getY();
            if (x > btnStartGameRect.left && x < btnStartGameRect.right
                    && y > btnStartGameRect.top && y < btnStartGameRect.bottom) {
                Message msg = new Message();
                msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_SELECTVIEW;
                this.mainActivity.myHandler.sendMessage(msg);
            }
        }
        return super.onTouchEvent(event);//调用基类的方法
    }

    @Override
    public void update() {
    }
}