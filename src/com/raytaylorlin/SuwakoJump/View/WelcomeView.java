package com.raytaylorlin.SuwakoJump.View;

import java.util.HashMap;

import android.content.Context;
import android.graphics.*;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.Sprite.GameButton;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;

public class WelcomeView extends CommonView implements SurfaceHolder.Callback {
    private GameButton btnStartGame;
    //    private WelcomeViewThread welcomeThread;//绘制线程
    int status = 1;//当前的状态值
    int k = 0;//状态为2时用到的切换图片
    int alpha = 255;//透明度

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
        this.bmpBackground = Bitmap.createScaledBitmap(this.bmpBackground,
                SuwakoJumpActivity.DISPLAY_WIDTH, SuwakoJumpActivity.DISPLAY_HEIGHT, true);
        this.bmpStartGameButton = BitmapFactory.decodeResource(getResources(),
                R.drawable.start_game_button);
        this.bmpStartGameButton = Bitmap.createScaledBitmap(this.bmpStartGameButton,
                (int) (this.bmpStartGameButton.getWidth() * SuwakoJumpActivity.X_SCALE_FACTOR),
                (int) (this.bmpStartGameButton.getHeight() * SuwakoJumpActivity.Y_SCALE_FACTOR), true);
//        background2 = BitmapFactory.decodeResource(getResources(), R.drawable.background2);//初始化背景图片
//        startGame = BitmapFactory.decodeResource(getResources(), R.drawable.startgame);//初始化开始游戏
//
//        closeSound = BitmapFactory.decodeResource(getResources(), R.drawable.closesound);//关闭声音
//        exit = BitmapFactory.decodeResource(getResources(), R.drawable.exit);//退出游戏

    }

    @Override
    protected void initSprite() {
        int btnX = (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5);
        int btnStartGameY = (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.75);
        int btnWidth = this.bmpStartGameButton.getWidth();
        int btnHeight = this.bmpStartGameButton.getHeight();

        this.btnStartGame = new GameButton(this.bmpStartGameButton,
                new Point(btnX, btnStartGameY),
                new Point(btnWidth, btnHeight)) {
            public void onClick() {
                System.out.println("start game");
            }
        };
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
        this.btnStartGame.draw(canvas, mainPaint);
    }

    /*
     * 屏幕被触摸事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        //屏幕被按下
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if(this.status != 4){//当不是菜单状态时返回
//                return false;
//            }
            Rect btnStartGameRect = this.btnStartGame.getRect();
            double x = event.getX();
            double y = event.getY();
            System.out.println(x + "," + y);
            if (x > btnStartGameRect.left && x < btnStartGameRect.right
                    && y > btnStartGameRect.top && y < btnStartGameRect.bottom) {
                this.mainActivity.myHandler.sendEmptyMessage(
                        SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW);
            }
//            if(x>10 && x<10 + openSound.getWidth()
//                    && y>70 && y<70 + openSound.getHeight()){//点击了开始有些按钮
//                mainActivity.myHandler.sendEmptyMessage(2);//发送消息
//            }
//            else if(x>390 && x<390 + help.getWidth()
//                    && y>60 && y<60 + help.getHeight()){//点击了帮助按钮
//                mainActivity.myHandler.sendEmptyMessage(3);//发送消息
//            }
//            else if(x>10 && x<10 + openSound.getWidth()
//                    && y>230 && y<230 + openSound.getHeight()){//点击了声音按钮
//                mainActivity.isSound = !mainActivity.isSound;//将声音标志位置反
//            }
//            else if(x>380 && x<380 + exit.getWidth()
//                    && y>230 && y<230 + exit.getHeight()){//点击了退出按钮
//                System.exit(0);//退出游戏
//            }
        }
        return super.onTouchEvent(event);//调用基类的方法
    }

    @Override
    public void update() {
    }
}