package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by naoya on 2015/04/11.
 */
public class Ball {

    final String TAG = "Ball";
    private int size;
    private int accelerationX;
    private int accelerationY;
    private int positionX;
    private int positionY;
    private int accelerationLimitX = 500;
    private int accelerationLimitY = 500;
    private int positionLimitX;
    private int positionLimitY;
    private boolean collisionFlag = false;
    private boolean goalFlag = false;

    public Ball(int x, int y, int size) {
        this.size = size;
        this.positionX = x;
        this.positionY = y;
    }

    public void setPositionLimitY(int positionLimitY) {
        this.positionLimitY = positionLimitY - size;
    }

    public void setPositionLimitX(int positionLimitX) {
        this.positionLimitX = positionLimitX - size;
    }

    public int getAccelerationX() {
        return accelerationX;
    }

    public int getAccelerationY() {
        return accelerationY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getSize() {
        return size;
    }

    public void setCollisionFlag(boolean collisionFlag) {
        this.collisionFlag = collisionFlag;
    }

    public boolean getGoalFlag(){
        return goalFlag;
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        Paint paint = new Paint();
        paint.setColor(Color.argb(150,255,50,50));
        canvas.drawCircle(positionX, positionY, size, paint);
    }


    public void move(float[] accelerometer, long delta) {
        if(goalFlag) return;
        accele(accelerometer);
        positionX += (int) ((delta / 1000.0) * accelerationX);
        if(!collisionFlag) {
            positionY += (int) ((delta / 1000.0) * accelerationY);
        }

        if (positionX < size) {
            positionX = size;
        } else if (positionX > positionLimitX) {
            positionX = positionLimitX;
        }

        if (positionY < size) {
            positionY = size;
        } else if (positionY > positionLimitY) {
            positionY = positionLimitY;
            goalFlag = true;
        }

    }

    private void accele(float[] accelerometer) {
        accelerationX += accelerometer[1] * 10;
        if(!collisionFlag){

            accelerationY += accelerometer[0] * 10;

        }

        if (accelerationX < -accelerationLimitX) {
            accelerationX = -accelerationLimitX;
        } else if (accelerationX > accelerationLimitX) {
            accelerationX = accelerationLimitX;
        }

        if (accelerationY > accelerationLimitY) {
            accelerationY = accelerationLimitY;
        } else if (accelerationY < -accelerationLimitY) {
            accelerationY = -accelerationLimitY;
        }
    }

}
