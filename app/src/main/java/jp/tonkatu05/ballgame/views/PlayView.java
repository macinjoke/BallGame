package jp.tonkatu05.ballgame.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.tonkatu05.ballgame.game.GameManager;

public class PlayView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    final String TAG = "PlayView";

    private SurfaceHolder mHolder;
    private Thread mLooper;
    private GameManager mGameManager;

    private int mHeight;
    private int mWidth;

    private float[] mAccelerometer = new float[3];

    public PlayView(Context context) {
        super(context);

        init();
    }

    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public PlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    public void init() {
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        mGameManager = new GameManager(mHolder, getResources());
        mLooper = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mLooper != null) {
            mHeight = height;
            mWidth = width;
            mGameManager.registerPositionLimit(mWidth, mHeight);
            mLooper.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mLooper = null;
    }

    @Override
    public void run() {
        while (mLooper != null) {
            mGameManager.setAccelerometer(mAccelerometer);
            mGameManager.loop();
        }
    }

    public void setAccelerometer(float[] accelerometer) {
        this.mAccelerometer = accelerometer;
    }
}
