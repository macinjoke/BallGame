package jp.tonkatu05.ballgame.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import jp.tonkatu05.ballgame.game.DroidSan;
import jp.tonkatu05.ballgame.R;
import jp.tonkatu05.ballgame.game.GameManager;

/**
 * Created by naoya on 2015/04/11.
 */
public class PlayView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    final String TAG = "PlayView";

//    private DroidSan droidsan;
    private SurfaceHolder mHolder;
    private Thread looper;
    private GameManager gameManager;

    private int mHeight;
    private int mWidth;

    private float[] accelerometer = new float[3];

//    private long mTime = 0;

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
        gameManager = new GameManager(mHolder, getResources());
        looper = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (looper != null) {
            mHeight = height;
            mWidth = width;
            gameManager.registerPositionLimit(mWidth, mHeight);
            looper.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        looper = null;
    }

    @Override
    public void run() {
        while (looper != null) {
            gameManager.setAccelerometer(accelerometer);
            gameManager.loop();
        }
    }

    public void setAccelerometer(float[] accelerometer) {
        this.accelerometer = accelerometer;
    }
}
