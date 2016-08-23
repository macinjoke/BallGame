package jp.tonkatu05.ballgame.util;


import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;

public class CsvManager {

    static public int[][] get2DArrayFromCsv(BufferedReader br) {

        int array2D[][];
        try {
            String line;
            int array2Dtmp[][] = new int[999][999];
            int count = 0;

            String[] cols = new String[0];
            while ((line = br.readLine()) != null) {
                cols = line.split(",");
                for (int i = 0; i < cols.length; i++) {
                    array2Dtmp[count][i] = Integer.parseInt(cols[i]);
                    Log.d("CsvManager", "cols["+i+"] :"+ cols[i]);
                }
                count++;
                Log.d("CsvManager", "line :"+ line);
                Log.d("CsvManager", "count :"+count);
            }

            // 読み込みデータの表示
            array2D = new int[count][cols.length];
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < cols.length; j++) {
                    array2D[i][j] = array2Dtmp[i][j];
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            array2D = new int[0][0];
        }
        return array2D;
    }

}
