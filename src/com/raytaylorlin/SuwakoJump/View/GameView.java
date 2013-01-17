package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.*;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    //背景图片
    private Bitmap bmpBackground, bmpScoreBoard,
            bmpSuwako, bmpBoard, bmpGameOverText,
            bmpNumber;
    private HashMap<String, Bitmap> bmpHashMap;

    private GameLogic gameLogic;

    public GameView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.gameLogic = new GameLogic(this, this.bmpHashMap);
        TutorialThread gameThread = new GameViewThread(getHolder(), this);
        gameThread.start();
        this.viewIndex = SuwakoJumpActivity.GAME_VIEW_INDEX;
    }

    @Override
    protected void initBitmap() {
        //加载图片资源
        this.bmpBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background);
        this.bmpScoreBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.score_bar);
        this.bmpSuwako = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_jump);
        this.bmpBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.board);
        this.bmpGameOverText= BitmapFactory.decodeResource(getResources(),
                R.drawable.gameover_text);
        this.bmpNumber= BitmapFactory.decodeResource(getResources(),
                R.drawable.number);

        //调整图片尺寸
        this.bmpBackground = ImageHelper.adjustScaleImage(this.bmpBackground);
        this.bmpScoreBoard = ImageHelper.adjustScaleImage(this.bmpScoreBoard);
        this.bmpSuwako = ImageHelper.adjustScaleImage(this.bmpSuwako);
        this.bmpBoard = ImageHelper.adjustScaleImage(this.bmpBoard);
        this.bmpGameOverText = ImageHelper.adjustScaleImage(this.bmpGameOverText);
        this.bmpNumber = ImageHelper.adjustScaleImage(this.bmpNumber);

        //建立字符串和图片的映射
        this.bmpHashMap = new HashMap<String, Bitmap>();
        this.bmpHashMap.put("background", this.bmpBackground);
        this.bmpHashMap.put("score_board", this.bmpScoreBoard);
        this.bmpHashMap.put("suwako", this.bmpSuwako);
        this.bmpHashMap.put("board", this.bmpBoard);
        this.bmpHashMap.put("game_over_text", this.bmpGameOverText);
        this.bmpHashMap.put("number", this.bmpNumber);
    }

    @Override
    protected void initSprite() {

    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.bmpBackground, 0, 0, this.mainPaint);
        ArrayList<JSprite> spritesList = this.gameLogic.getSpritesList();
        for (JSprite sprite : spritesList) {
            sprite.draw(canvas, this.mainPaint);
        }
//        canvas.drawBitmap(this.bmpScoreBoard, 0, 0, this.mainPaint);

    }

    /*
    * 屏幕被触摸事件
    */
    public boolean onTouchEvent(MotionEvent event) {
        //屏幕被按下
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Rect btnStartGameRect = this.btnStartGame.getRect();
            double x = event.getX();
            double y = event.getY();
            this.mainActivity.myHandler.sendEmptyMessage(
                    SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW);
//            if (x > btnStartGameRect.left && x < btnStartGameRect.right
//                    && y > btnStartGameRect.top && y < btnStartGameRect.bottom) {
//                this.mainActivity.myHandler.sendEmptyMessage(
//                        SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW);
//            }
        }
        return super.onTouchEvent(event);//调用基类的方法
    }

    @Override
    public void update() {
        this.gameLogic.update();
    }

    public float getSensorX() {
        return this.mainActivity.getSensorX();
    }
}
