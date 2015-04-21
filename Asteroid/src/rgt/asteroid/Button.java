/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 *
 * @author Casa
 */
public class Button
{
    private float x, y, width, height;
    private int indexTouch;
    private boolean pressed, cancel;
    private Bitmap bitmap;
    public Button(Bitmap arg0, float arg1, float arg2, float arg3, float arg4)
    {
        bitmap = arg0;
        x = arg1;
        y = arg2;
        width = arg3;
        height = arg4;
        indexTouch = -1;
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(128);
        
        arg0.save();
        
        arg0.translate(x, y);
        
        Rect bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        
        arg0.drawBitmap(bitmap, bitmapRect, new Rect(0, 0, (int)width, (int)height), paint);
        
        arg0.restore();
    }
    
    public void onClickDown(float arg0, float arg1, int arg2)
    {
        if(arg0 > x && arg0 < x + width
        && arg1 > y && arg1 < y + height
        && indexTouch < 0)
        {
            indexTouch = arg2;
            pressed = true;
        }
    }
    
    public int getIndex()
    {
        return indexTouch;
    }
    
    public void onCancel()
    {
        cancel = true;
    }
    
    public void onClickUp(int arg0)
    {
        if(indexTouch == arg0)
        {
            indexTouch = -1;
            pressed = false;
            cancel = false;
        }
        else if(arg0 < 0)
        {
            indexTouch += arg0;
        }
    }
            
    public boolean isPressed()
    {
        if(!cancel)
        return pressed;
        return false;
    }
}
