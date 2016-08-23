package jp.tonkatu05.ballgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Map {

    private int data[][];
    private Line lines[];

    public Map(int[][] data){
        this.data = data;
        lines = new Line[data.length];
        for(int i=0; i < data.length; i++){
            lines[i] = new Line(data[i][0], data[i][1], data[i][2]);
        }
    }

    public void draw(Canvas canvas){
        if (canvas == null) return;
        Log.d("Map", "draw!!!");
        for(int i=0; i < lines.length; i++){
            lines[i].draw(canvas);
        }

    }

    public Line[] getLines(){
        return lines;
    }

}
