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
import com.raytaylorlin.SuwakoJump.Lib.SoundHelper;
import com.raytaylorlin.SuwakoJump.Sprites.StarLevel;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class WelcomeView extends CommonView implements SurfaceHolder.Callback {
    HashMap<Integer, Integer> soundPoolMap;

    //背景图片
    private Bitmap bmpBackground;
    //开始游戏按钮图片
    private Bitmap bmpStartGameButton;

    public WelcomeView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.viewIndex = SuwakoJumpActivity.WELCOME_VIEW_INDEX;
        this.initSounds();
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

    }

    public void initSounds() {//初始化声音的方法
        Context context = this.getContext();
//        SoundHelper.SoundMap.put("bgm",
//                SoundHelper.load(this.getContext(), R.raw.bgm));
        SoundHelper.play(context, R.raw.bgm);
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