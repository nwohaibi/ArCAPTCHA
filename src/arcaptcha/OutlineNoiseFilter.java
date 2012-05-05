
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



public class OutlineNoiseFilter
{
  
  
  public static PImage apply(PApplet applet, Captcha captcha , float toleranceLines)
  {
      Font font;
      BufferedImage img;
      Graphics2D g2d;
      FontRenderContext frc;
      ArrayList al = new ArrayList();
      PGraphics tempPG;


      if (applet.g.getClass().getName().equals("PGraphicsJava2D"))
        {
          // JAVA2D has one of it's own already:
          g2d = ((PGraphicsJava2D)applet.g).g2; 

        }
      else
        {
          // P3D, OPENGL don't have one, so make one:
          img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
          g2d = img.createGraphics();
        }
        frc = g2d.getFontRenderContext();
        font = new Font(captcha.fontName, Font.PLAIN, captcha.captchaFontSize);
        g2d.setFont(font);
        // to array list : convert
        //if (font==null) return al;
        float [] seg = new float[6];
        float x=0, y=0, mx=0, my=0;
        //GlyphVector gv = font.createGlyphVector(frc, text);

        GlyphVector gv = font.layoutGlyphVector(frc,captcha.word.toCharArray(),0, (captcha.word.toCharArray()).length,1);
        //testing centering
        FontMetrics fm = g2d.getFontMetrics();
        int twidth = fm.stringWidth(captcha.word);
        int theight = fm.getHeight();
        int xo = (int)(captcha.captchaWidth/2-twidth/2); // centering
        int yo = (int)(((captcha.captchaHeight- theight)/ 2)+ fm.getAscent());

        Shape glyph = gv.getOutline(xo,yo);
        //

        //Shape glyph = gv.getOutline(xo, yo);
        PathIterator pi = glyph.getPathIterator(null);
        while (!pi.isDone())
        {
          int segtype = pi.currentSegment(seg);
          int mode = 0;
          switch(segtype)
          {
            case PathIterator.SEG_MOVETO:
              x = mx = seg[0];
              y = my = seg[1];
              mode = FontPoint.MOVE;
              break;
            // as written, all this code cares about are the endpoints,
            // quadratic and cubic curves are not interpolated, so all
            // three can be handled by the same code:
            case PathIterator.SEG_LINETO:
            case PathIterator.SEG_QUADTO:
            case PathIterator.SEG_CUBICTO:
              x = seg[0];
              y = seg[1];
              mode = FontPoint.DRAW;
              break;
            case PathIterator.SEG_CLOSE:
              x = mx;
              y = my;
              mode = FontPoint.DRAW;
              break;
           } // switch
           al.add(new FontPoint(x,y,mode));
           pi.next();
        } // while

        // now draw my friend

        tempPG = applet.createGraphics(captcha.captchaWidth, captcha.captchaHeight, applet.JAVA2D);
        tempPG.beginDraw();
        tempPG.smooth();
        tempPG.background(255);
        tempPG.stroke(0);tempPG.strokeCap(applet.ROUND);tempPG.strokeJoin(applet.ROUND);
        float t = 0;
        float x2=222, y2=55, ox=3, oy=55;
        for (int i=0, n=al.size(); i<n; i++)
        {
            FontPoint fp = (FontPoint)al.get(i);
            ox = x2;
            oy = y2;
            x2 = fp.x + applet.random((int)(captcha.captchaFontSize/toleranceLines));
            y2 = fp.y + applet.random((int)(captcha.captchaFontSize/toleranceLines));
            if (fp.mode==FontPoint.DRAW)
            {
              tempPG.line(ox,oy,x2,y2);
            }
        }
        tempPG.endDraw();
        captcha.distortedImg = tempPG;
        //tempPG.save("test.png"); // remove after testing

        // end of convert

      return null;
  }
  


}

