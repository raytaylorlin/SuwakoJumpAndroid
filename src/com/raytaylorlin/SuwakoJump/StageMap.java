package com.raytaylorlin.SuwakoJump;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Lib.RandomHelper;
import com.raytaylorlin.SuwakoJump.Sprites.Board.*;
import com.raytaylorlin.SuwakoJump.Sprites.Item.*;

import java.util.ArrayList;
import java.util.HashMap;

public class StageMap {
    private static HashMap<String, Bitmap> BmpHashMap;
    private static int DW = SuwakoJumpActivity.DISPLAY_WIDTH;
    private static int DH = SuwakoJumpActivity.DISPLAY_HEIGHT;
    private static int BOARD_X;
    private static int BASE_INTERVAL;

    private StageMap() {
    }

    public static void setBitmap(HashMap<String, Bitmap> bmpHashMap) {
        BmpHashMap = bmpHashMap;
        BOARD_X = bmpHashMap.get("board_normal").getWidth();
        BASE_INTERVAL = bmpHashMap.get("suwako_jump").getHeight() / 3;
    }

    /*
     * 获取新类型的板子
     * @param y
     * @return 新产生的板子
     */
    private Board getNewTypeBoard(int y) {
        Board newBoard = null;
        Item newItem = null;
        //随机计算板子的位置
        int boardX = RandomHelper.getRandom(DW - BOARD_X);
        int boardY = SuwakoJumpActivity.DISPLAY_HEIGHT - (y + 1) * 80;
//        int boardY = MainGame.GAME_FIELD_HEIGHT + MainGame.GAME_SCORE_BAR_HEIGHT - (y + 1) * 40;

        float boardTypeFloat = RandomHelper.getRandom();
//        newBoard = new NormalBoard(bmpBoard, new Point(boardX, boardY));
        if (boardTypeFloat < 0.1) {
//            newBoard = new VanishBoard(BmpBoard, new Point(boardX, boardY));
        } else if (boardTypeFloat < 0.3) {
//            newBoard = new BrokenBoard(BmpBoard, new Point(boardX, boardY));
        } else if (boardTypeFloat < 0.5) {
            boolean isVertical = RandomHelper.getRandom() < 0.5;
//            newBoard = new MovingBoard(BmpBoard, new Point(boardX, boardY), isVertical);
        } else {
//            newBoard = new NormalBoard(BmpBoard, new Point(boardX, boardY));
        }

        //设置道具
        float itemTypeFloat = RandomHelper.getRandom();
        Point bPos = newBoard.getPosition();
        Point bSize = newBoard.getSize();
//        newItem = new Spring(bmpItemSpring,
//                new Point(RandomHelper.getRandom(bPos.x,
//                        bPos.x + bSize.x - bmpItemSpring.getWidth()),
//                        bPos.y - bmpItemSpring.getHeight()));
        newBoard.setItem(newItem);

        return newBoard;
    }

    private static int getBoardX() {
        return RandomHelper.getRandom(DW - BOARD_X);
    }

    public static ArrayList<Board> getStageMap(int stageNum) {
        ArrayList<Board> boardList = new ArrayList<Board>();
        int TOTAL_NUM;
        switch (stageNum) {
            case 1:
                TOTAL_NUM = 5;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
//                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL);
                    int by_offset = 0;
                    Board newBoard = new NormalBoard(
                            BmpHashMap.get("board_normal"),
                            new Point(getBoardX(), by - by_offset));
                    boardList.add(newBoard);
                }

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }
        boardList.add(new FlagBoard(
                BmpHashMap.get("board_flag"),
                new Point(getBoardX(), DH - (boardList.size() + 2) * BASE_INTERVAL)));
        return boardList;
    }
}
