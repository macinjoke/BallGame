package jp.tonkatu05.ballgame.game;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.tonkatu05.ballgame.util.CsvManager;

public class GameManager {

    private SurfaceHolder mHolder;
    private long mTime = 0;
    private Ball[] balls;
    private Map map;
    private float[] accelerometer = new float[3];
    private AssetManager assetManager;
    private CollisionManager collisionManager;
    private long delta;
    private long startTime;
    private long currentTime;

    public GameManager(SurfaceHolder holder, Resources resources){
        mHolder = holder;
        int[][] ballData =  fetchFromCsv("ball.csv", resources);
        balls = new Ball[ballData.length];
        for(int i=0; i < ballData.length; i++){
            balls[i] = new Ball(ballData[i][0], ballData[i][1], ballData[i][2]);
        }
        map = new Map(fetchFromCsv("line.csv", resources));
        collisionManager = new CollisionManager(balls, map);

        startTime = System.currentTimeMillis();
    }

    public void loop(){
        Canvas canvas = mHolder.lockCanvas();
        Paint paint = new Paint();
        if(canvas != null){
            draw(canvas);
            paint.setTextSize(50);
            canvas.drawText("経過時間 :" + String.valueOf(currentTime), 1200, 50, paint);
            if(isGameEnd(balls)){
                paint.setTextSize(200);
                canvas.drawText("げーむくりあー", 200, 400, paint);
            }
            mHolder.unlockCanvasAndPost(canvas);
        }
        delta = System.currentTimeMillis() - mTime;
        mTime = System.currentTimeMillis();
        if(!isGameEnd(balls)) currentTime = mTime - startTime;

        move(accelerometer, delta);
        collisionManager.collision();
    }

    public void setAccelerometer(float[] accelerometer) {
        this.accelerometer = accelerometer;
    }

    public void registerPositionLimit(int width, int height){
        for(int i = 0; i < balls.length; i++){
            balls[i].setPositionLimitX(width);
            balls[i].setPositionLimitY(height);
        }
    }

    private void move(float[] accelerometer, long delta){
        for(int i = 0; i < balls.length; i++) {
            balls[i].move(accelerometer, delta);
        }
    }

    private void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        for(int i = 0; i < balls.length; i++) {
            balls[i].draw(canvas);
        }
        map.draw(canvas);

        Paint paint = new Paint();
        //debug info
        paint.setTextSize(50);
        canvas.drawText("ax : "+String.valueOf(accelerometer[0]), 10, 100, paint);
        canvas.drawText("ay : "+String.valueOf(accelerometer[1]), 10, 180, paint);
        canvas.drawText("az : "+String.valueOf(accelerometer[2]), 10, 260, paint);
        Log.d("GameManager", "delta : "+String.valueOf(delta));
    }

    private int[][] fetchFromCsv(String filename, Resources resources) {
        try {
            assetManager = resources.getAssets();
            InputStream is = assetManager.open(filename);
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
