/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

        /**
    fontoutline taken from http://processinghacks.com/hacks:fontoutline
    @author David Bollinger
    */

    // Dave Bollinger, Aug 2007
    // purpose: to convert a string of text in a specified font into
    //          a usable list of points represeting its outline

public class FontOutlineSystem
{
  PApplet applet;
  Font font;
  BufferedImage img;
  Graphics2D g2d;
  FontRenderContext frc;

  FontOutlineSystem(PApplet applet)
  {
    this(applet,"",12);
  }

  FontOutlineSystem(PApplet applet, String fontName, int fontSize)
  {
    this.applet = applet;
    // we need a Graphics2D...
    if (applet.g.getClass().getName().equals("PGraphicsJava2D"))
    {
      // JAVA2D has one of it's own already:
      g2d = ((PGraphicsJava2D)applet.g).g2;
    } else
    {
      // P3D, OPENGL don't have one, so make one:
      img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      g2d = img.createGraphics();
    }
    frc = g2d.getFontRenderContext();
    loadFont(fontName, fontSize);
  }
  
//    FontOutlineSystem(PGraphics pg, String fontName, int fontSize)
//  {
//    this.applet = applet;
//    // we need a Graphics2D...
//    if (applet.g.getClass().getName().equals("PGraphicsJava2D"))
//    {
//      // JAVA2D has one of it's own already:
//      g2d = ((PGraphicsJava2D)applet.g).g2;
//    } else
//    {
//      // P3D, OPENGL don't have one, so make one:
//      img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
//      g2d = img.createGraphics();
//    }
//    frc = g2d.getFontRenderContext();
//    loadFont(fontName, fontSize);
//  }

  void loadFont(String name, int size)
  {
    font = new Font(name, Font.PLAIN, size);
  }

  /**
   * Returns an array list containing FontPoint's that
   * represent the outline of the specified text at
   * specified origin.
   */
  ArrayList convert(String text, float xo, float yo)
  {
    ArrayList al = new ArrayList();
    if (font==null) return al;
    float [] seg = new float[6];
    float x=0, y=0, mx=0, my=0;
    //GlyphVector gv = font.createGlyphVector(frc, text);

    GlyphVector gv = font.layoutGlyphVector(frc,text.toCharArray(),0, (text.toCharArray()).length,1);


    Shape glyph = gv.getOutline(xo, yo);
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
    return al;
  } // convert

}