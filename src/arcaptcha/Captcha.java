
package arcaptcha;

import com.jhlabs.image.*;
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
import javax.imageio.ImageIO;

import java.applet.*;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;
import javax.imageio.ImageIO;

public class Captcha 
{
    int captchaWidth = 250;
    int captchaHeight = 150;
    int captchaFontSize = 22;
    int numberOfLetters = 12;
    int centerX = 0;
    int centerY = 0;
    String word =  "نهضة العربية";
    String fontName = "Arial";
    PFont fontType;
    //java awt image here src and distirted
    PGraphics distortedImgG;
    PImage distortedImg;
    PImage srcImg;
    float wordHeieght ;
    float wordWidth;
    String imageType = "GIF";
    float[] toleranceArgs;
    Font fontGlobal;
    PApplet papplet;


    // Distortions flags
    // 0= outlineNoise, 1 = blur , 2= twirl .....
    public boolean[] filterFlags ;



    //for Arabic rendering purposes
    FontOutlineSystem fos;
    ArrayList path;


    Captcha(PApplet papplet)
    {
        fos = new FontOutlineSystem(papplet);
        //fontType = createFont(fontName, captchaFontSize); // check size
        fontType = papplet.createFont(fontName, captchaFontSize);
        fos.font = fontType.getFont();
        GlyphVector gv = fos.font.layoutGlyphVector(fos.frc,word.toCharArray(),0, (word.toCharArray()).length,1);
    }
    
    Captcha(PApplet papplet,int centX,int centY, int wid,int heig,Font fontG,String text ,boolean[] flags, float[] tArgs)
    {
         this.papplet = papplet;
         filterFlags = flags;
         toleranceArgs = tArgs;
         fontGlobal = fontG;
         centerX = centX;
         centerY = centY;
         captchaWidth = wid;
         captchaHeight = heig;
         captchaFontSize = fontGlobal.getSize();
         //numberOfLetters = 12;
         word =  text;
         fontName = fontGlobal.getName();
//        fos = new FontOutlineSystem(papplet);
//        //fontType = createFont(fontName, captchaFontSize); // check size
//        fontType = papplet.createFont(fontName, captchaFontSize);
//        fos.font = fontType.getFont();
//        GlyphVector gv = fos.font.layoutGlyphVector(fos.frc,word.toCharArray(),0, (word.toCharArray()).length,1);
        distortedImg = new PImage(generateArabicImage(  wid, heig, fontGlobal, text));
        srcImg = new PImage(generateArabicImage(  wid, heig, fontGlobal, text));
        applyFilters();

    }
    ///------------------------------------------------------------------


    public void outputCaptchas(String prefix , String type, int amount,int numOfLetters)
    {
        //String time= PApplet.second()+"_"+PApplet.minute()+"_"+PApplet.hour()+" "+PApplet.day()+","+PApplet.month()+","+PApplet.year();
        Desktop desktop = null;
        if(Desktop.isDesktopSupported())
        {
             desktop =  Desktop.getDesktop();
        }
        String time= PApplet.year()+"."+PApplet.month()+"."+PApplet.day()+"_"+PApplet.hour()+"."+PApplet.minute()+"."+PApplet.second();
        String path = "Captchas_output_in_"+time;
        File outputFile = new File(path);
        boolean success = outputFile.mkdir();
        String tempWord;
        String eol = System.getProperty( "line.separator" );
        try
        {

             FileOutputStream fostream = new FileOutputStream(path+"/"+"Amount_"+amount+"_Time_"+time+".TXT");
             OutputStreamWriter out = new OutputStreamWriter(fostream, "UTF-8");
             FileOutputStream fostream2 = new FileOutputStream(path+"/"+"Amount_"+amount+"_Time_"+time+"_SQL.TXT");
             OutputStreamWriter out2 = new OutputStreamWriter(fostream2, "UTF-8");


             int i ;
              for(i = 0 ; i<amount;i++)
              {

                tempWord = wordGen(numOfLetters, 1, false);
                Captcha temp = new Captcha( papplet,centerX,centerY,captchaWidth,captchaHeight,fontGlobal,tempWord ,filterFlags, toleranceArgs);
                System.out.println(tempWord);
                try
                {
                  ImageIO.write((BufferedImage)temp.distortedImg.getImage(),type,new File(path+"/"+prefix+i+"."+type));
                  out.write(prefix+i+"."+type+","+tempWord+eol);
                  out2.write("INSERT INTO tableName VALUES ('" +prefix+ i+"."+type+"', '"+tempWord +"');"+eol);
                } catch (Exception e)
                {System.out.println("wrong file output, probelm in saving permisions");}

              }
              out.close();
              out2.close();
              if(desktop != null)
              {
                  desktop.open(outputFile);
              }
        }
        catch (IOException e)
        {
            System.out.println("cant creat text file");
        }


    }
    
    ///------------------------------------------------------------------
    public BufferedImage generateArabicImage( int wid,int heig,Font fontG,String text)// dont forget to flush
    {
        //img = ImageIO.read(new File("strawberry.jpg"));
//        BufferedImage img = new BufferedImage(wid, heig, BufferedImage.TYPE_INT_RGB);// chnage rgp to rgpa
        //===
        GraphicsEnvironment environment =GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device =environment.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        BufferedImage img =config.createCompatibleImage(wid, heig,Transparency.BITMASK);
        //=====
        Graphics2D g2 = img.createGraphics();
        //g2.getRenderingHints().KEY_ANTIALIASING.
        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(renderHints);
        g2.setPaint( Color.WHITE);
        g2.fillRect(0, 0, wid, heig);
        g2.setComposite(AlphaComposite.Src);
        g2.setFont(fontG);
        g2.setPaint(Color.BLACK );
        FontMetrics fm = g2.getFontMetrics();
        int twidth = fm.stringWidth(text);
        int theight = fm.getHeight();
        wordHeieght = theight ;
        wordWidth =  twidth;
        //g2.drawString(text, (int)(wid/2-twidth/2), (int)(((heig - theight)/ 2)+ fm.getAscent())); //centered text
        g2.drawString(text, (int)(wid/2-twidth/2), (int)(((heig - theight)/ 2)+ fm.getAscent()));
//        try
//        {
//          ImageIO.write(img,"PNG",new File("strawberry2.png"));
//        } catch (Exception e) {System.out.println("wrong file");}
        //g2.drawImage(img, 20, 0, null);
        //g.drawImage(img, 0, 0, null);
        //g.drawString("ناصر", 50, 50);
        return img;
    }
    
    ///------------------------------------------------------------------
    public void applyFilters()
    {
        if(filterFlags[0]==true)
        {
            OutlineNoiseFilter.apply(papplet, this , toleranceArgs[0]);
        }
        if(filterFlags[1]==true)
        {
            TwirlFilter tf = new TwirlFilter();
            tf.setAngle(papplet.random(-2*toleranceArgs[1], 2*toleranceArgs[1]));
            tf.setCentreX(papplet.random(0,1f));
            //tf.setCentreY(papplet.random(0, 1f));
            tf.setRadius(0);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist = tf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);

        }
        if(filterFlags[2] == true)
        {
            RippleFilter rf = new RippleFilter(); // do it compared to font size
            rf.setWaveType(RippleFilter.SINE);
            rf.setXAmplitude((captchaFontSize+papplet.random(0,4f))*toleranceArgs[2]);
            rf.setXWavelength((captchaFontSize+papplet.random(0,17f))*toleranceArgs[2]);
            rf.setYAmplitude((captchaFontSize+papplet.random(0,17f))*toleranceArgs[2]);
            rf.setYWavelength((captchaFontSize+papplet.random(0,4f))*toleranceArgs[2]);

            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= rf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[3] == true)
        {
            PinchFilter pf = new PinchFilter();
            pf.setAngle(0.5f);
            pf.setRadius(captchaWidth);
            pf.setAmount(toleranceArgs[3]);
            pf.setCentreX(papplet.random(0, 1f));
            pf.setCentreY(0.5f);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= pf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[4] == true)
        {
            MarbleFilter mf = new MarbleFilter();
            mf.setTurbulence(toleranceArgs[4]);
            mf.setXScale(5);
            mf.setYScale(5);
            //mf.setAmount(15);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= mf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[5] == true)
        {
//            GaussianFilter gf = new GaussianFilter(5);
//            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
//            dist= gf.filter((BufferedImage)(distortedImg.getImage()), dist);
//            distortedImg = new PImage(dist);
            distortedImg.filter(PApplet.BLUR,toleranceArgs[5]);
            //distortedImg = new PImage(distortedImg.getImage());
        }
        if(filterFlags[6] == true)
        {
//            SphereFilter sf = new SphereFilter();
//            sf.setRefractionIndex(1.1f);
//            sf.setRadius(captchaWidth/2);
//            sf.setCentreX(papplet.random(0, 1));
//            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
//            dist= sf.filter((BufferedImage)(distortedImg.getImage()), dist);
//            distortedImg = new PImage(dist);
            distortedImg.filter(PApplet.THRESHOLD,toleranceArgs[6]);

        }
        if(filterFlags[7] == true)
        {
            CrystallizeFilter cf = new CrystallizeFilter();
            cf.setEdgeThickness(0);
            cf.setScale(toleranceArgs[7]);
            cf.setFadeEdges(true);
            cf.setEdgeColor(0);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= cf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[8] == true)
        {
            ColorHalftoneFilter chtf = new ColorHalftoneFilter();
            chtf.setdotRadius(toleranceArgs[8]);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= chtf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[9] == true)
        {
            BlockFilter bf = new BlockFilter();
            bf.setBlockSize((int)toleranceArgs[9]);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= bf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
        if(filterFlags[10] == true)
        {
            PointillizeFilter pf = new PointillizeFilter();
            pf.setScale(toleranceArgs[10]);
            pf.setFuzziness(0.9f);
            BufferedImage dist = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_INT_RGB);
            dist= pf.filter((BufferedImage)(distortedImg.getImage()), dist);
            distortedImg = new PImage(dist);
        }
    }





    public static String wordGenOldOne(int wordSize, int maxTamdeed, boolean UseDigits)
    {
        // the generated(target) word
        String word = "";

        Random x = new Random();

        //**********************Start of Letters Groups*************************
        //******Start of Groups do not accept tamdeed ***********
        //Group 1
        char [] g1 = {'أ','ا'};
        char [] g2 = {'د','ذ'};
        char [] g3 = {'ر','ز','ؤ','و'};
        char [] g4 = {'ء'};
        //Group 16  (numbers) Not always used
        char [] g16 = {'1','2','3','4','5','6','7','8','9','0'};
        //******End of Groups do not accept tamdeed ***********

        //******Start of Groups accept tamdeed ***********

        char [] g5 = {'ب','ت','ث','ن', 'ي'};
        char [] g6 = {'ج','ح','خ'};
        char [] g7 = {'س','ش'};
        char [] g8 = {'ص','ض'};
        char [] g9 = {'ط','ظ'};
        char [] g10 = {'ع','غ'};
        char [] g11 = {'ف','ق'};
        char [] g12 = {'ك'};
        char [] g13 = {'ل'};
        char [] g14 = {'م'};
        char [] g15 = {'ه'};
        //******End of Groups do not accept tamdeed ***********


        //Sepcial character تمديد
        char tamdeed = 'ـ';
        //**********************END of Letters Groups**************************

        //array bigOne contains all possible characters, used heavily for randomization
        char [][] bigOne;
        if (UseDigits = true)
        {
            bigOne = new char[][] {g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16};
        }
        else
        {
             bigOne = new char[][] {g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15};
        }

        int prevGroupNumber = -1;
        int tamdeedCount = 0;

        String appendedStr = null;
        //one loop to generate every character
        for (int i=0; i<wordSize; i++)
        {
            int groupNumber = x.nextInt(bigOne.length);
            while (prevGroupNumber == groupNumber){
                groupNumber = x.nextInt(bigOne.length);
            }
            // System.out.println("g: " + groupNumber);
            int charNumber = x.nextInt(bigOne[groupNumber].length);
            //System.out.println("c: " + charNumber);
            //System.out.println("the letter is: " + bigOne[groupNumber][charNumber]);
            appendedStr = Character.toString(bigOne[groupNumber][charNumber]);
            word = word + appendedStr;
            prevGroupNumber = groupNumber;

            // randomly add tamdeed, except after the last character
            if (tamdeedCount < maxTamdeed && i != wordSize-1){
                if (x.nextBoolean() && !"اأدذرزوؤء0123456789".contains(appendedStr)){
                     word = word + tamdeed;
                     tamdeedCount++;
                }
            }
        }

        return word;
    }



    /*Random word generator
     *  Inupt:  (1) required generated word size
     *          (2) maximum number of tamdeeds "ـ"
     *          (3) option to use digits in words (true/false)
     *  Output: (1) The generated word.
     */
    public static String wordGen(int wordSize, int maxTamdeed, boolean UseDigits)
    {
        // the generated(target) word
        String word = "";

        Random x = new Random();

        //**********************Start of Letters Groups*************************
        //******Start of Groups do not accept tamdeed ***********
        //Group 1
        char [] g1 = {'أ','ا'};
        char [] g2 = {'د','ذ'};
        char [] g3 = {'ر','ز','ؤ','و'};
        //char [] g4 = {'ء'}; //not used since it seemingly separtes the word
        //Group 16  (numbers) Not always used
        char [] g16 = {'1','2','3','4','5','6','7','8','9','0'};
        //******End of Groups do not accept tamdeed ***********

        //******Start of Groups that accept tamdeed ***********
        char [] g5 = {'ب','ت','ث','ن', 'ي'};
        char [] g6 = {'ج','ح','خ'};
        char [] g7 = {'س','ش'};
        char [] g8 = {'ص','ض'};
        char [] g9 = {'ط','ظ'};
        char [] g10 = {'ع','غ'};
        char [] g11 = {'ف','ق'};
        char [] g12 = {'ك'};
        char [] g13 = {'ل'};
        char [] g14 = {'م'};
        char [] g15 = {'ه'};
        //******End of Groups that accept tamdeed ***********


        //Sepcial character تمديد
        char tamdeed = 'ـ';
        //**********************END of Letters Groups**************************

        //array bigOne contains all possible characters, used heavily for randomization
        char [][] bigOne;
        if (UseDigits == true)
        {
            bigOne = new char[][] {g1,g2,g3,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16};
        }
        else
        {
             bigOne = new char[][] {g1,g2,g3,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15};
        }

        int prevGroupNumber = -1;
        int tamdeedCount = 0;

        String appendedStr = null;
        //one loop to generate every character
        for (int i=0; i<wordSize; i++)
        {
            int groupNumber = x.nextInt(bigOne.length);
            while (prevGroupNumber == groupNumber){
                groupNumber = x.nextInt(bigOne.length);
            }
            // System.out.println("g: " + groupNumber);
            int charNumber = x.nextInt(bigOne[groupNumber].length);
            //System.out.println("c: " + charNumber);
            //System.out.println("the letter is: " + bigOne[groupNumber][charNumber]);
            appendedStr = Character.toString(bigOne[groupNumber][charNumber]);
            word = word + appendedStr;
            prevGroupNumber = groupNumber;

            // randomly add tamdeed, except after the last character
            if (tamdeedCount < maxTamdeed && i != wordSize-1){
                if (x.nextBoolean() && !"اأدذرزوؤء0123456789".contains(appendedStr)){
                    Random y = new Random();
                    double probabilty = y.nextDouble();
                    while(true){
                        if ( probabilty > 0.6 && tamdeedCount < maxTamdeed ){
                            probabilty = probabilty *y.nextDouble();
                            word = word + tamdeed;
                            tamdeedCount++;
                        }else
                        {
                            break;
                        }
                    }
                }
            }
        }

        //******** Checking for Offensive words******************
        //*** if the word is offensive, anthor one is created ***

        //first remove tamdeed
        String word2 = word.replace("ـ", "");


        //list of bad words
        String[] offensiveWordList = {"أير", "نيك", "لعن", "ملعون", "زب", "خنث", "خنيث", "كس", "كوس","بظر",
            "كلب", "حمار","قحب", "خرا", "خرى", "زق", "طيز", "مكوة", "مكوى", "خول", "ناك", "شرموط", "شراميط",
            "شاذ", "مص", "لحس", "خصي", "خصى", "نياك", "حيوان", "خنزير", "حرام", "قواد", "لوط", "بز", "عرس",
            "زنى", "زاني", "زنوة", "زنوه", "عكروت", "ماجن", "مجون", "عري", "عاري", "حر", "زغب", "زغوب", "تفو",
            "جحش", "جرار", "داعر", "دعارة", "ضرط", "ضراط", "فسو", "فسى", "فسي", "طقع", "نغل", "ديود", "ديد",
            "غبي", "عبد", "عير","حمق"};

        //simple flag
        boolean isOffensive = false;

        //if the word is offensive, recursivley call the method
        for (int k=0; k<offensiveWordList.length; k++)
        {
             if (word2.contains(offensiveWordList[k])){
                isOffensive = true;
            }
        }
        if (isOffensive){
            word = wordGen(wordSize, maxTamdeed, UseDigits);
            //System.out.println(word2 + "   offensive word!!  will try again.");
        }

        return word;
    }



}


//////////////////////////////////////////////////////////////

