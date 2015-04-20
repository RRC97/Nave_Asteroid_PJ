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
public class Asteroid
{
    private float x, y, radius, speedX, speedY, force;
    private float[] xHoles, yHoles;
    public Asteroid(float arg0, float arg1, float arg2)
    {
        x = arg0;
        y = arg1;
        radius = arg2;
        force = 2.0f - (radius / (50 / 3) * 0.5f);
        
        speedX = (float)(Math.random() * force * 2) - force;
        int side = (int)(Math.random() * 2);
        if(side == 0)
            if(speedX >= 0)
                speedY = force - speedX;
            else
                speedY = force + speedX;
        else
            if(speedX >= 0)
                speedY = speedX - force;
            else
                speedY = speedX + force;
        
        xHoles = new float[(int)(radius / 2)];
        yHoles = new float[(int)(radius / 2)];
        
        for(int i = 0; i < xHoles.length; i++)
        {
            float yHole = 0;
            float xHole = (float)(Math.random() * radius * 2) - radius;
            int sideHole = (int)(Math.random() * 2);
            if(sideHole == 0)
                if(xHole >= 0)
                    yHole = (float)(Math.random() * (radius - xHole));
                else
                    yHole = (float)(Math.random() * (radius + xHole));
            else
                if(xHole >= 0)
                    yHole = (float)(Math.random() * (xHole - radius));
                else
                    yHole = (float)(Math.random() * (xHole + radius));
            
            xHoles[i] = xHole;
            yHoles[i] = yHole;
        }
    }
    
    public void update(float arg0, float arg1)
    {
        x += speedX;
        y += speedY;
        
        if(x - radius > arg0)
            x -= arg0 + radius * 2;
        if(x + radius < 0)
            x += arg0 + radius * 2;
        
        if(y - radius > arg1)
            y -= arg1 + radius * 2;
        if(y + radius < 0)
            y += arg1 + radius * 2;
    }
    
    public float getX()
    {
        return x;
    }
    public float getRadius()
    {
        return radius;
    }
    public float getY()
    {
        return y;
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        
        arg0.drawCircle(x, y, radius, paint);
        
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radius / (50 / 3));
        paint.setColor(Color.WHITE);
        
        for(int i = 0; i < xHoles.length; i++)
        {
            arg0.drawRect(x + xHoles[i], y + yHoles[i], x + xHoles[i] + 1, y + yHoles[i] + 1, paint);
        }
        
        arg0.drawCircle(x, y, radius, paint);
    }
}
