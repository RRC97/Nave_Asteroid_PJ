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
public class BulletPlayerShadow
{
    public float x, y;
    private int alpha;
    public BulletPlayerShadow(float arg0, float arg1, int arg2)
    {
        x = arg0;
        y = arg1;
        alpha = arg2;
    }
    public void draw(Canvas arg0)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        paint.setAlpha(alpha);
        
        arg0.drawRect(x, y, x + 2, y + 2, paint);
    }
}
