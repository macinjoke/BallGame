package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by naoya on 2015/04/11.
 */
public class Ball {

    final String TAG = "Ball";
    private int mSize;
    private int mAccelerationX;
    private int mAccelerationY;
    private int mPositionX;
    private int mPositionY;
    private int mAccelerationLimitX = 500;
    private int mAccelerationLimitY = 500;
    private int mPositionLimitX;
    private int mPositionlimity;
    private boolean mCollisionFlag = false;
    private boolean mGoalFlag = false;

    public Ball(int x, int y, int size) {
        this.mSize = size;
        this.mPositionX = x;
        this.mPositionY = y;
    }

    public void setPositionLimitY(int positionLimitY) {
        this.mPositionlimity = positionLimitY - mSize;
    }

    public void setPositionLimitX(int positionLimitX) {
        this.mPositionLimitX = positionLimitX - mSize;
    }

    public int getAccelerationX() {
        return mAccelerationX;
    }

    public int getAccelerationY() {
        return mAccelerationY;
    }

    public int getPositionX() {
        return mPositionX;
    }

    public int getPositionY() {
        return mPositionY;
    }

    public int getSize() {
        return mSize;
    }

    public void setCollisionFlag(boolean collisionFlag) {
        this.mCollisionFlag = collisionFlag;
    }

    public boolean getGoalFlag(){
        return mGoalFlag;
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        Paint paint = new Paint();
        paint.setColor(Color.argb(150,255,50,50));
        canvas.drawCircle(mPositionX, mPositionY, mSize, paint);
    }


    public void move(float[] accelerometer, long delta) {
        if(mGoalFlag) return;
        accele(accelerometer);
        mPositionX += (int) ((delta / 1000.0) * mAccelerationX);
        if(!mCollisionFlag) {
            mPositionY += (int) ((delta / 1000.0) * mAccelerationY);
        }

        if (mPositionX < mSize) {
            mPositionX = mSize;
        } else if (mPositionX > mPositionLimitX) {
            mPositionX = mPositionLimitX;
        }

        if (mPositionY < mSize) {
            mPositionY = mSize;
        } else if (mPositionY > mPositionlimity) {
            mPositionY = mPositionlimity;
            mGoalFlag = true;
        }

    }

    private void accele(float[] accelerometer) {
        mAccelerationX += accelerometer[1] * 10;
        if(!mCollisionFlag){

            mAccelerationY += accelerometer[0] * 10;

        }

        if (mAccelerationX < -mAccelerationLimitX) {
            mAccelerationX = -mAccelerationLimitX;
        } else if (mAccelerationX > mAccelerationLimitX) {
            mAccelerationX = mAccelerationLimitX;
        }

        if (mAccelerationY > mAccelerationLimitY) {
            mAccelerationY = mAccelerationLimitY;
        } else if (mAccelerationY < -mAccelerationLimitY) {
            mAccelerationY = -mAccelerationLimitY;
        }
    }

}
