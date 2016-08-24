package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    final String TAG = "Ball";
    private int mSize;
    private int mSpeedX;
    private int mSpeedY;
    private int mPositionX;
    private int mPositionY;
    private int mSpeedLimitX = 500;
    private int mSpeedLimitY = 500;
    private int mJumpSpeed = -500;
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
        mPositionX += (int) ((delta / 1000.0) * mSpeedX);
        if(!mCollisionFlag) {
            mPositionY += (int) ((delta / 1000.0) * mSpeedY);
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

    public void jump(){
        mSpeedY += mJumpSpeed;
    }

    private void accele(float[] accelerometer) {
        mSpeedX += accelerometer[1] * 5;
        if(!mCollisionFlag){
            mSpeedY += accelerometer[0] * 5;
        } else{
            mSpeedY = 0;
        }

        if (mSpeedX < -mSpeedLimitX) {
            mSpeedX = -mSpeedLimitX;
        } else if (mSpeedX > mSpeedLimitX) {
            mSpeedX = mSpeedLimitX;
        }

        if (mSpeedY > mSpeedLimitY) {
            mSpeedY = mSpeedLimitY;
        } else if (mSpeedY < -mSpeedLimitY) {
            mSpeedY = -mSpeedLimitY;
        }
    }

}
