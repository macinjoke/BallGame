package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;

public class Map {

    private int mData[][];
    private Line mLines[];

    public Map(int[][] data){
        this.mData = data;
        mLines = new Line[data.length];
        for(int i=0; i < data.length; i++){
            mLines[i] = new Line(data[i][0], data[i][1], data[i][2]);
        }
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        for(int i = 0; i < mLines.length; i++){
            mLines[i].draw(canvas);
        }

    }

    public Line[] getLines(){
        return mLines;
    }

}
