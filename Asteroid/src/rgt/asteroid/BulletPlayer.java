/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 *
 * @author Casa
 */
public class BulletPlayer
{
    public float x, y, speedX, speedY;
    private int time;
    private boolean invalidate;
    private BulletPlayerShadow[] shadow;
    public BulletPlayer(float arg0, float arg1, float arg2)
    {
        x = arg0;
        y = arg1;
        speedX = (float)Math.sin(arg2 * (Math.PI/180)) * 5;
        speedY = (float)Math.cos(arg2 * (Math.PI/180)) * -5;
        
        shadow = new BulletPlayerShadow[3];
        for (int i = 0; i < shadow.length; i++)
        {
            shadow[i] = new BulletPlayerShadow(x, y, (int)(255 - (i + 1) * 64));
        }
    }
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        
        arg0.drawRect(x, y, x + 2, y + 2, paint);
        for(BulletPlayerShadow s : shadow)
        {
            s.draw(arg0);
        }
    }
    
    public void update()
    {
        x += speedX;
        y += speedY;
        
        for (int i = 0; i < shadow.length; i++)
        {
            shadow[i].x = x - speedX * (i + 1);
            shadow[i].y = y - speedY * (i + 1);
        }
        
        time++;
        
        if(time > 75)
        {
            invalidate = true;
        }
    }
    
    public boolean isInvalidate()
    {
        return invalidate;
    }
}
