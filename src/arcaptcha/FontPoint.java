
package arcaptcha;

/**
 * Represents one stroke of a glyph.  Glyphs typically
 * look like MOVE,DRAW,DRAW,DRAW,...,DRAW,CLOSE - no
 * need to distinguish between draw and close as they're
 * both effectively draw strokes.
 */

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

public class FontPoint
{
  static final int MOVE = PathIterator.SEG_MOVETO;
  static final int DRAW = PathIterator.SEG_LINETO;
  float x, y;
  int mode;
  FontPoint()
  {
    this(0f,0f,MOVE);
  }
  FontPoint(float x, float y)
  {
    this(x,y,MOVE);
  }
  FontPoint(float x, float y, int mode)
  {
    this.x = x;
    this.y = y;
    this.mode = mode;
  }
}