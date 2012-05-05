
package arcaptcha;

import processing.core.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class CaptchaEngine extends PApplet
{
    static int amountOfCaptchas = 0;
    Captcha captcha ;
    static  int enginWidth = 80;
    static int enginHeight = 30;
    static int captchaFontSize = 22;
    static int numberOfLetters = 12;
    static int centerX = 0;
    static int centerY = 0;
    static String word =  "نهضة العربية";
    static String fontName = "Arial";
    static String[] arguments;
    boolean[] filterFlags = {false,false,false,false,false,false,false,false,false,false,false,false,false};
    // global PFont
    PFont globalFont;


    CaptchaEngine(String fname,int fontSize)
    {
        boolean success = (new File("output")).mkdir();
        globalFont = this.createFont(fname, fontSize);
    }
    
    @Override
    public void setup()
    {
      
      size( enginWidth, enginHeight); // opengl
      //frame.setResizable(true);
      captcha = new Captcha(this, centerX ,centerY ,enginWidth,enginHeight,new Font("Tahoma",Font.PLAIN,32),word,filterFlags,null);
      smooth();noStroke();background(255);fill(0);
      noLoop();
      rectMode(CENTER);
      textFont(captcha.fontType);
      textAlign(CENTER);
      text(captcha.word, 40, 15+17);
      //generate(centerX,centerY,enginWidth,enginHeight,amountOfCaptchas,null,fontName,captchaFontSize,"");
//      this.getAppletContext().
//      this.frame.setEnabled(false);
      this.frame.setVisible(false);
      
    }
    
    @Override
    public void draw()
    {
//        background(255);fill(0);
//        text(mouseX+" , "+mouseY, 50, height-50);
//        rectMode(CENTER);
//        text("ناصر الوهيبي", (int)(width/2), (int)(height/2),500 ,308 );
    }


    
    // main generate function for captchas
    // call draw when needed
    public void generate(int centX,int centY,
                        int captchaSizeX,
                        int captchaSizeY ,
                        int amount,
                        String fontName,
                        int fontSize ,
                        boolean[] flags,
                        String absPath)
    {
        Captcha temp;
        //frame.setSize(captchaSizeX, captchaSizeY);
        this.smooth();this.noStroke();this.textAlign(CENTER,CENTER);rectMode(CENTER);
        int i ;
        for(i=0; i <= amount ; i++)
        {
            this.background(255);
            this.fill(0);
            temp = new Captcha(this,centX,centY,captchaSizeX,captchaSizeY,new Font("Tahoma",Font.PLAIN,32),"الله اكبر"+i,flags,null);
            this.textFont(temp.fontType);

            this.text(temp.word,(int)(captchaSizeX/2), (int)(captchaSizeY/2));
            this.loadPixels();
            temp.distortedImg = this.get();
            temp.distortedImg.save(i+".JPEG");
            updatePixels();
            this.draw();
        }
        //frame.setSize(600, 600);
    }


  public static void main(String args[])
  {
    println(args);
    arguments = args;
    centerX = Integer.parseInt(args[0]);
    centerY = Integer.parseInt(args[1]);
    enginWidth = Integer.parseInt(args[2]);
    enginHeight = Integer.parseInt(args[3]);
    amountOfCaptchas = Integer.parseInt(args[4]);
    //insert filters here
    fontName = args[6];
    captchaFontSize = Integer.parseInt(args[7]);
    //word =  "نهضة العربية";


    /*(int centX,int centY,
                        int captchaSizeX,
                        int captchaSizeY ,
                        int amount,
                        int[] filters,
                        String fontName,6
                        int fontSize ,
                        String absPath)*/

    PApplet.main(new String[] { "arcaptcha.CaptchaEngine" });
  }

}


//////////////////////////////////////////////////////////////

