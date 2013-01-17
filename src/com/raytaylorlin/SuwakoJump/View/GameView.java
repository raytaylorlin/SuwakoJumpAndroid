package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.*;
import com.raytaylorlin.SuwakoJump.Lib.ImageHelper;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    //背景图片
    private Bitmap bmpBackground;
    private Bitmap bmpScoreBoard;
    private Bitmap bmpSuwako;
    private Bitmap bmpBoard;
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

        //调整图片尺寸
        this.bmpBackground = Bitmap.createScaledBitmap(this.bmpBackground,
                SuwakoJumpActivity.DISPLAY_WIDTH, SuwakoJumpActivity.DISPLAY_HEIGHT, true);
        this.bmpScoreBoard = ImageHelper.adjustScaleImage(this.bmpScoreBoard);
        this.bmpSuwako = ImageHelper.adjustScaleImage(this.bmpSuwako);
        this.bmpBoard = ImageHelper.adjustScaleImage(this.bmpBoard);

        //建立字符串和图片的映射
        this.bmpHashMap = new HashMap<String, Bitmap>();
        this.bmpHashMap.put("background", this.bmpBackground);
        this.bmpHashMap.put("suwako", this.bmpSuwako);
        this.bmpHashMap.put("board", this.bmpBoard);
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
        canvas.drawBitmap(this.bmpScoreBoard, 0, 0, this.mainPaint);
    }

    @Override
    public void update() {
        this.gameLogic.update();
    }

    public float getSensorX() {
        return this.mainActivity.getSensorX();
    }
}
