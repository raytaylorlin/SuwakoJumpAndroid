package com.raytaylorlin.SuwakoJump;

//import SuwakoJump.Lib.RandomHelper;
//import SuwakoJump.Sprites.*;

import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Sprite.Suwako;
import com.raytaylorlin.SuwakoJump.View.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameLogic {
    private GameView gameView;
//    private CanvasPanel canvasPanel;

    private ArrayList<JSprite> spritesList = new ArrayList<JSprite>();
//    private ArrayList<Board> boardsList = new ArrayList<Board>();

    private JSprite background, scoreBar, gameOverText;
    //    private CountScore countScore;
    private Suwako suwako;

    private int gameScore = 0;
    private boolean isGameOver = false;
    private boolean boardsFallingDown = false;

    public GameLogic(GameView gameView, Suwako suwako) {
        this.gameView = gameView;
        this.suwako = suwako;
        this.init();
    }

    private void init() {
        this.spritesList.clear();
//        this.boardsList.clear();
//        this.background = new Board(this.mainActivity, fieldImage, new Point(0, MainGame.GAME_SCORE_BAR_HEIGHT),
//                new Point(MainGame.GAME_FIELD_WIDTH, MainGame.GAME_FIELD_HEIGHT), new Point(1, 1), new Point(0, 0));
//        this.scoreBar = new Board(this.mainActivity, scoreBarImage, new Point(0, 0),
//                new Point(MainGame.GAME_FIELD_WIDTH, MainGame.GAME_SCORE_BAR_HEIGHT), new Point(1, 1), new Point(0, 0));
//        this.countScore = new CountScore(this.mainActivity, scoreNumberImage);
//        this.gameOverText = new Board(this.mainActivity, gameOverTextImage, new Point(0, MainGame.GAME_FIELD_HEIGHT + MainGame.GAME_SCORE_BAR_HEIGHT),
//                new Point(MainGame.GAME_FIELD_WIDTH, MainGame.GAME_FIELD_HEIGHT), new Point(1, 1), new Point(0, 0));
//        this.add(this.background);
//
//        this.suwako = new Suwako(this.mainActivity, suwakoImage,
//                new Point(MainGame.GAME_FIELD_WIDTH / 2, MainGame.GAME_FIELD_HEIGHT + MainGame.GAME_SCORE_BAR_HEIGHT - 128), new Point(64, 128), new Point(20, 1), new Point(0, 0));
//
//        for (int i = 0; i < 1000; i++) {
//            Board newBoard = this.getNewTypeBoard(i);
//            this.boardsList.add(newBoard);
//            this.add(newBoard);
//        }
        this.add(this.suwako);
        this.add(this.scoreBar);
//        this.add(this.countScore);
    }

    public void update() {
        //游戏未结束的时候
        if (!this.isGameOver) {
//            this.countScore.update();
            //更新主角逻辑
            this.suwako.update();
            float x = this.gameView.getSensorX();
            this.suwako.setSensorX(x);
            if (this.suwako.isDead) {
                this.notifyGameOver();
            }
            //主角超过中线，设置所有板子下降
            if (this.suwako.isOverLine && !this.boardsFallingDown) {
//                int increaseScore = this.suwako.getDuration();
//                this.countScore.increaseScore(increaseScore / 10 * 10);
//                for (int i = 0; i < this.boardsList.size(); i++) {
//                    Board checkBoard = this.boardsList.get(i);
//                    checkBoard.setFallingDown(increaseScore, this.suwako.getFallingTime());
//                    this.boardsFallingDown = true;
//                }
            }
            //所有板子的下降逻辑
            boolean fallingUncompleted = false;
//            for (int i = this.boardsList.size() - 1; i >= 0; i--) {
//                Board checkBoard = this.boardsList.get(i);
//                checkBoard.update();
//                //检测是否所有板子都下降完毕
//                if (this.boardsFallingDown) {
//                    fallingUncompleted |= checkBoard.isDownMoving;
//                } else {
//                    this.suwako.checkLanding(checkBoard);
//                }
//            }
            //板子下降完毕，则通知主角继续运动
//            if (this.boardsFallingDown && !fallingUncompleted) {
//                this.suwako.isOverLine = false;
//                this.suwako.currentFrameX = this.suwako.sheetSize.x / 2;
//                this.boardsFallingDown = false;
//            }
            //游戏结束逻辑
        } else {
//            for (int i = 0; i < this.boardsList.size(); i++) {
//                this.boardsList.get(i).update();
//            }
//            if (this.gameOverText.drawPosition.y > MainGame.GAME_SCORE_BAR_HEIGHT) {
//                this.gameOverText.drawPosition.y -= 40;
//            } else {
//                this.countScore.setNewPosition(new Point(223,168));
//                if (CanvasPanel.EnterKeyPressed) {
//                    CanvasPanel.EnterKeyPressed = false;
//                    this.gameOverText.toDisposed = true;
//
//                    this.mainActivity.start();
//                }
//            }
        }
        //释放所有待清除的精灵的内存
        this.flushSpriteList();
    }

    private void notifyGameOver() {
//        this.isGameOver = true;
//        for (int i = 0; i < this.boardsList.size(); i++) {
//            this.boardsList.get(i).isUpMoving = true;
//        }

        this.add(this.gameOverText);
    }

    private void add(JSprite sprite) {
        this.spritesList.add(sprite);
//        this.canvasPanel.add(sprite);
    }

    private void flushSpriteList() {
        Iterator<JSprite> it = this.spritesList.iterator();
//        while (it.hasNext()) {
//            JSprite sprite = it.next();
//            if (sprite.toDisposed) {
//                it.remove();
////                sprite = null;
//                if (sprite != null) {
//                    if (this.spritesList.contains(sprite)) {
//                        this.spritesList.remove(sprite);
//                    }
//                    if (this.boardsList.contains(sprite)) {
//                        this.boardsList.remove(sprite);
//                    }
//                    this.canvasPanel.remove(sprite);
//                }
//            }
//        }

    }

//    private Board getNewTypeBoard(int y) {
//        Board newBoard = null;
//        int boardX = RandomHelper.getRandom(320);
//        int boardY = MainGame.GAME_FIELD_HEIGHT + MainGame.GAME_SCORE_BAR_HEIGHT - (y + 1) * 40;
//        float boardTypeFloat = RandomHelper.getRandom();
//        if (boardTypeFloat < 0.1) {
//            newBoard = new VanishBoard(this.mainActivity, fieldImage, new Point(boardX, boardY));
//        } else if (boardTypeFloat < 0.3) {
//            newBoard = new BrokenBoard(this.mainActivity, fieldImage, new Point(boardX, boardY));
//        } else if (boardTypeFloat < 0.5) {
//            boolean isVertical = RandomHelper.getRandom() < 0.5;
//            newBoard = new MovingBoard(this.mainActivity, fieldImage, new Point(boardX, boardY), isVertical);
//        } else {
//            newBoard = new NormalBoard(this.mainActivity, fieldImage, new Point(boardX, boardY));
//        }
//        return newBoard;
//    }
}
