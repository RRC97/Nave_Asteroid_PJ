package rgt.asteroid;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import java.util.ArrayList;

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
    private float x, y, angle, speedX, speedY;
    private float screenWidth, screenHeight;
    private ArrayList<BulletPlayer> bullets;
    public Player(float arg0, float arg1)
    {
        bullets = new ArrayList<BulletPlayer>();
        screenWidth = arg0;
        screenHeight = arg1;
        x = screenWidth / 2;
        y = screenHeight / 2;
    }
    
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        
        for (int i = 0; i < bullets.size(); i++)
        {
            BulletPlayer get = bullets.get(i);
            get.draw(arg0);
        }
        
        arg0.save();
        arg0.rotate(angle, x, y);
        
        Path path = new Path();
        path.moveTo(x-10, y+10);
        path.lineTo(x, y-10);
        path.lineTo(x+10, y+10);
        path.lineTo(x, y+5);
        path.lineTo(x-10, y+10);
        path.close();
        
        arg0.drawPath(path, paint);
        
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        
        arg0.drawPath(path, paint);
        arg0.restore();
    }
    
    public void acelerate()
    {
        speedX += (float)Math.sin(angle * (Math.PI/180)) * 0.1f;
        speedY += (float)Math.cos(angle * (Math.PI/180)) * -0.1f;
        
        if(speedX < -1.5f)speedX = -1.5f;
        if(speedY < -1.5f)speedY = -1.5f;
        
        if(speedX > 1.5f)speedX = 1.5f;
        if(speedY > 1.5f)speedY = 1.5f;
    }
    
    public void fire()
    {
        bullets.add(new BulletPlayer(x, y, angle));
    }
    
    public void rotate(float arg0)
    {
        angle += arg0;
    }
    
    public void update()
    {
        //angle += 0.1f;
        x += speedX;
        y += speedY;
        if(x - 10 > screenWidth)
            x -= screenWidth + 20;
        if(x + 10 < 0)
            x += screenWidth + 20;
        
        if(y - 10 > screenHeight)
            y -= screenHeight + 20;
        if(y + 10 < 0)
            y += screenHeight + 20;
        
        if(angle > 360) angle -= 360;
        if(angle < 0) angle += 360;
        
        ArrayList<BulletPlayer> removeBullet = new ArrayList<BulletPlayer>();
        
        for (int i = 0; i < bullets.size(); i++)
        {
            BulletPlayer get = bullets.get(i);
            get.update();
            
            if(get.x - 2 > screenWidth)
            get.x -= screenWidth + 4;
            if(get.x + 2 < 0)
                get.x += screenWidth + 4;

            if(get.y - 2 > screenHeight)
                get.y -= screenHeight + 4;
            if(get.y + 2 < 0)
                get.y += screenHeight + 4;
            
            if(get.isInvalidate())removeBullet.add(get);
        }
        
        for (int i = 0; i < removeBullet.size(); i++)
        {
            BulletPlayer get = removeBullet.get(i);
            bullets.remove(get);
        }
    }
}
