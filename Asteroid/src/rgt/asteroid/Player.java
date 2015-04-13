package rgt.asteroid;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JeanTozzi
 */
public class Player
{
    private float x, y, angle;
    public Player(float arg0, float arg1)
    {
        x = arg0;
        y = arg1;
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        
        arg0.translate(x, y);
        arg0.rotate(angle * (float)(180/Math.PI));
        
        Path path = new Path();
        path.moveTo(-10, 10);
        path.lineTo(0, -10);
        path.lineTo(10, 10);
        path.lineTo(0, 5);
        path.lineTo(-10, 10);
        
        arg0.drawPath(path, paint);
        
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        
        arg0.drawPath(path, paint);
    }
    
    public void update()
    {
        //angle += 0.1f;
        
        if(angle > 360) angle -= 360;
    }
}
