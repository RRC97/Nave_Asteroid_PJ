/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
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
    private Button acelerate, fire, rotateLeft, rotateRight;
    private Button[] buttons;
    private int lastPointerCount;
    private AsteroidManager asteroidManager;
    private boolean running, stop;
    public GameView(Context context)
    {
        super(context);
        
        running = true;
        stop = true;
        Resources res = context.getResources();
        
        widthScreen = res.getDisplayMetrics().widthPixels;
        heightScreen = res.getDisplayMetrics().heightPixels;
        
        setKeepScreenOn(true);
        
        player = new Player(widthScreen, heightScreen);
        
        bg = new Background(widthScreen, widthScreen);
        
        Bitmap button1 = BitmapFactory.decodeResource(res, R.drawable.button1);
        Bitmap button2 = BitmapFactory.decodeResource(res, R.drawable.button2);
        Bitmap button3 = BitmapFactory.decodeResource(res, R.drawable.button3);
        Bitmap button4 = BitmapFactory.decodeResource(res, R.drawable.button4);
        
        acelerate = new Button(button4, widthScreen - 70, heightScreen - 70, 60, 60);
        fire = new Button(button3, widthScreen - 140, heightScreen - 70, 60, 60);
        rotateLeft = new Button(button1, 10, heightScreen - 70, 60, 60);
        rotateRight = new Button(button2, 80, heightScreen - 70, 60, 60);
        
        buttons = new Button[]{acelerate, fire, rotateLeft, rotateRight};
        
        asteroidManager = new AsteroidManager(widthScreen, heightScreen);
        
        handler = new Handler();
        handler.postDelayed(this, 1);
    }
    
    @Override
    public void run()
    {
        if(!stop)
        {
            onUpdate();
            invalidate();
        }
        
        if(running)
            handler.postDelayed(this, 1);
    }
    
    public void onUpdate()
    {
        player.update();
        asteroidManager.update();
        
        player.remoteBullet(asteroidManager.collideAsteroidsWithBullet(player.getBullets()));
        
        if(rotateLeft.isPressed())
        {
            player.rotate(-6);
        }
        if(rotateRight.isPressed())
        {
            player.rotate(6);
        }
        if(acelerate.isPressed())
        {
            player.acelerate();
        }
        if(fire.isPressed())
        {
            fire.onCancel();
            player.fire();
        }
        
        if(asteroidManager.collideAsteroidsWithPlayer(player.getRegion()))
            onCloseGame();
    }
    
    public void onCloseGame()
    {
        Activity activity = (Activity)getContext();
        running = false;
        handler = null;
        Intent intent = new Intent(activity, GoverActivity.class);
        intent.putExtra("Score", player.getScore());
        activity.startActivity(intent);
        activity.finish();
    }
    
    @Override
    public void onDraw(Canvas canvas)
    {
        bg.draw(canvas);
        asteroidManager.draw(canvas);
        player.draw(canvas);
        for (Button button : buttons) {
            button.draw(canvas);
        }
    }
    
    public void pause()
    {
        stop = true;
    }
    public void play()
    {
        stop = false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        int action = me.getAction() & MotionEvent.ACTION_MASK;
        int pointerCount = me.getPointerCount();
        if(pointerCount > 1)
        {
            if(lastPointerCount == 1)
            {
                for (Button button : buttons)
                {
                    button.onClickUp(-1);
                }
            }
            for(int i = 0; i < pointerCount; i++)
            {
                int pointerId = me.getPointerId(i);
                int index = me.findPointerIndex(pointerId);
                int id = (pointerCount - 1) - index;

                float pointerX = me.getX(index);
                float pointerY = me.getY(index);
                int size = 0;
                for (Button button : buttons)
                {
                    switch (action)
                    {
                        case MotionEvent.ACTION_DOWN:
                        button.onClickDown(pointerX, pointerY, id);
                        break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                                button.onClickDown(pointerX, pointerY, id);
                                break;
                        case MotionEvent.ACTION_UP:
                            button.onClickUp(id);
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            button.onClickUp(id);
                            break;
                    }
                }
            }
        }
        else
        {
            if(lastPointerCount > 1)
            {
                for (Button button : buttons)
                {
                    button.onClickUp(-1);
                }
            }
            int pointerId = me.getPointerId(0);
            int index = me.findPointerIndex(pointerId);
            float pointerX = me.getX(index);
            float pointerY = me.getY(index);
            for (Button button : buttons)
            {
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        button.onClickDown(pointerX, pointerY, 0);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                            button.onClickDown(pointerX, pointerY, 0);
                            break;
                    case MotionEvent.ACTION_UP:
                        button.onClickUp(0);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        button.onClickUp(0);
                        break;
                }
            }
        }
        
        lastPointerCount = pointerCount;
        return true;
    }
}
