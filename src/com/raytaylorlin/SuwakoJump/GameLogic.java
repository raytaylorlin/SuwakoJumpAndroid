package com.raytaylorlin.SuwakoJump;

//import SuwakoJump.Lib.RandomHelper;
//import SuwakoJump.Sprites.*;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Lib.SoundHelper;
import com.raytaylorlin.SuwakoJump.Sprites.*;
import com.raytaylorlin.SuwakoJump.Sprites.Board.*;
import com.raytaylorlin.SuwakoJump.View.GameView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class GameLogic {
    private GameView gameView;

    private HashMap<String, Bitmap> bmpHashMap;
    private ArrayList<JSprite> spritesList = new ArrayList<JSprite>();
    private ArrayList<Board> boardsList = new ArrayList<Board>();

    private JSprite gameOverText, pauseBoardOn, pauseBoardOff;
    private TipsBoard tipsBoard;
    private ResultBoard resultBoard;
    private ScoreBoard scoreBoard;
    private Suwako suwako;

    private long startTime, endTime;
    private int totalBoardNum = 0;
    private int gameScore = 0;
    private int stageNum = 1;
    private boolean isGameOver, isShowingTips = true, isShowingResult;
    private boolean isPause;
    public boolean canPlaySound = true;
    private boolean boardsFallingDown = false;

    public GameLogic(GameView gameView, HashMap<String, Bitmap> bmpHashMap, int stageNum) {
        this.gameView = gameView;
        this.bmpHashMap = bmpHashMap;
        this.stageNum = stageNum;
        this.initSprite();
    }

    /*
     * 初始化精灵
     */
    private void initSprite() {
        //初始化关卡计算类
        StageMap.setBitmap(this.bmpHashMap);

        this.spritesList.clear();
        this.boardsList.clear();

        //初始化板子
        this.boardsList = StageMap.getStageMap(this.stageNum);
        for (Board board : this.boardsList) {
            this.add(board);
        }
        this.totalBoardNum = this.boardsList.size();

        //初始化suwako
        Point boardPos = this.boardsList.get(0).getPosition();
        Point boardSize = this.boardsList.get(0).getSize();
        int suwakoW = this.bmpHashMap.get("suwako_jump").getWidth() / 20;
        int suwakoH = this.bmpHashMap.get("suwako_jump").getHeight() / 2;
        int suwakoX = boardPos.x + (boardSize.x - suwakoW) / 2;
        int suwakoY = boardPos.y - suwakoH;

        this.suwako = new Suwako(this.bmpHashMap,
                new Point(suwakoX, suwakoY),
                new Point(suwakoW, suwakoH), this);

        //初始化游戏结束文字
        this.gameOverText = new JSprite(
                this.bmpHashMap.get("game_over_text"),
                new Point(0, SuwakoJumpActivity.DISPLAY_HEIGHT));

        //初始化游戏暂停界面
        this.pauseBoardOn = new JSprite(
                this.bmpHashMap.get("pause_board_on"),
                new Point(0, 0));
        this.pauseBoardOn.hide();
        this.pauseBoardOff = new JSprite(
                this.bmpHashMap.get("pause_board_off"),
                new Point(0, 0));
        this.pauseBoardOff.hide();

        //初始化分数板
        this.scoreBoard = new ScoreBoard(
                this.bmpHashMap.get("score_board"),
                new Point(0, 0), this.stageNum);

        //初始化提示板
        if (this.stageNum <= 5) {
            this.tipsBoard = new TipsBoard(
                    this.bmpHashMap.get("tips_board" + this.stageNum),
                    new Point(0, -SuwakoJumpActivity.DISPLAY_HEIGHT / 2));
        }

        //添加精灵
        this.add(this.gameOverText);
        this.add(this.suwako);
        this.add(this.pauseBoardOn);
        this.add(this.pauseBoardOff);
        this.add(this.scoreBoard);
        if (this.tipsBoard != null) {
            this.add(this.tipsBoard);
        }
    }

    public void update() {
        //游戏未结束的时候
        if (!this.isGameOver) {
            if (!this.isShowingTips && !this.isShowingResult) {
                //更新主角逻辑
                this.suwako.update();
                float x = this.gameView.getSensorX();
                this.suwako.setMoveStepX(x);
                if (this.suwako.isDead) {
                    this.notifyGameOver();
                }
                //主角超过中线的情况
                if (this.suwako.isOverLine && !this.boardsFallingDown) {
                    //计算得分
                    int increaseScore = this.suwako.getDuration() / 10 * 10;
                    this.gameScore += increaseScore;
                    this.scoreBoard.increaseScore(increaseScore);
                    //设置所有板子下降
                    for (int i = 0; i < this.boardsList.size(); i++) {
                        Board checkBoard = this.boardsList.get(i);
                        checkBoard.setFallingDown(increaseScore / 3 * 2, this.suwako.getFallingTime());
                        this.boardsFallingDown = true;
                    }
                }
                //执行所有板子的碰撞检测
                boolean fallingUncompleted = false;
                for (int i = this.boardsList.size() - 1; i >= 0; i--) {
                    Board checkBoard = this.boardsList.get(i);
                    checkBoard.update();
                    //检测是否所有板子都下降完毕
                    if (this.boardsFallingDown) {
                        fallingUncompleted |= checkBoard.isDownMoving;
                    } else {
                        this.suwako.checkLanding(checkBoard);
                    }
                }
                //板子下降完毕，则通知主角继续运动
                if (this.boardsFallingDown && !fallingUncompleted) {
                    this.suwako.isOverLine = false;
                    this.suwako.currentFrameX = 20 / 2; //20 == this.suwako.sheetSize.x
                    this.boardsFallingDown = false;
                }
            }
        } else {
            //游戏结束逻辑
            for (int i = 0; i < this.boardsList.size(); i++) {
                this.boardsList.get(i).update();
            }
            if (this.gameOverText.getPosition().y > 0) {
                this.gameOverText.getPosition().y -= 40;
            } else {
//                this.highScoreSprite.setVisible(true);
//                this.resultScoreSprite.setVisible(true);
            }
        }
        //更新显示分数
        this.scoreBoard.update();
        if (this.isShowingTips) {
            if (this.tipsBoard != null) {
                this.tipsBoard.update();
            } else {
                this.hideTipsBoard();
            }
        }
        if (this.isShowingResult) {
            this.resultBoard.update();
        }
        //释放所有待清除的精灵的内存
        this.flushSpriteList();
    }

    public void setSuwakoAttack(Point point) {
        this.suwako.attack(point);
    }

    /*
     * 通知过关，进行相关处理
     */
    public void notifyStageClear() {
        this.isShowingResult = true;
        //获取关卡结束时间
        this.endTime = new Date().getTime();
        //初始化提示板
        this.resultBoard = new ResultBoard(
                this.bmpHashMap.get("result_board"),
                this.bmpHashMap.get("star_level"),
                new Point((int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.125),
                        -SuwakoJumpActivity.DISPLAY_HEIGHT / 2),
                this.gameScore, this.endTime - this.startTime, this.totalBoardNum);
        this.add(this.resultBoard);
    }

    /*
     * 通知游戏结束，进行相关处理
     */
    private void notifyGameOver() {
        this.isGameOver = true;
        for (int i = 0; i < this.boardsList.size(); i++) {
            this.boardsList.get(i).isUpMoving = true;
        }
        SoundHelper.play(SoundHelper.SoundMap.get("die"), false);
    }

    public int getStageNum() {
        return this.stageNum;
    }

    /*
    * 获取精灵列表
    * @return 精灵列表
    */
    public ArrayList<JSprite> getSpritesList() {
        return this.spritesList;
    }

    /*
     * 查询游戏是否结束
     * @return 游戏是否结束
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }

    /*
     * 查询游戏是否暂停
     * @return 游戏是否暂停
     */
    public boolean isPause() {
        return this.isPause;
    }

    /*
     * 暂停或恢复游戏
     */
    public void pauseResume() {
        this.isPause = !this.isPause;
        if (this.isPause) {
            if (this.canPlaySound) {
                this.pauseBoardOn.show();
            } else {
                this.pauseBoardOff.show();
            }
            SoundHelper.play(SoundHelper.SoundMap.get("pause"), false);
        } else {
            this.pauseBoardOn.hide();
            this.pauseBoardOff.hide();
        }
    }

    /*
    * 查询游戏是否正在展示TIPS板
    * @return 游戏是否正在展示TIPS板
    */
    public boolean isShowingTips() {
        return this.isShowingTips;
    }

    /*
    * 查询游戏是否正在展示游戏结果板
    * @return 游戏是否正在展示游戏结果板
    */
    public boolean isShowingResult() {
        return this.isShowingResult;
    }

    /*
     * 隐藏TIPS板
     */
    public void hideTipsBoard() {
        if (this.tipsBoard != null) {
            this.tipsBoard.hide();
        }
        this.isShowingTips = false;
        this.startTime = new Date().getTime();
    }

    /*
     * 获取TIPS板上的OK按钮矩形区域
     */
    public Rect getTipsBoardButtonRect() {
        return this.tipsBoard.getRect();
    }

    public Rect getPauseButtonRect() {
        return new Rect(
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.4854),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.025),
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5625),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.07));
    }

    /*
     * 获取结果分数板上的两个按钮矩形区域列表
     */
    public ArrayList<Rect> getResultBoardButtonRect() {
        ArrayList<Rect> rectsList = new ArrayList<Rect>();
        rectsList.add(this.resultBoard.getRestartRect());
        rectsList.add(this.resultBoard.getNextStageRect());
        return rectsList;
    }

    /*
    * 获取游戏结束板上的两个按钮矩形区域列表
    */
    public ArrayList<Rect> getGameoverButtonRect() {
        ArrayList<Rect> rectsList = new ArrayList<Rect>();
        rectsList.add(new Rect(
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.3063),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.4675),
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.6812),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.525)));
        rectsList.add(new Rect(
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.3063),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.57),
                (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.6812),
                (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.64)));
        return rectsList;
    }

    public void turnVolumn() {
        this.canPlaySound = SoundHelper.turnVolumn();
        if (this.canPlaySound) {
            SoundHelper.resume();
            this.pauseBoardOn.show();
            this.pauseBoardOff.hide();
        } else {
            SoundHelper.pause();
            this.pauseBoardOn.hide();
            this.pauseBoardOff.show();
        }
    }

    /*
    * 将精灵添加入精灵列表
    * @param sprite 要添加的精灵
    */
    private void add(JSprite sprite) {
        this.spritesList.add(sprite);
    }

    private void flushSpriteList() {
        Iterator<JSprite> it = this.spritesList.iterator();
        while (it.hasNext()) {
            JSprite sprite = it.next();
            if (sprite.isToDisposed()) {
                it.remove();
//                sprite = null;
                if (sprite != null) {
                    if (this.spritesList.contains(sprite)) {
                        this.spritesList.remove(sprite);
                    }
                    if (this.boardsList.contains(sprite)) {
                        this.boardsList.remove(sprite);
                    }
                }
            }
        }
    }
}
