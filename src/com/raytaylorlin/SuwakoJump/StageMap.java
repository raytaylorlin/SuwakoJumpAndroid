package com.raytaylorlin.SuwakoJump;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Lib.RandomHelper;
import com.raytaylorlin.SuwakoJump.Sprites.Board.*;
import com.raytaylorlin.SuwakoJump.Sprites.Item.*;

import java.util.ArrayList;
import java.util.HashMap;

public class StageMap {
    private static Bitmap bmpNormalBoard, bmpBrokenBoard,
            bmpMovingBoard, bmpVanishBoard, bmpFlagBoard,
            bmpItemSpring;
    private static int DW = SuwakoJumpActivity.DISPLAY_WIDTH;
    private static int DH = SuwakoJumpActivity.DISPLAY_HEIGHT;
    private static int BOARD_X;
    private static int BASE_INTERVAL;

    private StageMap() {
    }

    public static void setBitmap(HashMap<String, Bitmap> bmpHashMap) {
        bmpNormalBoard = bmpHashMap.get("board_normal");
        bmpBrokenBoard = bmpHashMap.get("board_broken");
        bmpMovingBoard = bmpHashMap.get("board_moving");
        bmpVanishBoard = bmpHashMap.get("board_vanish");
        bmpFlagBoard = bmpHashMap.get("board_flag");
        bmpItemSpring = bmpHashMap.get("item_spring");
        BOARD_X = bmpHashMap.get("board_normal").getWidth();
        BASE_INTERVAL = bmpHashMap.get("suwako_jump").getHeight() / 4;
    }

    private static int getBoardX() {
        return RandomHelper.getRandom(DW - BOARD_X);
    }

    public static ArrayList<Board> getStageMap(int stageNum) {
        ArrayList<Board> boardList = new ArrayList<Board>();
        int TOTAL_NUM = 0;
        switch (stageNum) {
            case 1:
                TOTAL_NUM = 50;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
                    int by_offset = 0;
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard = new NormalBoard(bmpNormalBoard, pos);
                    boardList.add(newBoard);
                }
                break;
            case 2:
                TOTAL_NUM = 70;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL / 2);
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard;
                    float r = RandomHelper.getRandom();
                    if (r < 0.7) {
                        newBoard = new NormalBoard(bmpNormalBoard, pos);
                    } else {
                        newBoard = new BrokenBoard(bmpBrokenBoard, pos);
                        r = RandomHelper.getRandom();
                        if (r < 0.5) {
                            boardList.add(new NormalBoard(bmpNormalBoard,
                                    new Point(getBoardX(), by -
                                            RandomHelper.getRandom(BASE_INTERVAL / 2, BASE_INTERVAL))));
                        }
                    }
                    boardList.add(newBoard);
                }
                break;
            case 3:
                TOTAL_NUM = 70;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL / 2);
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard;
                    float r = RandomHelper.getRandom();
                    if (r < 0.5) {
                        newBoard = new NormalBoard(bmpNormalBoard, pos);
                    } else if (r < 0.7) {
                        newBoard = new BrokenBoard(bmpBrokenBoard, pos);
                        r = RandomHelper.getRandom();
                        if (r < 0.5) {
                            boardList.add(new NormalBoard(bmpNormalBoard,
                                    new Point(getBoardX(), by -
                                            RandomHelper.getRandom(BASE_INTERVAL / 2, BASE_INTERVAL))));
                        }
                    } else {
                        newBoard = new VanishBoard(bmpVanishBoard, pos);
                    }
                    boardList.add(newBoard);
                }
                break;
            case 4:
                TOTAL_NUM = 100;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL / 2);
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard;
                    float r = RandomHelper.getRandom();
                    if (r < 0.6) {
                        newBoard = new NormalBoard(bmpNormalBoard, pos);
                    } else if (r < 0.8) {
                        newBoard = new BrokenBoard(bmpBrokenBoard, pos);
                        r = RandomHelper.getRandom();
                        if (r < 0.3) {
                            boardList.add(new NormalBoard(bmpNormalBoard,
                                    new Point(getBoardX(), by -
                                            RandomHelper.getRandom(BASE_INTERVAL / 2, BASE_INTERVAL))));
                        }
                    } else {
                        newBoard = new VanishBoard(bmpVanishBoard, pos);
                    }
                    r = RandomHelper.getRandom();
                    if (r < 0.3) {
                        Point bPos = newBoard.getPosition();
                        Point bSize = newBoard.getSize();
                        Item newItem = new Spring(bmpItemSpring,
                                new Point(RandomHelper.getRandom(bPos.x,
                                        bPos.x + bSize.x - bmpItemSpring.getWidth()),
                                        bPos.y - bmpItemSpring.getHeight()));
                        newBoard.setItem(newItem);
                    }
                    boardList.add(newBoard);
                }
                break;
            case 5:
                TOTAL_NUM = 100;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL / 2);
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard;
                    float r = RandomHelper.getRandom();
                    if (r < 0.3) {
                        newBoard = new NormalBoard(bmpNormalBoard, pos);
                    } else if (r < 0.5) {
                        newBoard = new BrokenBoard(bmpBrokenBoard, pos);
                    } else if (r < 0.7) {
                        newBoard = new VanishBoard(bmpVanishBoard, pos);
                    } else {
                        boolean isVertical = RandomHelper.getRandom() < 0.5;
                        newBoard = new MovingBoard(bmpMovingBoard, pos, isVertical);
                    }
                    r = RandomHelper.getRandom();
                    if (r < 0.3) {
                        Point bPos = newBoard.getPosition();
                        Point bSize = newBoard.getSize();
                        Item newItem = new Spring(bmpItemSpring,
                                new Point(RandomHelper.getRandom(bPos.x,
                                        bPos.x + bSize.x - bmpItemSpring.getWidth()),
                                        bPos.y - bmpItemSpring.getHeight()));
                        newBoard.setItem(newItem);
                    }
                    boardList.add(newBoard);
                }
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                TOTAL_NUM = 10 * stageNum;
                for (int i = 0; i < TOTAL_NUM; i++) {
                    int by = DH - (i + 1) * BASE_INTERVAL;
//                    if (i > TOTAL_NUM / 2) {
//                        by = DH - (i + 1) * BASE_INTERVAL * 3 / 2;
//                    }
                    int by_offset = RandomHelper.getRandom(BASE_INTERVAL / 2);
                    Point pos = new Point(getBoardX(), by - by_offset);
                    Board newBoard;
                    float r = RandomHelper.getRandom();
                    if (r < 0.4) {
                        newBoard = new NormalBoard(bmpNormalBoard, pos);
                    } else if (r < 0.6) {
                        newBoard = new BrokenBoard(bmpBrokenBoard, pos);
                        if (r < 0.7) {
                            boardList.add(new NormalBoard(bmpNormalBoard,
                                    new Point(getBoardX(), by -
                                            RandomHelper.getRandom(BASE_INTERVAL / 2, BASE_INTERVAL))));
                        }
                    } else if (r < 0.8) {
                        newBoard = new VanishBoard(bmpVanishBoard, pos);
                    } else {
                        boolean isVertical = RandomHelper.getRandom() < 0.5;
                        newBoard = new MovingBoard(bmpMovingBoard, pos, isVertical);
                    }
                    r = RandomHelper.getRandom();
                    if (r < 0.3) {
                        Point bPos = newBoard.getPosition();
                        Point bSize = newBoard.getSize();
                        Item newItem = new Spring(bmpItemSpring,
                                new Point(RandomHelper.getRandom(bPos.x,
                                        bPos.x + bSize.x - bmpItemSpring.getWidth()),
                                        bPos.y - bmpItemSpring.getHeight()));
                        newBoard.setItem(newItem);
                    }
                    boardList.add(newBoard);
                }
                break;
        }
        boardList.add(new FlagBoard(bmpFlagBoard,
                new Point(getBoardX(), DH - (TOTAL_NUM + 2) * BASE_INTERVAL)));
        return boardList;
    }
}
