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
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        
        Path path = new Path();
        path.moveTo(x - 10, y + 10);
        path.lineTo(x, y - 10);
        path.lineTo(x + 10, y + 10);
        path.lineTo(x, y + 5);
        path.lineTo(x - 10, y + 10);
        
        arg0.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
        arg0.drawPath(path, paint);
    }
    
    public void update()
    {
        
    }
}
