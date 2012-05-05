

package arcaptcha;

import processing.core.*;
import processing.xml.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.PathIterator;
import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.awt.Shape;
import java.util.ArrayList;



public class test extends PApplet
{
      public static void main(String args[])
      {
        PApplet.main(new String[] { "arcaptcha.test" });
        //PApplet.main(new String[] { "--present", "arcaptcha.Main" });
      }



    boolean[] filterFlags = {false,false,false,false,false,false};
    FontOutlineSystem fos;
    ArrayList path;

    
    @Override
    public void setup()
    {
      size((int)(screenWidth/1.1), (int)(screenHeight/2),JAVA2D);
      smooth();noLoop();
      fos = new FontOutlineSystem(this);
      fos.loadFont("Arial", 100);
      path = fos.convert("لغة الضاد", (int)(width/2), (int)(height/2));
      background(255);
    } // setup end

    @Override
    public void draw()
    {
      fill(255,8);

      Captcha captcha = new Captcha(this,200,200, 400,400,new Font("Tahoma",Font.PLAIN,32),"تجربة",filterFlags,null);
      //OutlineNoiseFilter.apply(this, captcha);
      image(captcha.distortedImg,100,100);
      
      noStroke();
      stroke(0);strokeCap(ROUND);strokeJoin(ROUND);strokeWeight(3);
      float t = 0;
      float x=0, y=0, ox=3, oy=55;
      for (int i=0, n=path.size(); i<n; i++)
      {
        FontPoint fp = (FontPoint)path.get(i);
        ox = x;
        oy = y;
        x = fp.x +random(10);//+ noise(t, i) * 11f - 50f;
        y = fp.y +random(10);//+ noise(t, i) * 50f - 30f;
        if (fp.mode==FontPoint.DRAW)
        {
          line(ox,oy,x,y);
        }
      }



    } //--draw end
} //--PApplet end

