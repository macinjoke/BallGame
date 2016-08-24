package jp.tonkatu05.ballgame.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import jp.tonkatu05.ballgame.R;
import jp.tonkatu05.ballgame.fragments.StartFragment;
import jp.tonkatu05.ballgame.fragments.PlayFragment;

public class MainActivity extends FragmentActivity implements StartFragment.OnFragmentInteractionListener{

    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mManager = getSupportFragmentManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager.beginTransaction()
                .replace(R.id.container, StartFragment.newInstance())
                .commit();
    }

    @Override
    public void onFragmentAction(String message){
        mManager.beginTransaction()
                .replace(R.id.container, PlayFragment.newInstance())
                .commit();
    }

}
