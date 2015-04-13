package rgt.asteroid;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(new GameView(this));
    }
}
