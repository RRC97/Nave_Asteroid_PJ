/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 *
 * @author Casa
 */
public class Button
{
    private float x, y, width, height;
    private int indexTouch;
    private boolean pressed, cancel;
    public Button(float arg0, float arg1, float arg2, float arg3)
    {
        x = arg0;
        y = arg1;
        width = arg2;
        height = arg3;
        indexTouch = -1;
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setAlpha(48);
        
        arg0.save();
        
        arg0.translate(x, y);
        arg0.drawRect(0, 0, width, height, paint);
        
        arg0.restore();
    }
    
    public void onClickDown(float arg0, float arg1, int arg2)
    {
        if(arg0 > x && arg0 < x + width
        && arg1 > y && arg1 < y + height)
        {
            indexTouch = arg2;
            pressed = true;
        }
    }
    public void onCancel()
    {
        cancel = true;
    }
    
    public void onClickUp(int arg0)
    {
        if(indexTouch == arg0 || arg0 == -1)
        {
            indexTouch = -1;
            pressed = false;
            cancel = false;
        }
    }
            
    public boolean isPressed()
    {
        if(!cancel)
        return pressed;
        return false;
    }
}
