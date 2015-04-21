/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.asteroid;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import java.util.ArrayList;

/**
 *
 * @author Casa
 */
public class AsteroidManager
{
    private ArrayList<Asteroid> asteroids;
    private float screenWidth, screenHeight;
    private int countTime, countLimited;
    public AsteroidManager(float arg0, float arg1)
    {
        screenWidth = arg0;
        screenHeight = arg1;
        
        asteroids = new ArrayList<Asteroid>();
        
        asteroids.add(new Asteroid(100, 100, 50));
        asteroids.add(new Asteroid(screenWidth - 50, screenHeight - 50, 50));
        countLimited = 1500;
    }
    
    public void draw(Canvas arg0)
    {
        for(Asteroid asteroid : asteroids)
        {
            asteroid.draw(arg0);
        }
    }
    
    public void instantiateNewAsteroid()
    {
        int horizontalOrVertical = (int)(Math.random() * 2);
        int side = (int)(Math.random() * 2);
        float xPos = (float)(Math.random() * screenWidth);
        float yPos = (float)(Math.random() * screenHeight);
        
        if(horizontalOrVertical == 0)
            if(side == 0)
                xPos = -50;
            else
                xPos = screenWidth + 50;
        else
            if(side == 0)
                yPos = -50;
            else
                yPos = screenHeight + 50;
        
        asteroids.add(new Asteroid(xPos, yPos, 50));
    }
    
    public void update()
    {
        for(Asteroid asteroid : asteroids)
        {
            asteroid.update(screenWidth, screenHeight);
        }
        
        if(countTime >= countLimited)
        {
            instantiateNewAsteroid();
            countTime = 0;
            countLimited--;
        }
        
        if(asteroids.size() <= 0)
        {
            countLimited -= 10;
            instantiateNewAsteroid();
            countTime = 0;
        }
        
        countTime++;
    }
    
    public BulletPlayer collideAsteroidsWithBullet(BulletPlayer[] bullets)
    {
        BulletPlayer eliminate = null;
        Asteroid exploded = null;
        for(Asteroid asteroid : asteroids)
        {
            float asteroidX = asteroid.getX();
            float asteroidY = asteroid.getY();
            float asteroidR = asteroid.getRadius();
            for(BulletPlayer bullet : bullets)
            {
                float bulletX = bullet.getX();
                float bulletY = bullet.getY();
                float bulletW = 1, bulletH = 1;
                float dX = bulletX - asteroidX;
                float dY = bulletY - asteroidY;
                
                float hip = (float)Math.sqrt((dX * dX) + (dY * dY));
                
                if(hip <= asteroidR)
                {
                    exploded = asteroid;
                    eliminate = bullet;
                    break;
                }
            }
            
            if(eliminate != null)
                break;
        }
        
        if(exploded != null)
        {
            float explodedX = exploded.getX();
            float explodedY = exploded.getY();
            float explodedR = exploded.getRadius();
            if(explodedR > 13)
            {
                for(int i = 0; i < 5 - (int)(explodedR / 25); i++)
                {
                    asteroids.add(new Asteroid(explodedX, explodedY, explodedR / 2));
                }
            }
            asteroids.remove(exploded);
            return eliminate;
        }
        return null;
    }
    
    public boolean collideAsteroidsWithPlayer(Region player)
    {
        for(Asteroid asteroid : asteroids)
        {
            Path path = new Path();
            path.addCircle(asteroid.getX(), asteroid.getY(), asteroid.getRadius(), Path.Direction.CCW);
            
            Region region = new Region();
            region.setPath(path, new Region(0, 0, (int)screenWidth, (int)screenHeight));
            
            if(region.op(player, Region.Op.INTERSECT))
            {
                return true;
            }
        }
        return false;
    }
}
