/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.content.Context;
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
    public GameView(Context context)
    {
        super(context);
        
        widthScreen = context.getResources().getDisplayMetrics().widthPixels;
        heightScreen = context.getResources().getDisplayMetrics().heightPixels;
        context.getResources().getDisplayMetrics().densityDpi = 240;
        
        setKeepScreenOn(true);
        
        player = new Player(widthScreen, heightScreen);
        
        bg = new Background(widthScreen, widthScreen);
        
        acelerate = new Button(widthScreen - 70, heightScreen - 70, 60, 60);
        fire = new Button(widthScreen - 140, heightScreen - 70, 60, 60);
        rotateLeft = new Button(10, heightScreen - 70, 60, 60);
        rotateRight = new Button(80, heightScreen - 70, 60, 60);
        
        buttons = new Button[]{acelerate, fire, rotateLeft, rotateRight};
        
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
    }
    
    @Override
    public void onDraw(Canvas canvas)
    {
        bg.draw(canvas);
        player.draw(canvas);
        for (Button button : buttons) {
            button.draw(canvas);
        }
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
                        case MotionEvent.ACTION_POINTER_DOWN:
                            button.onClickDown(pointerX, pointerY, id);
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
