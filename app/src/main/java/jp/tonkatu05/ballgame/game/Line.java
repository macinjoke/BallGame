package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Line {

    private int mX, mY, mLength;

    public Line(int x, int y, int length){
        this.mX = x;
        this.mY = y;
        this.mLength = length;
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.drawRect(mX, mY, mX + mLength, mY +6, paint);
    }

    public int getX() {
        return mX;
    }

    public int getLength() {
        return mLength;
    }

    public int getY() {
        return mY;
    }
}
