package rgt.asteroid;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity
{
    GameView view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        view = new GameView(this);
        setContentView(view);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        view.play();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        view.pause();
    }
}
