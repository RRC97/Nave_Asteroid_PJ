/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

/**
 *
 * @author Casa
 */
public class GameView extends View implements Runnable
{
    private Handler handler;
    private Player player;
    private Background bg;
    private float heightScreen, widthScreen;
    public GameView(Context context)
    {
        super(context);
        
        widthScreen = context.getResources().getDisplayMetrics().widthPixels;
        heightScreen = context.getResources().getDisplayMetrics().heightPixels;
        
        player = new Player(widthScreen / 2, heightScreen / 2);
        
        bg = new Background(widthScreen, widthScreen);
        
        handler = new Handler();
        handler.postDelayed(this, 1);
    }
    
    @Override
    public void run()
    {
        onUpdate();
        invalidate();
        
        handler.postDelayed(this, 1);
    }
    
    public void onUpdate()
    {
        player.update();
    }
    
    @Override
    public void onDraw(Canvas canvas)
    {
        bg.draw(canvas);
        player.draw(canvas);
    }
}
