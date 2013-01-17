package com.raytaylorlin.SuwakoJump.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.raytaylorlin.SuwakoJump.Lib.JSprite;

import java.util.ArrayList;

public class CountScore extends JSprite {
    private static Point drawInitSize = new Point(16, 24);
    private static Point drawInitPoint = new Point(100, 7);

    private static Bitmap scoreNumberImage;
    private ArrayList<ScoreNumber> numberList = new ArrayList<ScoreNumber>();
    private int realScore, showNumber;

    public CountScore(Bitmap image,Point drawPosition) {
        super(image, drawPosition);
        drawInitPoint = drawPosition;
        this.scoreNumberImage = image;
        this.numberList.add(new ScoreNumber(image, drawInitPoint, 0));
    }

    public void draw(Canvas canvas,Paint paint) {
        for (int i = 0; i < this.numberList.size(); i++) {
            this.numberList.get(i).draw(canvas,paint);
        }
    }

    public void update() {
        if (this.showNumber < this.realScore) {
            this.showNumber += 10;
            int number = this.showNumber;
            int bitTotal = (int) (Math.log10(this.showNumber)) + 1;
            for (int i = 0; i < bitTotal; i++) {
                if (i < this.numberList.size()) {
                    this.numberList.get(i).setShowNumber(number % 10);
                    this.numberList.get(i).setBitIndex(bitTotal - i - 1);
                } else {
                    this.numberList.add(new ScoreNumber(this.scoreNumberImage,
                            drawInitPoint, number % 10));
                }
                number /= 10;
            }

        }
        for (int i = 0; i < this.numberList.size(); i++) {
            this.numberList.get(i).update();
        }
    }

    public void increaseScore(int n) {
        this.realScore += n;
    }

    public void setNewPosition(Point point) {
        for (int i = 0; i < this.numberList.size(); i++) {
            this.numberList.get(i).setPosition(point);
        }
    }
}
