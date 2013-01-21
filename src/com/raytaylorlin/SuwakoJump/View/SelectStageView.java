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

public class SelectStageView extends CommonView implements SurfaceHolder.Callback {
    SoundPool soundPool;//声音
    HashMap<Integer, Integer> soundPoolMap;

    //背景图片
    private Bitmap bmpBackground;
    private Rect[][] stageRectList;

    public SelectStageView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.viewIndex = SuwakoJumpActivity.WELCOME_VIEW_INDEX;
//        initSounds();//初始化声音

//        playSound(1);
    }

    @Override
    protected void initBitmap() {//初始化图片资源的方法
        this.bmpBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.select_stage_view);
        this.bmpBackground = ImageHelper.adjustScaleImage(this.bmpBackground);
    }

    @Override
    protected void initSprite() {
        int DW = SuwakoJumpActivity.DISPLAY_WIDTH;
        int DH = SuwakoJumpActivity.DISPLAY_HEIGHT;
        int x_offset = (int) (76 * SuwakoJumpActivity.X_SCALE_FACTOR);
        int y_offset = (int) (86 * SuwakoJumpActivity.Y_SCALE_FACTOR);
        this.stageRectList = new Rect[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                this.stageRectList[i][j] = new Rect(
                        (int) (DW * 0.125 + x_offset * j),
                        (int) (DH * 0.2525 + y_offset * i),
                        (int) (DW * 0.2333 + x_offset * j),
                        (int) (DH * 0.3275 + y_offset * i)
                );
            }
        }
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
            double x = event.getX();
            double y = event.getY();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (x > this.stageRectList[i][j].left &&
                            x < this.stageRectList[i][j].right &&
                            y > this.stageRectList[i][j].top &&
                            y < this.stageRectList[i][j].bottom) {
                        Message msg = new Message();
                        msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW;
                        msg.arg2 = i * 5 + j + 1;
                        this.mainActivity.myHandler.sendMessage(msg);
                        return super.onTouchEvent(event);
                    }
                }
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void update() {
    }
}