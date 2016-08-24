package jp.tonkatu05.ballgame.game;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.tonkatu05.ballgame.util.CsvManager;

public class GameManager {

    private SurfaceHolder mHolder;
    private Resources mResources;
    private long mTime = 0;
    private Ball[] mBalls;
    private Map mMap;
    private float[] mAccelerometer = new float[3];
    private AssetManager mAssetManager;
    private CollisionManager mCollisionManager;
    private long mDelta;
    private long mStartTime;
    private long mCurrentTime;

    public GameManager(SurfaceHolder holder, Resources resources){
        mHolder = holder;
        mResources = resources;
        int[][] ballData =  fetchFromCsv("ball.csv");
        mBalls = new Ball[ballData.length];
        for(int i=0; i < ballData.length; i++){
            mBalls[i] = new Ball(ballData[i][0], ballData[i][1], ballData[i][2]);
        }
        mMap = new Map(fetchFromCsv("line.csv"));
        mCollisionManager = new CollisionManager(mBalls, mMap);

        mStartTime = System.currentTimeMillis();
    }

    public void loop(){
        Canvas canvas = mHolder.lockCanvas();
        Paint paint = new Paint();
        if(canvas != null){
            draw(canvas);
            paint.setTextSize(50);
            canvas.drawText("経過時間 :" + String.valueOf(mCurrentTime), 1200, 50, paint);
            if(isGameEnd(mBalls)){
                paint.setTextSize(200);
                canvas.drawText("Game Clear", 200, 400, paint);
            }
            mHolder.unlockCanvasAndPost(canvas);
        }
        mDelta = System.currentTimeMillis() - mTime;
        mTime = System.currentTimeMillis();
        if(!isGameEnd(mBalls)) mCurrentTime = mTime - mStartTime;

        move(mAccelerometer, mDelta);
        mCollisionManager.collision();
    }

    public void setAccelerometer(float[] accelerometer) {
        this.mAccelerometer = accelerometer;
    }

    public void onTouchEvent(MotionEvent event){
        for(int i = 0; i < mBalls.length; i++) {
            mBalls[i].jump();
        }
    }

    public void registerPositionLimit(int width, int height){
        for(int i = 0; i < mBalls.length; i++){
            mBalls[i].setPositionLimitX(width);
            mBalls[i].setPositionLimitY(height);
        }
    }

    private void move(float[] accelerometer, long delta){
        for(int i = 0; i < mBalls.length; i++) {
            mBalls[i].move(accelerometer, delta);
        }
    }

    private void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        for(int i = 0; i < mBalls.length; i++) {
            mBalls[i].draw(canvas);
        }
        mMap.draw(canvas);

        //debug info
        Paint paint = new Paint();
        paint.setTextSize(50);
        canvas.drawText("ax : "+String.valueOf(mAccelerometer[0]), 10, 100, paint);
        canvas.drawText("ay : "+String.valueOf(mAccelerometer[1]), 10, 180, paint);
        canvas.drawText("az : "+String.valueOf(mAccelerometer[2]), 10, 260, paint);
//        Log.d("GameManager", "delta : "+String.valueOf(mDelta));
    }

    private int[][] fetchFromCsv(String filename) {
        try {
            mAssetManager = mResources.getAssets();
            InputStream is = mAssetManager.open(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(inputStreamReader);
            return CsvManager.get2DArrayFromCsv(br);
        }
        catch (IOException e){
            e.printStackTrace();
            return new int[0][0];
        }
    }

    private boolean isGameEnd(Ball[] balls){
        for(int i = 0; i < balls.length; i++){
            if(!balls[i].getGoalFlag()) return false;
        }
        return true;
    }

}
