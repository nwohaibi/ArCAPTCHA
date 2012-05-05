// Take a Captcha and apply distortion in its Captcha.distortedImg

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

import java.applet.*;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;


public class Filter extends PApplet
{
    PGraphics temp;

    public static PImage apply(Captcha captcha,String filterType)
    {
        if(filterType.equalsIgnoreCase("0") || filterType.equalsIgnoreCase("outline noise"))
        {

        }
        else if(filterType.equalsIgnoreCase("1") || filterType.equalsIgnoreCase("blur"))
        {

        }
        else if(filterType.equalsIgnoreCase("2") || filterType.equalsIgnoreCase("twirl"))
        {

        }
        else if(filterType.equalsIgnoreCase("3") || filterType.equalsIgnoreCase("cdfkd"))
        {

        }
        else if(filterType.equalsIgnoreCase("4") || filterType.equalsIgnoreCase("rfs"))
        {

        }
        else if(filterType.equalsIgnoreCase("5") || filterType.equalsIgnoreCase("ergr"))
        {

        }
        return null;
    }

    public void resetDistortions(Captcha captcha)
    {

    }


}
