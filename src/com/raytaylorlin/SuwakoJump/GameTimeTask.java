package com.raytaylorlin.SuwakoJump;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

public class GameTimeTask extends TimerTask {
    private Handler handler;


    public GameTimeTask(GameLogic gameLogic, Handler handler) {
//        this.gameLogic = gameLogic;
        this.handler = handler;
    }

    public void run() {
//        this.draw();
        Message message = new Message();
        message.what = SuwakoJumpActivity.MSG_REFRESH;
        this.handler.sendMessage(message);
    }

    private void draw() {
//        this.canvasPanel.paintComponent(this.canvasPanel.getGraphics());
//        this.canvasPanel.repaint();
    }

    private void update() {
//        this.gameLogic.update();
    }

//    private void remove(SuwakoJump.Lib.JSprite sprite) {
//        if (this.spritesVector.contains(sprite)) {
//            this.spritesVector.removeElement(sprite);
//        }
//    }
}