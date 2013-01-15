package com.raytaylorlin.SuwakoJump.View;

import android.graphics.*;
import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.GameViewThread;
import com.raytaylorlin.SuwakoJump.R;
import com.raytaylorlin.SuwakoJump.Sprite.Suwako;
import com.raytaylorlin.SuwakoJump.SuwakoJumpActivity;
import com.raytaylorlin.SuwakoJump.TutorialThread;

public class GameView extends CommonView implements SurfaceHolder.Callback {
    private TutorialThread gameThread;

    //背景图片
    private Bitmap bmpBackground;
    private Bitmap bmpSuwako;

    private Suwako suwako;

    public GameView(SuwakoJumpActivity mainActivity) {
        super(mainActivity);
        this.gameThread = new GameViewThread(getHolder(),this);
        this.gameThread.start();
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
    }

    @Override
    protected void initSprite() {
        int suwakoX = (int) (SuwakoJumpActivity.DISPLAY_WIDTH * 0.5);
        int suwakoY = (int) (SuwakoJumpActivity.DISPLAY_HEIGHT * 0.7);
        int suwakoW = this.bmpSuwako.getWidth() / 20;
        int suwakoH = this.bmpSuwako.getHeight() / 2;

        this.suwako = new Suwako(this.bmpSuwako,
                new Point(suwakoX, suwakoY),
                new Point(suwakoW, suwakoH));
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.bmpBackground, 0, 0, this.mainPaint);
        this.suwako.draw(canvas, this.mainPaint);
    }

    @Override
    public void update() {
        this.suwako.update();
    }

}
