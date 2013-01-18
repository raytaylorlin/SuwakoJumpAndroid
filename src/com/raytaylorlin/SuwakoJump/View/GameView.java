package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.*;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Lib.TutorialThread;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    //背景图片
    private Bitmap bmpBackground, bmpScoreBoard,
            bmpSuwakoJump, bmpSuwakoWin, bmpSuwakoFly,
            bmpGameOverText, bmpNumber;
    private Bitmap bmpItemSpring;
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
        this.bmpHashMap = new HashMap<String, Bitmap>();
        //加载图片资源
        this.bmpBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background);
        this.bmpScoreBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.score_bar);
        this.bmpSuwakoJump = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_jump);
        this.bmpSuwakoWin = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_win);
        this.bmpSuwakoFly = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_fly);
        this.bmpGameOverText = BitmapFactory.decodeResource(getResources(),
                R.drawable.gameover_text);
        this.bmpNumber = BitmapFactory.decodeResource(getResources(),
                R.drawable.number);
        this.bmpItemSpring = BitmapFactory.decodeResource(getResources(),
                R.drawable.item_spring);

        //加载板子资源
        Bitmap bmpBoardNormal = BitmapFactory.decodeResource(getResources(),
                R.drawable.board_normal);
        this.bmpHashMap.put("board_normal", ImageHelper.adjustScaleImage(bmpBoardNormal));
        Bitmap bmpBoardBroken = BitmapFactory.decodeResource(getResources(),
                R.drawable.board_broken);
        this.bmpHashMap.put("board_broken", ImageHelper.adjustScaleImage(bmpBoardBroken));
        Bitmap bmpBoardMoving = BitmapFactory.decodeResource(getResources(),
                R.drawable.board_moving);
        this.bmpHashMap.put("board_moving", ImageHelper.adjustScaleImage(bmpBoardMoving));
        Bitmap bmpBoardVanish = BitmapFactory.decodeResource(getResources(),
                R.drawable.board_vanish);
        this.bmpHashMap.put("board_vanish", ImageHelper.adjustScaleImage(bmpBoardVanish));
        Bitmap bmpBoardFlag = BitmapFactory.decodeResource(getResources(),
                R.drawable.board_flag);
        this.bmpHashMap.put("board_flag", ImageHelper.adjustScaleImage(bmpBoardFlag));

        //调整图片尺寸
        this.bmpBackground = ImageHelper.adjustScaleImage(this.bmpBackground);
        this.bmpScoreBoard = ImageHelper.adjustScaleImage(this.bmpScoreBoard);
        this.bmpSuwakoJump = ImageHelper.adjustScaleImage(this.bmpSuwakoJump);
        this.bmpSuwakoWin = ImageHelper.adjustScaleImage(this.bmpSuwakoWin);
        this.bmpSuwakoFly = ImageHelper.adjustScaleImage(this.bmpSuwakoFly);
        this.bmpGameOverText = ImageHelper.adjustScaleImage(this.bmpGameOverText);
        this.bmpNumber = ImageHelper.adjustScaleImage(this.bmpNumber);
        this.bmpItemSpring = ImageHelper.adjustScaleImage(this.bmpItemSpring);

        //建立字符串和图片的映射
        this.bmpHashMap.put("background", this.bmpBackground);
        this.bmpHashMap.put("score_board", this.bmpScoreBoard);
        this.bmpHashMap.put("suwako_jump", this.bmpSuwakoJump);
        this.bmpHashMap.put("suwako_win", this.bmpSuwakoWin);
        this.bmpHashMap.put("suwako_win", this.bmpSuwakoWin);
        this.bmpHashMap.put("suwako_fly", this.bmpSuwakoFly);
        this.bmpHashMap.put("game_over_text", this.bmpGameOverText);
        this.bmpHashMap.put("number", this.bmpNumber);
        this.bmpHashMap.put("item_spring", this.bmpItemSpring);
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
            if (this.gameLogic.isGameOver()) {
                this.mainActivity.myHandler.sendEmptyMessage(
                        SuwakoJumpActivity.MSG_CHANGE_TO_GAMEVIEW);
            } else {
                this.gameLogic.setSuwakoAttack(new Point((int) x, (int) y));
            }
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
