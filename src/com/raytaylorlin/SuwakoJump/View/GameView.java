package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.*;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;
import com.raytaylorlin.SuwakoJump.Sprites.Suwako;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    private TutorialThread gameThread;

    //背景图片
    private Bitmap bmpBackground;
    private Bitmap bmpSuwako;
    private Bitmap bmpBoard;
    private HashMap<String, Bitmap> bmpHashMap;

    private GameLogic gameLogic;

    public GameView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.gameLogic = new GameLogic(this, this.bmpHashMap);
        this.gameThread = new GameViewThread(getHolder(), this);
        this.gameThread.start();
        this.viewIndex = SuwakoJumpActivity.GAME_VIEW_INDEX;
    }

    @Override
    protected void initBitmap() {
        this.bmpBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_view_background);
        this.bmpBackground = Bitmap.createScaledBitmap(this.bmpBackground,
                SuwakoJumpActivity.DISPLAY_WIDTH, SuwakoJumpActivity.DISPLAY_HEIGHT, true);
        this.bmpSuwako = BitmapFactory.decodeResource(getResources(),
                R.drawable.suwako_jump);
        this.bmpSuwako = Bitmap.createScaledBitmap(this.bmpSuwako,
                (int) (this.bmpSuwako.getWidth() * SuwakoJumpActivity.X_SCALE_FACTOR),
                (int) (this.bmpSuwako.getHeight() * SuwakoJumpActivity.Y_SCALE_FACTOR), true);
        this.bmpBoard = BitmapFactory.decodeResource(getResources(),
                R.drawable.board);
        this.bmpBoard = Bitmap.createScaledBitmap(this.bmpBoard,
                (int) (this.bmpBoard.getWidth() * SuwakoJumpActivity.X_SCALE_FACTOR),
                (int) (this.bmpBoard.getHeight() * SuwakoJumpActivity.Y_SCALE_FACTOR), true);

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
    }

    @Override
    public void update() {
        this.gameLogic.update();
    }

    public float getSensorX() {
        return this.mainActivity.getSensorX();
    }
}
