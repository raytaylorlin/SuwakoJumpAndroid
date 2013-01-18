package com.raytaylorlin.SuwakoJump;

//import SuwakoJump.Lib.RandomHelper;
//import SuwakoJump.Sprites.*;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Lib.FileHelper;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Lib.RandomHelper;
import com.raytaylorlin.SuwakoJump.Sprites.*;
import com.raytaylorlin.SuwakoJump.Sprites.Board.*;
import com.raytaylorlin.SuwakoJump.Sprites.Item.Item;
import com.raytaylorlin.SuwakoJump.Sprites.Item.Spring;
import com.raytaylorlin.SuwakoJump.View.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameLogic {
    private GameView gameView;

    private HashMap<String, Bitmap> bmpHashMap = new HashMap<String, Bitmap>();
    private ArrayList<JSprite> spritesList = new ArrayList<JSprite>();
    private ArrayList<Board> boardsList = new ArrayList<Board>();


    private JSprite scoreBoard, gameOverText;
    private CountScore countScore,resultScoreSprite,highScoreSprite;
    private Suwako suwako;

    private int gameScore = 0;
    private boolean isGameOver = false;
    private boolean boardsFallingDown = false;

    public GameLogic(GameView gameView, HashMap<String, Bitmap> bmpHashMap) {
        this.gameView = gameView;
        this.bmpHashMap = bmpHashMap;
        this.initSprite();
    }

    /*
     * 初始化精灵
     */
    private void initSprite() {
        this.spritesList.clear();
        this.boardsList.clear();

        //初始化suwako
        int suwakoX = (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5);
        int suwakoY = (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.8);
        Bitmap bmpSuwako = this.bmpHashMap.get("suwako");
        int suwakoW = bmpSuwako.getWidth() / 20;
        int suwakoH = bmpSuwako.getHeight() / 2;
        this.suwako = new Suwako(bmpSuwako,
                new Point(suwakoX, suwakoY),
                new Point(suwakoW, suwakoH));

        //初始化板子
        for (int i = 0; i < 100; i++) {
            Board newBoard = this.getNewTypeBoard(i);
            this.boardsList.add(newBoard);
            this.add(newBoard);
        }

        //初始化游戏结束文字
        this.gameOverText = new JSprite(
                this.bmpHashMap.get("game_over_text"),
                new Point(0, SuwakoJumpActivity.DISPLAY_HEIGHT));

        this.scoreBoard = new JSprite(
                this.bmpHashMap.get("score_board"),
                new Point(0, 0));

        //初始化分数精灵
        Bitmap bmpNumber = this.bmpHashMap.get("number");
        this.countScore = new CountScore(bmpNumber,
                new Point((int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.2917),
                        (int) (bmpNumber.getHeight() * 0.2)));

        this.add(this.gameOverText);
        this.add(this.suwako);
        this.add(this.scoreBoard);
        this.add(this.countScore);
    }

    public void update() {
        //游戏未结束的时候
        if (!this.isGameOver) {
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
                this.countScore.increaseScore(increaseScore);
                //设置所有板子下降
                for (int i = 0; i < this.boardsList.size(); i++) {
                    Board checkBoard = this.boardsList.get(i);
                    checkBoard.setFallingDown(increaseScore, this.suwako.getFallingTime());
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
        } else {
            //游戏结束逻辑
            for (int i = 0; i < this.boardsList.size(); i++) {
                this.boardsList.get(i).update();
            }
            if (this.gameOverText.getPosition().y > 0) {
                this.gameOverText.getPosition().y -= 40;
            } else {
                this.highScoreSprite.setVisible(true);
                this.resultScoreSprite.setVisible(true);
            }
        }
        //更新显示分数
        this.countScore.update();
        //释放所有待清除的精灵的内存
        this.flushSpriteList();
    }

    public void setSuwakoAttack(Point point) {
        this.suwako.attack(point);
    }

    /*
     * 通知游戏结束，进行相关处理
     */
    private void notifyGameOver() {
        this.isGameOver = true;
        for (int i = 0; i < this.boardsList.size(); i++) {
            this.boardsList.get(i).isUpMoving = true;
        }
        //显示得分和最高分
        this.showResultScore();
    }

    /*
     * 在Gameover界面中显示得分和最高分
     */
    private void showResultScore() {
        FileHelper fileHelper = new FileHelper(this.gameView.getContext());
        String scoreStr = String.valueOf(this.gameScore) + "\n";
        //将分数写入到排行榜文件中
        fileHelper.writeFile("score1.dat", scoreStr);
        //读取排行榜文件，找出最大分数
        String readContent = fileHelper.readFile("score1.dat");
        String[] scoreStrArray = readContent.split("\n");
        int highScore = 0;
        for (String s : scoreStrArray) {
            int number = Integer.parseInt(s);
            if (number > highScore) {
                highScore = number;
            }
        }
        Bitmap bmpNumber = this.bmpHashMap.get("number");
        double xScale = SuwakoJumpActivity.X_SCALE_FACTOR;
        double yScale = SuwakoJumpActivity.Y_SCALE_FACTOR;
        this.resultScoreSprite = new CountScore(bmpNumber,
                new Point((int) (480 * 0.6 * xScale),
                        (int) (800 * 0.285 * yScale)));
        this.resultScoreSprite.setFixedNumber(this.gameScore);
        this.resultScoreSprite.setVisible(false);
        this.highScoreSprite = new CountScore(bmpNumber,
                new Point((int) (480 * 0.6 * xScale),
                        (int) (800 * 0.34 * yScale)));
        this.highScoreSprite.setFixedNumber(highScore);
        this.highScoreSprite.setVisible(false);
        this.add(resultScoreSprite);
        this.add(highScoreSprite);
    }

    /*
     * 获取新类型的板子
     * @param y
     * @return 新产生的板子
     */
    private Board getNewTypeBoard(int y) {
        Bitmap bmpBoard = this.bmpHashMap.get("board");
        Board newBoard = null;
        Item newItem = null;
        //随机计算板子的位置
        int boardX = RandomHelper.getRandom(
                SuwakoJumpActivity.DISPLAY_WIDTH - bmpBoard.getWidth());
        int boardY = SuwakoJumpActivity.DISPLAY_HEIGHT - (y + 1) * 80;
//        int boardY = MainGame.GAME_FIELD_HEIGHT + MainGame.GAME_SCORE_BAR_HEIGHT - (y + 1) * 40;

        float boardTypeFloat = RandomHelper.getRandom();
//        newBoard = new NormalBoard(bmpBoard, new Point(boardX, boardY));
        if (boardTypeFloat < 0.1) {
            newBoard = new VanishBoard(bmpBoard, new Point(boardX, boardY));
        } else if (boardTypeFloat < 0.3) {
            newBoard = new BrokenBoard(bmpBoard, new Point(boardX, boardY));
        } else if (boardTypeFloat < 0.5) {
            boolean isVertical = RandomHelper.getRandom() < 0.5;
            newBoard = new MovingBoard(bmpBoard, new Point(boardX, boardY), isVertical);
        } else {
            newBoard = new NormalBoard(bmpBoard, new Point(boardX, boardY));
        }

        //设置道具
        float itemTypeFloat = RandomHelper.getRandom();
        Point bPos = newBoard.getPosition();
        Point bSize = newBoard.getSize();
        Bitmap bmpItemSpring = this.bmpHashMap.get("item_spring");
        newItem = new Spring(this.suwako, bmpItemSpring,
                new Point(RandomHelper.getRandom(bPos.x,
                        bPos.x + bSize.x - bmpItemSpring.getWidth()),
                        bPos.y - bmpItemSpring.getHeight()));
        newBoard.setItem(newItem);

        return newBoard;

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
     * @return 游戏是否接受
     */
    public boolean isGameOver() {
        return this.isGameOver;
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
