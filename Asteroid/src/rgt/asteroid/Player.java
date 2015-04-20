package rgt.asteroid;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
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
    private int score;
    private Path path;
    public Player(float arg0, float arg1)
    {
        bullets = new ArrayList<BulletPlayer>();
        speedX = (float)(Math.random()) - 0.5f;
        speedY = (float)(Math.random()) - 0.5f;
        screenWidth = arg0;
        screenHeight = arg1;
        x = screenWidth / 2;
        y = screenHeight / 2;
        
        path = new Path();
        path.moveTo(x-10, y+10);
        path.lineTo(x, y-10);
        path.lineTo(x+10, y+10);
        path.lineTo(x, y+5);
        path.lineTo(x-10, y+10);
        path.close();
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
        
        Paint paintScore = new Paint(Paint.LINEAR_TEXT_FLAG);
        paintScore.setTextAlign(Paint.Align.RIGHT);
        paintScore.setColor(Color.WHITE);
        paintScore.setAlpha(128);
        paintScore.setTextSize(32);
        arg0.drawText(""+score, screenWidth - 10, 32, paintScore);
        
        arg0.save();
        arg0.rotate(angle, x, y);
        
        path = new Path();
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
    
    public int getScore()
    {
       return score; 
    }
    
    public Region getRegion()
    {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, x, y);
        Path pathRotated = new Path(path);
        pathRotated.transform(matrix);
        
        Region region = new Region();
        region.setPath(pathRotated, new Region(0, 0, (int)screenWidth, (int)screenHeight));
        
        return region;
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
            
            float xBullet = get.getX();
            float yBullet = get.getY();
            
            if(xBullet - 4 > screenWidth)
                get.setX(xBullet - screenWidth + 8);
            if(xBullet + 4 < 0)
                get.setX(xBullet + screenWidth + 8);

            if(yBullet - 4 > screenHeight)
                get.setY(yBullet - screenHeight + 8);
            if(yBullet + 4 < 0)
                get.setY(yBullet + screenHeight + 8);
            
            if(get.isInvalidate())removeBullet.add(get);
        }
        
        for (int i = 0; i < removeBullet.size(); i++)
        {
            BulletPlayer get = removeBullet.get(i);
            bullets.remove(get);
        }
    }
    
    public void remoteBullet(BulletPlayer bullet)
    {
        if(bullet != null)
            score += 10;
        bullets.remove(bullet);
    }
    
    public BulletPlayer[] getBullets()
    {
        BulletPlayer[] result = new BulletPlayer[bullets.size()];
        result = bullets.toArray(result);
        return result;
    }
}
