package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.*;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Lib.SoundHelper;
import com.raytaylorlin.SuwakoJump.Lib.TutorialThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    private static final int MSG_NEXT_STAGE = 0;
    private static final int MSG_RESTART = 1;
    private static final int MSG_SELECT_STAGE = 2;

    //图片资源
    private ArrayList<Bitmap> bmpBackgroundList;
    private Bitmap bmpBackground1, bmpBackground2, bmpBackground3, bmpBackground4,
            bmpScoreBoard, bmpSuwakoJump, bmpSuwakoWin,
            bmpGameOverText, bmpResultBoard;
    private Bitmap bmpPauseBoardOn, bmpPauseBoardOff;
    private Bitmap bmpTipsBoard1, bmpTipsBoard2,
            bmpTipsBoard3, bmpTipsBoard4, bmpTipsBoard5;
    private Bitmap bmpItemSpring, bmpStarLevel;


    //游戏更新线程
    private TutorialThread gameThread;
    //游戏逻辑
    private GameLogic gameLogic;

    public GameView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.initSound();
    }

    public void initialize(int stageNum){
        this.gameLogic = new GameLogic(this, this.bmpHashMap, stageNum);
        this.gameThread = new GameViewThread(getHolder(), this);
        this.gameThread.start();
    }

    @Override
    protected void initBitmap() {
        //加载图片资源
        this.bmpBackground1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background1);
        this.bmpBackground2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background2);
        this.bmpBackground3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background3);
        this.bmpBackground4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background4);
        this.bmpScoreBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.score_board);
        this.bmpSuwakoJump = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_jump);
        this.bmpSuwakoWin = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_win);
        this.bmpGameOverText = BitmapFactory.decodeResource(getResources(),
                R.drawable.gameover_text);
        this.bmpItemSpring = BitmapFactory.decodeResource(getResources(),
                R.drawable.item_spring);
        this.bmpTipsBoard1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.tips_board1);
        this.bmpTipsBoard2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.tips_board2);
        this.bmpTipsBoard3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.tips_board3);
        this.bmpTipsBoard4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.tips_board4);
        this.bmpTipsBoard5 = BitmapFactory.decodeResource(getResources(),
                R.drawable.tips_board5);
        this.bmpResultBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.result_board);
        this.bmpPauseBoardOn = BitmapFactory.decodeResource(getResources(),
                R.drawable.pause_board_on);
        this.bmpPauseBoardOff = BitmapFactory.decodeResource(getResources(),
                R.drawable.pause_board_off);
        this.bmpStarLevel = BitmapFactory.decodeResource(getResources(),
                R.drawable.star_level);

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
        this.bmpBackground1 = ImageHelper.adjustScaleImage(this.bmpBackground1);
        this.bmpBackground2 = ImageHelper.adjustScaleImage(this.bmpBackground2);
        this.bmpBackground3 = ImageHelper.adjustScaleImage(this.bmpBackground3);
        this.bmpBackground4 = ImageHelper.adjustScaleImage(this.bmpBackground4);
        this.bmpScoreBoard = ImageHelper.adjustScaleImage(this.bmpScoreBoard);
        this.bmpSuwakoJump = ImageHelper.adjustScaleImage(this.bmpSuwakoJump);
        this.bmpSuwakoWin = ImageHelper.adjustScaleImage(this.bmpSuwakoWin);
        this.bmpGameOverText = ImageHelper.adjustScaleImage(this.bmpGameOverText);
        this.bmpItemSpring = ImageHelper.adjustScaleImage(this.bmpItemSpring);
        this.bmpTipsBoard1 = ImageHelper.adjustScaleImage(this.bmpTipsBoard1);
        this.bmpTipsBoard2 = ImageHelper.adjustScaleImage(this.bmpTipsBoard2);
        this.bmpTipsBoard3 = ImageHelper.adjustScaleImage(this.bmpTipsBoard3);
        this.bmpTipsBoard4 = ImageHelper.adjustScaleImage(this.bmpTipsBoard4);
        this.bmpTipsBoard5 = ImageHelper.adjustScaleImage(this.bmpTipsBoard5);
        this.bmpResultBoard = ImageHelper.adjustScaleImage(this.bmpResultBoard);
        this.bmpPauseBoardOn = ImageHelper.adjustScaleImage(this.bmpPauseBoardOn);
        this.bmpPauseBoardOff = ImageHelper.adjustScaleImage(this.bmpPauseBoardOff);
        this.bmpStarLevel = ImageHelper.adjustScaleImage(this.bmpStarLevel);

        //建立字符串和图片的映射
        this.bmpBackgroundList = new ArrayList<Bitmap>();
        this.bmpBackgroundList.add(this.bmpBackground1);
        this.bmpBackgroundList.add(this.bmpBackground2);
        this.bmpBackgroundList.add(this.bmpBackground3);
        this.bmpBackgroundList.add(this.bmpBackground4);
        this.bmpHashMap.put("background1", this.bmpBackground1);
        this.bmpHashMap.put("background2", this.bmpBackground2);
        this.bmpHashMap.put("background3", this.bmpBackground3);
        this.bmpHashMap.put("background4", this.bmpBackground4);
        this.bmpHashMap.put("score_board", this.bmpScoreBoard);
        this.bmpHashMap.put("suwako_jump", this.bmpSuwakoJump);
        this.bmpHashMap.put("suwako_win", this.bmpSuwakoWin);
        this.bmpHashMap.put("game_over_text", this.bmpGameOverText);
        this.bmpHashMap.put("item_spring", this.bmpItemSpring);
        this.bmpHashMap.put("tips_board1", this.bmpTipsBoard1);
        this.bmpHashMap.put("tips_board2", this.bmpTipsBoard2);
        this.bmpHashMap.put("tips_board3", this.bmpTipsBoard3);
        this.bmpHashMap.put("tips_board4", this.bmpTipsBoard4);
        this.bmpHashMap.put("tips_board5", this.bmpTipsBoard5);
        this.bmpHashMap.put("result_board", this.bmpResultBoard);
        this.bmpHashMap.put("pause_board_on", this.bmpPauseBoardOn);
        this.bmpHashMap.put("pause_board_off", this.bmpPauseBoardOff);
        this.bmpHashMap.put("star_level", this.bmpStarLevel);
    }

    @Override
    protected void initSprite() {

    }

    private void initSound() {
        SoundHelper.SoundMap.put("jump",
                SoundHelper.load(this.getContext(), R.raw.jump));
        SoundHelper.SoundMap.put("jump2",
                SoundHelper.load(this.getContext(), R.raw.jump2));
        SoundHelper.SoundMap.put("die",
                SoundHelper.load(this.getContext(), R.raw.die));
        SoundHelper.SoundMap.put("broken",
                SoundHelper.load(this.getContext(), R.raw.broken));
        SoundHelper.SoundMap.put("pause",
                SoundHelper.load(this.getContext(), R.raw.pause));
    }

    public void onDraw(Canvas canvas) {
        int bgNum = (this.gameLogic.getStageNum() - 1) / 5;
        canvas.drawBitmap(this.bmpBackgroundList.get(bgNum), 0, 0, this.mainPaint);
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
            double x = event.getX();
            double y = event.getY();
            //TIPS板展示阶段的触摸屏事件检测
            if (this.gameLogic.isShowingTips()) {
                Rect btnOkRect = this.gameLogic.getTipsBoardButtonRect();
                if (x > btnOkRect.left && x < btnOkRect.right
                        && y > btnOkRect.top && y < btnOkRect.bottom) {
                    this.gameLogic.hideTipsBoard();
                }
            } else if (this.gameLogic.isShowingResult()) {
                //显示分数结果阶段的触摸屏事件检测
                ArrayList<Rect> btnRectList = this.gameLogic.getResultBoardButtonRect();
                Rect btnRestartRect = btnRectList.get(0);
                Rect btnNextStageRect = btnRectList.get(1);
                //按下Restart按钮事件
                if (x > btnRestartRect.left && x < btnRestartRect.right
                        && y > btnRestartRect.top && y < btnRestartRect.bottom) {
                    this.changeNextView(MSG_RESTART);
                    //按下NextStage按钮事件
                } else if (x > btnNextStageRect.left && x < btnNextStageRect.right
                        && y > btnNextStageRect.top && y < btnNextStageRect.bottom) {
                    this.changeNextView(MSG_NEXT_STAGE);
                }
            }
            //游戏结束阶段的触摸屏事件检测
            ArrayList<Rect> btnRectList = this.gameLogic.getGameoverButtonRect();
            Rect btnRestartRect = btnRectList.get(0);
            Rect btnSelectRect = btnRectList.get(1);
            if (this.gameLogic.isGameOver()) {
                //按下Restart按钮事件
                if (x > btnRestartRect.left && x < btnRestartRect.right
                        && y > btnRestartRect.top && y < btnRestartRect.bottom) {
                    this.changeNextView(MSG_RESTART);
                    //按下SelectStage按钮事件
                } else if (x > btnSelectRect.left && x < btnSelectRect.right
                        && y > btnSelectRect.top && y < btnSelectRect.bottom) {
                    this.changeNextView(MSG_SELECT_STAGE);
                }
            } else {
                //游戏没有结束时候的触摸屏事件检测
                Rect btnPauseRect = this.gameLogic.getPauseButtonRect();
                if (x > btnPauseRect.left && x < btnPauseRect.right
                        && y > btnPauseRect.top && y < btnPauseRect.bottom) {
                    this.gameLogic.pauseResume();
                }
                //游戏处于暂停阶段的触摸屏事件检测
                if (this.gameLogic.isPause()) {
                    ArrayList<Rect> btnPRectList = this.gameLogic.getGameoverButtonRect();
                    btnRestartRect = btnPRectList.get(0);
                    Rect btnSoundRect = btnPRectList.get(1);
                    if (x > btnRestartRect.left && x < btnRestartRect.right
                            && y > btnRestartRect.top && y < btnRestartRect.bottom) {
                        this.changeNextView(MSG_RESTART);
                        //按下SelectStage按钮事件
                    } else if (x > btnSoundRect.left && x < btnSoundRect.right
                            && y > btnSoundRect.top && y < btnSoundRect.bottom) {
                        this.gameLogic.turnVolumn();
                    }
                }
//                this.gameLogic.setSuwakoAttack(new Point((int) x, (int) y));
            }
        }
        return super.onTouchEvent(event);//调用基类的方法
    }

    @Override
    public void update() {
        if (!this.gameLogic.isPause()) {
            this.gameLogic.update();
        }
    }

    public float getSensorX() {
        return this.mainActivity.getSensorX();
    }

    private void changeNextView(int msgType) {
        //发送消息
        Message msg = new Message();
        switch (msgType) {
            case MSG_NEXT_STAGE:
                msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_LOADINGVIEW;
                msg.arg2 = this.gameLogic.getStageNum() + 1;
                if (msg.arg2 >= 21) {
                    msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_SELECTVIEW;
                }
                break;
            case MSG_RESTART:
                msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_LOADINGVIEW;
                msg.arg2 = this.gameLogic.getStageNum();
                break;
            case MSG_SELECT_STAGE:
                msg.arg1 = SuwakoJumpActivity.MSG_CHANGE_TO_SELECTVIEW;
                break;
        }
        this.mainActivity.myHandler.sendMessage(msg);
    }
}
