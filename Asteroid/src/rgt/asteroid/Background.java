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
 * @author JeanTozzi
 */
public class Background
{
    private float height, width;
    private float[] x, y;
    private int length;
    public Background(float arg0, float arg1)
    {
        width = arg0;
        height = arg1;
        length = 100;
        
        x = new float[length];
        y = new float[length];
        
        for(int i = 0; i < length; i++)
        {
            float posX = width * (float)Math.random();
            float posY = height * (float)Math.random();
            
            x[i] = posX;
            y[i] = posY;
        }
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        
        for(int i = 0; i < length; i++)
        {
            arg0.drawRect(x[i], y[i], x[i] + 1, y[i] + 1, paint);
        }
    }
}
