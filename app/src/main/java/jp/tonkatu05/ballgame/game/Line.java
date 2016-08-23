package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Line {

    private int x,y,length;

    public Line(int x, int y, int length){
        this.x = x;
        this.y = y;
        this.length = length;
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.drawRect(x, y, x+length, y+6, paint);
    }

    public int getX() {
        return x;
    }

    public int getLength() {
        return length;
    }

    public int getY() {
        return y;
    }
}
